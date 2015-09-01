package io.tradeworks.components.client.local;

//import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

/**
 * Created by Victor Wjugow on his 25th birthday.
 */
@Templated
@Dependent
public class NaturalLanguageDropDown extends Composite implements HasValue<String> {

	private static final String OPEN_CLASS = "nl-field-open";
	private static final String SELECTED_CLASS = "nl-dd-checked";
	private Logger logger = Logger.getLogger(getClass().getName());
	@DataField
	private Element field = DOM.createDiv();
	@DataField
	private Element toggle = DOM.createAnchor();
	@DataField
	private Element list = DOM.createElement("ul");
	@DataField
	private Element overlay = DOM.createDiv();
	private Element[] optionsElements = null;

	private boolean open = false;
	private int selectedIndex = -1;

	@EventHandler("toggle")
	private void onToggleClick(ClickEvent event) {
		event.preventDefault();
		event.stopPropagation();
		this.open();
	}

	@EventHandler("toggle")
	private void onToggleTouch(TouchStartEvent event) {
		event.preventDefault();
		event.stopPropagation();
		this.open();
	}

	@EventHandler("overlay")
	private void onOverlayClick(ClickEvent event) {
		close(-1);
	}

	@EventHandler("overlay")
	private void onOverlayTouch(TouchStartEvent event) {
		close(-1);
	}

	/**
	 * @param options
	 */
	public void setOptions(String[] options) {
	logger.info("Seteando operaciones 4");
			//		list.removeAllChildren();
		for (int i = list.getChildCount() - 1; i >= 0; i--) {
			list.removeChild(list.getChild(i));
		}
		this.optionsElements = new Element[options.length];
		for (int i = 0; i < options.length; i++) {
			this.optionsElements[i] = DOM.createElement("li");
			this.optionsElements[i].setInnerHTML(options[i]);
			final int finalI = i;
			Event.sinkEvents(this.optionsElements[i], Event.ONCLICK);
			Event.setEventListener(this.optionsElements[i], new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if (Event.ONCLICK == event.getTypeInt() || Event.ONTOUCHSTART == event.getTypeInt()) {
						event.preventDefault();
						close(finalI);
					}
				}
			});
			list.appendChild(this.optionsElements[i]);
		}
		setSelected(0, true);
	}

	private void open() {
		if (!open) {
			open = true;
			field.addClassName(OPEN_CLASS);
		}
	}

	private void close(int newSelectedIndex) {
		if (open) {
			open = false;
			field.removeClassName(OPEN_CLASS);
			optionsElements[this.selectedIndex].setClassName("");
			if (newSelectedIndex >= 0) {
				setSelected(newSelectedIndex, true);
			}
		}
	}

	private void setSelected(int index, boolean fireEvents) {
		if (fireEvents) {
			ValueChangeEvent.fireIfNotEqual(this, getValue(), optionsElements[index].getInnerHTML());
		}
		if (selectedIndex != -1) {
			this.optionsElements[selectedIndex].removeClassName(SELECTED_CLASS);
		}
		this.optionsElements[index].addClassName(SELECTED_CLASS);
		this.selectedIndex = index;
		toggle.setInnerHTML(optionsElements[index].getInnerHTML());
	}

	@Override
	public String getValue() {
		return selectedIndex >= 0 ? optionsElements[selectedIndex].getInnerHTML() : null;
	}

	@Override
	public void setValue(String value) {
		setValue(value, true);
	}

	@Override
	public void setValue(String newValue, boolean fireEvents) {
		for (int i = 0; i < optionsElements.length; i++) {
			Element optionsElement = optionsElements[i];
			if (optionsElement.getInnerHTML().equals(newValue)) {
				setSelected(i, fireEvents);
				break;
			}
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}
}