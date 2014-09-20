package com.roshan.gearman;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Roshan
 *
 */
public abstract class AbstractBootstrapper {

	private static ApplicationContext applicationContext;

	private static Object lock = new Object();

	protected AbstractBootstrapper() throws Exception {
		this.loadSpringApplicationContext();
	}

	protected void loadSpringApplicationContext() {
		if (applicationContext == null) {
			synchronized (lock) {
				if (applicationContext == null) {
					applicationContext = new ClassPathXmlApplicationContext(getApplicationContextConfigurationFiles());
				}
			}
		}
	}

	protected abstract String[] getApplicationContextConfigurationFiles();

}
