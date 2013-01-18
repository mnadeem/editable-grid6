package com.nadeem.app.grid.column;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;

import com.nadeem.app.grid.component.EditableGridSubmitLink;

public abstract class EditableGridActionsPanel<T> extends Panel {

	public final static MetaDataKey<Boolean> EDITING 	= new MetaDataKey<Boolean>() {
		private static final long serialVersionUID = 1L;
	};

	private static final long serialVersionUID = 1L;
	
	protected abstract void onSave(AjaxRequestTarget target);
	protected abstract void onError(AjaxRequestTarget target);
	protected abstract void onCancel(AjaxRequestTarget target);
	protected abstract void onDelete(AjaxRequestTarget target);

	public EditableGridActionsPanel(String id, final Item<ICellPopulator<T>> cellItem) {
		super(id);

		@SuppressWarnings("unchecked")
		final Item<T> rowItem = ((Item<T>) cellItem.findParent(Item.class));

		AjaxLink<String> editLink = new AjaxLink<String>("edit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {							
				rowItem.setMetaData(EDITING, Boolean.TRUE);
				send(getPage(), Broadcast.BREADTH, rowItem);
				target.add(rowItem);
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
				rowItem.setMetaData(EDITING, Boolean.FALSE);
				send(getPage(), Broadcast.BREADTH, rowItem);
				target.add(rowItem);
				onCancel(target);
			}
			@Override
			public boolean isVisible() {
				return isThisRowBeingEdited(rowItem);
			}
		};
		AjaxLink<String> deleteLink = new AjaxLink<String>("delete") {

			private static final long serialVersionUID = 1L;
			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
				super.updateAjaxAttributes(attributes);
				AjaxCallListener listener = new AjaxCallListener(); 
				listener.onPrecondition("if(!confirm('Do you really want to delete?')){return false;}"); 
				attributes.getAjaxCallListeners().add(listener); 
			}
			@Override
			public void onClick(AjaxRequestTarget target) {
			
			}
		};
		
		AjaxSubmitLink saveLink = new EditableGridSubmitLink("save", rowItem) {

			private static final long serialVersionUID = 1L;
		
			@Override
			public boolean isVisible() {
				return isThisRowBeingEdited(rowItem);
			}
			@Override
			protected void onSuccess(AjaxRequestTarget target) {
				rowItem.setMetaData(EDITING, Boolean.FALSE);
				send(getPage(), Broadcast.BREADTH, rowItem);
				target.add(rowItem);
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
		add(deleteLink);
	}
	
	private boolean isThisRowBeingEdited(Item<T> rowItem) {
		return rowItem.getMetaData(EDITING);
	}

}
