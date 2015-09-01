package io.tradeworks.lab.client.local.widgets;

import com.google.gwt.user.client.ui.Label;
import javax.enterprise.context.Dependent;
import org.jboss.errai.ui.client.widget.HasModel;

/**
 * Created by Victor Wjugow on 8/14/15.
 */
@Dependent
public class LabelWidget extends Label implements HasModel<String> {

	@Override
	public String getModel() {
		return getText();
	}

	@Override
	public void setModel(String model) {
		setText(model);
	}
}
