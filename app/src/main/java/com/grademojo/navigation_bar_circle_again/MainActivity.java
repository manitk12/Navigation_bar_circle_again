package com.grademojo.navigation_bar_circle_again;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int DECIMAL_SCALE = 1;
    float Documents = 38f , Photos;

    PieChart pieChart;

    PieDataSet dataSet;

    View context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        pieChart = (PieChart) findViewById(R.id.piechart);

        context = findViewById(R.id.view);

        setSupportActionBar(toolbar);




        //Log.d(All.TAG_LOG, "Absent: " + absentPercent);
        //Log.d(All.TAG_LOG, "Leave: " + leavePercent);
        //Log.d(All.TAG_LOG, "Present: " + presentPercent);



        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);






        // Set the margins.
//        pieChart.setExtraOffsets(0, 0, 0, 0);
//        pieChart.setExtraOffsets(0.f, 0.f, 0.f, 0.f);


       pieChart.setDragDecelerationFrictionCoef(0.95f);


        // The hole in middle of Pie to make it a donut
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(ContextCompat.getColor(context.getContext(),R.color.new_grey));

        pieChart.setCenterText("38 %");
        pieChart.setCenterTextColor(ContextCompat.getColor(context.getContext(),R.color.new_white));
        pieChart.setCenterTextSize(70f);


        //pieChart.setTransparentCircleColor(ContextCompat.getColor(context.getContext(), R.color.new_grey));
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(95f);
        pieChart.setTransparentCircleRadius(61f);


        // Rotation of the pie chart
        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);


        // Highlight means make that "pie" section a bit bigger on click
        pieChart.setHighlightPerTapEnabled(false);



        Documents = new BigDecimal(Documents).setScale(DECIMAL_SCALE, BigDecimal.ROUND_FLOOR).floatValue();


        Photos = 100.0f - Documents ;
        Photos = new BigDecimal(Photos).setScale(DECIMAL_SCALE, BigDecimal.ROUND_FLOOR).floatValue();



        // Need an ArrayList of "PieEntry"
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        if(Photos > 0){
            entries.add(new PieEntry(Photos, ""));
        }

        if(Documents > 0){
            entries.add(new PieEntry(Documents, ""));
        }







        // Pass the ArrayList to make the DataSet
        dataSet = new PieDataSet(entries, "");


        // Dunno But below method cannot be resolved in this project
        // Though it works fine in ExampleChart app
        //dataSet.setDrawIcons(false);

        // Sets the distance between 2 pies
        dataSet.setSliceSpace(3f);

        // Another unresolvable method
        //dataSet.setIconsOffset(new MPPointF(0, 40));

        dataSet.setSelectionShift(5f);



        // Supply the color list to be used for the pies
        // The sequence matters
        if(Photos == 0f){
            // Means present is NOT there

            if(Documents == 0f){
                // Means data sequence is: absent
                dataSet.setColors(ContextCompat.getColor(context.getContext(), R.color.blue));
            }else{
                // Means data sequence is: leave And/OR:absent
                dataSet.setColors(ContextCompat.getColor(context.getContext(), R.color.light_blue));
            }

        }else if(Documents == 0f){
            // Means leave is NOT there
            // But present is there
            // => Sequence is: present And/Or:absent

            dataSet.setColors(ContextCompat.getColor(context.getContext(), R.color.yellow),ContextCompat.getColor(context.getContext(), R.color.orange));
        }else{
            // Means present is there
            // leave is there
            // => Sequence is: present leave And/Or:absent
            dataSet.setColors(ContextCompat.getColor(context.getContext(), R.color.blue),ContextCompat.getColor(context.getContext(), R.color.colorAccent));
        }



        // Move Values out of the "pies"
//        dataSet.setValueLinePart1OffsetPercentage(60.f);
//        dataSet.setValueLinePart1Length(0.4f);
//        dataSet.setValueLinePart2Length(0.4f);
//        dataSet.setValueLineColor(ContextCompat.getColor(context.getContext(),R.color.new_grey));
//        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);





        //mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");



        // Make the "PieData" object which will be actually used by PieChart
        PieData data = new PieData(dataSet);

        // Formatting of the "values" shown alongside the pies
        data.setValueFormatter(new PercentFormatter());

          data.setValueTextSize(0f);
//        data.setValueTextColor(ContextCompat.getColor(context.getContext(), R.color.blue));


        // Apply the "PieData" object to pieChart
        pieChart.setData(data);


        // undo all highlights
        pieChart.highlightValues(null);

        // Refresh the pieChart so that it shows the data we just provided
        pieChart.invalidate();


        // Animate the pieChart as it is shown to the user
        pieChart.animateY(1400, Easing.EasingOption.EaseInElastic);


        /*
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        */

        // Whether to show the "default" horizontal legend
        pieChart.getLegend().setEnabled(false);


        // entry label styling
        pieChart.setEntryLabelColor(ContextCompat.getColor(context.getContext(), R.color.new_white));

        pieChart.setEntryLabelTextSize(16f);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        // Retrieve the SearchView and plug it into SearchManager


        return true;
    }

}
