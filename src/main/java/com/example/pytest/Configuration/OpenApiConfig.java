package com.example.pytest.Configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI springDocOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title("Puyan API")
                        .description("Puyan接口文檔説明")
                        .version("v0.0.1-SNAPSHOT"));
                // 配置Authorizations
//                .components(new Components().addSecuritySchemes("bearer-key",
//                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")));
    }
//    @Bean
//    public GroupedOpenApi siteApi() {
//        return GroupedOpenApi.builder()
//                .group("demo接口")
//                .pathsToMatch("/demo/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("sys接口")
//                .pathsToMatch("/sys/**")
//                .build();
//    }

}
