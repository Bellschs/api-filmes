package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Schema(description = "Representa um diretor de filmes")
class Diretor extends PanacheEntity {

    @Schema(description = "Nome completo do diretor", example = "Christopher Nolan", required = true)
    public String nome;
}