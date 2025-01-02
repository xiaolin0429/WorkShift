package com.shiftschedule.app.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.io.OutputStream;
import java.io.InputStream;

public class LogUtil {
    private static final String TAG = "ShiftSchedule";
    private static final String LOG_FILE_PREFIX = "shift_schedule_log_";
    private static boolean isDebug = true;
    private static String logFilePath;
    
    public static void init(Context context) {
        // 设置日志文件路径
        File logDir = new File(context.getExternalFilesDir(null), "logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        
        String timestamp = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        logFilePath = new File(logDir, LOG_FILE_PREFIX + timestamp + ".txt").getAbsolutePath();
        
        // 记录应用启动日志
        i("Application", "App started");
    }
    
    public static void d(String tag, String message) {
        if (isDebug) {
            Log.d(TAG, tag + ": " + message);
            writeToFile("D", tag, message);
        }
    }
    
    public static void i(String tag, String message) {
        Log.i(TAG, tag + ": " + message);
        writeToFile("I", tag, message);
    }
    
    public static void w(String tag, String message) {
        Log.w(TAG, tag + ": " + message);
        writeToFile("W", tag, message);
    }
    
    public static void e(String tag, String message) {
        Log.e(TAG, tag + ": " + message);
        writeToFile("E", tag, message);
    }
    
    public static void e(String tag, String message, Throwable e) {
        Log.e(TAG, tag + ": " + message, e);
        writeToFile("E", tag, message + "\n" + Log.getStackTraceString(e));
    }
    
    private static synchronized void writeToFile(String level, String tag, String message) {
        if (logFilePath == null) return;
        
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(logFilePath, true));
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date());
            writer.append(String.format("%s %s/%s: %s: %s\n", timestamp, level, TAG, tag, message));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed to write to log file", e);
        }
    }
    
    public static File getLatestLogFile(Context context) {
        File logDir = new File(context.getExternalFilesDir(null), "logs");
        File[] logFiles = logDir.listFiles((dir, name) -> name.startsWith(LOG_FILE_PREFIX));
        
        if (logFiles == null || logFiles.length == 0) {
            return null;
        }
        
        File latestLog = logFiles[0];
        for (File file : logFiles) {
            if (file.lastModified() > latestLog.lastModified()) {
                latestLog = file;
            }
        }
        
        return latestLog;
    }
    
    /**
     * 导出日志文件
     * @param context 上下文
     * @param uri 目标URI
     * @return 是否导出成功
     */
    public static boolean exportLogFile(Context context, Uri uri) {
        File logFile = getLatestLogFile(context);
        if (logFile == null || !logFile.exists()) {
            return false;
        }

        try {
            OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
            if (outputStream == null) {
                return false;
            }

            FileInputStream inputStream = new FileInputStream(logFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 获取所有日志文件
     */
    public static List<File> getAllLogFiles(Context context) {
        File logDir = new File(context.getExternalFilesDir(null), "logs");
        File[] files = logDir.listFiles((dir, name) -> name.startsWith(LOG_FILE_PREFIX));
        if (files == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(files);
    }
} 