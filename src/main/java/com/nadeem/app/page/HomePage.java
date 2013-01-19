package com.nadeem.app.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.nadeem.app.grid.EditableGrid;
import com.nadeem.app.grid.column.AbstractEditablePropertyColumn;
import com.nadeem.app.grid.column.EditableCellPanel;
import com.nadeem.app.grid.column.EditableRequiredDropDownCellPanel;
import com.nadeem.app.grid.column.RequiredEditableTextFieldColumn;
import com.nadeem.app.grid.provider.EditableListDataProvider;

public class HomePage extends WebPage
{
	private static final long serialVersionUID = 1L;
	
	private FeedbackPanel feedbackPanel;

	public HomePage(final PageParameters parameters)
	{
		super(parameters);
		
		feedbackPanel = new FeedbackPanel("feedBack");
		feedbackPanel.setOutputMarkupPlaceholderTag(true);
		
		add(feedbackPanel);

		add(new EditableGrid<Person, String>("grid", getColumns(), new EditableListDataProvider<Person, String>(getPersons()), 5, Person.class)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target) {
				target.add(feedbackPanel);
			}
			@Override
			protected void onCancel(AjaxRequestTarget target) {
				target.add(feedbackPanel);
			}
			@Override
			protected void onDelete(AjaxRequestTarget target,
					IModel<Person> rowModel) {
				target.add(feedbackPanel);
			}
			@Override
			protected void onSave(AjaxRequestTarget target, IModel<Person> rowModel) {
				target.add(feedbackPanel);
			}
		});
    }

	private List<AbstractEditablePropertyColumn<Person, String>> getColumns()
	{
		List<AbstractEditablePropertyColumn<Person, String>> columns = new ArrayList<AbstractEditablePropertyColumn<Person, String>>();
		columns.add(new RequiredEditableTextFieldColumn<Person, String>(new Model<String>("Name"), "name"));
		columns.add(new RequiredEditableTextFieldColumn<Person, String>(new Model<String>("Address"), "address"));
		columns.add(new AbstractEditablePropertyColumn<Person, String>(new Model<String>("Age"), "age")
		{

			private static final long serialVersionUID = 1L;

			public EditableCellPanel<Person> getEditableCellPanel(String componentId)
			{
				return new EditableRequiredDropDownCellPanel<Person, String>(componentId, this, Arrays.asList("10","11","12","13","14","15"));
			}
			
		});
		return columns;
	}

	private List<Person> getPersons()
	{
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person("Person1","12", "Address1"));
		persons.add(new Person("Person2","13", "Address2"));
		persons.add(new Person("Person3","13", "Address3"));
		persons.add(new Person("Person4","13", "Address4"));
		persons.add(new Person("Person5","13", "Address5"));
		persons.add(new Person("Person6","13", "Address6"));
		return persons;
	}
}
