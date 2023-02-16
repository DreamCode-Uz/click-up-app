package uz.pdp.clickupsecondpart.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class SpringOpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.swagger-ui.version}") String version) {
        return new OpenAPI()
                .info(new Info()
                        .title("Clickup Second Part API")
                        .description("This project is a project made for PDP.uz training center")
                        .version(version)
                        .termsOfService("https://smartbear.com/terms-of-use/")
                        .license(new License()
                                .name("MIT LICENSE")
                                .url("https://github.com/DreamCode-Uz/click-up-app/blob/main/LICENSE.md")
                                .identifier("MIT")
                        )
                        .contact(new Contact()
                                .name("DreamCode-Uz")
                                .url("https://github.com/DreamCode-Uz/")
                                .email("fullstack.dev.uz@gmail.com")
                        ).summary("This project is a project made for PDP.uz training center")
                );
    }
}

