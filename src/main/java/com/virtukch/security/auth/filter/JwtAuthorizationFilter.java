package com.virtukch.security.auth.filter;

import com.virtukch.security.auth.model.DetailsUser;
import com.virtukch.security.common.AuthConstants;
import com.virtukch.security.common.VirtusRole;
import com.virtukch.security.common.utils.TokenUtils;
import com.virtukch.security.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * JWT (Json Web Token) 기반 인증을 처리하는 필터 클래스입니다.
 * 이 필터는 HTTP 요청에서 JWT 를 검증하고, 유효한 경우 인증 정보를 설정합니다.
 *
 * @author Virtus_Chae
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    /**
     * 생성자: JwtAuthorizationFilter 의 인스턴스를 생성합니다.
     *
     * @param authenticationManager 인증 매니저
     */
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 필터링 로직을 수행하는 메서드입니다.
     * HTTP 요청에서 JWT 를 검증하고, 인증 정보를 설정합니다.
     *
     * @param request  HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param chain    필터 체인
     * @throws IOException I/O 예외
     * @throws ServletException 서블릿 예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        /*
         * 권한이 필요 없는 리소스
         */
        List<String> roleLeessList = Arrays.asList(
            "/signup"
        );

        if (roleLeessList.contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        try {
            if (header != null && !header.equalsIgnoreCase("")) {
                String token = TokenUtils.splitHeader(header);

                if (TokenUtils.isValidToken(token)) {
                    Claims claims = TokenUtils.getClaimsFormToken(token);

                    DetailsUser authentication = new DetailsUser();
                    User user = new User();
                    user.setUserName(claims.get("userName").toString());
//                    user.setUserEmail(claims.get("userEmail").toString());
                    user.setRole(VirtusRole.valueOf(claims.get("Role").toString()));
                    authentication.setUser(user);

                    AbstractAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(
                        authentication, token, authentication.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(request, response);
                } else {
                    throw new RuntimeException("토큰이 유효하지 않습니다.");
                }
            } else {
                throw new RuntimeException("토큰이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            JSONObject jsonObject = jsonresponseWrapper(e);
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }

    /**
     * JWT 관련 예외 발생 시 JSON 형식으로 응답하는 메서드입니다.
     *
     * @param e 발생한 예외
     * @return 예외에 대한 정보를 포함하는 JSON 객체
     */
    private JSONObject jsonresponseWrapper(Exception e) {
        String resultMsg = "";
        if (e instanceof ExpiredJwtException) {
            resultMsg = "Token Expired";
        } else if (e instanceof SignatureException) {
            resultMsg = "TOKEN SignatureException Login";
        } else if (e instanceof JwtException) {
            resultMsg = "TOKEN Parsing JwtException";
        } else {
            resultMsg = "OTHER TOKEN ERROR";
        }

        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", 401);
        jsonMap.put("message", resultMsg);
        jsonMap.put("reason", e.getMessage());
        return new JSONObject(jsonMap);
    }
}