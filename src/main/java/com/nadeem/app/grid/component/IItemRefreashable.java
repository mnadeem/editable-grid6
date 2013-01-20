package com.nadeem.app.grid.component;

import org.apache.wicket.markup.repeater.Item;

public interface IItemRefreashable<T>
{	
	void refreash(Item<T> item);
}
