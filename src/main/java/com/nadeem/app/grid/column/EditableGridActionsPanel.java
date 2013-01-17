package com.nadeem.app.grid.column;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;

import com.nadeem.app.grid.component.EditableGridSubmitLink;

public abstract class EditableGridActionsPanel<T> extends Panel {

	private static final long serialVersionUID = 1L;
	
	protected abstract void onSave(AjaxRequestTarget target);
	protected abstract void onError(AjaxRequestTarget target);
	protected abstract void onCancel(AjaxRequestTarget target);
	protected abstract void onDelete(AjaxRequestTarget target);

	public EditableGridActionsPanel(String id, final Item<ICellPopulator<T>> cellItem) {
		super(id);
		
		final ListItem<T> rowItem = ((ListItem<T>) cellItem.findParent(ListItem.class));
		
		AjaxLink<String> editLink = new AjaxLink<String>("edit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {							

			}
			@Override
			public boolean isVisible() {
				return !isThisRowBeingEdited(rowItem);
			}
		};
		AjaxLink<String> cancelLink = new AjaxLink<String>("cancel") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				onCancel(target);
			}
			@Override
			public boolean isVisible() {
				return isThisRowBeingEdited(rowItem);
			}
		};
		/*AjaxLink<String> deleteLink = new RemoveItemLink<T>("delete", rowItem) {

			private static final long serialVersionUID = 1L;
			@Override
			protected void onRemove(AjaxRequestTarget target) {
				dataTable.setSelectedItem(null);
				target.addComponent(dataTable);	
				onDelete(target);
			}
		};*/
		
		AjaxSubmitLink saveLink = new EditableGridSubmitLink("save", rowItem) {

			private static final long serialVersionUID = 1L;
		
			@Override
			public boolean isVisible() {
				return isThisRowBeingEdited(rowItem);
			}
			@Override
			protected void onSuccess(AjaxRequestTarget target) {

				onSave(target);
				
			}
			@Override
			protected void onError(AjaxRequestTarget target) {
				EditableGridActionsPanel.this.onError(target);
			}
		};
		
		add(editLink);
		add(saveLink);
		add(cancelLink);
	}

	private boolean isThisRowBeingEdited(ListItem<T> rowItem) {
		return true;
	}
}
