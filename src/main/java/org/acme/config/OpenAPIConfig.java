package org.acme.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Filmes",
                version = "1.0.0",
                description = "API para gerenciamento de filmes, diretores e gêneros",
                contact = @Contact(
                        name = "Suporte da API",
                        url = "https://github.com/Bellschs/api-filmes",
                        email = "bellschs@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        tags = {
                @Tag(name = "Filmes", description = "Todos os Filmes adicionados"),
                @Tag(name = "Diretores", description = "Todos os Diretores dos Filmes"),
                @Tag(name = "Gêneros", description = "Todos os Gêneros dos Filmes")
        }
)
public class OpenAPIConfig extends Application {
    //
}
