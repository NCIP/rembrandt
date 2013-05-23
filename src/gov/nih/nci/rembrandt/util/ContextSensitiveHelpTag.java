package gov.nih.nci.rembrandt.util;

import gov.nih.nci.rembrandt.web.struts.action.QuickSearchAction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

/**
 * Custom tag for context sensitive help.
 */
public class ContextSensitiveHelpTag implements Tag, Serializable {

	private static final long serialVersionUID = 5618297483211863400L;
	
	private static Logger logger = Logger.getLogger(QuickSearchAction.class);

	private PageContext myPageContext = null;

	private Tag myParent = null;

	// Tag attributes
	private String myKey = null;

	private String myImage = "images/help.png";

	private String myLabelName = null;

	private String myHref = null;

	private String myTopic = null;

	private String myBundle = "ContextSensitiveHelp";

	private String myStyleClass = "helpText";

	private String myJavascriptKey = "help_javascript";
	
	private String wikiSiteBeginKey = "wiki_help_main";
	
	private String myStyle = "cursor:pointer;border:0px;padding:2px;";

	public void setPageContext(PageContext inPageContext) {
		myPageContext = inPageContext;
	}

	public void setParent(Tag inParent) {
		myParent = inParent;
	}

	public Tag getParent() {
		return myParent;
	}

	public void setTopic(String inTopic) {
		myTopic = inTopic;
	}

	public String getTopic() {
		return myTopic;
	}

	/**
	 * Sets the key attribute. This is included in the tld file.
	 * 
	 * @jsp.attribute description="The key attribute used to look up the value
	 *                in the properties file"
	 * 
	 * required="true"
	 * 
	 * rtexprvalue="false"
	 */
	public void setKey(String inKey) {
		myKey = inKey;
	}

	public String getKey() {
		return myKey;
	}

	/**
	 * Sets the Image attribute. This is included in the tld file.
	 * 
	 * @jsp.attribute description="The key attribute used to look up the value
	 *                in the properties file"
	 * 
	 * required="false"
	 * 
	 * rtexprvalue="false"
	 */
	public void setImage(String inKey) {
		myImage = inKey;
	}

	public String getImage() {
		return myImage;
	}

	/**
	 * Sets the text attribute. This is included in the tld file.
	 * 
	 * @jsp.attribute description="The text the CS help will be for"
	 * 
	 * required="true"
	 * 
	 * rtexprvalue="false"
	 */
	public void setText(String inLabelName) {
		myLabelName = inLabelName;
	}

	public String getText() {
		return myLabelName;
	}

	public String getHref() {
		return myHref;
	}

	/**
	 * Sets the href attribute. This is included in the tld file.
	 * 
	 * @jsp.attribute description="Where to go when the text is clicked.
	 *                Currently not implemented"
	 * 
	 * required="false"
	 * 
	 * rtexprvalue="false"
	 */
	public void setHref(String inHref) {
		myHref = inHref;
	}

	public String getBundle() {
		return myBundle;
	}

	/**
	 * Sets the bundle attribute. This is included in the tld file.
	 * 
	 * @jsp.attribute description="What bundle to use for the key lookup.
	 *                Currently defaults to ContextSensitiveHelp.properties"
	 * 
	 * required="false"
	 * 
	 * rtexprvalue="false"
	 */
	public void setBundle(String inBundle) {
		myBundle = inBundle;
	}

	public String getStyleClass() {
		return myStyleClass;
	}

	/**
	 * Sets the styleClass. This is included in the tld file.
	 * 
	 * @jsp.attribute description="What style to use for the popup. Currently
	 *                defaults to style_0"
	 * 
	 * required="false"
	 * 
	 * rtexprvalue="false"
	 */
	public void setStyleClass(String inStyleClass) {
		myStyleClass = inStyleClass;
	}
	
	

	public String getStyle() {
		return myStyle;
	}

	public void setStyle(String myStyle) {
		this.myStyle = myStyle;
	}

