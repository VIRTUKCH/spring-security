package com.virtukch.security.auth.config;

import com.virtukch.security.auth.filter.HeaderFilter;
import com.virtukch.security.auth.interceptor.JwtTokenInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 관련 설정을 위한 구성 클래스입니다. 정적 자원 처리 및 보안 필터 등록을 포함합니다.
 * 커스텀 필터를 설정하고, 그에 맞는 경로를 설정합니다.
 *
 * @author Virtus_Chae
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // 정적 자원을 제공하기 위한 경로
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
        "classpath:/static/",
        "classpath:/public/",
        "classpath:/",
        "classpath:/resources/",
        "classpath:/META-INF/resources/",
        "classpath:/META-INF/resources/webjars/"
    };

    /**
     * 정적 자원을 제공하기 위한 리소스 핸들러를 추가합니다.
     *
     * @param registry 리소스 핸들러 레지스트리
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    /**
     * HeaderFilter 를 등록하고, 지정된 URL 패턴 및 우선순위를 설정합니다.
     *
     * @return HeaderFilter 가 설정된 FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<HeaderFilter> getFilterRegistrationBean(){
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>(createHeaderFilter());
        registrationBean.setOrder(Integer.MIN_VALUE); // 필터 우선순위를 최하로 설정
        registrationBean.addUrlPatterns("/*"); // 모든 URL 패턴에 필터 적용
        return registrationBean;
    }

    /**
     * HeaderFilter 의 새 인스턴스를 생성하고 반환합니다.
     *
     * @return 새 HeaderFilter 인스턴스
     */
    @Bean
    public HeaderFilter createHeaderFilter(){
        return new HeaderFilter();
    }

    /**
     * JwtTokenInterceptor 의 새 인스턴스를 생성하고 반환합니다.
     *
     * @return 새 JwtTokenInterceptor 인스턴스
     */
    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor(){
        return new JwtTokenInterceptor();
    }
}