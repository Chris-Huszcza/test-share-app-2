package com.example.mytestapplication.data;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class DataManagerTest {

    private DataManager dataManager;
    private final Context context = ApplicationProvider.getApplicationContext();

    @Before
    public void setUp() {
        dataManager = new DataManager(context);
    }

    @Test
    public void testSaveDataItem() {
        String key = "STOCK_01";
        String valueToSet = ">200.0";
        dataManager.save(key, valueToSet);
        String retrievedValue = dataManager.get(key);
        assertEquals(valueToSet, retrievedValue);
    }

    @Test(expected = MissingPreferenceException.class)
    public void testGetMissingItem() {
        dataManager.get("MISSING_KEY");
    }
}
