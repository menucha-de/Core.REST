package havis.net.rest.core;

import havis.net.rest.shared.Resource;
import havis.util.core.app.AppCurator;
import havis.util.core.app.AppException;
import havis.util.core.app.AppInfo;
import havis.util.core.app.AppState;
import havis.util.core.license.License;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("apps")
public class AppService extends Resource {

	Logger log = Logger.getLogger(AppService.class.getName());

	private static final Lock lock = new ReentrantLock();

	private AppCurator curator;

	public AppService(AppCurator curator) {
		lock.lock();
		try {
			this.curator = curator;
		} finally {
			lock.unlock();
		}
	}

	@RolesAllowed("admin")
	@POST
	@Path("{name}")
	public void install(@PathParam("name") String name, InputStream input) throws AppException {
		curator.install(name, input);
	}

	@RolesAllowed("admin")
	@DELETE
	@Path("{name}")
	public void remove(@PathParam("name") String name) throws AppException {
		curator.remove(name);
	}

	@RolesAllowed("admin")
	@GET
	@Path("{name}/plug")
	@Produces(MediaType.APPLICATION_JSON)
	public void plug(@PathParam("name") String name) throws AppException {
		curator.plug(name);
	}

	@RolesAllowed("admin")
	@GET
	@Path("{name}/unplug")
	@Produces(MediaType.APPLICATION_JSON)
	public void unplug(@PathParam("name") String name) throws AppException {
		curator.unplug(name);
	}

	@PermitAll
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AppInfo> getApps() {
		return curator.get();
	}

	@PermitAll
	@GET
	@Path("{name}/state")
	@Produces({ "application/json", "application/xml" })
	public AppState getState(@PathParam("name") String name) throws AppException {
		return curator.getState(name);
	}

	@RolesAllowed("admin")
	@PUT
	@Path("{name}/state")
	@Consumes({ "application/json", "application/xml" })
	public void setState(@PathParam("name") String name, AppState state) throws AppException {
		curator.setState(name, state);
	}

	@RolesAllowed("admin")
	@GET
	@Path("{name}/config")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getConfig(@PathParam("name") String name) throws AppException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		curator.getConfig(name, stream);
		return Response.ok(stream.toByteArray()).header("Content-Disposition", "attachment; filename=\"" + name + ".config\"")
				.header("Content-Type", "application/zip").build();
	}

	@RolesAllowed("admin")
	@POST
	@Path("{name}/config")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public void setConfig(@PathParam("name") String name, InputStream stream) throws AppException {
		curator.setConfig(name, stream);
	}

	@RolesAllowed("admin")
	@DELETE
	@Path("{name}/config")
	public void resetConfig(@PathParam("name") String name) throws AppException {
		curator.resetConfig(name);
	}

	@RolesAllowed("admin")
	@GET
	@Path("{name}/license/request")
	@Produces(MediaType.APPLICATION_JSON)
	public License getLicenseRequest(@PathParam("name") String name) throws AppException {
		return curator.getLicenseRequest(name);
	}

	@RolesAllowed("admin")
	@GET
	@Path("{name}/license")
	@Produces(MediaType.APPLICATION_JSON)
	public License getLicense(@PathParam("name") String name) throws AppException {
		return curator.getLicense(name);
	}

	@RolesAllowed("admin")
	@PUT
	@Path("{name}/license")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public void setLicense(@PathParam("name") String name, InputStream stream) throws AppException {
		curator.setLicense(name, stream);
	}

	@RolesAllowed("admin")
	@GET
	@Path("{name}/config/backups")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<String> getBackups(@PathParam("name") String name) throws AppException {
		return curator.getBackups(name);
	}

	@RolesAllowed("admin")
	@PUT
	@Path("{name}/config/backups/{label}")
	public void storeBackup(@PathParam("name") String name, @PathParam("label") String label) throws AppException {
		curator.storeBackup(name, label);
	}

	@RolesAllowed("admin")
	@GET
	@Path("{name}/config/backups/{label}")
	public void restoreBackup(@PathParam("name") String name, @PathParam("label") String label) throws AppException {
		curator.restoreBackup(name, label);
	}

	@RolesAllowed("admin")
	@DELETE
	@Path("{name}/config/backups/{label}")
	public void dropBackup(@PathParam("name") String name, @PathParam("label") String label) throws AppException {
		curator.dropBackup(name, label);
	}
}