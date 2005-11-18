package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.ClusterTypeDE;
import gov.nih.nci.caintegrator.dto.de.DistanceMatrixTypeDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.LinkageMethodTypeDE;
import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;

import java.util.Collection;

/**
 * @author sahnih
 *
 */
public class HierarchicalClusteringQueryDTOImpl implements HierarchicalClusteringQueryDTO {
	private String queryName;
	private Collection<GeneIdentifierDE> geneIdentifierDEs;
	private Collection<CloneIdentifierDE> reporterIdentifierDEs;
	private ArrayPlatformDE arrayPlatformDE;
	private InstitutionDE institutionDE;
	private GeneVectorPercentileDE geneVectorPercentileDE;
	private DistanceMatrixTypeDE distanceMatrixTypeDE;
	private LinkageMethodTypeDE linkageMethodTypeDE;
	private ClusterTypeDE clusterTypeDE;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public HierarchicalClusteringQueryDTOImpl() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return Returns the queryName.
	 */
	public String getQueryName() {
		return queryName;
	}


	/**
	 * @param queryName The queryName to set.
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getArrayPlatformDE()
	 */
	public ArrayPlatformDE getArrayPlatformDE() {
		return arrayPlatformDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setArrayPlatformDE(gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE)
	 */
	public void setArrayPlatformDE(ArrayPlatformDE arrayPlatformDE) {
		this.arrayPlatformDE = arrayPlatformDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getInstitutionNameDE()
	 */
	public InstitutionDE getInstitutionDE() {
		return institutionDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setInstitutionNameDE(gov.nih.nci.caintegrator.dto.de.InstitutionNameDE)
	 */
	public void setInstitutionDE(InstitutionDE institutionDE) {
		this.institutionDE = institutionDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getGeneIdentifierDEs()
	 */
	public Collection<GeneIdentifierDE> getGeneIdentifierDEs() {
		return geneIdentifierDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setGeneIdentifierDEs(java.util.Collection)
	 */
	public void setGeneIdentifierDEs(Collection<GeneIdentifierDE> geneIdentifierDEs) {
		this.geneIdentifierDEs = geneIdentifierDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getGeneVectorPercentileDE()
	 */
	public GeneVectorPercentileDE getGeneVectorPercentileDE() {
		return geneVectorPercentileDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setGeneVectorPercentileDE(gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE)
	 */
	public void setGeneVectorPercentileDE(
			GeneVectorPercentileDE geneVectorPercentileDE) {
		this.geneVectorPercentileDE = geneVectorPercentileDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getReporterIdentifierDEs()
	 */
	public Collection<CloneIdentifierDE> getReporterIdentifierDEs() {
		return reporterIdentifierDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setReporterIdentifierDEs(java.util.Collection)
	 */
	public void setReporterIdentifierDEs(
			Collection<CloneIdentifierDE> reporterIdentifierDEs) {
		this.reporterIdentifierDEs = reporterIdentifierDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getClusterTypeDE()
	 */
	public ClusterTypeDE getClusterTypeDE() {
		return clusterTypeDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setClusterTypeDE(gov.nih.nci.caintegrator.dto.de.ClusterTypeDE)
	 */
	public void setClusterTypeDE(ClusterTypeDE clusterTypeDE) {
		this.clusterTypeDE = clusterTypeDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getDistanceMatrixTypeDE()
	 */
	public DistanceMatrixTypeDE getDistanceMatrixTypeDE() {
		return distanceMatrixTypeDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setDistanceMatrixTypeDE(gov.nih.nci.caintegrator.dto.de.DistanceMatrixTypeDE)
	 */
	public void setDistanceMatrixTypeDE(DistanceMatrixTypeDE distanceMatrixTypeDE) {
		this.distanceMatrixTypeDE = distanceMatrixTypeDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getLinkageMethodTypeDE()
	 */
	public LinkageMethodTypeDE getLinkageMethodTypeDE() {
		return linkageMethodTypeDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setLinkageMethodTypeDE(gov.nih.nci.caintegrator.enumeration.LinkageMethodType)
	 */
	public void setLinkageMethodTypeDE(LinkageMethodTypeDE linkageMethodTypeDE) {
		this.linkageMethodTypeDE = linkageMethodTypeDE;
	}



}
