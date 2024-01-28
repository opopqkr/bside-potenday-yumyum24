package best.bside.potenday.yumyum24.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI api() {
        Info info = new Info()
                .title("Convenience store honey combination API")
                .version("1.0")
                // .license(new License()
                //         .name("NonCommercial")
                //         .url("http://yumyum24.com"))
                .description("Convenience store honey combination recommendation service.");

        return new OpenAPI().info(info);
    }
}
