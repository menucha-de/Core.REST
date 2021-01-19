package havis.net.rest.core.osgi;

import havis.net.rest.core.RESTApplication;
import havis.util.core.Core;

import java.util.Date;
import java.util.logging.Logger;

import javax.ws.rs.core.Application;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

	Logger log = Logger.getLogger(Activator.class.getName());

	private ServiceRegistration<Application> registration;
	private ServiceTracker<Core, Core> tracker;

	@Override
	public void start(BundleContext context) throws Exception {
		long now = new Date().getTime();

		tracker = new ServiceTracker<Core, Core>(context, Core.class, null) {
			@Override
			public Core addingService(ServiceReference<Core> reference) {
				Core service = super.addingService(reference);
				registration = context.registerService(Application.class, new RESTApplication(service), null);
				return service;
			}

			@Override
			public void removedService(ServiceReference<Core> reference, Core service) {
				registration.unregister();
				super.removedService(reference, service);
			}
		};
		tracker.open();

		log.info("Bundle start took " + (new Date().getTime() - now) + "ms");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		tracker.close();
	}
}