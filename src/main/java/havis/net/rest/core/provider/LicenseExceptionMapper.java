package havis.net.rest.core.provider;

import havis.util.core.license.LicenseException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LicenseExceptionMapper implements ExceptionMapper<LicenseException> {

	@Override
	public Response toResponse(LicenseException e) {
		return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}
}
