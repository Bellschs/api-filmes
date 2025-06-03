package org.acme.config;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.WebApplicationException;

import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        int statusCode = 500;
        String mensagem = "Erro interno no servidor";

        if (exception instanceof WebApplicationException) {
            statusCode = ((WebApplicationException) exception).getResponse().getStatus();
            mensagem = exception.getMessage();
        }

        Map<String, Object> erro = new HashMap<>();
        erro.put("erro", "Erro ao processar a requisição");
        erro.put("mensagem", mensagem);
        erro.put("status", statusCode);

        return Response.status(statusCode).entity(erro).build();
    }
}
