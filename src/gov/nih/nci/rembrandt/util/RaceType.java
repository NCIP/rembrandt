/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.util;

public enum RaceType {
		AMERICAN_INDIAN_OR_ALASKA_NATIVE { public String toString() { return "American Indian or Alaska Native"; }} ,
		ASIAN { public String toString() { return "ASIAN NOS"; }} ,
		BLACK_OR_AFRICAN_AMERICAN { public String toString() { return "BLACK"; }} ,
		NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER { public String toString() { return "NATIVE HAWAIIAN"; }} ,
		WHITE { public String toString() { return "WHITE"; }}, 
		UNKNOWN  { public String toString() { return "UNKNOWN"; }},	
		OTHER  { public String toString() { return "OTHER"; }};	
}
