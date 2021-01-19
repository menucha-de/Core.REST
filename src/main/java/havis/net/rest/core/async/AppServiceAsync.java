package havis.net.rest.core.async;

import havis.util.core.app.AppInfo;
import havis.util.core.app.AppState;
import havis.util.core.license.License;

import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

@Path("../rest/apps")
public interface AppServiceAsync extends RestService {

	@OPTIONS
	@Path("")
	void optionsApps(MethodCallback<Void> callback);

	@GET
	@Path("")
	void get(MethodCallback<Collection<AppInfo>> callback);

	@OPTIONS
	@Path("{name}/state")
	void optionsState(MethodCallback<Void> callback);

	@GET
	@Path("{name}/state")
	void getState(@PathParam("name") String name, MethodCallback<AppState> callback);

	@PUT
	@Path("{name}/state")
	void setState(@PathParam("name") String name, AppState state, MethodCallback<Void> callback);

	@OPTIONS
	@Path("{name}")
	void optionsInstall(MethodCallback<Void> callback);

	@DELETE
	@Path("{name}")
	void remove(@PathParam("name") String name, MethodCallback<Void> callback);

	@OPTIONS
	@Path("{name}/config")
	void optionsConfig(MethodCallback<Void> callback);

	@DELETE
	@Path("{name}/config")
	void resetConfig(@PathParam("name") String name, MethodCallback<Void> callback);

	@OPTIONS
	@Path("{name}/config/backups/{label}")
	void optionsBackup(MethodCallback<Void> callback);

	@GET
	@Path("{name}/config/backups")
	void getBackups(@PathParam("name") String name, MethodCallback<String> callback);

	@PUT
	@Path("{name}/config/backups/{label}")
	void storeBackup(@PathParam("name") String name, @PathParam("label") String label, MethodCallback<Void> callback);

	@GET
	@Path("{name}/config/backups/{label}")
	void restoreBackup(@PathParam("name") String name, @PathParam("label") String label, MethodCallback<Void> callback);

	@DELETE
	@Path("{name}/config/backups/{label}")
	void dropBackup(@PathParam("name") String name, @PathParam("label") String label, MethodCallback<Void> callback);

	@OPTIONS
	@Path("{name}/license/request")
	void optionsLicenseRequest(MethodCallback<Void> callback);

	@GET
	@Path("{name}/license/request")
	void getLicenseRequest(@PathParam("name") String name, MethodCallback<License> callback);

	@OPTIONS
	@Path("{name}/license")
	void optionsLicense(MethodCallback<Void> callback);

	@GET
	@Path("{name}/license")
	void getLicense(String name, MethodCallback<License> callback);

	@PUT
	@Path("{name}/license")
	void setLicense(@PathParam("name") String name, String response, MethodCallback<Void> callback);

}