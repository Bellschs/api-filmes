package org.acme.v1;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.Diretor;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/v1/diretores")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Diretores - v1", description = "Versão 1 da API de diretores de filmes")
public class DiretorResourceV1 {

    @GET
    @RateLimit(value = 5, window = 10)
    @Fallback(fallbackMethod = "rateLimitFallback")
    @Operation(summary = "Listar todos os diretores", description = "Retorna uma lista com todos os diretores cadastrados.")
    public List<Diretor> listarTodos() {
        return Diretor.listAll();
    }

    public List<Diretor> rateLimitFallback() {
        throw new WebApplicationException("Muitas requisições. Tente novamente mais tarde.", 429);
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
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Diretor criado com sucesso"),
            @APIResponse(responseCode = "409", description = "Diretor já existe")
    })
    public Response criar(Diretor diretor) {
        boolean jaExiste = Diretor.find("nome", diretor.nome).firstResultOptional().isPresent();
        if (jaExiste) {
            return Response.status(409).entity("Diretor com esse nome já existe").build();
        }

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
