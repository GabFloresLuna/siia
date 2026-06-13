package cl.duocuc.siia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("SIIA - API de Automatización Aduanera")
                .version("1.0")
                .description("Sistema Integral de Interoperabilidad Aduanera para el Paso Los Libertadores. Permite registrar pasajeros, gestionar trámites vehiculares y consultar estado de folios.")
                .contact(new Contact()
                    .name("Antonio Cayulen & Gabriel Flores")
                    .email("soporte@aduana.cl")
                    .url("https://www.aduana.cl"))
                .license(new License()
                    .name("MIT")
                    .url("https://opensource.org/licenses/MIT")));
    }
}