package com.wayne.javaexercise.security;

import com.wayne.javaexercise.config.AppConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.net.URL;
import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers(headers -> headers
                        .frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                        .xssProtection(
                                xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                        )
                        .contentSecurityPolicy(
                                cps -> cps.policyDirectives("script-src 'self' 'unsafe-inline' 'unsafe-eval' https://cdn.jsdelivr.net; object-src 'self'")
                        )
                )
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/api/**")
                )
                .cors(withDefaults());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(getCurrentHost()));
        configuration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers","Access-Control-Allow-Headers"));
//        configuration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept",
//                "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Access-Control-Allow-Credentials"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private String getCurrentHost(){
        String urlString = AppConfig.appUrl;

        try {
            URL url = new URL(urlString);
            String baseUrl = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort();

            return baseUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return urlString;
    }
}
