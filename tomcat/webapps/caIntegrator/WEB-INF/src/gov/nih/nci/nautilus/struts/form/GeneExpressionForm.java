// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl
package gov.nih.nci.nautilus.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.util.LabelValueBean;

import java.util.*;
import java.lang.reflect.*;

import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.de.*;




/**
 * GeneExpressionForm.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 08-11-2004
 *
 * XDoclet definition:
 * @struts:form name="geneExpressionForm"
 */
public class GeneExpressionForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	/** geneList property */
	private String geneList;

	/** goClassification property */
	private String goClassification;

	/** goCellularComp property */
	private String goCellularComp;

	/** goMolecularFunction property */
	private String goMolecularFunction;

	/** goBiologicalProcess property */
	private String goBiologicalProcess;

	/** tumorGrade property */
	private String tumorGrade;

	/** region property */
	private String region;

	/** foldChangeValueDown property */
	private String foldChangeValueDown = "2";

	/** cytobandRegion property */
	private String cytobandRegion;

	/** cloneId property */
	private String cloneId;

	/** pathways property */
	private String pathways;

	/** tumorType property */
	private String tumorType;

	/** arrayPlatform property */
	private String arrayPlatform;

	/** cloneListFile property */
	private String cloneListFile;



	/** cloneListSpecify property */
	private String cloneListSpecify;


	/** basePairEnd property */
	private String basePairEnd;

	/** chrosomeNumber property */
	private String chrosomeNumber;

	/** regulationStatus property */
	private String regulationStatus;

	/** foldChangeValueUnchangeFrom property */
	private String foldChangeValueUnchangeFrom = "0.8";

	/** foldChangeValueUnchangeTo property */
	private String foldChangeValueUnchangeTo = "1.2";

	/** foldChangeValueUp property */
	private String foldChangeValueUp = "2";

	/** geneType property */
	private String geneType;

	/** foldChangeValueUDUp property */
	private String foldChangeValueUDUp;

	/** resultView property */
	private String resultView;

	/** geneFile property */
	private String geneFile;

	/** foldChangeValueUDDown property */
	private String foldChangeValueUDDown = "2";

	/** geneGroup property */
	private String geneGroup;

	/** cloneList property */
	private String cloneList;

	/** queryName property */
	private String queryName;

	/** basePairStart property */
	private String basePairStart;

	// Collections used for Lookup values.
	private ArrayList diseaseType;
	private ArrayList geneTypeColl;
	private ArrayList cloneTypeColl;
	private ArrayList arrayPlatformTypeColl;


	private DiseaseOrGradeCriteria diseaseOrGradeCriteria;
	private GeneIDCriteria geneCriteria;
	private FoldChangeCriteria foldChangeCriteria;
	private RegionCriteria regionCriteria;
	private CloneOrProbeIDCriteria cloneOrProbeIDCriteria;
	private GeneOntologyCriteria geneOntologyCriteria;
	private PathwayCriteria pathwayCriteria;
	private ArrayPlatformCriteria arrayPlatformCriteria;

	// UntranslatedRegionCriteria: for both 5' and 3', "included" is used as default,
	// on the jsp, it may be commented out for now
	private UntranslatedRegionCriteria untranslatedRegionCriteria;


