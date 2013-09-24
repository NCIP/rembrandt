/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

import java.applet.Applet;
import java.awt.HeadlessException;
import java.awt.*;

/**
 * @author Dag Sunde
 * @version 1.0
 */

public class Detector extends Applet {
	public Detector() throws HeadlessException {
	}

	public void init() {
		add(new Label(getJavaVersion()));

	}

	public String getJavaVersion() {
		return System.getProperty("java.version");
	}

}