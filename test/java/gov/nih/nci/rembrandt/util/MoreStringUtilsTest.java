package gov.nih.nci.rembrandt.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoreStringUtilsTest {

	@Test
	public void testInsertParameterToErrorString() {
		String error = "There is no record found for {0} <b>{1}</b><br> To improve your search, you may want to try <b>{1}*</b> or <b>*{1}*</b>";
		
		String replaced = MoreStringUtils.insertParameterToErrorString(error, "Test", 1);
		assertTrue(replaced.equals("There is no record found for {0} <b>Test</b><br> To improve your search, you may want to try <b>Test*</b> or <b>*Test*</b>"));
		
		error = "There is no record found for {0} <b>{ 1}</b><br> To improve your search, you may want to try <b>{  1}*</b> or <b>*{  1}*</b>";
		assertTrue(replaced.equals("There is no record found for {0} <b>Test</b><br> To improve your search, you may want to try <b>Test*</b> or <b>*Test*</b>"));
		
		error = "There is no record found for {0} <b>{ 1  }</b><br> To improve your search, you may want to try <b>{  1  }*</b> or <b>*{  1 }*</b>";
		assertTrue(replaced.equals("There is no record found for {0} <b>Test</b><br> To improve your search, you may want to try <b>Test*</b> or <b>*Test*</b>"));
	}

}
