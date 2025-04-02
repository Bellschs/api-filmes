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

@Path("/diretores")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Diretores", description = "Todos os Diretores dos Filmes")
public class DiretorResource {

    @GET
    @Operation(summary = "Listar todos os diretores", description = "Retorna uma lista com todos os diretores cadastrados.")
    public List<Diretor> listarTodos() {
        return Diretor.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar diretor por ID", description = "Retorna os dados de um diretor pelo ID.", operationId = "getDiretorById")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Diretor encontrado", content = @Content(schema = @Schema(implementation = Diretor.class))),
            @APIResponse(responseCode = "404", description = "Diretor não encontrado")
    })
    public Diretor buscarPorId(
            @Parameter(description = "ID do diretor", required = true)
            @PathParam("id") Long id) {
        Diretor diretor = Diretor.findById(id);
        if (diretor == null) {
            throw new NotFoundException("Diretor não encontrado: " + id);
        }
        return diretor;
    }

    @POST
    @Transactional
    @Operation(summary = "Criar novo diretor", description = "Cadastra um novo diretor no sistema.")
    @APIResponse(responseCode = "201", description = "Diretor criado com sucesso")
    public Response criar(Diretor diretor) {
        diretor.persist();
        return Response.status(Response.Status.CREATED).entity(diretor).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar diretor", description = "Atualiza os dados de um diretor existente.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Diretor atualizado com sucesso"),
            @APIResponse(responseCode = "404", description = "Diretor não encontrado")
    })
    public Diretor atualizar(
            @Parameter(description = "ID do diretor", required = true)
            @PathParam("id") Long id, Diretor diretor) {
        Diretor entidade = Diretor.findById(id);
        if (entidade == null) {
            throw new NotFoundException("Diretor não encontrado: " + id);
        }

        entidade.nome = diretor.nome;
        return entidade;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Excluir diretor", description = "Remove um diretor pelo ID.")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Diretor removido com sucesso"),
            @APIResponse(responseCode = "404", description = "Diretor não encontrado")
    })
    public Response excluir(
            @Parameter(description = "ID do diretor", required = true)
            @PathParam("id") Long id) {
        boolean excluido = Diretor.deleteById(id);
        if (excluido) {
            return Response.noContent().build();
        }
        throw new NotFoundException("Diretor não encontrado: " + id);
    }
}
