package com.shiftschedule.app.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.shiftschedule.app.dao.ShiftScheduleDao;
import com.shiftschedule.app.dao.UserPreferencesDao;
import com.shiftschedule.app.dao.AlarmSettingsDao;
import com.shiftschedule.app.model.ShiftSchedule;
import com.shiftschedule.app.model.RotationPattern;
import com.shiftschedule.app.model.ShiftType;
import com.shiftschedule.app.model.UserPreferences;
import com.shiftschedule.app.model.AlarmSettings;

@Database(
    entities = {
        ShiftSchedule.class,
        RotationPattern.class,
        ShiftType.class,
        UserPreferences.class,
        AlarmSettings.class
    },
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract ShiftScheduleDao shiftScheduleDao();
    public abstract UserPreferencesDao userPreferencesDao();
    public abstract AlarmSettingsDao alarmSettingsDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
} 