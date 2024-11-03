package com.virtukch.security.auth.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtukch.security.auth.model.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 사용자 인증을 처리하기 위한 커스텀 필터입니다.
 * 이 필터는 사용자 로그인 요청을 가로채어 인증을 수행합니다.
 *
 * @author Virtus_Chae
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    /**
     * 지정된 URL 요청을 가로채어 검증 로직을 수행하는 메서드입니다.
     *
     * @param request HTTP 요청 객체, 인증에 필요한 매개변수를 추출합니다.
     * @param response HTTP 응답 객체, 필요시 리다이렉션을 수행할 수 있습니다.
     * @return 인증된 사용자 정보
     * @throws AuthenticationException 인증 과정에서 발생한 예외
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;

        try {
            authRequest = getAuthRequest(request);
            setDetails(request, authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 사용자의 로그인 요청 시 요청 정보를 임시 인증 토큰에 저장하는 메서드입니다.
     *
     * @param request HTTP 요청 객체
     * @return UsernamePasswordAuthenticationToken 인증 요청에 사용될 토큰
     * @throws IOException 요청 본문을 읽는 과정에서 발생할 수 있는 예외
     */
    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        LoginDto user = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        return new UsernamePasswordAuthenticationToken(user.getId(), user.getPass());
    }
}