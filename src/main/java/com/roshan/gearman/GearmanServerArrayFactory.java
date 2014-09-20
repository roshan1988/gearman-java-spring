package com.roshan.gearman;

/**
 *
 * A factory bean that creates {@link GearmanServer} objects.
 *
 * @author Roshan Alexander
 *
 */

import java.util.Collection;
import java.util.HashSet;

import org.gearman.Gearman;
import org.gearman.GearmanServer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

public class GearmanServerArrayFactory implements FactoryBean<GearmanServer[]>, InitializingBean {
	private GearmanServer[] serverArray;

	private Gearman gearman;
	private String servers;

	@Override
	public void afterPropertiesSet() throws Exception {
		Collection<GearmanServer> values = new HashSet<>();

		createGearmanServers(this.servers, values);
		// startGearmanServers(ports, persistence, values);

		this.serverArray = values.toArray(new GearmanServer[values.size()]);
	}

	@Override
	public GearmanServer[] getObject() throws Exception {
		return serverArray;
	}

	@Override
	public Class<GearmanServer[]> getObjectType() {
		return GearmanServer[].class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Required
	public void setGearman(Gearman gearman) {
		this.gearman = gearman;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	private void createGearmanServers(String servers, Collection<GearmanServer> values) {
		if ((servers == null) || servers.isEmpty()) {
			return;
		}

		Collection<String> checker = new HashSet<>();
		String[] serversArray = servers.split(";");

		for (String serverUnit : serversArray) {
			serverUnit = serverUnit.trim();

			if (!checker.add(serverUnit)) {
				continue;
			}

			int index = serverUnit.indexOf(':');

			String host = serverUnit.substring(0, index);
			String portstr = serverUnit.substring(index + 1, serverUnit.length());

			int port = Integer.parseInt(portstr);

			values.add(gearman.createGearmanServer(host, port));
		}
	}
}