package com.nadeem.app.grid;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.nadeem.app.grid.column.EditableGridActionsColumn;
import com.nadeem.app.grid.provider.IEditableDataProvider;

public class EditableGrid<T, S> extends Panel {

	private static final long serialVersionUID = 1L;

	public EditableGrid(final String id, final List<IColumn<T, S>> columns,
				final IEditableDataProvider<T> dataProvider, final long rowsPerPage) {
		super(id);

		add(buildForm(columns, dataProvider, rowsPerPage));
	}

	private Component buildForm(List<IColumn<T, S>> columns, IEditableDataProvider<T> dataProvider, long rowsPerPage) {
		Form<T> form = new Form<T>("form");
		form.setOutputMarkupId(true);
		form.add(newDataTable(columns, dataProvider, rowsPerPage));
		return form;
	}

	private Component newDataTable(final List<IColumn<T, S>> columns, IEditableDataProvider<T> dataProvider, long rowsPerPage) {
			columns.add(newActionsColumn());
			DataTable<T, S> dataTable = new DataTable<T, S>("dataTable", columns, dataProvider, rowsPerPage) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Item<T> newRowItem(String id, int index, IModel<T> model) {
				return new RowItem<T>(id, index, model);
			}
		};

		return dataTable;
	}

	private EditableGridActionsColumn<T, S> newActionsColumn() {
		return new EditableGridActionsColumn<T, S>(new Model<String>("Actions")){

			private static final long serialVersionUID = 1L;
			@Override
			protected void onError(AjaxRequestTarget target, IModel<T> rowModel) {
				EditableGrid.this.onError(target);
			}
			@Override
			protected void onSave(AjaxRequestTarget target, IModel<T> rowModel) {
				EditableGrid.this.onSave(target, rowModel);
			}
			@Override
			protected void onDelete(AjaxRequestTarget target, IModel<T> rowModel) {
				EditableGrid.this.onDelete(target, rowModel);
			}			
		};
	}

	protected void onDelete(AjaxRequestTarget target, IModel<T> rowModel) {
		// TODO Auto-generated method stub
		
	}

	protected void onSave(AjaxRequestTarget target, IModel<T> rowModel) {
		// TODO Auto-generated method stub
		
	}

	protected void onError(AjaxRequestTarget target) {
		// TODO Auto-generated method stub
		
	}

	public static class RowItem<T> extends Item<T> {

		private static final long serialVersionUID = 1L;
		
		public RowItem(final String id, int index, final IModel<T> model) {
			super(id, index, model);
			this.setOutputMarkupId(true);
		}		
	}
}
