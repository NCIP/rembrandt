/* * Created on Jun 3, 2004 * */

/**
 * @author Himanso Sahni
 * 
 * This utility reads Affy CHIP files using the GDACS software package and
 * creates csv files
 *  
 */

import affymetrix.gdacfiles.*;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.io.*;

import org.apache.log4j.Logger;

public class ProcessCHPFile {
    public static GDACCHPFile GetCHPFile(String strCHPFileName) {
        private static Logger logger = Logger
                .getLogger(NautilusConstants.LOGGER);
        logger.debug("Reading CHP file [" + strCHPFileName + "]");
        String strOutput;
        String strChipType = "";

        File wFile = new File(strCHPFileName);
        logger.debug("Path-->" + wFile.getParent());
        logger.debug("FileName-->" + wFile.getName());

        GDACCHPFile chpFile = new GDACCHPFile();
        chpFile.DataPath = wFile.getParent();
        chpFile.LibPath = System.getProperty("user.dir") + "/library";
        chpFile.Name = wFile.getName();
        chpFile.ServerName = "";

        try {
            if (chpFile.Exists()) {
                chpFile.Read();
                String outputFilename = wFile.getName().substring(0,
                        wFile.getName().indexOf("."))
                        + ".csv";
                BufferedWriter out = new BufferedWriter(new FileWriter(System
                        .getProperty("user.dir")
                        + "\\csv" + "\\" + outputFilename));
                logger.debug("Writing to FileName-->" + outputFilename);

                strChipType = chpFile.ChipType;
                out.write(chpFile.Name);
                out.newLine();
                out.write("Name" + "," + //The name of the probe set, blank for
                                         // control probes.
                        "Detection" + "," + //The detection value calculated by
                                            // the MAS 5 or GCOS software.
                        "Signal" + "," + //The signal value calculated by MAS 5
                                         // or GCOS software.
                        "DetectionPValue"); //The detection p-value calculated
                                            // by the MAS 5 or GCOS software.

                out.newLine();
                if (chpFile.LoadProbeSets()) {
                    GDACProbeSet wP;
                    for (int i = 0; i < chpFile.NumProbeSetsLoaded; i++) {
                        wP = chpFile.GetProbeSet(i);
                        out.write(wP.Name + "," + wP.Detection + ","
                                + wP.Signal + "," + wP.DetectionPValue);

                        out.newLine();
                    }
                }
                out.close();
                logger.debug("Completed FileName-->" + outputFilename);
                logger.debug("************************************");

            } else {
                logger.debug("CHP file does not exist.");
            }
        } catch (Throwable t) {
            logger.error(t);
        }
        return chpFile;
    }

    public static void main(String[] args) {
        String curDir = System.getProperty("user.dir") + File.separator + "CHP";
        File dir = new File(curDir);
        logger.debug(dir);
        String[] children = dir.list();
        if (children == null) {
            // Either dir does not exist or is not a directory
        } else {
            for (int i = 0; i < children.length; i++) {
                // Get filename of file or directory
                String filename = children[i];
                if (filename.endsWith(".CHP") || filename.endsWith(".chp")) {
                    GetCHPFile(curDir + File.separator + filename);
                }
            }
        }

    }

}