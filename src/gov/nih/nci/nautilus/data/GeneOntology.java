
// Generated by OJB SchemeGenerator

package gov.nih.nci.nautilus.data;

public class GeneOntology
{
    public final static String GENE_SYMBOL = "geneSymbol";
    public final static String GO_ID = "goId";

      private String geneSymbol;
      private String goId;
      private String goName;
      public String getGoId() {
         return this.goId;
      }
      public void setGoId(String param)
      {
        this.goId = param;
      }


      public String getGoName()
      {
         return this.goName;
      }
      public void setGoName(String param)
      {
        this.goName = param;
      }

}

