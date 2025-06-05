package org.acme;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.config.IdempotencyRecord;
import org.eclipse.microprofile.faulttolerance.Fallback;
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
    @Operation(summary = "Criar novo diretor com idempotência", description = "Cadastra um novo diretor no sistema se a chave de idempotência for única.")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Diretor criado com sucesso"),
            @APIResponse(responseCode = "409", description = "Requisição duplicada (mesma idempotency-key)"),
            @APIResponse(responseCode = "400", description = "Chave de idempotência ausente")
    })
    public Response criar(@HeaderParam("x-idempotency-key") String key, @Valid Diretor diretor) {
        if (key == null || key.isBlank()) {
            return Response.status(400).entity("Chave de idempotência (x-idempotency-key) é obrigatória").build();
        }

        boolean chaveUsada = IdempotencyRecord.find("keyRecord", key).firstResultOptional().isPresent();
        if (chaveUsada) {
            return Response.status(409).entity("Esta requisição já foi processada anteriormente").build();
        }

        boolean jaExiste = Diretor.find("nome", diretor.nome).firstResultOptional().isPresent();
        if (jaExiste) {
            return Response.status(409).entity("Diretor com esse nome já existe").build();
        }

        IdempotencyRecord record = new IdempotencyRecord();
        record.keyRecord = key;
        record.persist();

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
            @PathParam("id") Long id, @Valid Diretor diretor) {
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
