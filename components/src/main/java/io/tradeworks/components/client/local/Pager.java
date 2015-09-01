package io.tradeworks.components.client.local;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import io.tradeworks.components.client.shared.GenericRunnable;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

/**
 * Created by Victor Wjugow on 8/12/15.
 */
@Templated
@Dependent
public class Pager extends Composite {

	private static final String ACTIVE_CLASS = "active";
	private static final String DISABLED_CLASS = "disabled";
	private static final int MAX_NUMBER_PAGES = 5;
	private Logger logger = Logger.getLogger(getClass().getName());
	@DataField
	private Element ul = DOM.createElement("ul");
	@DataField
	private Element previousButton = DOM.createElement("li");
	@DataField
	private Element nextButton = DOM.createElement("li");
	private Element[] pageButtons;
	private GenericRunnable<Integer> action;
	private int currentPageNumber = 0;
	private int lastPage;
	private int currentOffset;

	@EventHandler("previousButton")
	private void onPreviousClicked(ClickEvent event) {
		event.preventDefault();
		if (!previousButton.getClassName().contains(DISABLED_CLASS)) {
			movePage(currentPageNumber, --currentPageNumber);
		}
	}

	@EventHandler("nextButton")
	private void onNextClicked(ClickEvent event) {
		event.preventDefault();
		if (!nextButton.getClassName().contains(DISABLED_CLASS)) {
			movePage(currentPageNumber, ++currentPageNumber);
		}
	}

	public void setPageCount(int count) {
		this.lastPage = count - 1;
		count = Math.min(count, MAX_NUMBER_PAGES);
		pageButtons = new Element[count];
		for (int i = 0; i < count; i++) {
			pageButtons[i] = createPageButton(i);
			ul.appendChild(pageButtons[i]);
		}
		pageButtons[0].setClassName(ACTIVE_CLASS);
		ul.appendChild(nextButton);
	}

	private Element createPageButton(int i) {
		Element a = DOM.createAnchor();
		a.setAttribute("href", "#");
		Element pageButton = DOM.createElement("li");
		pageButton.appendChild(a);
		setOnClickEvent(pageButton, i);
		return pageButton;
	}

	private void setOnClickEvent(Element pageButton, final int i) {
		pageButton.getFirstChildElement().setInnerHTML(String.valueOf(i + 1));
		Event.sinkEvents(pageButton, Event.ONCLICK);
		Event.setEventListener(pageButton, new EventListener() {
			public void onBrowserEvent(Event event) {
				if (Event.ONCLICK == event.getTypeInt()) {
					event.preventDefault();
					movePage(currentPageNumber, i);
				}
			}
		});
	}

	public void refresh() {
		movePage(currentPageNumber, 0);
		currentPageNumber = 0;
	}

	public void setAction(GenericRunnable<Integer> action) {
		this.action = action;
	}

	private void movePage(int currentPage, int nextPage) {
		if (currentPage != nextPage) {
			this.currentPageNumber = nextPage;
			if (action != null) {
				action.run(nextPage);
			}
			if (this.currentPageNumber == 0) {
				previousButton.addClassName(DISABLED_CLASS);
			} else {
				previousButton.removeClassName(DISABLED_CLASS);
			}
			if (this.currentPageNumber == lastPage) {
				nextButton.addClassName(DISABLED_CLASS);
			} else {
				nextButton.removeClassName(DISABLED_CLASS);
			}

			pageButtons[nextPage - currentOffset].addClassName(ACTIVE_CLASS);
			pageButtons[currentPage - currentOffset].removeClassName(ACTIVE_CLASS);
			if (lastPage >= MAX_NUMBER_PAGES) {
				int centerIndex = MAX_NUMBER_PAGES / 2;
				if ((currentPage < nextPage && nextPage > centerIndex) || (currentPage > nextPage && nextPage <
						lastPage - centerIndex)) {
					int centerPage;
					if (currentPage < nextPage) {
						centerPage = nextPage <= lastPage - centerIndex ? nextPage : lastPage - centerIndex;
					} else {
						centerPage = nextPage >= centerIndex ? nextPage : centerIndex;
					}
					currentOffset = centerPage - centerIndex;
					for (int i = 0; i < pageButtons.length; i++) {
						setOnClickEvent(pageButtons[i], currentOffset + i);
					}
				}
			}
		}
	}
}