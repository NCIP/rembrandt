package gov.nih.nci.rembrandt.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;

public class RembrandtListFilter {
	
	public static ListType[] values()	{

		ListType[] lsa = {ListType.GeneSymbol, ListType.PatientDID, ListType.Reporter}; //no duplicates here
		
		return lsa;
	}
	
	public static ListSubType[] getSubTypesForType(ListType lt)	{
		//control the mapping between which subtypes are associated with a primary type
		if(lt == ListType.Reporter){
			//list the reporter subtypes here and return them
			ListSubType[] lsta = {ListSubType.PROBE_SET, ListSubType.IMAGE_CLONE, ListSubType.DBSNP, ListSubType.SNPProbeSet};
			return lsta;
		}
		else if(lt == ListType.GeneSymbol){
			ListSubType[] lsta = {ListSubType.GENBANK_ACCESSION_NUMBER, ListSubType.GENESYMBOL, ListSubType.LOCUS_LINK};
			return lsta;
		}

		//   Default, Custom, IMAGE_CLONE, PROBE_SET, SNPProbeSet, DBSNP, GENBANK_ACCESSION_NUMBER, GENESYMBOL, LOCUS_LINK;

		ListSubType[] lsta = {ListSubType.Custom}; //stub
		return lsta;
	}
}
