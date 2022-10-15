package com.alkemy.Disney.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE,"/api/character/delete","/api/character/delete").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/character/edit","/api/movieSerie/edit").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/character/created","/api/movieSerie/created").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/auth/login").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/api/movies","/api/movieSerie/detail","/api/characters","/api/character/detail").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/rest/**","/h2-console","/api/**").hasAuthority("ADMIN");

        http.formLogin()

                .usernameParameter("email")
                .passwordParameter("pwd")
                .loginPage("/auth/login");


        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }
}
