package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.util.ApplicationContext;
import gov.nih.nci.nautilus.util.DEBeanAttrMapping;
import java.util.ArrayList;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.metadata.ClassDescriptor;
import org.apache.ojb.broker.metadata.DescriptorRepository;
import org.apache.ojb.broker.metadata.FieldDescriptor;



/**
 * @author BhattarR, BauerD
 */
abstract public class QueryHandler {

    private static Logger logger = Logger.getLogger(QueryHandler.class);
    private static Map deBeanMappings = null;
        
    protected abstract ResultSet[] handle(Query query) throws Exception;
   
    final static DEBeanAttrMapping getBeanAttrMappingFor(String deClassName) throws Exception {
        return getBeanAttrMappingFor(deClassName, null);
    }
    
    final static DEBeanAttrMapping getBeanAttrMappingFor(String deClassName, String inBeanName) throws Exception {
        ArrayList valueObjects = (ArrayList) deBeanMappings.get(deClassName);
        DEBeanAttrMapping value = null;

        if (valueObjects == null)
           throw new Exception("DEBeanAttrMapping  could not be found for: " + deClassName);

        if (inBeanName != null)
            for (int i = 0; i < valueObjects.size(); i++) {
                value = (DEBeanAttrMapping) valueObjects.get(i);
                if (value.getMappedBean().equals(inBeanName)) return value;
            }
        else
            value = (DEBeanAttrMapping) valueObjects.get(0);

        if (value != null) return value;
        else throw new Exception("DEBeanAttrMapping  could not be found for: " + deClassName + "within " + inBeanName);
    }

    public static String getColumnName(PersistenceBroker pb, String deClassName) throws Exception {
        DescriptorRepository dr = pb.getDescriptorRepository();
        DEBeanAttrMapping mappingObj = getBeanAttrMappingFor(deClassName);
        String beanName = mappingObj.getMappedBean();
        String beanAttrName = mappingObj.getMappedBeanAttribute();
        ClassDescriptor cd = dr.getDescriptorFor( beanName );
        FieldDescriptor fd = cd.getFieldDescriptorByName(beanAttrName);
        return fd.getColumnName();
    }
    
    public static String getAttrNameForTheDE(String deClassName, String inBeanName) throws Exception{
        DEBeanAttrMapping  mappingsObj = getBeanAttrMappingFor(deClassName, inBeanName);
        return mappingsObj.getMappedBeanAttribute();
    }
    
    public static String getColumnName(PersistenceBroker pb, String deClassName, String inBeanName) throws Exception {
        DescriptorRepository dr = pb.getDescriptorRepository();
        DEBeanAttrMapping mappingObj = getBeanAttrMappingFor(deClassName, inBeanName);
        String beanName = mappingObj.getMappedBean();
        String beanAttrName = mappingObj.getMappedBeanAttribute();
        ClassDescriptor cd = dr.getDescriptorFor( beanName );
        FieldDescriptor fd = cd.getFieldDescriptorByName(beanAttrName);
        return fd.getColumnName();
    }
     // used in report queries for columnNames attrributes
    public static String getColumnNameForBean(PersistenceBroker pb, String beanClassName, String attrName) throws Exception {
        DescriptorRepository dr = pb.getDescriptorRepository();
        ClassDescriptor cd = dr.getDescriptorFor( beanClassName );
        FieldDescriptor fd = cd.getFieldDescriptorByName(attrName);
        return fd.getColumnName();
    }
    
    public static void init() {
    	deBeanMappings= ApplicationContext.getDEtoBeanAttributeMappings();
    }

    
}
