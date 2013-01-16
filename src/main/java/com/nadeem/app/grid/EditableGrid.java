package com.nadeem.app.grid;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

public class EditableGrid<T, S> extends Panel {

	private static final long serialVersionUID = 1L;

	public EditableGrid(final String id, final List<? extends IColumn<T, S>> columns,
				final IDataProvider<T> dataProvider, final long rowsPerPage) {
		super(id);
		
		add(buildForm(columns, dataProvider, rowsPerPage));
	}

	private Component buildForm(List<? extends IColumn<T, S>> columns, IDataProvider<T> dataProvider, long rowsPerPage) {
		Form<T> form = new Form<T>("form");
		form.setOutputMarkupId(true);
		form.add(newDataTable(columns, dataProvider, rowsPerPage));
		return form;
	}

	private Component newDataTable(List<? extends IColumn<T, S>> columns, IDataProvider<T> dataProvider, long rowsPerPage) {

		return new DataTable<T, S>("dataTable", columns, dataProvider, rowsPerPage) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Item<T> newRowItem(String id, int index, IModel<T> model) {
				return new RowItem<T>(id, index, model);
			}
		};
	}

	private class RowItem<T> extends Item<T> {

		private static final long serialVersionUID = 1L;
		
		public RowItem(final String id, int index, final IModel<T> model) {
			super(id, index, model);
			this.setOutputMarkupId(true);
		}				
	}
}
