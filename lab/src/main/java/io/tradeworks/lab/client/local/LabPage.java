package io.tradeworks.lab.client.local;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import io.tradeworks.components.client.local.NaturalLanguageDropDown;
import io.tradeworks.lab.client.local.entities.DummyEntity;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

/**
 * Created by Victor Wjugow on 8/4/15.
 */
@Page(role = DefaultPage.class)
@Templated
public class LabPage extends Composite {

	private Logger logger = Logger.getLogger(getClass().getName());
	@Inject
	@DataField
	@Bound
	private NaturalLanguageDropDown food;
	@Inject
	@DataField
	private Button button;

	@Inject
	@AutoBound
	private DataBinder<DummyEntity> binder;

	@Override
	public void onAttach() {
		super.onAttach();
		String[] foods = {"Any Food", "Japanese", "Italian", "French", "Indian"};
		food.setOptions(foods);
	}

	@EventHandler("button")
	private void onButtonClicked(ClickEvent event) {
		logger.info("databinder model: " + binder.getModel().getFood());
	}
}
