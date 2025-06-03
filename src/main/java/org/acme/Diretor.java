package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Entity
@Schema(description = "Representa um diretor de filmes")
public class Diretor extends PanacheEntity {

    @NotBlank(message = "O nome do diretor é obrigatório")
    @Schema(description = "Nome completo do diretor", example = "Christopher Nolan", required = true)
    public String nome;
}