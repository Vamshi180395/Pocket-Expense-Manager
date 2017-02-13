package com.example.PocketExpenseManager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class Summary extends AppCompatActivity {
int p=0;
    private static String TAG = "Summary";
ArrayList userexpense=new ArrayList();
    ArrayList<Float> amountlist=new ArrayList<Float>();
    ArrayList<String> categorylist=new ArrayList<String>();
    ArrayList<Expense> userexpenses=new ArrayList<Expense>();
    ArrayList<Expense> summarylist=new ArrayList<Expense>();
    private float[] yData;
    private String[] xData;
    PieChart pieChart;
Spinner spmonth,spyear;
    String selectedmonth,selectedyear;
    float totalamountforgroceries=0;
    float totalamountfortransportation=0;
    float totalamountforshopping=0;
    float totalamountfortrips=0;
    float totalamountforrent=0;
    float totalamountforinvoice=0;
    float totalamountforutilities=0;
    float totalamountforother=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Log.d(TAG, "onCreate: starting to create chart");
        if(getIntent().getExtras()!=null){
            userexpense= getIntent().getExtras().getParcelableArrayList(AppActivity.EXPENSES_ARRAY);
            userexpenses.addAll(userexpense);
        }
        spmonth = (Spinner) findViewById(R.id.spmonth);
        spyear = (Spinner) findViewById(R.id.spyear);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spmonth.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spyear.setAdapter(adapter1);
        pieChart = (PieChart) findViewById(R.id.summarypiechart);
        pieChart.setDescription("Expenses Summary");
        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Expenses Summary");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!
spmonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedmonth=spmonth.getSelectedItem().toString();
        setPieChart();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});
        spyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedyear=spyear.getSelectedItem().toString();
                setPieChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int i, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1 + 7);

                for(int in = 0; in < yData.length; in++){
                    if(yData[in] == Float.parseFloat(sales)){
                        pos1 = in;
                        break;
                    }
                }
                String employee = xData[pos1];
                Toast.makeText(Summary.this, "Expense " + employee + "\n" + "Amount: $" + sales + " ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void setPieChart() {
        if(selectedyear!=null && selectedmonth!=null && !selectedyear.equals("Year") && !selectedmonth.equals("Month")){
            for(int i=0;i<userexpenses.size();i++){
             if(userexpenses.get(i).date.substring(0,userexpenses.get(i).date.indexOf("-")).equals(selectedmonth) && userexpenses.get(i).date.substring(userexpenses.get(i).date.length()-5,userexpenses.get(i).date.length()-1).equals(selectedyear)){
                 summarylist.add(userexpenses.get(i));
             }
            }
            setSummaryListandxyvalues();
            addDataSet();
        }
        else if(selectedmonth.equals("Month") && selectedyear!=null && !selectedyear.equals("Year")) {
            if (spyear != null) {
                for (int i = 0; i < userexpenses.size(); i++) {
                    if (userexpenses.get(i).date.substring(userexpenses.get(i).date.length() - 5, userexpenses.get(i).date.length() - 1).equals(selectedyear)) {
                        summarylist.add(userexpenses.get(i));
                    }
                }
                setSummaryListandxyvalues();
            }
            addDataSet();
        }

    }

    private void setSummaryListandxyvalues() {
        for(int k=0;k<summarylist.size();k++){
            if(summarylist.get(k).expensecategory.equals("Groceries")){
                categorylist.add(summarylist.get(k).expensecategory);
                totalamountforgroceries=totalamountforgroceries+Float.valueOf(summarylist.get(k).amount);

            }else if(summarylist.get(k).expensecategory.equals("Invoice")){
                categorylist.add(summarylist.get(k).expensecategory);
                totalamountforinvoice=totalamountforinvoice+Float.valueOf(summarylist.get(k).amount);

            }
            else if(summarylist.get(k).expensecategory.equals("Transportation")){
                categorylist.add(summarylist.get(k).expensecategory);
                totalamountfortransportation=totalamountfortransportation+Float.valueOf(summarylist.get(k).amount);


            }else if(summarylist.get(k).expensecategory.equals("Shopping")){
                categorylist.add(summarylist.get(k).expensecategory);
                totalamountforshopping=totalamountforshopping+Float.valueOf(summarylist.get(k).amount);


            }else if(summarylist.get(k).expensecategory.equals("Rent")){
                categorylist.add(summarylist.get(k).expensecategory);
                totalamountforrent=totalamountforrent+Float.valueOf(summarylist.get(k).amount);


            }else if(summarylist.get(k).expensecategory.equals("Trips")){
                categorylist.add(summarylist.get(k).expensecategory);
                totalamountfortrips=totalamountfortrips+Float.valueOf(summarylist.get(k).amount);


            }
            else if(summarylist.get(k).expensecategory.equals("Utilities")){
                categorylist.add(summarylist.get(k).expensecategory);
                totalamountforutilities=totalamountforutilities+Float.valueOf(summarylist.get(k).amount);


            }
            else if(summarylist.get(k).expensecategory.equals("Other")){
                categorylist.add(summarylist.get(k).expensecategory);
                totalamountforother=totalamountforother+Float.valueOf(summarylist.get(k).amount);

            }

        }
        if(totalamountforinvoice!=0) {
            amountlist.add(totalamountforinvoice);
        }
        if(totalamountfortransportation!=0) {
            amountlist.add(totalamountfortransportation);
        }
        if(totalamountforshopping!=0) {
            amountlist.add(totalamountforshopping);
        }
        if(totalamountforrent!=0) {
            amountlist.add(totalamountforrent);
        }
        if(totalamountfortrips!=0) {
            amountlist.add(totalamountfortrips);
        }
        if(totalamountforutilities!=0) {
            amountlist.add(totalamountforutilities);
        }
        if(totalamountforgroceries!=0) {
            amountlist.add(totalamountforgroceries);
        }
        if(totalamountforother!=0) {
            amountlist.add(totalamountforother);
        }
        xData=new String[categorylist.size()];
        yData=new float[amountlist.size()];

        int i = 0;

        for (Float f : amountlist) {
            yData[i++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }
        xData= (String[]) categorylist.toArray(new String[0]);
    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<Entry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new Entry(yData[i] , i));
        }

        for(int i = 0; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }
        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys,"");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
        colors.add(Color.LTGRAY);
        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie 1data object
        if( summarylist.size()!=0) {
            PieData data = new PieData(xEntrys, pieDataSet); // initialize Piedata<br />
            pieChart.setData(data);
            pieChart.invalidate();
             totalamountforgroceries=0;
             totalamountfortransportation=0;
             totalamountforshopping=0;
            totalamountfortrips=0;
             totalamountforrent=0;
             totalamountforinvoice=0;
           totalamountforutilities=0;
            totalamountforother=0;
            summarylist.clear();
            categorylist.clear();
            amountlist.clear();
        }
    }
}

