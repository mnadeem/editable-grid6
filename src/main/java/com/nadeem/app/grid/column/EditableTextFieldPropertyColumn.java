package com.nadeem.app.grid.column;

import org.apache.wicket.model.IModel;

public class EditableTextFieldPropertyColumn<T, S> extends AbstractEditablePropertyColumn<T, S> {

	private static final long serialVersionUID = 1L;

	public EditableTextFieldPropertyColumn(IModel<String> displayModel, String propertyExpression) {
		super(displayModel, propertyExpression);
	}
	
	
	public EditableTextFieldPropertyColumn(IModel<String> displayModel, String propertyExpression, boolean isEditable) {
		super(displayModel, propertyExpression, isEditable);
	}

	@Override
	public EditableCellPanel<T> getEditableCellPanel(String componentId) {
		return new EditableTextFieldCellPanel<T, S>(componentId, this);
	}
}
