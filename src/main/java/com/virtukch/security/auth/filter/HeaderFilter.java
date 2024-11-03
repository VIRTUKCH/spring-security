package com.virtukch.security.auth.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * CORS 설정을 위한 필터입니다.
 * 이 필터는 HTTP 응답 헤더에 CORS 관련 설정을 추가하여
 * 다른 도메인에서의 요청을 허용합니다.
 *
 * @author Virtus_Chae
 */
public class HeaderFilter implements Filter {

    /**
     * 요청과 응답을 필터링하는 과정에서 호출되는 메서드입니다. CORS 관련 헤더를 설정하고, 다음 필터로 요청과 응답을 전달합니다.
     *
     * @param request  처리할 요청 객체
     * @param response 처리할 응답 객체
     * @param chain    다음 필터 체인에 대한 접근을 제공하여 요청과 응답을 다음 필터로 전달합니다.
     * @throws IOException      I/O 예외
     * @throws ServletException 서블릿 예외
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;

        // CORS 헤더 설정
        res.setHeader("Access-Control-Allow-Origin", "*");  // 모든 출처에 대한 요청 허용
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");  // 허용할 HTTP 메서드
        res.setHeader("Access-Control-Max-Age", "3600"); // OPTIONS 요청에 대한 캐시 시간 (초)
        res.setHeader(
            "Access-Control-Allow-Headers",
            "X-Requested-With, Content-Type, Authorization, X-XSRF-token" // 허용할 요청 헤더
        );
        res.setHeader("Access-Control-Allow-Credentials", "false"); // 자격 증명 허용 여부

        // 다음 필터로 요청과 응답 전달
        chain.doFilter(request, response);
    }
}