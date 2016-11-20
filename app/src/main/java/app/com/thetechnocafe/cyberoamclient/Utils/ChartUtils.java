package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.Models.SessionLogModel;

/**
 * Created by gurleensethi on 20/11/16.
 */

public class ChartUtils {
    /**
     * Create the bar chart on the basis of data consumed today
     */
    public static BarData getBarChartDataForToday(Context context) {
        //Get today time
        long todayTimeInMillis = TimeUtils.getTodayTimeInMillis();

        //Create bar entries
        List<BarEntry> barEntries = new ArrayList<>();

        //Get list of logs
        final List<SessionLogModel> modelList = RealmDatabase.getInstance(context).getSessionLogs();

        //Iterate and check for time greater than today
        //Add the list of bar entries
        for (int count = 0; count < modelList.size(); count++) {
            SessionLogModel model = modelList.get(count);
            if (model.getLoggedInTime() > todayTimeInMillis) {
                barEntries.add(new BarEntry((float) count, (float) TrafficUtils.getTwoDecimalPlaces(model.getDataConsumed())));
            }
        }

        //Create data set
        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return TimeUtils.convertLongToString(modelList.get((int) entry.getX()).getLoggedInTime()) + "\n" +
                        String.valueOf(modelList.get((int) entry.getX()).getDataConsumed());
            }
        });

        return new BarData(dataSet);
    }
}
