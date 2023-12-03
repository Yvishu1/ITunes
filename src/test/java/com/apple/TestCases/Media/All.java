package com.apple.TestCases.Media;

import com.apple.APIManager.Base;
import com.apple.APIManager.ITunesSearchResource;
import com.apple.APIManager.TestDataProvider;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;

public class All extends TestDataProvider {

    @BeforeTest(groups = {"all"})
    public void setTestDataFile()
    {
        setMediaType("all");
    }

    @Test(dataProvider = "mediaTestData", groups = {"all"})
    public void testMoviesSearch(Map<String, String> testData) {

        testData.remove("testCase");
        Response response = ITunesSearchResource.search(testData);
        response.then().contentType(ContentType.JSON).body("resultCount", greaterThan(0));





    }
}
