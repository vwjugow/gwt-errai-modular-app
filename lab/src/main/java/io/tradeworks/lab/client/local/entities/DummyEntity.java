package io.tradeworks.lab.client.local.entities;

import org.jboss.errai.databinding.client.api.Bindable;

/**
 * Created by Victor Wjugow on 8/4/15.
 */
@Bindable
public class DummyEntity {

	private String food;

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}
}