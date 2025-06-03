package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Schema(description = "Representa um gênero de filme")
public class Genero extends PanacheEntity {

    @NotBlank(message = "O nome do gênero é obrigatório")
    @Schema(description = "Nome do gênero", example = "Ação", required = true)
    public String nome;
}
