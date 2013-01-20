package com.nadeem.app.grid.column;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;

public interface IColumnProvider<T, S>
{	
	List<? extends IColumn<T, S>> getColumns();
}
