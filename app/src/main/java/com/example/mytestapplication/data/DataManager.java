package com.example.mytestapplication.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mytestapplication.files.ManagedFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DataManager {

    private ManagedFile settingsFile;
    private static final String FILE_NAME = "settings";

    public DataManager(Context context) {
        settingsFile = new ManagedFile(context, FILE_NAME);
    }

    public void save(String key, String value) throws IOException {
        Properties properties = new Properties();
        properties.load(settingsFile.readAsFileInputStream());
        properties.setProperty(key, value);
        properties.store(settingsFile.openFileOutputStream(), null);
    }

    public String get(String key) throws IOException {
        Properties properties = new Properties();
        properties.load(settingsFile.readAsFileInputStream());
        return properties.getProperty(key);
    }
}
