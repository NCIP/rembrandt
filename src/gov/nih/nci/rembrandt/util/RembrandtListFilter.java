package gov.nih.nci.rembrandt.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;

public class RembrandtListFilter {
	
	public static ListType[] values()	{

		ListType[] lsa = {ListType.Gene, ListType.PatientDID, ListType.Reporter}; //no duplicates here
		
		return lsa;
	}
	
	public static List<ListSubType> getSubTypesForType(ListType lt)	{
		//control the mapping between which subtypes are associated with a primary type
		//for example: when adding a new "Reporter List", the app needs to know
		//which subtypes "Reporter" has so we can add a "IMAGE CLONE REPORTER" list
		ArrayList<ListSubType> lsta = new ArrayList();
		if(lt == ListType.Reporter){
			//list the reporter subtypes here and return them
			lsta.add(ListSubType.PROBE_SET);
			lsta.add(ListSubType.IMAGE_CLONE);
			lsta.add(ListSubType.DBSNP);
			lsta.add(ListSubType.SNP_PROBESET);
		}
		else if(lt == ListType.Gene){
			//ListSubType[] lsta = {ListSubType.GENBANK_ACCESSION_NUMBER, ListSubType.GENESYMBOL, ListSubType.LOCUS_LINK};
			lsta.add(ListSubType.GENBANK_ACCESSION_NUMBER);
			lsta.add(ListSubType.GENESYMBOL);
			lsta.add(ListSubType.LOCUS_LINK);
		}
		//   Default, Custom, IMAGE_CLONE, PROBE_SET, SNPProbeSet, DBSNP, GENBANK_ACCESSION_NUMBER, GENESYMBOL, LOCUS_LINK;
		return lsta;
	}
	
	public static List<String> getSubTypesForTypeFromString(String lt)	{
		List<ListSubType> lsta = new ArrayList();
		lsta = getSubTypesForType(ListType.valueOf(lt));
		
		List<String> stringlist = new ArrayList();
		for(ListSubType l : lsta){
			stringlist.add(l.toString());
		}
		return stringlist;
	}
}
