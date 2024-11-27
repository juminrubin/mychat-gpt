package org.jrtech.ai.mychatgpt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt {}
            }
        return http.build()
    }
}