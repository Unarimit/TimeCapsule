package com.unarimit.timecapsuleapp;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.unarimit.timecapsuleapp.entities.ExceptionInfo;
import com.unarimit.timecapsuleapp.ui.home.HomeFragment;
import com.unarimit.timecapsuleapp.ui.home.TimingService;
import com.unarimit.timecapsuleapp.ui.period.manager.PeriodManagerActivity;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static MainActivity Activity;
    private AppBarConfiguration mAppBarConfiguration;
    private View header_layout; // left menu bar's top

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PeriodManagerActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_curvejob, R.id.nav_taskclass, R.id.nav_task, R.id.nav_period, R.id.nav_exceptioninfo, R.id.nav_setting)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // init DbContext
        DbContext.InitDbContext(getApplicationContext());

        // get screen width
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        DbContext.WindowsWidth = point.x;

        // Exception Process
        InitExceptionProcessor();

        // Start Timing Service
        Intent intent = new Intent(this, TimingService.class);
        startService(intent);

        // Set Value to Nav
        header_layout = navigationView.getHeaderView(0);
        refreshLeftMenuBar(); // username & achievement
        DbContext.IsMainActive = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        DbContext.IsMainActive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        DbContext.IsMainActive = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void refreshLeftMenuBar(){
        ((TextView)header_layout.findViewById(R.id.nav_bar_username)).setText(DbContext.CurrentUser.getUsername());
        ((TextView)header_layout.findViewById(R.id.nav_bar_achieve)).setText((int)DbContext.CurrentUser.getAchievePoint() + "");
    }


    /**
    * refresh current fragment
    * */
    public void refreshFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager()
                .getPrimaryNavigationFragment().getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.detach(fragment);
        transaction.attach(fragment);
        transaction.commit();
    }

    public void refreshHomeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> list =  getSupportFragmentManager().getPrimaryNavigationFragment().getChildFragmentManager().getFragments();
        Log.d("MainActivity.java", "ex");
        for (Fragment f:list
             ) {
            if(f instanceof HomeFragment){
                ((HomeFragment)f).BigUpdateUI();
            }
        }
    }

    /**
     * ???????????????????????????
     * */
    private void InitExceptionProcessor(){
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                //??????????????????
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                throwable.printStackTrace(pw);
                DbContext.ExceptionInfos.Add(new ExceptionInfo(sw.toString()));

                //?????????????????????????????????????????????????????????????????????APP????????????
                defaultHandler.uncaughtException(thread, throwable);
            }
        });
    }
}