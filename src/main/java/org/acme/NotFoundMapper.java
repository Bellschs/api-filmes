package org.acme;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErroDTO("Não localizado", exception.getMessage()))
                .build();
    }

    public static class ErroDTO {
        public String erro;
        public String detalhe;

        public ErroDTO(String erro, String detalhe) {
            this.erro = erro;
            this.detalhe = detalhe;
        }
    }
}
