package com.virtukch.security.auth.interceptor;

import com.virtukch.security.common.AuthConstants;
import com.virtukch.security.common.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.rmi.RemoteException;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 토큰을 검증하기 위한 인터셉터 클래스입니다.
 * 이 인터셉터는 요청 처리 전에 JWT 토큰의 유효성을 검사하여 인증된 요청만을 허용합니다.
 *
 * @author Virtus_Chae
 */
public class JwtTokenInterceptor implements HandlerInterceptor {

    /**
     * 요청을 처리하기 전에 호출되는 메서드입니다.
     * JWT 토큰의 유효성을 검사하고, 유효한 경우 요청을 계속 진행합니다.
     *
     * @param request  현재 HTTP 요청 객체
     * @param response 현재 HTTP 응답 객체
     * @param handler  실행할 핸들러, 타입 및 인스턴스 평가를 위해 사용
     * @return 요청을 계속 진행할 경우 true, 그렇지 않으면 false
     * @throws Exception 토큰 유효성 검사 중 발생한 예외
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader(AuthConstants.AUTH_HEADER);
        String token = TokenUtils.splitHeader(header);

        if (token != null) {
            if (TokenUtils.isValidToken(token)) {
                return true; // 토큰이 유효할 경우 요청을 ₩계속 진행
            } else {
                throw new RemoteException("token 이 만료되었습니다."); // 토큰이 만료된 경우 예외 발생
            }
        } else {
            throw new RemoteException("token 정보가 없습니다."); // 토큰 정보가 없는 경우 예외 발생
        }
    }
}
