READ ME

Himanso Sahni
7/20/2004
Requires ANT version 1.5.3 or higher
Download ANT from http://ant.apache.org/
Install ANT and set ANT_HOME in your environment

To Run the GDACSProcessFiles utility:
cd /GDACFiles/build folder

To compile the source code files:
Type "ant" 

Next 
Copy all the *.CHP files to the /GDACFiles/CHP folder

To execute the program
(I configured the ant to run any program from the classes folder)
Type "ant run" (without the double quotes, of course)
When prompted by
"    [input] Please enter the name of program name in the classes\ folder that you want to run:"
Type "ProcessCHPFile" (without the double quotes, of course)

The program will output something like this.....

     [java] C:\Dev\rembrandt\GDACFiles\CHP
     [java] Reading CHP file [C:\Dev\rembrandt\GDACFiles\CHP\E09419_U133P2.CHP]
     [java] Path-->C:\Dev\rembrandt\GDACFiles\CHP
     [java] FileName-->E09419_U133P2.CHP
     [java] Writing to FileName-->E09419_U133P2.csv
     [java] Completed FileName-->E09419_U133P2.csv
     [java] ************************************
     [java] Reading CHP file [C:\Dev\rembrandt\GDACFiles\CHP\E09448_U133P2.CHP]
     [java] Path-->C:\Dev\rembrandt\GDACFiles\CHP
     [java] FileName-->E09448_U133P2.CHP

The output files are placed back in the csv folder .

Note: The program reads all the CHP files in the CHP folder, 
so if you do not want to reprocess the CHP files, please remove them from the folder.
