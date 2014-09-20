package com.roshan.gearman;

import org.gearman.Gearman;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * A factory bean that creates {@link Gearman} objects
 *
 * @author Roshan Alexander
 *
 */
public class GearmanFactory implements FactoryBean<Gearman>, DisposableBean, InitializingBean {

	/** the gearman instance */
	private Gearman gearman;

	@Override
	public Gearman getObject() throws Exception {
		return gearman;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.gearman = Gearman.createGearman();
	}

	@Override
	public Class<Gearman> getObjectType() {
		return Gearman.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void destroy() throws Exception {
		this.gearman.shutdown();
	}
}
