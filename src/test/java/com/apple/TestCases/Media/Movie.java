package com.apple.TestCases.Media;

import com.apple.APIManager.Base;
import com.apple.APIManager.ITunesSearchResource;
import com.apple.APIManager.TestDataProvider;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class Movie extends TestDataProvider {



    @BeforeTest(groups = {"movies"})
    public void setTestDataFile()
    {
        setMediaType("movies");
    }

    @Test(dataProvider = "mediaTestData", groups = {"movies"})
    public void testMoviesSearch(Map<String, String> testData) {

        testData.remove("testCase");
        Response response = ITunesSearchResource.search(testData);
        response.then().contentType(ContentType.JSON).body("resultCount", greaterThan(0));
        int resultCount = response.jsonPath().get("resultCount");
        Base.verifyAttributeValueExactMatchForMultipleObjects(response,resultCount,"kind","feature-movie");

        if(testData.containsKey("releaseYearTerm"))
        {
            verifyEntityReleaseYearTerm(response,resultCount,testData.get("releaseYearTerm"));
        }




    }

    public void verifyMusicArtistName(Response response,int resultCount,String expected)
    {
        String []keys={"artistName"};
        Base.verifyAttributeValuePartialMatchForExpectedArrayOfObjects(response,resultCount,keys,expected.split(" "));

    }

    public void verifyEntityReleaseYearTerm(Response response,int resultCount,String expected)
    {
        String []keys={"collectionName","collectionCensoredName","trackCensoredName","trackName","releaseDate"};
        Base.verifyAttributeValuePartialMatchForExpectedArrayOfObjects(response,resultCount,keys,expected.split(" "));
    }




}
