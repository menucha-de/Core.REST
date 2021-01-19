package havis.net.rest.core;

import havis.net.rest.shared.Resource;
import havis.util.core.log.LogConfiguration;
import havis.util.core.log.LogCurator;
import havis.util.core.log.LogEntry;
import havis.util.core.log.LogException;
import havis.util.core.log.LogLevel;
import havis.util.core.log.LogTarget;
import havis.util.core.log.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("log")
public class LogService extends Resource {

	private static final Lock lock = new ReentrantLock();

	private LogCurator curator;

	public LogService(LogCurator curator) {
		lock.lock();
		try {
			this.curator = curator;
		} finally {
			lock.unlock();
		}
	}

	private static String target(String target) {
		return target(target, "havis");
	}

	private static String target(String target, String standard) {
		return "ALL".equals(target) ? standard : target;
	}

	@PermitAll
	@GET
	@Path("levels")
	@Produces({ "application/json", "application/xml" })
	public List<LogLevel> getLevels() throws LogException {
		return curator.getLevels();
	}

	@PermitAll
	@GET
	@Path("targets")
	@Produces({ "application/json", "application/xml" })
	public Collection<LogTarget> getTargets() throws LogException {
		return curator.getTargets();
	}

	@PermitAll
	@GET
	@Path("{target}/level")
	@Produces({ "application/json", "application/xml" })
	public LogLevel getLevel(@PathParam("target") String target) throws LogException {
		return curator.getLevel(target(target));
	}

	@RolesAllowed("admin")
	@PUT
	@Path("{target}/level")
	@Consumes({ "application/json", "application/xml" })
	public void setLevel(@PathParam("target") String target, LogLevel level) throws LogException {
		curator.setLevel(target(target), level);
	}

	@PermitAll
	@GET
	@Path("{target}/{level}")
	@Produces({ "application/json", "application/xml" })
	public Integer size(@PathParam("target") String target, @PathParam("level") LogLevel level) throws LogException {
		return curator.size(target(target), level);
	}

	@PermitAll
	@GET
	@Path("{target}/{level}/{limit}/{offset}")
	@Produces({ "application/json", "application/xml" })
	public List<LogEntry> get(@PathParam("target") String target, @PathParam("level") LogLevel level,
			@PathParam("limit") int limit, @PathParam("offset") int offset, @QueryParam("locale") String locale)
			throws LogException {
		return curator.get(target(target), level, limit, offset,
				locale == null ? Locale.getDefault() : new Locale(locale));
	}

	@RolesAllowed("admin")
	@DELETE
	@Path("{target}")
	public void clear(@PathParam("target") String target) throws LogException {
		curator.clear(target(target));
	}

	@PermitAll
	@GET
	@Path("{target}/{level}/export")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response export(@PathParam("target") String target, @PathParam("level") LogLevel level,
			@QueryParam("locale") String locale) throws LogException {

		StringBuilder sb = new StringBuilder();
		for (LogEntry entry : get(target(target, ""), level, -1, 0, locale)) {
			sb.append(LogUtil.format(entry));
			sb.append("\n");
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String filename = String.format("Log_%s_%s_%s.txt", target, level, dateFormat.format(new Date()));

		byte[] data = sb.toString().getBytes();
		InputStream is = new ByteArrayInputStream(data);
		return Response.ok(is, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
				.header("Content-Type", "text/plain; charset=utf-8").header("Content-Length", data.length).build();
	}

	@PermitAll
	@GET
	@Path("config")
	@Produces({ "application/json" })
	public LogConfiguration getConfiguration() throws LogException {
		return curator.getConfiguration();
	}

	@RolesAllowed("admin")
	@PUT
	@Path("config")
	@Consumes({ "application/json" })
	public void setConfiguration(LogConfiguration configuration) throws LogException {
		curator.setConfiguration(configuration);
	}
}