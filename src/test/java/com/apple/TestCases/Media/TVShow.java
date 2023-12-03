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

public class TVShow extends TestDataProvider {

    @BeforeTest(groups = {"tvShow"})
    public void setTestDataFile()
    {
        setMediaType("tvShow");
    }

    @Test(dataProvider = "mediaTestData", groups = {"tvShow"})
    public void testMoviesSearch(Map<String, String> testData) {

        testData.remove("testCase");
        Response response = ITunesSearchResource.search(testData);
        response.then().contentType(ContentType.JSON).body("resultCount", greaterThan(0));
        int resultCount = response.jsonPath().get("resultCount");
        Base.verifyAttributeValueExactMatchForMultipleObjects(response,resultCount,"kind","tv-episode");





    }
}
