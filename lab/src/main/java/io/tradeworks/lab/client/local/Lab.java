package io.tradeworks.lab.client.local;

import com.google.gwt.user.client.ui.Composite;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.jboss.errai.ioc.client.api.EntryPoint;

/**
 * Created by Victor Wjugow on 8/14/15.
 */
@ApplicationScoped
@EntryPoint
public class Lab extends Composite {
	private Logger logger = Logger.getLogger(getClass().getName());

	@PostConstruct
	private void post() {
		logger.info("constructing");
	}

	@Override
	public void onAttach() {
		logger.info("attaching");
	}
}
