package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.de.GradeDE;
import gov.nih.nci.nautilus.de.OccurrenceDE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class encapsulates both Disease and Grade criteria. It contains the
 * collections of both DiseaseNameDE & GradeDE.
 * 
 * @author Dana Zhang, BauerD
 */

public class DiseaseOrGradeCriteria extends Criteria implements Serializable,
		Cloneable {
	/**
	 * IMPORTANT! This class requires a clone method! This requires that any new
	 * data field that is added to this class also be cloneable and be added to
	 * clone calls in the clone method.If you do not do this, you will not
	 * seperate the references of at least one data field when we generate a
	 * copy of this object.This means that if the data field ever changes in one
	 * copy or the other it will affect both instances... this will be hell to
	 * track down if you aren't ultra familiar with the code base, so add those
	 * methods now! (Not necesary for primitives.)
	 */
	private Collection diseases;

	private Collection grades;

	public DiseaseOrGradeCriteria() {
	}

	// this is to deal with single disease entry
	public void setDisease(DiseaseNameDE diseaseNameDE) {
		if (diseaseNameDE != null) {
			getDiseaseMembers().add(diseaseNameDE);
		}
	}

	// this is to deal with multiple disease entries
	public void setDiseases(Collection multiDiseases) {
		if (multiDiseases != null) {
			Iterator iter = multiDiseases.iterator();
			while (iter.hasNext()) {
				DiseaseNameDE diseaseNamede = (DiseaseNameDE) iter.next();
				getDiseaseMembers().add(diseaseNamede);
			}
		}
	}

	private Collection getDiseaseMembers() {
		if (diseases == null) {
			diseases = new ArrayList();
		}
		return diseases;
	}

	public Collection getDiseases() {
		return diseases;
	}

	// this is to deal with single grade entry
	public void setGrade(GradeDE gradeDE) {
		if (gradeDE != null) {
			getGradeMembers().add(gradeDE);
		}
	}

	// this is to deal with multiple grade entries
	public void setGrades(Collection multiGrades) {
		if (multiGrades != null) {
			Iterator iter = multiGrades.iterator();
			while (iter.hasNext()) {
				GradeDE gradede = (GradeDE) iter.next();
				getGradeMembers().add(gradede);
			}
		}
	}

	private Collection getGradeMembers() {
		if (grades == null) {
			grades = new ArrayList();
		}
		return grades;
	}

	public Collection getGrades() {
		return grades;
	}

	public boolean isValid() {
		// need to have a disease entry(entries) first
		if (diseases == null || grades == null) {
			return false;
		} else if (diseases != null & diseases.isEmpty()) {
			return false;
		}
		return true;
	}
	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		DiseaseOrGradeCriteria myClone = null;
		myClone = (DiseaseOrGradeCriteria) super.clone();
		if(diseases!=null) {
			myClone.diseases = new ArrayList();
			for (Iterator i = diseases.iterator(); i.hasNext();) {
				myClone.diseases.add(((DiseaseNameDE) i.next()).clone());
			}
		}
		if(grades!=null) {
			myClone.grades = new ArrayList();
			for (Iterator i = grades.iterator(); i.hasNext();) {
				myClone.grades.add(((GradeDE) i.next()).clone());
			}
		}
		return myClone;
	}

}
