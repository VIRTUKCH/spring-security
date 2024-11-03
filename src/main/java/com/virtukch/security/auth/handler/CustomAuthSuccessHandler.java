package com.virtukch.security.auth.handler;

import com.virtukch.security.auth.model.DetailsUser;
import com.virtukch.security.common.AuthConstants;
import com.virtukch.security.common.utils.ConvertUtil;
import com.virtukch.security.common.utils.TokenUtils;
import com.virtukch.security.user.entity.User;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 사용자 인증 성공 시 처리하는 커스텀 핸들러입니다. 이 핸들러는 인증이 성공했을 때 사용자 정보를 JSON 형식으로 응답합니다.
 */
@Configuration
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * 인증 성공 시 호출되는 메서드입니다.
     *
     * @param request        인증 성공을 유발한 HTTP 요청
     * @param response       인증 성공에 대한 응답
     * @param authentication 인증 과정에서 생성된 <tt>Authentication</tt> 객체
     * @throws ServletException 서블릿 예외
     * @throws IOException      I/O 예외
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        User user = ((DetailsUser) authentication.getPrincipal()).getUser();
        JSONObject jsonValue = (JSONObject) ConvertUtil.convertObjectToJsonObject(user);
        HashMap<String, Object> responseMap = new HashMap<>();

        JSONObject jsonObject;
        if (user.getState().equals("N")) {
            // 휴먼 상태인 계정인 경우
            responseMap.put("userInfo", jsonValue);
            responseMap.put("message", "휴먼상태인 계정입니다.");
        } else {
            // 정상적인 로그인 처리
            String token = TokenUtils.generateJwtToken(user);
            responseMap.put("userInfo", jsonValue);
            responseMap.put("message", "로그인 성공");

            // JWT 토큰을 응답 헤더에 추가
            response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);
        }

        jsonObject = new JSONObject(responseMap);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonObject);
        printWriter.flush();
        printWriter.close();
    }
}