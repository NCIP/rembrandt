/*
 * Created on Mar 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.lookup;

import gov.nih.nci.nautilus.data.AllGeneAlias;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GeneAliasMap extends HashMap {
	
	public void addGenes( AllGeneAlias geneAlias){
		if(geneAlias != null && geneAlias.getAlias() != null){
			Collection geneAliasCollection = (Collection) this.get(geneAlias.getAlias().trim());
			if(geneAliasCollection == null){
				geneAliasCollection = new Vector();			
			}
			geneAliasCollection.add(geneAlias);	
			this.put(geneAlias.getAlias().trim(), geneAliasCollection);
		}
	}
	public AllGeneAliasLookup[] getGenes(String alias){
		if(alias != null){
			Collection genes = (Collection) this.get(alias.trim());
			if(genes != null){
				return (AllGeneAliasLookup[]) genes.toArray(new AllGeneAliasLookup[genes.size()]);
			}
		}
		return null;
	}

}
