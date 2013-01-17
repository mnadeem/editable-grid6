package com.nadeem.app.grid.provider;

import org.apache.wicket.markup.repeater.data.IDataProvider;

public interface IEditableDataProvider<T> extends IDataProvider<T> {

	void add(T item);
	void remove(T item);
}
