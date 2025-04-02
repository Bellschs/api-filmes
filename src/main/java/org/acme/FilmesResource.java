package org.acme;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/filmes")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Filmes", description = "Todos os Filmes adicionados")
public class FilmesResource {

    @GET
    @Operation(
        summary = "Listar todos os filmes.",
        description = "Listar todos os filmes cadastrados"
    )
    public List<Filmes> listarTodos() {
        return Filmes.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar filme por ID", description = "Retorna os dados de um filme pelo ID.", operationId = "getFilmeById")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Filme encontrado", content = @Content(schema = @Schema(implementation = Filmes.class))),
            @APIResponse(responseCode = "404", description = "Filme não encontrado")
    })
    public Filmes buscarPorId(
            @Parameter(description = "ID do filme", required = true)
            @PathParam("id") Long id) {
        Filmes filme = Filmes.findById(id);
        if (filme == null) {
            throw new NotFoundException("Filme não encontrado: " + id);
        }
        return filme;
    }

    @POST
    @Transactional
    @Operation(summary = "Adiciona um novo filme", description = "Cadastra um novo filme no sistema.")
    @APIResponse(responseCode = "201", description = "Filme criado com sucesso")
    public Response criar(Filmes filme) {
        filme.persist();
        return Response.status(Response.Status.CREATED).entity(filme).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar filme", description = "Atualização de filme existente.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Filme atualizado com sucesso"),
            @APIResponse(responseCode = "404", description = "Filme não encontrado")
    })
    public Filmes atualizar(
            @Parameter(description = "ID do filme", required = true)
            @PathParam("id") Long id, Filmes filme) {
        Filmes entidade = Filmes.findById(id);
        if (entidade == null) {
            throw new NotFoundException("Filme não encontrado: " + id);
        }

        entidade.titulo = filme.titulo;
        entidade.anoLancamento = filme.anoLancamento;
        entidade.diretor = filme.diretor;
        entidade.genero = filme.genero;

        return entidade;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Excluir filme", description = "Remove um filme pelo ID.")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Filme removido com sucesso"),
            @APIResponse(responseCode = "404", description = "Filme não encontrado")
    })
    public Response excluir(
            @Parameter(description = "ID do filme", required = true)
            @PathParam("id") Long id) {
        boolean excluido = Filmes.deleteById(id);
        if (excluido) {
            return Response.noContent().build();
        }
        throw new NotFoundException("ID do Filme não localizado: " + id);
    }
}