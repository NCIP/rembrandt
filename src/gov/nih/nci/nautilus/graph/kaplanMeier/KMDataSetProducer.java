package gov.nih.nci.nautilus.graph.kaplanMeier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.io.Serializable;
import org.jfree.data.CategoryDataset;
import org.jfree.data.DefaultCategoryDataset;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.CategoryItemLinkGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.tooltips.CategoryToolTipGenerator;

public class KMDataSetProducer implements DatasetProducer, Serializable {
    
    // These values would normally not be hard coded but produced by
    // some kind of data source like a database or a file
    private final String[] categories = { "mon", "tue", "wen", "thu", "fri",
            "sat", "sun" };

    private final String[] seriesNames = { "Himanso", "Ram",
            "Ryan", "Dave" };

    private final Integer[][] values = new Integer[seriesNames.length][categories.length];

    public Object produceDataset(Map params) throws DatasetProduceException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int series = 0; series < seriesNames.length; series++) {
            int lastY = (int) (Math.random() * 1000 + 1000);
            for (int i = 0; i < categories.length; i++) {
                final int y = lastY + (int) (Math.random() * 200 - 100);
                lastY = y;
                dataset
                        .addValue((double) y, seriesNames[series],
                                categories[i]);

            }
        }
        return dataset;
    }

    public boolean hasExpired(Map params, Date since) {
        return (System.currentTimeMillis() - since.getTime()) > 5000;
    }

    public String getProducerId() {
        return "KMDataSetProducer";
    }

}