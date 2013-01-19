package com.nadeem.app.grid.column;


public interface IEditableGridColumn<T>
{
	EditableCellPanel<T> getEditableCellPanel(String componentId);
}
