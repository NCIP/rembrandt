package gov.nih.nci.nautilus.queryprocessing.ge;

import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.FoldChangeCriteria;
import gov.nih.nci.nautilus.criteria.SampleCriteria;
import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.de.ExprFoldChangeDE;
import gov.nih.nci.nautilus.de.SampleIDDE;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.query.Criteria;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 2, 2004
 * Time: 12:25:58 PM
 * To change this template use Options | File Templates.
 */
public class FoldChangeCriteriaHandler {

    static void addFoldChangeCriteria(GeneExpressionQuery geQuery, Class beanClass, PersistenceBroker pb, Criteria criteria)
    throws Exception {
       FoldChangeCriteria foldChangeCrit = geQuery.getFoldChgCrit();
       if (foldChangeCrit != null) {
               String columnName = QueryHandler.getColumnName(pb, ExprFoldChangeDE.class.getName(), beanClass.getName());
               Collection objs = foldChangeCrit.getFoldChangeObjects();
               Object[] foldObjs = objs.toArray();

                // Either only UpRegulation or DownRegulation
                if (foldObjs.length == 1) {
                    ExprFoldChangeDE foldChgObj = (ExprFoldChangeDE) foldObjs[0];
                    Double foldChange = new Double(foldChgObj.getValueObject().floatValue());
                    addSingleUpORDownCriteria(foldChange, foldChgObj.getRegulationType(), columnName, criteria, pb);
                }

                // else it could be EITHER both (UpRegulation or DownRegulation) OR UnChangedRegulation
                else if (foldObjs.length == 2) {
                   String type = ((ExprFoldChangeDE)foldObjs[0]).getRegulationType();
                   if (type.equals(ExprFoldChangeDE.UNCHANGED_REGULATION_UPPER_LIMIT) || type.equals(ExprFoldChangeDE.UNCHANGED_REGULATION_DOWN_LIMIT) ) {
                       addUnChangedCriteria(foldObjs, columnName, criteria, pb);
                   }
                   else if (type.equals(ExprFoldChangeDE.UP_REGULATION) || type.equals(ExprFoldChangeDE.DOWN_REGULATION) ) {
                       addUpAndDownCriteria(foldObjs, columnName, criteria, pb);
                   }
                }
                else {
                   throw new Exception("Invalid number of FoldChange Criteria objects: " + foldObjs.length);
                }
           }

    }

    private static void addUpAndDownCriteria(Object[] foldObjs, String columnName, Criteria criteria, PersistenceBroker pb) throws Exception {
        String type1 = ((ExprFoldChangeDE)foldObjs[0]).getRegulationType();
        Double foldChange1 = new Double(((ExprFoldChangeDE)foldObjs[0]).getValueObject().floatValue());
        addSingleUpORDownCriteria(foldChange1, type1, columnName, criteria, pb);

        String type2 = ((ExprFoldChangeDE)foldObjs[1]).getRegulationType();
        Double foldChange2 = new Double(((ExprFoldChangeDE)foldObjs[1]).getValueObject().floatValue());
        Criteria fold2Crit = new Criteria();
        addSingleUpORDownCriteria(foldChange2, type2, columnName, fold2Crit, pb);

        criteria.addOrCriteria(fold2Crit);
    }

    private static void addUnChangedCriteria(Object[] foldObjs, String columnName, Criteria criteria, PersistenceBroker pb) throws Exception {

        String type1 = ((ExprFoldChangeDE)foldObjs[0]).getRegulationType();
        Double upperLimit;
        Double lowerLimit;

        if (type1.equals(ExprFoldChangeDE.UNCHANGED_REGULATION_DOWN_LIMIT)) {
            upperLimit = new Double(((ExprFoldChangeDE) foldObjs[1]).getValueObject().floatValue());
            lowerLimit = new Double(((ExprFoldChangeDE)foldObjs[0]).getValueObject().floatValue());
        }
        else {
            upperLimit = new Double(((ExprFoldChangeDE)foldObjs[0]).getValueObject().floatValue());
            lowerLimit = new Double(((ExprFoldChangeDE)foldObjs[1]).getValueObject().floatValue());
        }
        criteria.addBetween(columnName, lowerLimit, upperLimit);
    }

    private static void addSingleUpORDownCriteria(Double foldChange, String type, String colunName, Criteria subCrit, PersistenceBroker pb) throws Exception {

        if (type.equals(ExprFoldChangeDE.UP_REGULATION)) {
            subCrit.addGreaterOrEqualThan(colunName,foldChange);
        }
        else if (type.equals(ExprFoldChangeDE.DOWN_REGULATION)) {
            double convertedDownFold = 1 / (foldChange.doubleValue());
            subCrit.addLessOrEqualThan(colunName, new Double(convertedDownFold));
        }
        else {
            throw new Exception("Invalid Regulation: " + type + " Value:" + foldChange);
        }

    }
}
