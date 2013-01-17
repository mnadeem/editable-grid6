package com.nadeem.app.grid.component;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

public abstract class EditableGridSubmitLink extends AjaxSubmitLink {

	private static final long serialVersionUID = 1L;
	private WebMarkupContainer encapsulatingContainer;
	
	protected abstract void onSuccess(AjaxRequestTarget target);
	protected abstract void onError(AjaxRequestTarget target);

	public EditableGridSubmitLink(String id, WebMarkupContainer encapsulatingComponent) {
		super(id);
		this.encapsulatingContainer = encapsulatingComponent;
	}

	@Override
	protected final void onSubmit(AjaxRequestTarget target, Form<?> form) {
		
		final Boolean[] error = { false };
		
		// first iteration - validate components

		encapsulatingContainer.visitChildren(FormComponent.class, new IVisitor<FormComponent<?>, Void>() {
			@Override
			public void component(FormComponent<?> formComponent, IVisit<Void> visit) {

				if (formComponentActive(formComponent)) {
					formComponent.validate();
					if (formComponent.isValid()) {
						if (!formComponent.processChildren()) {
							visit.dontGoDeeper();
						}
					} else {
						error[0] = true;
						visit.dontGoDeeper();
					}
				}
				visit.dontGoDeeper();
				
			}
		});

		// second iteration - update models if the validation passed
		if (error[0] == false) {
			encapsulatingContainer.visitChildren(FormComponent.class, new IVisitor<FormComponent<?>, Void>() {				

				@Override
				public void component(FormComponent<?> formComponent, IVisit<Void> visit) {
					if (formComponentActive(formComponent)) {

						formComponent.updateModel();

						if (!formComponent.processChildren()) {
							visit.dontGoDeeper();
						}
					}
					visit.dontGoDeeper();					
				}
			});
			
			onSuccess(target);
		} else {
			EditableGridSubmitLink.this.onError(target);
		}

	}
	@Override
	protected final void onError(AjaxRequestTarget target, Form<?> form) {
		EditableGridSubmitLink.this.onError(target);
	}

	private boolean formComponentActive(FormComponent<?> formComponent) {
		return formComponent.isVisibleInHierarchy()
				&& formComponent.isValid()
				&& formComponent.isEnabled()
				&& formComponent.isEnableAllowed();
	}
}
