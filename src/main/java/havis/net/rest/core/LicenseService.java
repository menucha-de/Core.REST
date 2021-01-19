package havis.net.rest.core;

import havis.net.rest.shared.Resource;
import havis.util.core.app.AppInfo;
import havis.util.core.license.License;
import havis.util.core.license.LicenseCurator;
import havis.util.core.license.LicenseException;

import java.io.InputStream;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("license")
public class LicenseService extends Resource {

	private LicenseCurator curator;

	public LicenseService(LicenseCurator licenseCurator) {
		this.curator = licenseCurator;
	}

	@RolesAllowed("admin")
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public License getLicense() throws LicenseException {
		return curator.getLicense();
	}

	@RolesAllowed("admin")
	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public void setLicense(InputStream stream) throws LicenseException {
		curator.setLicense(stream);
	}

	@RolesAllowed("admin")
	@GET
	@Path("request")
	@Produces(MediaType.APPLICATION_JSON)
	public License getLicenseRequest() throws LicenseException {
		return curator.getLicenseRequest();
	}

	@PermitAll
	@GET
	@Path("appinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public AppInfo getAppInfo() {
		return curator.getAppInfo();
	}
}
