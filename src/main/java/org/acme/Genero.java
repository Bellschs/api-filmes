package org.acme;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Schema(description = "Representa um gênero de filme")
class Genero extends PanacheEntity {

    @Schema(description = "Nome do gênero", example = "Ação", required = true)
    public String nome;
}
