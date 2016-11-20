package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.Models.SessionLogModel;
import app.com.thetechnocafe.cyberoamclient.R;

/**
 * Created by gurleensethi on 20/11/16.
 */

public class ChartUtils {
    /**
     * Create the bar chart on the basis of data consumed today
     */

    private static final int[] CHART_COLORS_800 = {R.color.md_yellow_800, R.color.md_red_800, R.color.md_blue_800, R.color.md_green_800, R.color.md_amber_800, R.color.md_blue_grey_800};

    public static BarData getBarChartDataForToday(final Context context) {
        //Get today time
        long todayTimeInMillis = TimeUtils.getTodayTimeInMillis();

        //Create bar entries
        List<BarEntry> barEntries = new ArrayList<>();

        //Get list of logs
        final List<SessionLogModel> modelList = RealmDatabase.getInstance(context).getSessionLogs();

        //Iterate and check for time greater than today
        //Add the list of bar entries
        int placeCounter = 0;
        for (int count = 0; count < modelList.size(); count++) {
            SessionLogModel model = modelList.get(count);
            if (model.getLoggedInTime() > todayTimeInMillis) {
                barEntries.add(new BarEntry((float) placeCounter, (float) TrafficUtils.getTwoDecimalPlaces(model.getDataConsumed())));
                placeCounter++;
            }
        }

        //Create data set
        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.setValueTextSize(8f);

        //Set custom colors
        dataSet.setColors(CHART_COLORS_800, context);

        return new BarData(dataSet);
    }
}
