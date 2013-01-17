package com.nadeem.app.grid.column;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public abstract class AbstractEditablePropertyColumn<T, S> extends PropertyColumn<T, S> implements IEditableGridColumn {

	private static final long serialVersionUID 	= 1L;
	private boolean isEditable 					= true;

	public AbstractEditablePropertyColumn(IModel<String> displayModel, String propertyExpression) {
		super(displayModel, propertyExpression);		
	}
	
	public AbstractEditablePropertyColumn(IModel<String> displayModel, String propertyExpression, boolean isEditable) {
		super(displayModel, propertyExpression);
		this.isEditable = isEditable;
	}

	@Override
	public final void populateItem(final Item<ICellPopulator<T>> item, final String componentId, final IModel<T> rowModel) {
		@SuppressWarnings("unchecked")
		Item<ICellPopulator<T>> rowItem = (Item<ICellPopulator<T>>) item.findParent(Item.class);
		
		if (inEditiingMode(rowItem) && isEditable) {
			EditableCellPanel<T> provider 		= getEditableCellPanel(componentId);
			FormComponent<T> editorComponent 	= provider.getEditableComponent();
			editorComponent.setModel((IModel<T>) getDataModel(rowModel));
			addBehavior(editorComponent, rowModel);
			item.add(provider);
		} else {
			super.populateItem(item, componentId, rowModel);
		}		
	}

	private boolean inEditiingMode(Item<ICellPopulator<T>> rowItem){return true;}
	 
	protected void addBehavior(FormComponent<T> editorComponent, IModel<T> rowModel) {

	}	
}
