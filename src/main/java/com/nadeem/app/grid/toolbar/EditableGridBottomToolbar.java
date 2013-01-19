package com.nadeem.app.grid.toolbar;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.form.IFormVisitorParticipant;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.model.PropertyModel;

import com.nadeem.app.grid.column.AbstractEditablePropertyColumn;
import com.nadeem.app.grid.column.EditableCellPanel;
import com.nadeem.app.grid.component.EditableDataTable;
import com.nadeem.app.grid.component.EditableGridSubmitLink;

public abstract class EditableGridBottomToolbar<T, S> extends AbstractEditableGridToolbar {

	private static final long serialVersionUID 	= 1L;
	private static final String CELL_ID 		= "cell";
	private static final String CELLS_ID 		= "cells";

	private T newRow							= null;
	
	protected abstract void onAdd(T newRow, AjaxRequestTarget target);

	public EditableGridBottomToolbar(EditableDataTable<?, ?> table, Class<T> clzz){
		super(table);
		add(new AddToolBarForm("addToolbarForm"));
		createNewInstance(clzz);		
	}

	protected void onError(AjaxRequestTarget target) {	}

	//TODO: use Objenesis instead of the following
	@SuppressWarnings("unchecked")
	private void createNewInstance(Class<T> clzz) {
		try {
			newRow = clzz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class AddToolBarForm extends Form implements IFormVisitorParticipant {

		private static final long serialVersionUID = 1L;

		public AddToolBarForm(String id) {
			super(id);
			add(newEditorComponents());
			add(newAddButton(this));
		}
		public boolean processChildren() {
			IFormSubmitter submitter = getRootForm().findSubmittingButton();
            return submitter != null && submitter.getForm() == this;
		}		
	}

	private Component newAddButton(WebMarkupContainer encapsulatingContainer) {
		return new EditableGridSubmitLink("add", encapsulatingContainer) {

			private static final long serialVersionUID = 1L;		
			@Override
			protected void onSuccess(AjaxRequestTarget target) {
				onAdd(newRow, target);
				createNewInstance((Class<T>) newRow.getClass());
				target.add(getTable());
				
			}
			@Override
			protected void onError(AjaxRequestTarget target) {				
				EditableGridBottomToolbar.this.onError(target);
			}
		};
	}
	
	private Loop newEditorComponents() {
		final List<AbstractEditablePropertyColumn<T, S>> columns = getEditableColumns();
		return new Loop(CELLS_ID, columns.size() ) {

			private static final long serialVersionUID 	= 	1L;

			protected void populateItem(LoopItem item) {
				addEditorComponent(item, getEditorColumn(columns, item.getIndex()));
			}
		};
	}

	private void addEditorComponent(LoopItem item, AbstractEditablePropertyColumn<T, S> toolBarCell) {
		item.add(newCell(toolBarCell));		
	}

	@SuppressWarnings("unchecked")
	private List<AbstractEditablePropertyColumn<T, S>> getEditableColumns() {
		 List<AbstractEditablePropertyColumn<T, S>> columns = new ArrayList<AbstractEditablePropertyColumn<T, S>>();
		 for (IColumn column : getTable().getColumns()) {
			if (column instanceof AbstractEditablePropertyColumn) {
				columns.add((AbstractEditablePropertyColumn<T, S>)column);
			}
			
		}
		 
		 return columns;
	}	

	private Component newCell(AbstractEditablePropertyColumn<T, S> editableGridColumn) {
		EditableCellPanel<T> panel 			= editableGridColumn.getEditableCellPanel(CELL_ID);
		FormComponent<T> editorComponent 	= panel.getEditableComponent();
		editorComponent.setModel(new PropertyModel<T>(newRow , editableGridColumn.getPropertyExpression()));
		return panel;
	}

	private AbstractEditablePropertyColumn<T, S> getEditorColumn(final List<AbstractEditablePropertyColumn<T, S>> editorColumn, int index) {
		return editorColumn.get(index);
	}
}
