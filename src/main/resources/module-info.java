module havis.net.rest.core {
    requires transitive javax.annotation.api;
    requires transitive javax.ws.rs.api;
    requires havis.net.rest.shared;
    requires havis.util.core.api;

    exports havis.net.rest.core;
    exports havis.net.rest.core.provider;
}