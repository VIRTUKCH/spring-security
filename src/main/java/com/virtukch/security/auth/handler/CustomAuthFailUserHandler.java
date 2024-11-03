package com.virtukch.security.auth.handler;

import org.json.simple.JSONObject;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 사용자의 로그인 실패 시 실패 요청을 커스텀하기 위한 핸들러입니다.
 * 이 핸들러는 로그인 실패 시 적절한 오류 메시지를 클라이언트에 반환합니다.
 * <p>
 * [패키지 구조]
 * AuthenticationFailureHandler(interface) -&gt; SimpleUrlAuthenticationFailureHandler(class) -&gt; CustomAuthFailUserHandler
 *
 * @author Virtus_Chae
 */
public class CustomAuthFailUserHandler implements AuthenticationFailureHandler {

    /**
     * 인증 실패 시 호출되는 메서드입니다.
     * 로그인 시도가 실패하면 적절한 오류 메시지를 JSON 형식으로 클라이언트에 반환합니다.
     *
     * @param request 인증 시도가 발생한 HTTP 요청
     * @param response 인증 실패에 대한 응답
     * @param exception 인증 요청을 거부하는 데 사용된 예외
     * @throws IOException I/O 예외
     * @throws ServletException 서블릿 예외
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        JSONObject jsonObject;
        String failMsg;

        // 인증 예외에 따라 적절한 메시지를 설정합니다.
        if (exception instanceof AuthenticationServiceException) {
            // 사용자의 로그인 또는 인증 처리 과정에서 문제가 발생한 경우
            failMsg = "존재하지 않는 사용자입니다.";
        } else if (exception instanceof BadCredentialsException) {
            // 사용자의 아이디가 DB에 존재하지 않거나 비밀번호가 틀린 경우
            failMsg = "아이디 또는 비밀번호가 틀립니다.";
        } else if (exception instanceof LockedException) {
            // 계정이 잠긴 경우
            failMsg ="잠긴 계정입니다.";
        } else if (exception instanceof DisabledException) {
            // 비활성화된 계정인 경우
            failMsg ="비활성화된 계정입니다.";
        } else if (exception instanceof AccountExpiredException) {
            // 계정이 만료된 경우
            failMsg ="만료된 계정입니다.";
        } else if (exception instanceof CredentialsExpiredException) {
            // 자격 증명이 만료된 경우
            failMsg = "자격증명이 만료되었습니다.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            // 인증 객체가 존재하지 않거나 인증 정보가 없는 경우
            failMsg = "인증 요청이 거부되었습니다.";
        } else if (exception instanceof UsernameNotFoundException) {
            // DB에 사용자의 정보가 없는 경우
            failMsg = "존재하지 않는 이메일입니다.";
        } else {
            // 정의된 케이스가 아닌 경우
            failMsg = "정의되지 않은 오류입니다.";
        }

        // 응답 설정
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("failType", failMsg);

        jsonObject = new JSONObject(resultMap);

        printWriter.println(jsonObject);
        printWriter.flush();
        printWriter.close();
    }
}