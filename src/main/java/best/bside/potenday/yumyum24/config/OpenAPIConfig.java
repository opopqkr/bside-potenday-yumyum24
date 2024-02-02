package best.bside.potenday.yumyum24.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI api() {
        Info info = new Info()
                .title("Convenience store honey combination API")
                .version("1.0")
                .description("Convenience store honey combination recommendation service.")
                .license(new License()
                        .name("NonCommercial")
                        .url("http://api-yumyum24.com"));

        final String securitySchemeName = "Authorization";
        final Components components = new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .components(components)
                .security(Collections
                        .singletonList(new SecurityRequirement().addList(securitySchemeName)))
                .info(info);
    }
}
