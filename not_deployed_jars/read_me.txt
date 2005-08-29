This directory is intended to hold jar files that are required
for a CVS download and build using the build.xml file, BUT are
not to be deployed in the web application.

For instance:
	At this time JBoss seems to have conflicts if a log4j 
	implimentation jar is packaged in the war file.  But we
	need this file to perform a compilation of the source. 
	Because of this we reference it in the build.xml but do
	not package it in the war file.