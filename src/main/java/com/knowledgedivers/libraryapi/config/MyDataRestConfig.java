package com.knowledgedivers.libraryapi.config;

import com.knowledgedivers.libraryapi.entity.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private String theAllowedOrigins = "http://localhost:3000";
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {
                HttpMethod.POST,
                HttpMethod.PATCH,
                HttpMethod.DELETE,
                HttpMethod.PUT,
        };
        config.exposeIdsFor(Book.class);
        disableHttpMethods(Book.class, config, theUnsupportedActions);
//        cors.addMapping(config.getBasePath() + "/**")
//                        .allowedOrigins(theAllowedOrigins)
//                        .allowedMethods("GET")
//                        .allowedHeaders("*");
        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(theAllowedOrigins).allowedHeaders("*");

    }
    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));
        /**
         * disableHttpMethods is a private method with the purpose of disabling certain Http methods for a given class in the context of Spring Data Rest
         * 'Class theClass' parameter represents the domain type (entity class) for which the Http methods need to be disabled
         * 'RepositoryRestConfiguration config' parameter provides access to the configuration settings for Spring Data REST
         * 'HttpMethod[] theUnsupportedActions' An array of Http methods that should be disabled for the specified domain type
         * 'config.getExposureConfiguration()' This gets the exposure configuration from the provided 'RepositoryRestConfiguration'
         * '.forDomainType(theClass)' Specifies that the configuration is to be applied to the given domain type(entity class)
         * '.withItemExposure(...) This method is used to configure how individual instances of the domain type should be exposed. It takes a lambda expression with two parameters: metadata and httpMethods.
         * (metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions): This lambda expression is responsible for disabling the specified HTTP methods (theUnsupportedActions) for individual items.
         * metadata: Provides metadata information about the domain type.
         * httpMethods: Represents the supported HTTP methods for the specified domain type. The disable method is called on this object to disable the specified HTTP methods.
         * .withCollectionExposure(...): Similar to .withItemExposure(...), this method is used to configure how collections of items should be exposed.
         * (metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions): Again, a lambda expression that disables the specified HTTP methods for collections.
         */
    }
}
