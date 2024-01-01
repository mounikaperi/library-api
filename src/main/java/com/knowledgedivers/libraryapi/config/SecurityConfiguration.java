package com.knowledgedivers.libraryapi.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Disable Cross Site Request Forgery
        http.csrf().disable();

        // Protect endpoints at /api/<type>/secure
        http.authorizeRequests(configurer ->
                        configurer
                                .antMatchers("/api/books/secure/**")
                                .authenticated())
                .oauth2ResourceServer()
                .jwt();

        // Add CORS filters
        http.cors();

        // Add content negotiation strategy
        /*
         * In the context of Spring MVC, content negotiation refers to the process of determining the media type (e.g., JSON, XML) that should be used for the response based on the client's preferences. Content negotiation is a crucial aspect of building RESTful APIs where clients might prefer different representations of the same resource.
         * The ContentNegotiationConfigurer in Spring MVC allows you to configure content negotiation strategies. The strategies determine how the server selects the appropriate media type for the response based on factors such as the client's Accept header.
         */
        http.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());

        // Force a non-empty response body for 401's to make the response friendly
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }

}








