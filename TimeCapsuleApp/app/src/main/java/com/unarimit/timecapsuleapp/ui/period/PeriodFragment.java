package com.unarimit.timecapsuleapp.ui.period;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.unarimit.timecapsuleapp.R;
import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.ui.common.HorizontalMenuItem;
import com.unarimit.timecapsuleapp.ui.common.IconTextView;
import com.unarimit.timecapsuleapp.ui.period.barchart.BarChartRecyclerViewAdapter;
import com.unarimit.timecapsuleapp.ui.period.barchart.TaskClassElement;
import com.unarimit.timecapsuleapp.ui.period.barchart.TaskElement;
import com.unarimit.timecapsuleapp.utils.CustomDate;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class PeriodFragment extends Fragment {



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PeriodFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    PeriodViewModel viewModel;
    RecyclerView recyclerView;
    TextView nullHintText;
    TextView chooseDate;
    IconTextView statisticBtn;

    View menuView;
    HorizontalMenuItem nextDate;
    HorizontalMenuItem lastDate;
    HorizontalMenuItem aggregateDay;
    HorizontalMenuItem aggregateWeek;
    HorizontalMenuItem aggregateMonth;
    HorizontalMenuItem aggregateYear;
    MenuChooseState menuOnChoose;

    PopupWindow popupStaticMenu;
    View statisticMenuView;
    TextView listMenuItem;
    TextView pieMenuItem;
    TextView barMenuItem;

    LinearLayout mainLayout;
    View statisticListView;
    View statisticPieView;
    View statisticBarView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_period, container, false);

        nullHintText = view.findViewById(R.id.period_nullhint);
        chooseDate = view.findViewById(R.id.period_list_date_choose);
        statisticBtn = view.findViewById(R.id.period_list_statistic_button);
        mainLayout = view.findViewById(R.id.period_main_layout);
        statisticListView = inflater.inflate(R.layout.fragment_period_statistic_view_list, container,false);
        recyclerView = statisticListView.findViewById(R.id.period_list);
        statisticPieView = inflater.inflate(R.layout.fragment_period_statistic_view_pie, container,false);
        statisticBarView = inflater.inflate(R.layout.fragment_period_statistic_view_bar_list, container,false);

        menuView = inflater.inflate(R.layout.view_period_menu, container, false);
        lastDate = menuView.findViewById(R.id.period_menu_item_pre);
        nextDate = menuView.findViewById(R.id.period_menu_item_next);
        aggregateDay = menuView.findViewById(R.id.period_menu_item_day);
        aggregateWeek = menuView.findViewById(R.id.period_menu_item_week);
        aggregateMonth = menuView.findViewById(R.id.period_menu_item_month);
        aggregateYear = menuView.findViewById(R.id.period_menu_item_year);

        statisticMenuView = inflater.inflate(R.layout.view_period_statistic_menu, container, false);
        listMenuItem = statisticMenuView.findViewById(R.id.period_statistic_menu_list);
        pieMenuItem = statisticMenuView.findViewById(R.id.period_statistic_menu_pie);
        barMenuItem = statisticMenuView.findViewById(R.id.period_statistic_menu_bar);

        // init arrange choose menu
        aggregateDay.SetOnClick(true);
        menuOnChoose = MenuChooseState.DAY;
        // init statistic
        viewModel = new PeriodViewModel();
        /* UI will update in onResume() */
        // init arrange switch button
        lastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.LastDay();
                UpdateUI();
            }
        });
        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.NextDay();
                UpdateUI();
            }
        });
        // init arrange menu
        PopupWindow popupWindow = new PopupWindow(menuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(chooseDate);
            }
        });
        // init statistic menu
        popupStaticMenu = new PopupWindow(statisticMenuView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupStaticMenu.setOutsideTouchable(true);
        statisticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupStaticMenu.showAsDropDown(statisticBtn);
            }
        });
        InitStatisticMenuItems();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.Refresh(); // TODO: optimise
        UpdateUI();
    }



    public void UpdateUI(){
        CustomDate date = new CustomDate(viewModel.getCalendar());
        chooseDate.setText(date.GetDateString());
        if(DbContext.statisticType == StatisticType.LIST){
            mainLayout.removeAllViews();
            mainLayout.addView(statisticListView, 0);
            if(viewModel.getPeriods() == null){
                recyclerView.setVisibility(View.GONE);
                nullHintText.setVisibility(View.VISIBLE);
            }else {
                recyclerView.setVisibility(View.VISIBLE);
                nullHintText.setVisibility(View.GONE);
                recyclerView.setAdapter(new PeriodRecyclerViewAdapter(viewModel.getPeriods()));
            }
            //******************** BAR *************************//
        }else if(DbContext.statisticType == StatisticType.BAR){
            mainLayout.removeAllViews();
            if(viewModel.getPeriods() == null){
                nullHintText.setVisibility(View.VISIBLE);
                return;
            }
            nullHintText.setVisibility(View.GONE);
            mainLayout.addView(statisticBarView, 0);
            RecyclerView barRecyclerView = statisticBarView.findViewById(R.id.statistic_bar_list);
            List<TaskClassElement> taskClassElementList = new LinkedList<>();
            for (Period period:
                    viewModel.getPeriods()) {
                long real_begin = Math.max(period.getBegin(), viewModel.getCalendar() * 24 * 3600);
                long real_end = Math.min(period.getEnd(), (viewModel.getCalendar() + 1) * 24 * 3600);
                long during = real_end - real_begin;
                // statistic period by task, tasks sort by taskclass
                TaskClassElement taskClassElement = taskClassElementList.stream()
                        .filter(x -> x.name.equals(period.getTask().getTaskClass().getName()))
                        .findFirst()
                        .orElse(null);
                if(taskClassElement == null){
                    TaskClassElement classElement = new TaskClassElement(period.getTask().getTaskClass().getName(), period.getTask().getTaskClass().getColor(), during);
                    classElement.taskList.add(new TaskElement(classElement, period.getTask().getName(), period.getTask().getIcon(), during));
                    taskClassElementList.add(classElement);
                }else{
                    taskClassElement.totalDuring += during;
                    TaskElement taskElement =  taskClassElement.taskList.stream()
                            .filter(x -> x.name.equals(period.getTask().getName()))
                            .findFirst()
                            .orElse(null);
                    if(taskElement == null){
                        taskClassElement.taskList.add(new TaskElement(taskClassElement, period.getTask().getName(), period.getTask().getIcon(), during));
                    }else{
                        taskElement.time += during;
                    }
                }
            }
            // sort by during
            List<TaskElement> viewList = new LinkedList<>();
            taskClassElementList.sort((x, y) -> (int)(y.totalDuring - x.totalDuring)); // descend
            for (TaskClassElement element:
                 taskClassElementList) {
                element.taskList.sort((x, y) -> (int)(y.time - x.time));
                viewList.addAll(element.taskList);
            }
            long max_time = viewList.stream().mapToLong(TaskElement::getTime).max().getAsLong();
            barRecyclerView.setAdapter(new BarChartRecyclerViewAdapter(viewList, max_time));


            //******************** PIE *************************//
        }else if(DbContext.statisticType == StatisticType.PIE){
            nullHintText.setVisibility(View.GONE);
            mainLayout.removeAllViews();
            mainLayout.addView(statisticPieView, 0);
            PieChart pieChart = statisticPieView.findViewById(R.id.period_pie_chart);
            Map<String, PieElement>  map = new HashMap<>();
            long sum = 0;
            if(viewModel.getPeriods() != null){
                for (Period period:
                        viewModel.getPeriods()) {
                    long real_begin = Math.max(period.getBegin(), viewModel.getCalendar() * 24 * 3600);
                    long real_end = Math.min(period.getEnd(), (viewModel.getCalendar() + 1) * 24 * 3600);
                    long during = real_end - real_begin;
                    sum += during;
                    if(map.containsKey(period.getTask().getTaskClass().getName())){
                        map.get(period.getTask().getTaskClass().getName()).time += during;
                    }else {
                        map.put(period.getTask().getTaskClass().getName(), new PieElement(period.getTask().getTaskClass().getColor(), during)

                        );
                    }
                }
            }
            List<PieEntry> entry_list = new LinkedList<>();
            List<Integer> color_list = new LinkedList<>();
            for (String classname:
                 map.keySet()) {
                PieElement element = map.get(classname);
                entry_list.add(new PieEntry(element.GetFormatDuring(), classname));
                color_list.add(Color.parseColor(element.color));
            }
            float last = 1 - (float)sum / 24 / 3600;
            // TODO: remove following debug output
            if(last < 0){
                last = 0;
                Log.d("debug", "bug < 0");
            }else if(last > 1){
                Log.d("debug", "bug > 1");
            }
            // TODO: use string value
            entry_list.add(new PieEntry(last * 100, "未记录时间"));
            color_list.add(getResources().getColor(R.color.white_gray, null));

            PieDataSet pieDataSet = new PieDataSet(entry_list, "");
            pieDataSet.setColors(color_list);
            PieData pieData = new PieData(pieDataSet);
            pieChart.setUsePercentValues(true);
            pieData.setValueFormatter(new PercentFormatter(pieChart));
            pieData.setValueTextSize(18f);
            pieChart.setData(pieData);
            pieChart.invalidate();
        }
    }
    class PieElement{
        String color;
        long time;

        public PieElement(String color, long time) {
            this.color = color;
            this.time = time;
        }
        public float GetFormatDuring(){
            return (float)time / 3600 / 24 * 100;
        }
    }



    private void InitStatisticMenuItems(){
        listMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbContext.statisticType = StatisticType.LIST;
                popupStaticMenu.dismiss();
                UpdateUI();

            }
        });
        barMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbContext.statisticType = StatisticType.BAR;
                popupStaticMenu.dismiss();
                UpdateUI();
            }
        });
        pieMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbContext.statisticType = StatisticType.PIE;
                popupStaticMenu.dismiss();
                UpdateUI();
            }
        });
    }

    class BarValueFormat extends ValueFormatter{
        private String[] values;

        public BarValueFormat(String[] values) {
            this.values = values;
        }

        @Override
        public String getFormattedValue(float value) {
            return this.values[(int)value];
        }
    }

    enum MenuChooseState{
        DAY,
        WEEK,
        MONTH,
        YEAR
    }

    public enum StatisticType{
        LIST,
        PIE,
        BAR
    }

}