package gov.nih.nci.nautilus.criteria;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:45:33 PM
 * To change this template use Options | File Templates.
 */
abstract public class Criteria {
    abstract public boolean isValid();
    public static boolean isEmpty(Criteria critObj) {
        try {
            String className = critObj.getClass().getName();
            Class critClass = Class.forName(className);
            Method[] methods = critClass.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (method.invoke(critObj, null) != null) {
                    return false;
                }
            }
         } catch (Throwable e) {
                e.printStackTrace();
         }
         return true;
    }
}
