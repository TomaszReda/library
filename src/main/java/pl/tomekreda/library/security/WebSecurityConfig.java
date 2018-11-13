package pl.tomekreda.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Configuration
    class JdbcSessionConfig {

        @Bean
        public HttpSessionIdResolver httpSessionStrategy() {
            return HeaderHttpSessionIdResolver.xAuthToken();
        }

    }

    @Bean
    public UserDetailsService customUserDetailsService() {
        return new UserLoginService();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/tokenValid").fullyAuthenticated()
                .antMatchers("/api/user/change/password").fullyAuthenticated()
                .antMatchers("/api/logout/**").permitAll()
                .antMatchers("/api/login/**").permitAll()
                .antMatchers(HttpMethod.GET,"/**").permitAll()
                .antMatchers(HttpMethod.POST,"/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .requestCache()
                .requestCache(new NullRequestCache())
                .and();


        http.cors();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration().applyPermitDefaultValues();
        corsConfig.addAllowedMethod(HttpMethod.PUT);
        corsConfig.addAllowedMethod(HttpMethod.DELETE);
        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.POST);
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

}
