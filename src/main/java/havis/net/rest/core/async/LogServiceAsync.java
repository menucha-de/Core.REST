package havis.net.rest.core.async;

import havis.net.rest.shared.data.ServiceAsync;
import havis.util.core.log.LogConfiguration;
import havis.util.core.log.LogEntry;
import havis.util.core.log.LogLevel;
import havis.util.core.log.LogTarget;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;

@Path("../rest/log")
public interface LogServiceAsync extends ServiceAsync {

	@GET
	@Path("levels")
	void getLevels(MethodCallback<List<LogLevel>> callback);

	@GET
	@Path("targets")
	void getTargets(MethodCallback<List<LogTarget>> callback);

	@OPTIONS
	@Path("{target}/level")
	void optionsLevel(MethodCallback<Void> callback);

	@GET
	@Path("{target}/level")
	void getLevel(@PathParam("target") String target, MethodCallback<LogLevel> callback);

	@PUT
	@Path("{target}/level")
	void setLevel(@PathParam("target") String target, LogLevel level, MethodCallback<Void> callback);

	@GET
	@Path("{target}/{level}")
	void size(@PathParam("target") String target, @PathParam("level") LogLevel level, MethodCallback<Integer> callback);

	@GET
	@Path("{target}/{level}/{limit}/{offset}")
	void get(@PathParam("target") String target, @PathParam("level") LogLevel level, @PathParam("limit") int limit,
			@PathParam("offset") int offset, @QueryParam("locale") String locale,
			MethodCallback<List<LogEntry>> callback);

	@OPTIONS
	@Path("{target}")
	void optionsClear(MethodCallback<Void> callback);

	@DELETE
	@Path("{target}")
	void clear(@PathParam("target") String target, MethodCallback<Void> callback);

	@GET
	@Path("config")
	void getConfiguration(MethodCallback<LogConfiguration> callback);

	@PUT
	@Path("config")
	void setConfiguration(LogConfiguration configuration, MethodCallback<Void> callback);
}