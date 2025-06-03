package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Schema(description = "Representa um filme com título, ano, diretor e gênero")
public class Filmes extends PanacheEntity {

    @NotBlank(message = "O título é obrigatório")
    @Schema(description = "Título do filme", example = "Matrix", required = true)
    public String titulo;

    @Min(value = 1888, message = "Ano de lançamento deve ser válido")
    @Schema(description = "Ano de lançamento do filme", example = "1999", required = true)
    public int anoLancamento;

    @ManyToOne
    @NotNull(message = "O diretor é obrigatório")
    @Schema(description = "Diretor do filme", required = true)
    public Diretor diretor;

    @ManyToOne
    @NotNull(message = "O gênero é obrigatório")
    @Schema(description = "Gênero do filme", required = true)
    public Genero genero;
}

//um gênero pode ter vários filmes many to one
//um diretor pode ter vários filmes many to one