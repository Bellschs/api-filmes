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

@Path("/genero")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Gêneros", description = "Todos os Gêneros dos Filmes")
public class GeneroResource {

    @GET
    @RateLimit(value = 5, window = 10)
    @Fallback(fallbackMethod = "rateLimitFallback")
    @Operation(summary = "Listar todos os gêneros", description = "Retorna uma lista com todos os gêneros cadastrados.")
    public List<Genero> listarTodos() {
        return Genero.listAll();
    }

    public List<Genero> rateLimitFallback() {
        throw new WebApplicationException("Muitas requisições. Tente novamente mais tarde.", 429);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar gênero por ID", description = "Retorna os dados de um gênero pelo ID.", operationId = "getGeneroById")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Gênero encontrado", content = @Content(schema = @Schema(implementation = Genero.class))),
            @APIResponse(responseCode = "404", description = "Gênero não encontrado")
    })
    public Genero buscarPorId(
            @Parameter(description = "ID do gênero", required = true)
            @PathParam("id") Long id) {
        Genero genero = Genero.findById(id);
        if (genero == null) {
            throw new NotFoundException("Gênero não encontrado: " + id);
        }
        return genero;
    }

    @POST
    @Transactional
    @Operation(summary = "Criar novo gênero com idempotência", description = "Cadastra um novo gênero no sistema se a chave de idempotência for única.")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Gênero criado com sucesso"),
            @APIResponse(responseCode = "409", description = "Requisição duplicada (mesma idempotency-key) ou gênero já existe"),
            @APIResponse(responseCode = "400", description = "Chave de idempotência ausente")
    })
    public Response criar(@HeaderParam("x-idempotency-key") String key, @Valid Genero genero) {
        if (key == null || key.isBlank()) {
            return Response.status(400).entity("Chave de idempotência (x-idempotency-key) é obrigatória").build();
        }

        boolean chaveUsada = IdempotencyRecord.find("keyRecord", key).firstResultOptional().isPresent();
        if (chaveUsada) {
            return Response.status(409).entity("Esta requisição já foi processada anteriormente").build();
        }

        boolean jaExiste = Genero.find("nome", genero.nome).firstResultOptional().isPresent();
        if (jaExiste) {
            return Response.status(409).entity("Gênero com esse nome já existe").build();
        }

        IdempotencyRecord record = new IdempotencyRecord();
        record.keyRecord = key;
        record.persist();

        genero.persist();
        return Response.status(Response.Status.CREATED).entity(genero).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar gênero", description = "Atualiza os dados de um gênero existente.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Gênero atualizado com sucesso"),
            @APIResponse(responseCode = "404", description = "Gênero não encontrado")
    })
    public Genero atualizar(
            @Parameter(description = "ID do gênero", required = true)
            @PathParam("id") Long id, @Valid Genero genero) {
        Genero entidade = Genero.findById(id);
        if (entidade == null) {
            throw new NotFoundException("Gênero não encontrado: " + id);
        }

        entidade.nome = genero.nome;

        return entidade;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Excluir gênero", description = "Remove um gênero pelo ID.")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Gênero removido com sucesso"),
            @APIResponse(responseCode = "404", description = "Gênero não encontrado")
    })
    public Response excluir(
            @Parameter(description = "ID do gênero", required = true)
            @PathParam("id") Long id) {
        boolean excluido = Genero.deleteById(id);
        if (excluido) {
            return Response.noContent().build();
        }
        throw new NotFoundException("Gênero não encontrado: " + id);
    }
}
