package gov.nih.nci.nautilus.ui.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


public class QuickSearchForm extends BaseForm {
	private String plot = null;

	private String quickSearchName = null;

	public String getPlot() {
		return plot;
	}

	public void setPlot(String str) {
		plot = str;
	}

	public void setQuickSearchName(String str) {
		this.quickSearchName = str;
	}

	public String getQuickSearchName() {
		return this.quickSearchName;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
	    ActionErrors errors = new ActionErrors();
	    UIFormValidator.validateGeneSymbol(quickSearchName, errors);
		return errors;

	}
	
}