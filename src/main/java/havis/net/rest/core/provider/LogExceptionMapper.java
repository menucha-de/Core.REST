package havis.net.rest.core.provider;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import havis.util.core.log.LogException;

@Provider
public class LogExceptionMapper implements ExceptionMapper<LogException> {

	@Override
	public Response toResponse(LogException e) {
		return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.TEXT_PLAIN)
				.build();
	}
}
