package havis.net.rest.core.async;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import havis.util.core.app.AppInfo;
import havis.util.core.license.License;

@Path("../rest/license")
public interface LicenseServiceAsync extends RestService {

	@OPTIONS
	@Path("request")
	void optionsLicenseRequest(MethodCallback<Void> callback);

	@GET
	@Path("request")
	void getLicenseRequest(MethodCallback<License> callback);

	@OPTIONS
	@Path("")
	void optionsLicense(MethodCallback<Void> callback);

	@GET
	@Path("")
	void getLicense(MethodCallback<License> callback);

	@PUT
	@Path("")
	void setLicense(String response, MethodCallback<Void> callback);

	@GET
	@Path("appinfo")
	void getAppInfo(MethodCallback<AppInfo> callback);

}