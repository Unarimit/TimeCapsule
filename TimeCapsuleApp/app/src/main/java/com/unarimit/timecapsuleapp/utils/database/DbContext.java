package com.unarimit.timecapsuleapp.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.unarimit.timecapsuleapp.entities.CurveJobBase;
import com.unarimit.timecapsuleapp.entities.Period;
import com.unarimit.timecapsuleapp.entities.Task;
import com.unarimit.timecapsuleapp.entities.TaskClass;
import com.unarimit.timecapsuleapp.entities.User;
import com.unarimit.timecapsuleapp.ui.common.UserConfig;
import com.unarimit.timecapsuleapp.ui.period.PeriodFragment;
import com.unarimit.timecapsuleapp.utils.TimeHelper;

import java.util.UUID;

public class DbContext {
    static DatabaseHelper _databaseHelper;
    static SQLiteDatabase _SQLiteDatabase;
    public static TaskDAO Tasks;
    public static TaskClassDAO TaskClasses;
    public static PeriodDAO Periods;
    public static CurveJobBaseDAO CurveJobBases;
    public static CurveJobDAO CurveJobs;
    public static ExceptionInfoDAO ExceptionInfos;
    public static UserDAO UserInfos;

    static boolean JustCreate = false;
    /**
     * 当前用户
     * */
    public static User CurrentUser;

    // in memory user preference
    public static PeriodFragment.StatisticType statisticType = PeriodFragment.StatisticType.LIST;
    public static int WindowsWidth;
    public static Context Context;
    public static boolean IsMainActive;

    private static final int DB_VERSION = 62;         //database version

    public static void InitDbContext(Context context){
        Context = context;
        _databaseHelper = new DatabaseHelper(context);
        _SQLiteDatabase = _databaseHelper.getWritableDatabase();
        Tasks = new TaskDAO();
        TaskClasses = new TaskClassDAO();
        Periods = new PeriodDAO();
        ExceptionInfos = new ExceptionInfoDAO();
        CurveJobBases = new CurveJobBaseDAO();
        CurveJobs = new CurveJobDAO();
        UserInfos = new UserDAO();

        DbContext.UserInfos.AddOrUpdateValue(UserConfig.SYNC, "false");



        if(JustCreate){
            // basic config
            DbContext.UserInfos.AddOrUpdateValue(UserConfig.ACHIEVE, 100+"");
            DbContext.UserInfos.AddOrUpdateValue(UserConfig.LAST_SYNC, 1+"");
            SeedDataForTest();
        }

        CurrentUser = new User();
    }

    private static void SeedDataForTest(){
        Log.d("debug", "database seed");

        DbContext.UserInfos.AddOrUpdateValue(UserConfig.USER_NAME, "test");
        CurrentUser = new User();
        // add task class
        TaskClass eatTaskClass = new TaskClass(UUID.randomUUID().toString(),"eat", "#444444",1);
        DbContext.TaskClasses.Add(eatTaskClass);
        TaskClass studyTaskClass = new TaskClass(UUID.randomUUID().toString(),"study", "#FF6666",2);
        DbContext.TaskClasses.Add(studyTaskClass);
        // add task
        Task eatTask = new Task(1, UUID.randomUUID().toString(), "eat", "eat make me strong",
                eatTaskClass, 0.2, false, "\uf805", true, TimeHelper.GetCurrentSeconds(), -1);
        DbContext.Tasks.Add(eatTask);
        Task englishTask = new Task(2, UUID.randomUUID().toString(),"english", "i like english",
                studyTaskClass, 1, false,"\uf805", true, TimeHelper.GetCurrentSeconds(), -1);
        Task readingTask = new Task(3, UUID.randomUUID().toString(),"reading", "reading make me feel good",
                studyTaskClass, 1, false,"\uf805", true, TimeHelper.GetCurrentSeconds(), -1);
        Task mathTask = new Task(4, UUID.randomUUID().toString(),"math", "i hate math",
                studyTaskClass, 1, false,"\uf805", false, TimeHelper.GetCurrentSeconds(), -1);
        DbContext.Tasks.Add(englishTask);
        DbContext.Tasks.Add(mathTask);
        DbContext.Tasks.Add(readingTask);
        // add period
        long begin_c = TimeHelper.GetCurrentSeconds() / 3600 / 24 - 1; // begin from yesterday
        long begin = begin_c * 3600 * 24;
        DbContext.Periods.Add(new Period(UUID.randomUUID().toString(), 0, eatTask, begin + 3600 * 8, begin + 3600 * 9, begin_c, begin_c), false);
        DbContext.Periods.Add(new Period(UUID.randomUUID().toString(), 0, mathTask, begin + 3600 * 9, begin + 3600 * 12, begin_c, begin_c), false);
        DbContext.Periods.Add(new Period(UUID.randomUUID().toString(), 0, eatTask, begin + 3600 * 12, begin + 3600 * 13, begin_c, begin_c), false);
        DbContext.Periods.Add(new Period(UUID.randomUUID().toString(), 0, mathTask, begin + 3600 * 13, begin + 3600 * 16, begin_c, begin_c), false);
        DbContext.Periods.Add(new Period(UUID.randomUUID().toString(), 0, englishTask, begin + 3600 * 16, begin + 3600 * 18, begin_c, begin_c), false);
        DbContext.Periods.Add(new Period(UUID.randomUUID().toString(), 0, eatTask, begin + 3600 * 18, begin + 3600 * 18 + 900, begin_c, begin_c), false);
        DbContext.Periods.Add(new Period(UUID.randomUUID().toString(), 0, readingTask, begin + 3600 * 18 + 900, begin + 3600 * 22, begin_c, begin_c), false);
        DbContext.Periods.Add(new Period(UUID.randomUUID().toString(), 0, mathTask, begin + 3600 * 22, begin + 3600 * 25, begin_c, begin_c+1), false);
        DbContext.Periods.Add(new Period(UUID.randomUUID().toString(), 0, eatTask, begin + 3600 * 32, begin + 3600 * 33, begin_c+1, begin_c+1), false);

        DbContext.CurveJobBases.Add(new CurveJobBase(mathTask, begin_c-1, 40));

        JustCreate = false;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context) {
            super(context, "time_capsule",  null , DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(TaskClassDAO.CreateSql);
            db.execSQL(TaskDAO.CreateSql);
            db.execSQL(PeriodDAO.CreateSql);
            db.execSQL(CurveJobBaseDAO.CreateSql);
            db.execSQL(CurveJobDAO.CreateSql);
            db.execSQL(UserDAO.CreateSql);

            db.execSQL(ExceptionInfoDAO.CreateSql);
            DbContext.JustCreate = true;
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS "+ TaskDAO.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+ TaskClassDAO.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+ PeriodDAO.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+ CurveJobDAO.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+ CurveJobBaseDAO.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+ UserDAO.TABLE_NAME);

            db.execSQL("DROP TABLE IF EXISTS "+ ExceptionInfoDAO.TABLE_NAME);
            onCreate(db);
        }

    }

}
