package com.nadeem.app.grid;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.nadeem.app.grid.column.EditableGridActionsColumn;
import com.nadeem.app.grid.component.DataTable;
import com.nadeem.app.grid.provider.IEditableDataProvider;

public class EditableGrid<T, S> extends Panel {

	private static final long serialVersionUID = 1L;

	public EditableGrid(final String id, final List<? extends IColumn<T, S>> columns,
				final IEditableDataProvider<T> dataProvider, final long rowsPerPage) {
		super(id);
		List<IColumn<T, S>>  newCols = new ArrayList<IColumn<T,S>>();
		newCols.addAll(columns);
		newCols.add(newActionsColumn());

		add(buildForm(newCols, dataProvider, rowsPerPage));
	}

	private Component buildForm(final List<? extends IColumn<T, S>> columns, IEditableDataProvider<T> dataProvider, long rowsPerPage) {
		Form<T> form = new Form<T>("form");
		form.setOutputMarkupId(true);
		form.add(newDataTable(columns, dataProvider, rowsPerPage));
		return form;
	}

	private Component newDataTable(final List<? extends IColumn<T, S>> columns, IEditableDataProvider<T> dataProvider, long rowsPerPage) {
			
			DataTable<T, S> dataTable = new DataTable<T, S>("dataTable", columns, dataProvider, rowsPerPage);
		dataTable.setOutputMarkupId(true);
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

}
