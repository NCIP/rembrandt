/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

/**
 * 
 */
package gov.nih.nci.rembrandt.dbbean;

/**
 * @author sahnih 
 * maps to UNFIED_HUGO_GENE
 * 
 */
public class UnifiedHugoGene {
	public final static String UNIFIED_GENE = "unifiedGene";

	public final static String GENE_SYMBOL = "geneSymbol";

	private String unifiedGene;

	private String geneSymbol;

	/**
	 * @return Returns the geneSymbol.
	 */
	public String getGeneSymbol() {
		return geneSymbol;
	}

	/**
	 * @param geneSymbol
	 *            The geneSymbol to set.
	 */
	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}

	/**
	 * @return Returns the unifiedGene.
	 */
	public String getUnifiedGene() {
		return unifiedGene;
	}

	/**
	 * @param unifiedGene
	 *            The unifiedGene to set.
	 */
	public void setUnifiedGene(String unifiedGene) {
		this.unifiedGene = unifiedGene;
	}
}
