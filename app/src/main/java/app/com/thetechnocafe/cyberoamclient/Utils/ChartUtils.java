package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final int[] CHART_COLORS_800_2 = {R.color.md_pink_800, R.color.md_teal_800, R.color.md_deep_orange_800, R.color.md_indigo_800, R.color.md_amber_800, R.color.md_cyan_800};

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

    public static PieData getPieChartDataForEnrollmentUsed(final Context context) {
        //Create pir entries
        List<PieEntry> pieEntries = new ArrayList<>();

        //Get list of logs
        final List<SessionLogModel> modelList = RealmDatabase.getInstance(context).getSessionLogs();

        //Create a HashMap to store data
        HashMap<String, Integer> enrollmentIdHashMap = new HashMap<>();

        //Iterate and fill out hash map
        for (SessionLogModel model : modelList) {
            if (enrollmentIdHashMap.get(model.getUsername()) == null) {
                enrollmentIdHashMap.put(model.getUsername(), 1);
            } else {
                enrollmentIdHashMap.put(model.getUsername(), enrollmentIdHashMap.get(model.getUsername()) + 1);
            }
        }

        //Crate new pie entries
        for (Map.Entry<String, Integer> entry : enrollmentIdHashMap.entrySet()) {
            pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        //Create pie data set
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.WHITE);

        //Set colors
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        return new PieData(dataSet);
    }
}
