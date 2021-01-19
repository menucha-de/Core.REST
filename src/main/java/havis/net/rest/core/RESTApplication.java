package havis.net.rest.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Providers;

import havis.net.rest.core.provider.AppExceptionMapper;
import havis.net.rest.core.provider.LicenseExceptionMapper;
import havis.net.rest.core.provider.LogExceptionMapper;
import havis.net.rest.shared.NetService;
import havis.net.rest.shared.provider.URISyntaxExceptionMapper;
import havis.util.core.Core;

public class RESTApplication extends Application {

	private final static String PROVIDERS = Providers.class.getName();
	private final static Map<String, Object> properties = new HashMap<>();

	private final Set<Object> singletons = new HashSet<Object>();

	static {
		properties.put(PROVIDERS,
				new Class<?>[] { AppExceptionMapper.class, LicenseExceptionMapper.class, LogExceptionMapper.class, URISyntaxExceptionMapper.class });
	}

	public RESTApplication(Core core) {
		singletons.add(new LogService(core.getLog()));
		singletons.add(new AppService(core.getApp()));
		singletons.add(new NetService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}
}