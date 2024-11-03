package com.virtukch.security.auth.handler;

import com.virtukch.security.auth.model.DetailsUser;
import com.virtukch.security.auth.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 토큰을 받고 해석하여 사용자 인증을 처리하는 클래스입니다.
 * 이 클래스는 사용자 ID와 비밀번호를 검증하여 인증을 수행합니다.
 *
 * @author Virtus_Chae
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private DetailsService detailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 주어진 인증 요청을 처리하여 인증을 수행합니다.
     *
     * @param authentication 인증 요청 객체
     * @return 인증된 사용자 정보
     * @throws AuthenticationException 인증 과정에서 발생한 예외
     */
    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        UsernamePasswordAuthenticationToken loginToken = (UsernamePasswordAuthenticationToken) authentication;

        String id = loginToken.getName();
        String pass = (String) loginToken.getCredentials();

        DetailsUser detailsUser = (DetailsUser) detailsService.loadUserByUsername(id);

        if (!passwordEncoder.matches(pass, detailsUser.getPassword())) {
            throw new BadCredentialsException(pass + "는 비밀번호가 아닙니다."); // 비밀번호 불일치 시 예외 발생
        }
        return new UsernamePasswordAuthenticationToken(detailsUser, pass,
            detailsUser.getAuthorities());
    }

    /**
     * 주어진 인증 클래스를 지원하는지 여부를 반환합니다.
     *
     * @param authentication 인증 클래스
     * @return 해당 인증 클래스를 지원하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
