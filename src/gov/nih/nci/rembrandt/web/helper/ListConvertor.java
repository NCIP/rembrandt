/**
 * 
 */
package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SNPIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.OperationNotSupportedException;

/**
 * @author sahnih
 *
 */
public class ListConvertor {
/**
 * This method converts a string list to  GeneIdentifierDE list type based on ListSubType
 * @param list
 * @param listSubType
 * @return
 */
public static List<GeneIdentifierDE> convertToGeneIdentifierDE(List<String> list,ListSubType listSubType){
	List<GeneIdentifierDE> geneIdentifierList = new ArrayList<GeneIdentifierDE>();
	switch (listSubType){
	case GENESYMBOL:
		for(String listItem: list){
			geneIdentifierList.add(new GeneIdentifierDE.GeneSymbol(listItem));
		}
		break;
	case GENBANK_ACCESSION_NUMBER:
		for(String listItem: list){
			geneIdentifierList.add(new GeneIdentifierDE.GenBankAccessionNumber(listItem));
		}
		break;
	case LOCUS_LINK:
		for(String listItem: list){
			geneIdentifierList.add(new GeneIdentifierDE.LocusLink(listItem));
		}
		break;
	}
	return geneIdentifierList;
}
/**
 * This method converts a string list to  CloneIdentifierDE list type based on ListSubType
 * @param list
 * @param listSubType
 * @return
 */
public static List<CloneIdentifierDE> convertToCloneIdentifierDE(List<String> list,ListSubType listSubType){
	List<CloneIdentifierDE> cloneIdentifierDE = new ArrayList<CloneIdentifierDE>();
	switch (listSubType){
	case IMAGE_CLONE:
		for(String listItem: list){
			cloneIdentifierDE.add(new CloneIdentifierDE.IMAGEClone(listItem));
		}
		break;
	case PROBE_SET:
		for(String listItem: list){
			cloneIdentifierDE.add(new CloneIdentifierDE.ProbesetID(listItem));
		}
		break;
	}
	return cloneIdentifierDE;
}
	/**
	 * This method converts a string list to  SNPIdentifierDE list type based on ListSubType
	 * @param list
	 * @param listSubType
	 * @return
	 */
	public static List<SNPIdentifierDE> convertToSNPIdentifierDE(List<String> list,ListSubType listSubType){
		List<SNPIdentifierDE> snpIdentifierDE = new ArrayList<SNPIdentifierDE>();
		switch (listSubType){
		case DBSNP:
			for(String listItem: list){
				snpIdentifierDE.add(new SNPIdentifierDE.DBSNP(listItem));
			}
			break;
		case SNP_PROBESET:
			for(String listItem: list){
				snpIdentifierDE.add(new SNPIdentifierDE.SNPProbeSet(listItem));
			}
			break;
		}
		return snpIdentifierDE;
	}
	public static Collection<SampleIDDE> convertToSampleIDDEs(Collection<String> sampleIDs)throws OperationNotSupportedException{
		Collection<SampleIDDE> samplesDEs = new ArrayList<SampleIDDE>();
		if(sampleIDs != null){
			for(String sampleID: sampleIDs){
				samplesDEs.add(new SampleIDDE(sampleID));
			}
		}
		return samplesDEs;
	}
}
