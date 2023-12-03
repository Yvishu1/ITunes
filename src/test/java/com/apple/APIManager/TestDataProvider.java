package com.apple.APIManager;

import com.google.gson.Gson;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class TestDataProvider {
    private static final ThreadLocal<String> mediaType = new ThreadLocal<>();

    public static void setMediaType(String type) {
        mediaType.set(type);
    }

    public static String getMediaType() {
        return mediaType.get();
    }

    @DataProvider(name = "mediaTestData")
    public Object[][] getMoviesTestData() throws FileNotFoundException {


        File testDataFile = new File("src/test/resources/mediaType/"+getMediaType()+".json");
        Map<String, Object>[] testDataArray = new Gson().fromJson(new FileReader(testDataFile), Map[].class);
        Object[][] testData = new Object[testDataArray.length][1];
        for (int i = 0; i < testDataArray.length; i++) {
            testData[i][0] = testDataArray[i];
        }
        return testData;
    }


}
