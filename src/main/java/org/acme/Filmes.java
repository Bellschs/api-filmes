package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Schema(description = "Representa um filme com título, ano, diretor e gênero")
public class Filmes extends PanacheEntity {

    @Schema(description = "Título do filme", example = "Matrix")
    public String titulo;

    @Schema(description = "Ano de lançamento do filme", example = "1999")
    public int anoLancamento;

    @ManyToOne
    @Schema(description = "Diretor do filme")
    public Diretor diretor;

    @ManyToOne
    @Schema(description = "Gênero do filme")
    public Genero genero;
}


//um gênero pode ter vários filmes many to one
//um diretor pode ter vários filmes many to one