package com.nadeem.app.grid.column;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class EditableGrid<T> extends Panel {

	private static final long serialVersionUID = 1L;

	public EditableGrid(String id, IModel<?> model) {
		super(id, model);
	}
}
