package gov.nih.nci.caintegrator.ui.graphing.util;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.*;

public class FileDeleter {

	public void deleteFiles(String d, String e) {
		ExtensionFilter filter = new ExtensionFilter(e);
		File dir = new File(d);
		String[] list = dir.list(filter);
		File file;
		if (list.length == 0)
			return;

		for (int i = 0; i < list.length; i++) {
			file = new File(d + list[i]);
			boolean isdeleted = file.delete();
		}
	}

	class ExtensionFilter implements FilenameFilter {
		private String extension;

		public ExtensionFilter(String extension) {
			this.extension = extension;
		}

		public boolean accept(File dir, String name) {
			return (name.endsWith(extension));
		}
	}

	public static void main(String args[]) {
		FileDeleter td = new FileDeleter();
		td.deleteFiles("c:/test/", ".gif");
	}
}