	public int doStartTag() throws JspException {

		try {
			String theHref = "";
			String wikiSiteBegin = "";
			String theJavascript = "";
			String theText = "";
			String theStyleClass = "";
			String imageHref = "";
			try {
				// Get the text
//				ResourceBundle theBundle = ResourceBundle.getBundle(myBundle);

				// Process optional attributes
				if (myTopic != null) {
					String theTopic = "";
//					String theJavascript = theBundle.getString(myJavascriptKey);
					
					if (myTopic.equals( "geneexpression"))
						myTopic = "Advanced_gene_expression";
					else if (myTopic.equals( "comparitivegenomic" ))
						myTopic = "Advanced_copy_number";
					else if (myTopic.equals( "clinical" ))
						myTopic = "Advanced_clinical_data";
					else if (myTopic.equals( "classcomparison" ))
						myTopic = "Class_comparison";
					else if (myTopic.equals( "principalcomponent" ))
						myTopic = "PCA_analysis";
					else if (myTopic.equals( "hierarchicalclustering" ))
						myTopic = "HCA_analysis";
					else if (myTopic.equals( "gpintegration" ))
						myTopic = "GenePattern_help";
					else if (myTopic.equals( "Clinical" ))
						myTopic = "Clinical_report";
					else if (myTopic.equals( "clinicalPlot" ))
						myTopic = "Clinical_plot";
					else if (myTopic.equals( "Gene Expression Sample" ))
						myTopic = "Gene_Expression_Sample";
					else if (myTopic.equals( "Gene Expression Disease" ))
						myTopic = "Gene_Expression_Disease";
					else if (myTopic.equals( "Copy Number" ))
						myTopic = "Copy_Number_Sample";
					else if (myTopic.equals( "Class Comparison" ))
						myTopic = "Class_comparison";
					
					Properties wikihelpProperties = new Properties();
					try {

						String wikihelpPropertiesFileName = null;

						wikihelpPropertiesFileName = System.getProperty("gov.nih.nci.rembrandt.wikihelpProperties");
						
						try {
						
						FileInputStream in = new FileInputStream(wikihelpPropertiesFileName);
						wikihelpProperties.load(in);
				
						} 
						catch (FileNotFoundException e) {
							logger.error("Caught exception finding file for properties: ", e);
							e.printStackTrace();			
						} catch (IOException e) {
							logger.error("Caught exception finding file for properties: ", e);
							e.printStackTrace();			
						}
						theJavascript = wikihelpProperties.getProperty(myJavascriptKey);
//						theText = wikihelpProperties.getProperty(myKey);
						theStyleClass = wikihelpProperties.getProperty(myStyleClass);
						wikiSiteBegin =  wikihelpProperties.getProperty(wikiSiteBeginKey);
						theTopic = wikihelpProperties.getProperty(myTopic);
					}

					// Default to 100 on an exception
					catch (Exception e) {
						System.err.println("Error loading system.properties file");
						e.printStackTrace();
					}
					theHref = "href=\"" + theJavascript + wikiSiteBegin + theTopic + "')\"";
					imageHref = theJavascript + wikiSiteBegin + theTopic + "')";
				}

				if (myLabelName == null) {
					/*					myPageContext.getOut().write(
					"<a " + theHref + " onMouseOver=\"stm(" + theText
							+ "," + theStyleClass
							+ ")\" onMouseOut=\"htm();\"><img alt=\"Help\" src=\""
							+ myImage + "\" border=\"0\"/>" + "</a>");
							*/
					
					myPageContext.getOut().write(
					"<img align=\"right\" onclick=\"" + imageHref + "\" name=\"helpIcon\" id=\"helpIcon\" alt=\"help\" title=\"help\" src=\"/rembrandt/images/help.png\" style=\"" + myStyle + "\">");					


				} else {
					myPageContext.getOut().write(
							"<a style=\"" + myStyle + "\"" + theHref + ">"
									+ myLabelName + "</a>");
				}

			} catch (Exception e) {
				e.printStackTrace();

				System.out.println("Can't get bundle. Ignore tooltip");
				// Can't get bundle. Ignore tooltip
				myPageContext.getOut().write(
						"<a " + theHref + " \">" + myLabelName + "</a>");
			}

		} catch (IOException e) {
			throw new JspTagException("An IOException occurred.");
		} catch (Exception e) {
			throw new JspTagException("An unknown exception occurred.");
		}

		return SKIP_BODY;

	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public void release() {
		myPageContext = null;
		myParent = null;
		myKey = null;
	}
	
	/* This method is used during the XSL transformation to show reports help link */
	public static String getHelpLink(String myTopic) {
		String theHref = "";
		String wikiSiteBegin = "";
		String theJavascript = "";
		String theText = "";
		String theStyleClass = "";
		String imageHref = "";
		String helpLink = "";

	try {	
		if (myTopic != null) {
			String theTopic = "";
			
			if (myTopic.equals( "Clinical" ))
				myTopic = "Clinical_report";
			else if (myTopic.equals( "clinicalPlot" ))
				myTopic = "Clinical_plot";
			else if (myTopic.equals( "Gene Expression Sample" ))
				myTopic = "Gene_Expression_Sample";
			else if (myTopic.equals( "Gene Expression Disease" ))
				myTopic = "Gene_Expression_Disease";
			else if (myTopic.equals( "Copy Number" ))
				myTopic = "Copy_Number_Sample";
			else if (myTopic.equals( "Class Comparison" ))
				myTopic = "Class_comparison";
			
			Properties wikihelpProperties = new Properties();
			try {

				String wikihelpPropertiesFileName = null;

				wikihelpPropertiesFileName = System.getProperty("gov.nih.nci.rembrandt.wikihelpProperties");
				
				try {
				
				FileInputStream in = new FileInputStream(wikihelpPropertiesFileName);
				wikihelpProperties.load(in);
		
				} 
				catch (FileNotFoundException e) {
					logger.error("Caught exception finding file for properties: ", e);
					e.printStackTrace();			
				} catch (IOException e) {
					logger.error("Caught exception finding file for properties: ", e);
					e.printStackTrace();			
				}
				theJavascript = wikihelpProperties.getProperty("help_javascript");
				wikiSiteBegin =  wikihelpProperties.getProperty("wiki_help_main");
				theTopic = wikihelpProperties.getProperty(myTopic);
			}

			catch (Exception e) {
				System.err.println("Error loading system.properties file");
				e.printStackTrace();
			}
			//theHref = "href=\"" + theJavascript + wikiSiteBegin + theTopic + "')\"";
			//imageHref = theJavascript + wikiSiteBegin + theTopic + "')";
			theHref = wikiSiteBegin + theTopic;
		}

		//helpLink = "<img align=\"right\" onclick=\"" + imageHref + "\" name=\"helpIcon\" id=\"helpIcon\" alt=\"help\" title=\"help\" src=\"/rembrandt/images/help.png\" style=\"" + "cursor:pointer;border:0px;padding:2px;" + "\">";
		helpLink = theHref;

	} catch (Exception e) {
		e.printStackTrace();

		System.out.println("Can't get bundle. Ignore tooltip");
	}
	
	return helpLink;
 }


}


