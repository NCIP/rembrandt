package gov.nih.nci.rembrandt.util;

/**
 * @author BhattarR, BauerD
 */
public class DEBeanAttrMapping  {
        private String mappedBean;
        private String mappedBeanAttribute;
        private String name;
        public DEBeanAttrMapping() {}
        public DEBeanAttrMapping(String name, String mappedBeanName, String beanAttributeName) {
            this.name = name;
            this.mappedBean = mappedBeanName;
            this.mappedBeanAttribute = beanAttributeName;
        }
		/**
		 * @return Returns the mappedBean.
		 */
		public String getMappedBean() {
			return mappedBean;
		}
		/**
		 * @param mappedBean The mappedBean to set.
		 */
		public void setMappedBean(String mappedBean) {
			this.mappedBean = mappedBean;
		}
		/**
		 * @return Returns the mappedBeanAttribute.
		 */
		public String getMappedBeanAttribute() {
			return mappedBeanAttribute;
		}
		/**
		 * @param mappedBeanAttribute The mappedBeanAttribute to set.
		 */
		public void setMappedBeanAttribute(String mappedBeanAttribute) {
			this.mappedBeanAttribute = mappedBeanAttribute;
		}
		/**
		 * @return Returns the name.
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name The name to set.
		 */
		public void setName(String name) {
			this.name = name;
		}
}


