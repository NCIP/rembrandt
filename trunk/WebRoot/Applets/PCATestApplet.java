import java.applet.*;
import java.awt.Color;

import org.apache.commons.lang.StringUtils;
import org.math.plot.*;

public class PCATestApplet extends Applet  {
	 
		public void init() {
			
			/*
			// Data definition
			int n = 10;
			//read params to create these
			double[][] datas1 = new double[n][3];
			double[][] datas2 = new double[n][3];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < 3; j++) { // 3 == set the x,y,z...goto next of the 10
					datas1[i][j] = Math.random();
					datas2[i][j] = Math.random();
				}
			}
			*/
			
			Plot3DPanel plotpanel = new Plot3DPanel();
			plotpanel.setAxeLabel(0, "PC1");
			plotpanel.setAxeLabel(1, "PC2");
			plotpanel.setAxeLabel(2, "PC3");
			plotpanel.addLegend("SOUTH");
			
			String totalPts = getParameter("totalPts");
			String totalGps = getParameter("totalGps");
			//groupLabel_x
			//groupCount_x
			//pt_x_x (group#_pt#)
			
			for(int i=0; i<Integer.parseInt(totalGps); i++)	{
				String thisGrpLabel = getParameter("groupLabel_"+i);
				String thisGrpCount = getParameter("groupCount_"+i);
				String thisGrpColor = getParameter("groupColor_"+i);
				double[][] lumpData = new double[Integer.parseInt(thisGrpCount)][3];
				
				for(int t=0; t<Integer.parseInt(thisGrpCount); t++){
					String v = getParameter("pt_" + i + "_" + t);
					String[] xyz = StringUtils.split(v, ",");
					for(int j=0; j<3; j++){
						lumpData[t][j] = Double.valueOf(xyz[j]).doubleValue();
					}	
				}
				
				//plotpanel.addScatterPlot(thisGrpLabel, lumpData);
				plotpanel.addScatterPlot(thisGrpLabel, new Color(Integer.parseInt(thisGrpColor)), lumpData);
				lumpData = null;
			}
			
			/*
			//lump data example
			double[][] lumpData = new double[Integer.parseInt(totalPts)][3];
			for(int i=0; i<Integer.parseInt(totalPts); i++){
				String v = getParameter("pt_"+i);
				String[] xyz = StringUtils.split(v, ",");
				for(int j=0; j<3; j++){
					lumpData[i][j] = Double.valueOf(xyz[j]);
				}
			}
			*/
			
			
			// PlotPanel construction
			//Plot3DPanel plotpanel = new Plot3DPanel();
			//plotpanel.setAxeLabel(0, "PC1");
			//plotpanel.setAxeLabel(1, "PC2");
			//plotpanel.setAxeLabel(2, "PC3");
			//plotpanel.addLegend("SOUTH");
	 
			// Data plots addition
			//plotpanel.addScatterPlot("lump data", lumpData);
			
			//plotpanel.addScatterPlot(totalPts + " datas1", datas1);
			//plotpanel.addScatterPlot("datas2", datas2);
			//plotpanel.addBarPlot("datas2", datas2);
	 
			// include plot in applet
			add(plotpanel);
		}
	
}