// Hashmap to store Domain elements
    private HashMap diseaseDomainMap;
	private HashMap gradeDomainMap;// this one may not be used for this release.
	private HashMap geneDomainMap;
	private HashMap foldDomainMap;
	private HashMap regionDomainMap;
	private HashMap cloneDomainMap;
	private HashMap geneOntologyDomainMap;
	private HashMap pathwayDomainMap;
	private HashMap arrayPlatformDomainMap;
	private HashMap untranslatedRegionDomainMap;// this one may not be used for this release.

	private HttpServletRequest thisRequest;


	// --------------------------------------------------------- Methods
	public GeneExpressionForm(){

		// Create Lookups for Gene Expression screens
		setGeneExpressionLookup();

	}

	/**
	 * Method validate
	 * @param ActionMapping mapping
	 * @param HttpServletRequest request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		// Query Name cannot be blank
		if ((queryName == null || queryName.length() < 1))
			errors.add("queryName", new ActionError("gov.nih.nci.nautilus.struts.form.queryname.no.error"));

		// Chromosomal region validations
		if (this.getRegion().equalsIgnoreCase("chnum")) {

			if (basePairStart.length() > 0 || basePairEnd.length() > 0) {
				if (chrosomeNumber.length() < 1 || basePairStart.length() < 1 || (basePairEnd.length() < 1)) {
					errors.add("region", new ActionError("gov.nih.nci.nautilus.struts.form.basePair.no.error"));
				} else {
					if (!isBasePairValid(basePairStart, basePairEnd))
						errors.add("region", new ActionError("gov.nih.nci.nautilus.struts.form.basePair.incorrect.error"));
				}
			}
		}

		if(this.getGoClassification()!= null){
		  if(this.getGoMolecularFunction()== null && this.getGoCellularComp() == null && this.getGoBiologicalProcess() == null){
		     errors.add("Gene Ontology (GO) Classifications", new ActionError("error:gov.nih.nci.nautilus.struts.form.geneOntology.functions.required"));
			 }
		   }

		if (errors.isEmpty()) {
		    createDiseaseCriteriaObject();
			createGeneCriteriaObject();
			createFoldChangeCriteriaObject();
			createRegionCriteriaObject();
			createCloneOrProbeCriteriaObject();
			createGeneOntologyCriteriaObject();
			createPathwayCriteriaObject();
			createArrayPlatformCriteriaObject();
		}

		return errors;
	}


	private void createDiseaseCriteriaObject(){
	  //look thorugh the diseaseDomainMap to extract out the domain elements and create respective Criteria Objects
	  Set keys = diseaseDomainMap.keySet();
	  Iterator iter = keys.iterator();
	  while(iter.hasNext()){
	     Object key = iter.next();

		 try{
	        String strDiseaseDomainClass = (String)diseaseDomainMap.get(iter.next());//use key to get value
		    Constructor[] diseaseConstructors = Class.forName(strDiseaseDomainClass).getConstructors();
			Object [] parameterObjects = {key};
			DiseaseNameDE diseaseNameDEObj = (DiseaseNameDE) diseaseConstructors[0].newInstance(parameterObjects);
		    diseaseOrGradeCriteria.setDisease(diseaseNameDEObj);

		   }
		 catch (Exception ex) {
				System.out.println("Error in createDiseaseCriteriaObject  "+ex.getMessage());
				ex.printStackTrace();
			}
		  catch (LinkageError le) {
				System.out.println("Linkage Error in createDiseaseCriteriaObject "+ le.getMessage());
				le.printStackTrace();
			}
	      }
	  }
	private void createGeneCriteriaObject() {


		// Loop thru the HashMap, extract the Domain elements and create respective Criteria Objects
		Set keys = geneDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + geneDomainMap.get(key));

			try {
				String strgeneDomainClass = (String) geneDomainMap.get(key);
				Constructor [] geneConstructors = Class.forName(strgeneDomainClass).getConstructors();
				Object [] parameterObjects = {key};

				GeneIdentifierDE geneSymbolDEObj = (GeneIdentifierDE) geneConstructors[0].newInstance(parameterObjects);
				geneCriteria.setGeneIdentifier(geneSymbolDEObj);

				System.out.println("Gene Domain Element Value==> "+geneSymbolDEObj.getValueObject());
			} catch (Exception ex) {
				System.out.println("Error in createGeneCriteriaObject  "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createGeneCriteriaObject "+ le.getMessage());
				le.printStackTrace();
			}



			}


	}

	private void createFoldChangeCriteriaObject() {


		Set keys = foldDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + foldDomainMap.get(key));

			try {
				String strFoldDomainClass = (String) foldDomainMap.get(key);
				Constructor [] foldConstructors = Class.forName(strFoldDomainClass).getConstructors();
				Object [] parameterObjects = {Float.valueOf((String) key)};

				ExprFoldChangeDE foldChangeDEObj = (ExprFoldChangeDE) foldConstructors[0].newInstance(parameterObjects);
				foldChangeCriteria.setFoldChangeObject(foldChangeDEObj);

				System.out.println("Fold Change Domain Element Value is ==>"+foldChangeDEObj.getValueObject());


			} catch (Exception ex) {
				System.out.println("Error in createFoldChangeCriteriaObject  "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createFoldChangeCriteriaObject "+ le.getMessage());
				le.printStackTrace();
			}



			}

	}

	private void createRegionCriteriaObject() {


		Set keys = regionDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + regionDomainMap.get(key));

			try {
				String strRegionDomainClass = (String) regionDomainMap.get(key);
				Constructor [] regionConstructors = Class.forName(strRegionDomainClass).getConstructors();

				if (strRegionDomainClass.endsWith("CytobandDE")) {
					Object [] parameterObjects = {(String) key};
				    CytobandDE cytobandDEObj = (CytobandDE) regionConstructors[0].newInstance(parameterObjects);
					regionCriteria.setCytoband(cytobandDEObj);
					System.out.println("Test Cytoband Criteria" + regionCriteria.getCytoband().getValue());


				}
				if (strRegionDomainClass.endsWith("ChromosomeNumberDE")) {
					Object [] parameterObjects = {(String) key};
					ChromosomeNumberDE chromosomeDEObj = (ChromosomeNumberDE) regionConstructors[0].newInstance(parameterObjects);
					regionCriteria.setChromNumber(chromosomeDEObj);
					System.out.println("Test Chromosome Criteria " + regionCriteria.getChromNumber().getValue());
				}
				if (strRegionDomainClass.endsWith("StartPosition")) {
					Object [] parameterObjects = {Integer.valueOf((String) key)};
					BasePairPositionDE.StartPosition baseStartDEObj = (BasePairPositionDE.StartPosition) regionConstructors[0].newInstance(parameterObjects);
					regionCriteria.setStart(baseStartDEObj);
					System.out.println("Test Start Criteria" + regionCriteria.getStart().getValue());
				}
				if (strRegionDomainClass.endsWith("EndPosition")) {
					Object [] parameterObjects = {Integer.valueOf((String) key)};
					BasePairPositionDE.EndPosition baseEndDEObj = (BasePairPositionDE.EndPosition) regionConstructors[0].newInstance(parameterObjects);
					regionCriteria.setEnd(baseEndDEObj);
					System.out.println("Test End Criteria" + regionCriteria.getEnd().getValue());
				}


			} catch (Exception ex) {
				System.out.println("Error in createRegionCriteriaObject  "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createRegionCriteriaObject "+ le.getMessage());
				le.printStackTrace();
			}



			}

	}


	private void createCloneOrProbeCriteriaObject() {


		// Loop thru the cloneDomainMap HashMap, extract the Domain elements and create respective Criteria Objects
		Set keys = cloneDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + cloneDomainMap.get(key));

			try {
				String strCloneDomainClass = (String) cloneDomainMap.get(key);
				Constructor [] cloneConstructors = Class.forName(strCloneDomainClass).getConstructors();
				Object [] parameterObjects = {key};

				CloneIdentifierDE cloneIdentfierDEObj = (CloneIdentifierDE) cloneConstructors[0].newInstance(parameterObjects);
				cloneOrProbeIDCriteria.setCloneIdentifier(cloneIdentfierDEObj);

				System.out.println("Clone Domain Element Value==> "+cloneIdentfierDEObj.getValueObject());
			} catch (Exception ex) {
				System.out.println("Error in createGeneCriteriaObject  "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createGeneCriteriaObject "+ le.getMessage());
				le.printStackTrace();
			}



			}


	}


		private void createGeneOntologyCriteriaObject() {


		// Loop thru the geneOntologyDomainMap HashMap, extract the Domain elements and create respective Criteria Objects
		Set keys = geneOntologyDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + geneOntologyDomainMap.get(key));

			try {
				String strGeneOntologyDomainClass = (String) geneOntologyDomainMap.get(key);
				Constructor [] geneOntologyConstructors = Class.forName(strGeneOntologyDomainClass).getConstructors();
				Object [] parameterObjects = {key};

				GeneOntologyDE geneOntologyDEObj = (GeneOntologyDE)geneOntologyConstructors[0].newInstance(parameterObjects);
				geneOntologyCriteria.setGOIdentifier(geneOntologyDEObj);

				System.out.println("GO Domain Element Value==> "+geneOntologyDEObj.getValueObject());
			} catch (Exception ex) {
				System.out.println("Error in createGeneCriteriaObject  "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createGeneCriteriaObject "+ le.getMessage());
				le.printStackTrace();
			}


			}

	}

	private void createPathwayCriteriaObject() {


		// Loop thru the pathwayDomainMap HashMap, extract the Domain elements and create respective Criteria Objects
		Set keys = pathwayDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + pathwayDomainMap.get(key));

			try {
				String strPathwayDomainClass = (String) pathwayDomainMap.get(key);
				Constructor [] pathwayConstructors = Class.forName(strPathwayDomainClass).getConstructors();
				Object [] parameterObjects = {key};

				PathwayDE pathwayDEObj = (PathwayDE)pathwayConstructors[0].newInstance(parameterObjects);
				pathwayCriteria.setPathwayDE(pathwayDEObj);

				System.out.println("GO Domain Element Value==> "+pathwayDEObj.getValueObject());
			} catch (Exception ex) {
				System.out.println("Error in createGeneCriteriaObject  "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createGeneCriteriaObject "+ le.getMessage());
				le.printStackTrace();
			}
			}
	}



		private void createArrayPlatformCriteriaObject() {


		// Loop thru the pathwayDomainMap HashMap, extract the Domain elements and create respective Criteria Objects
		Set keys = arrayPlatformDomainMap.keySet();
		Iterator i = keys.iterator();
		while (i.hasNext()) {
			Object key = i.next();
			System.out.println(key + "=>" + arrayPlatformDomainMap.get(key));

			try {
				String strArrayPlatformDomainClass = (String) arrayPlatformDomainMap.get(key);
				Constructor [] arrayPlatformConstructors = Class.forName(strArrayPlatformDomainClass).getConstructors();
				Object [] parameterObjects = {key};

				ArrayPlatformDE arrayPlatformDEObj = (ArrayPlatformDE)arrayPlatformConstructors[0].newInstance(parameterObjects);
				arrayPlatformCriteria.setArrayPlatform(arrayPlatformDEObj);

				System.out.println("GO Domain Element Value==> "+arrayPlatformDEObj.getValueObject());
			} catch (Exception ex) {
				System.out.println("Error in createArrayPlatformCriteriaObject  "+ex.getMessage());
				ex.printStackTrace();
			} catch (LinkageError le) {
				System.out.println("Linkage Error in createArrayPlatformCriteriaObject "+ le.getMessage());
				le.printStackTrace();
			}

			}

	}
	private boolean isBasePairValid(String basePairStart, String basePairEnd) {

		int intBasePairStart;
		int intBasePairEnd;

		try {
			intBasePairStart = Integer.parseInt(basePairStart);
			intBasePairEnd = Integer.parseInt(basePairEnd);

		}
		catch (NumberFormatException e) {
			return false;
		}

		if (intBasePairStart >= intBasePairEnd) return false;
		return true;
	}

	public void setGeneExpressionLookup() {

		diseaseType = new ArrayList();
		geneTypeColl = new ArrayList();
		cloneTypeColl = new ArrayList();
		arrayPlatformTypeColl = new ArrayList();


		// These are hardcoded but will come from DB
		diseaseType.add( new LabelValueBean( "Astrocytic", "astro" ) );
		diseaseType.add( new LabelValueBean( "Oligodendroglial", "oligo" ) );
		diseaseType.add( new LabelValueBean( "Ependymal cell", "Ependymal cell" ) );
		diseaseType.add( new LabelValueBean( "Mixed gliomas", "Mixed gliomas" ) );
		diseaseType.add( new LabelValueBean( "Neuroepithelial", "Neuroepithelial" ) );
		diseaseType.add( new LabelValueBean( "Choroid Plexus", "Choroid Plexus" ) );
		diseaseType.add( new LabelValueBean( "Neuronal and mixed neuronal-glial", "neuronal-glial" ) );
		diseaseType.add( new LabelValueBean( "Pineal Parenchyma", "Pineal Parenchyma" ));
		diseaseType.add( new LabelValueBean( "Embryonal", "Embryonal" ));
		diseaseType.add( new LabelValueBean( "Glioblastoma", "Glioblastoma" ));


		geneTypeColl.add( new LabelValueBean( "All Genes", "allgenes" ) );
		geneTypeColl.add( new LabelValueBean( "Name/Symbol", "genesymbol" ) );
		geneTypeColl.add( new LabelValueBean( "Locus Link Id", "genelocus" ) );
		geneTypeColl.add( new LabelValueBean( "GenBank AccNo.", "genbankno" ) );

		cloneTypeColl.add( new LabelValueBean( "IMAGE Id", "TSCId" ) );
		cloneTypeColl.add( new LabelValueBean( "BAC Id", "BACId" ) );
		cloneTypeColl.add( new LabelValueBean( "Probe Set Id", "probeSetId" ) );

		arrayPlatformTypeColl.add( new LabelValueBean( "all", "all" ) );
		arrayPlatformTypeColl.add( new LabelValueBean( "Oligo (Affymetrix)", "Oligo (Affymetrix)" ) );
		arrayPlatformTypeColl.add( new LabelValueBean( "cDNA", "cDNA" ) );

	}


	/**
	 * Method reset.
	 * Reset all properties to their default values.
	 * @param ActionMapping mapping used to select this instance.
	 * @param HttpServletRequest request The servlet request we are processing.
	 */

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		geneList = "";
		goBiologicalProcess = "";
		tumorGrade = "";
		region = "";
		foldChangeValueDown = "2";
		cytobandRegion = "";
		cloneId = "";
		pathways = "";
		tumorType = "";
		arrayPlatform = "";
		cloneListFile = "";
		goCellularComp = "";
		goMolecularFunction = "";
		cloneListSpecify = "";
		goClassification = "";
		basePairEnd = "";
		chrosomeNumber = "";
		regulationStatus = "";
		foldChangeValueUnchangeFrom = "0.8";
		foldChangeValueUnchangeTo = "1.2";
		foldChangeValueUp = "2";
		geneType = "";
		foldChangeValueUDUp = "";
		resultView = "";
		geneFile = "";
		foldChangeValueUDDown = "2";
		geneGroup = "";
		cloneList = "";
		queryName = "";
		basePairStart = "";

		//Set the Request Object
		this.thisRequest = request;

		diseaseDomainMap = new HashMap();
		gradeDomainMap = new HashMap();// this one may not be used for this release.
		geneDomainMap = new HashMap();
		foldDomainMap = new HashMap();
		regionDomainMap = new HashMap();
		cloneDomainMap = new HashMap();
		geneOntologyDomainMap = new HashMap();
		pathwayDomainMap = new HashMap();
		arrayPlatformDomainMap = new HashMap();
		untranslatedRegionDomainMap = new HashMap();// this one may not be used for this release.

		diseaseOrGradeCriteria = new DiseaseOrGradeCriteria();
		geneCriteria = new GeneIDCriteria();
		foldChangeCriteria = new FoldChangeCriteria();
		regionCriteria = new RegionCriteria();
		cloneOrProbeIDCriteria = new CloneOrProbeIDCriteria();
		geneOntologyCriteria = new GeneOntologyCriteria();
		pathwayCriteria = new PathwayCriteria();
		arrayPlatformCriteria = new ArrayPlatformCriteria();

	}

	/**
	 * Returns the geneList.
	 * @return String
	 */
	public String getGeneList() {

		return geneList;
	}

	/**
	 * Set the geneList.
	 * @param geneList The geneList to set
	 */
	public void setGeneList(String geneList) {
		this.geneList = geneList;

		String thisGeneType = this.thisRequest.getParameter("geneType");
		String thisGeneGroup = this.thisRequest.getParameter("geneGroup");

		if ((thisGeneGroup != null) && thisGeneGroup.equalsIgnoreCase("Specify") && (thisGeneType.length() >0) && (this.geneList.length() > 0)){

			String [] splitValue = this.geneList.split("\\x2C");

			for (int i = 0; i < splitValue.length; i++) {

				if (thisGeneType.equalsIgnoreCase("genesymbol")){
					geneDomainMap.put(splitValue[i], GeneIdentifierDE.GeneSymbol.class.getName());
				} else
				if (thisGeneType.equalsIgnoreCase("genelocus")){
					geneDomainMap.put(splitValue[i], GeneIdentifierDE.LocusLink.class.getName());
				} else
				if (thisGeneType.equalsIgnoreCase("genbankno")){
					geneDomainMap.put(splitValue[i], GeneIdentifierDE.GenBankAccessionNumber.class.getName());
				}
			}
		}

		// Set for all genes
		if (thisGeneGroup != null && thisGeneGroup.equalsIgnoreCase("Specify") && (thisGeneType.equalsIgnoreCase("allgenes"))){
			geneDomainMap.put("allgenes", GeneIdentifierDE.GeneSymbol.class.getName());
		}
	}


	public GeneIDCriteria getGeneIDCriteria() {
		return this.geneCriteria;
	}
	public FoldChangeCriteria getFoldChangeCriteria() {
		return this.foldChangeCriteria;
	}
	public RegionCriteria getRegionCriteria() {
		return this.regionCriteria;
	}

	public DiseaseOrGradeCriteria getDiseaseOrGradeCriteria() {
		return this.diseaseOrGradeCriteria;
	}
	public CloneOrProbeIDCriteria getCloneOrProbeIDCriteria() {
		return this.cloneOrProbeIDCriteria;
	}
	public GeneOntologyCriteria getGeneOntologyCriteria() {
		return this.geneOntologyCriteria;
	}

	public PathwayCriteria getPathwayCriteria() {
		return this.pathwayCriteria;
	}
	public ArrayPlatformCriteria getArrayPlatformCriteria() {
		return this.arrayPlatformCriteria;
	}



	/**
	 * Returns the goBiologicalProcess.
	 * @return String
	 */
	public String getGoBiologicalProcess() {
		return goBiologicalProcess;
	}

	/**
	 * Set the goBiologicalProcess.
	 * @param goBiologicalProcess The goBiologicalProcess to set
	 */
	public void setGoBiologicalProcess(String goBiologicalProcess) {
		this.goBiologicalProcess = goBiologicalProcess;
	}

	/**
	 * Returns the tumorGrade.
	 * @return String
	 */
	public String getTumorGrade() {
		return tumorGrade;
	}

	/**
	 * Set the tumorGrade.
	 * @param tumorGrade The tumorGrade to set
	 */
	public void setTumorGrade(String tumorGrade) {
		this.tumorGrade = tumorGrade;
		gradeDomainMap.put(this.tumorGrade, GradeDE.class.getName());
	}

	/**
	 * Returns the region.
	 * @return String
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Set the region.
	 * @param region The region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Returns the foldChangeValueDown.
	 * @return String
	 */
	public String getFoldChangeValueDown() {
		return foldChangeValueDown;
	}

	/**
	 * Set the foldChangeValueDown.
	 * @param foldChangeValueDown The foldChangeValueDown to set
	 */
	public void setFoldChangeValueDown(String foldChangeValueDown) {
		this.foldChangeValueDown = foldChangeValueDown;

		String thisRegulationStatus = this.thisRequest.getParameter("regulationStatus");

		if (thisRegulationStatus != null && thisRegulationStatus.equalsIgnoreCase("down") && (this.foldChangeValueDown.length() > 0))

			foldDomainMap.put(this.foldChangeValueDown, ExprFoldChangeDE.DownRegulation.class.getName());


	}

	/**
	 * Returns the cytobandRegion.
	 * @return String
	 */
	public String getCytobandRegion() {
		return cytobandRegion;
	}

	/**
	 * Set the cytobandRegion.
	 * @param cytobandRegion The cytobandRegion to set
	 */
	public void setCytobandRegion(String cytobandRegion) {
		this.cytobandRegion = cytobandRegion;

		String thisRegion = this.thisRequest.getParameter("region");

		if (thisRegion != null && thisRegion.equalsIgnoreCase("cytoband"))

			regionDomainMap.put(this.cytobandRegion, CytobandDE.class.getName());

	}

	/**
	 * Returns the cloneId.
	 * @return String
	 */
	public String getCloneId() {
		return cloneId;
	}

	/**
	 * Set the cloneId.
	 * @param cloneId The cloneId to set
	 */
	public void setCloneId(String cloneId) {
		this.cloneId = cloneId;
	}

	/**
	 * Returns the pathways.
	 * @return String
	 */
	public String getPathways() {
		return pathways;
	}

	/**
	 * Set the pathways.
	 * @param pathways The pathways to set
	 */
	public void setPathways(String pathways) {
		this.pathways = pathways;
		pathwayDomainMap.put(this.pathways, PathwayDE.class.getName());
	}

	/**
	 * Returns the tumorType.
	 * @return String
	 */
	public String getTumorType() {
		return tumorType;
	}

	/**
	 * Set the tumorType.
	 * @param tumorType The tumorType to set
	 */
	public void setTumorType(String tumorType) {
		this.tumorType = tumorType;
		diseaseDomainMap.put(this.tumorType, DiseaseNameDE.class.getName());
	}

	/**
	 * Returns the arrayPlatform.
	 * @return String
	 */
	public String getArrayPlatform() {
		return arrayPlatform;
	}

	/**
	 * Set the arrayPlatform.
	 * @param arrayPlatform The arrayPlatform to set
	 */
	public void setArrayPlatform(String arrayPlatform) {
		this.arrayPlatform = arrayPlatform;
		arrayPlatformDomainMap.put(this.arrayPlatform, ArrayPlatformDE.class.getName());
	}

	/**
	 * Returns the cloneListFile.
	 * @return String
	 */
	public String getCloneListFile() {
		return cloneListFile;
	}

	/**
	 * Set the cloneListFile.
	 * @param cloneListFile The cloneListFile to set
	 */
	public void setCloneListFile(String cloneListFile) {
		this.cloneListFile = cloneListFile;
	}

	/**
	 * Returns the goCellularComp.
	 * @return String
	 */
	public String getGoCellularComp() {
		return goCellularComp;
	}

	/**
	 * Set the goCellularComp.
	 * @param goCellularComp The goCellularComp to set
	 */
	public void setGoCellularComp(String goCellularComp) {
		this.goCellularComp = goCellularComp;
	}

	/**
	 * Returns the goMolecularFunction.
	 * @return String
	 */
	public String getGoMolecularFunction() {
		return goMolecularFunction;
	}

	/**
	 * Set the goMolecularFunction.
	 * @param goMolecularFunction The goMolecularFunction to set
	 */
	public void setGoMolecularFunction(String goMolecularFunction) {
		this.goMolecularFunction = goMolecularFunction;
	}

	/**
	 * Returns the cloneListSpecify.
	 * @return String
	 */
	public String getCloneListSpecify() {
		return cloneListSpecify;
	}

	/**
	 * Set the cloneListSpecify.
	 * @param cloneListSpecify The cloneListSpecify to set
	 */
	public void setCloneListSpecify(String cloneListSpecify) {
		this.cloneListSpecify = cloneListSpecify;
		  this.cloneListSpecify = cloneListSpecify;

		   // this is to check if the radio button is selected for the clone category
		   String thisCloneId = (String)thisRequest.getParameter("cloneId");

		   // this is to check the type of the clone
		   String thisCloneList = (String)thisRequest.getParameter("cloneList");

		   if(thisCloneId != null && thisCloneList != null && !thisCloneList.equals("")){
		      if(this.cloneListSpecify != null && !cloneListSpecify.equals("")){
			    String [] cloneStr = cloneListSpecify.split("\\x2C");
				for(int i=0; i<cloneStr.length; i++){
				  if(thisCloneList.equalsIgnoreCase("imageId")){
				     cloneDomainMap.put(cloneStr[i],CloneIdentifierDE.IMAGEClone.class.getName());
					}
				  else if(thisCloneList.equalsIgnoreCase("BACId")){
				     cloneDomainMap.put(cloneStr[i],CloneIdentifierDE.BACClone.class.getName());
					}
				  else if(thisCloneList.equalsIgnoreCase("probeSetId")){
				     cloneDomainMap.put(cloneStr[i],CloneIdentifierDE.ProbesetID.class.getName());
					}

				  }	 // end of for loop
			   }// end of  if(this.cloneListSpecify != null && !cloneListSpecify.equals("")){

		    }
	}

	/**
	 * Returns the goClassification.
	 * @return String
	 */
	public String getGoClassification() {
		return goClassification;
	}

	/**
	 * Set the goClassification.
	 * @param goClassification The goClassification to set
	 */
	public void setGoClassification(String goClassification) {
		this.goClassification = goClassification;
		geneOntologyDomainMap.put(this.goClassification,GeneOntologyDE.class.getName());

	}

	/**
	 * Returns the basePairEnd.
	 * @return String
	 */
	public String getBasePairEnd() {
		return basePairEnd;
	}

	/**
	 * Set the basePairEnd.
	 * @param basePairEnd The basePairEnd to set
	 */
	public void setBasePairEnd(String basePairEnd) {
		this.basePairEnd = basePairEnd;

		String thisRegion = this.thisRequest.getParameter("region");

		if (thisRegion != null && thisRegion.equalsIgnoreCase("chnum") && this.basePairEnd.length() > 0)

			regionDomainMap.put(this.basePairEnd, BasePairPositionDE.EndPosition.class.getName());

	}

	/**
	 * Returns the chrosomeNumber.
	 * @return String
	 */
	public String getChrosomeNumber() {
		return chrosomeNumber;
	}

	/**
	 * Set the chrosomeNumber.
	 * @param chrosomeNumber The chrosomeNumber to set
	 */
	public void setChrosomeNumber(String chrosomeNumber) {
		this.chrosomeNumber = chrosomeNumber;

		String thisRegion = this.thisRequest.getParameter("region");

		if (thisRegion != null && thisRegion.equalsIgnoreCase("chnum"))

			regionDomainMap.put(this.chrosomeNumber, ChromosomeNumberDE.class.getName());

	}

	/**
	 * Returns the regulationStatus.
	 * @return String
	 */
	public String getRegulationStatus() {
		return regulationStatus;
	}

	/**
	 * Set the regulationStatus.
	 * @param regulationStatus The regulationStatus to set
	 */
	public void setRegulationStatus(String regulationStatus) {
		this.regulationStatus = regulationStatus;
	}

	/**
	 * Returns the foldChangeValueUnchange.
	 * @return String
	 */
	public String getFoldChangeValueUnchangeFrom() {
		return foldChangeValueUnchangeFrom;
	}

	/**
	 * Set the foldChangeValueUnchange.
	 * @param foldChangeValueUnchange The foldChangeValueUnchange to set
	 */
	public void setFoldChangeValueUnchangeFrom(String foldChangeValueUnchangeFrom) {
		this.foldChangeValueUnchangeFrom = foldChangeValueUnchangeFrom;
		String thisRegulationStatus = this.thisRequest.getParameter("regulationStatus");

		if (thisRegulationStatus != null && thisRegulationStatus.equalsIgnoreCase("unchange") && (this.foldChangeValueUnchangeFrom.length() > 0))

			foldDomainMap.put(this.foldChangeValueUnchangeFrom, ExprFoldChangeDE.UnChangedRegulationDownLimit.class.getName());
	}

	/**
	 * Returns the foldChangeValueUp.
	 * @return String
	 */
	/**
	 * Returns the foldChangeValueUnchange.
	 * @return String
	 */
	public String getFoldChangeValueUnchangeTo() {
		return foldChangeValueUnchangeTo;
	}

	/**
	 * Set the foldChangeValueUnchange.
	 * @param foldChangeValueUnchange The foldChangeValueUnchange to set
	 */
	public void setFoldChangeValueUnchangeTo(String foldChangeValueUnchangeTo) {
		this.foldChangeValueUnchangeTo = foldChangeValueUnchangeTo;
		String thisRegulationStatus = this.thisRequest.getParameter("regulationStatus");

		if (thisRegulationStatus != null && thisRegulationStatus.equalsIgnoreCase("unchange") && (this.foldChangeValueUnchangeTo.length() > 0)) {
			foldDomainMap.put(this.foldChangeValueUnchangeTo, ExprFoldChangeDE.UnChangedRegulationUpperLimit.class.getName());
		}
	}

	/**
	 * Returns the foldChangeValueUp.
	 * @return String
	 */
	public String getFoldChangeValueUp() {
		return foldChangeValueUp;
	}

	/**
	 * Set the foldChangeValueUp.
	 * @param foldChangeValueUp The foldChangeValueUp to set
	 */
	public void setFoldChangeValueUp(String foldChangeValueUp) {
		this.foldChangeValueUp = foldChangeValueUp;
		String thisRegulationStatus = this.thisRequest.getParameter("regulationStatus");

		if (thisRegulationStatus != null && thisRegulationStatus.equalsIgnoreCase("up") && (this.foldChangeValueUp.length() > 0))

			foldDomainMap.put(this.foldChangeValueUp, ExprFoldChangeDE.UpRegulation.class.getName());


	}

	/**
	 * Returns the geneType.
	 * @return String
	 */
	public String getGeneType() {
		return geneType;
	}

	/**
	 * Set the geneType.
	 * @param geneType The geneType to set
	 */
	public void setGeneType(String geneType) {
		this.geneType = geneType;


	}

	/**
	 * Returns the foldChangeValueUDUp.
	 * @return String
	 */
	public String getFoldChangeValueUDUp() {
		return foldChangeValueUDUp;
	}

	/**
	 * Set the foldChangeValueUDUp.
	 * @param foldChangeValueUDUp The foldChangeValueUDUp to set
	 */
	public void setFoldChangeValueUDUp(String foldChangeValueUDUp) {
		this.foldChangeValueUDUp = foldChangeValueUDUp;

		String thisRegulationStatus = this.thisRequest.getParameter("regulationStatus");

		if (thisRegulationStatus != null && thisRegulationStatus.equalsIgnoreCase("updown") && (this.foldChangeValueUDUp.length() > 0))

			foldDomainMap.put(this.foldChangeValueUDUp, ExprFoldChangeDE.UpRegulation.class.getName());
	}

	/**
	 * Returns the resultView.
	 * @return String
	 */
	public String getResultView() {
		return resultView;
	}

	/**
	 * Set the resultView.
	 * @param resultView The resultView to set
	 */
	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	/**
	 * Returns the geneFile.
	 * @return String
	 */
	public String getGeneFile() {
		return geneFile;
	}

	/**
	 * Set the geneFile.
	 * @param geneFile The geneFile to set
	 */
	public void setGeneFile(String geneFile) {
		this.geneFile = geneFile;
	}

	/**
	 * Returns the foldChangeValueUDDown.
	 * @return String
	 */
	public String getFoldChangeValueUDDown() {
		return foldChangeValueUDDown;
	}

	/**
	 * Set the foldChangeValueUDDown.
	 * @param foldChangeValueUDDown The foldChangeValueUDDown to set
	 */
	public void setFoldChangeValueUDDown(String foldChangeValueUDDown) {
		this.foldChangeValueUDDown = foldChangeValueUDDown;
		String thisRegulationStatus = this.thisRequest.getParameter("regulationStatus");

		if (thisRegulationStatus != null && thisRegulationStatus.equalsIgnoreCase("updown") && (this.foldChangeValueUDDown.length() > 0))

			foldDomainMap.put(this.foldChangeValueUDDown, ExprFoldChangeDE.DownRegulation.class.getName());
	}

	/**
	 * Returns the geneGroup.
	 * @return String
	 */
	public String getGeneGroup() {
		return geneGroup;
	}

	/**
	 * Set the geneGroup.
	 * @param geneGroup The geneGroup to set
	 */
	public void setGeneGroup(String geneGroup) {
		this.geneGroup = geneGroup;
	}

	/**
	 * Returns the cloneList.
	 * @return String
	 */
	public String getCloneList() {
		return cloneList;
	}

	/**
	 * Set the cloneList.
	 * @param cloneList The cloneList to set
	 */
	public void setCloneList(String cloneList) {
		this.cloneList = cloneList;
	}

	/**
	 * Returns the queryName.
	 * @return String
	 */
	public String getQueryName() {
		return queryName;
	}

	/**
	 * Set the queryName.
	 * @param queryName The queryName to set
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	/**
	 * Returns the basePairStart.
	 * @return String
	 */
	public String getBasePairStart() {
		return basePairStart;
	}

	/**
	 * Set the basePairStart.
	 * @param basePairStart The basePairStart to set
	 */
	public void setBasePairStart(String basePairStart) {
		this.basePairStart = basePairStart;

		String thisRegion = this.thisRequest.getParameter("region");

		if (thisRegion != null && thisRegion.equalsIgnoreCase("chnum") && this.basePairStart.length() > 0)

			regionDomainMap.put(this.basePairStart, BasePairPositionDE.StartPosition.class.getName());

	}
// Getter methods for Gene Expression Lookup
	public ArrayList getDiseaseType()
	{ return diseaseType; }

	public ArrayList getGeneTypeColl()
	{ return geneTypeColl; }

}
