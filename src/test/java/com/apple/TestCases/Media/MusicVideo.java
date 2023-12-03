package com.apple.TestCases.Media;

import com.apple.APIManager.Base;
import com.apple.APIManager.ITunesSearchResource;
import com.apple.APIManager.TestDataProvider;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Properties;

import static org.hamcrest.Matchers.greaterThan;

public class MusicVideo extends TestDataProvider {

    private static final ThreadLocal<String> musicArtist = new ThreadLocal<>();
    private static final ThreadLocal<String> album = new ThreadLocal<>();

    private static final ThreadLocal<String> mix = new ThreadLocal<>();

    @BeforeTest( groups = {"musicVideo"})
    public void setTestDataFile()
    {
        setMediaType("musicVideo");
    }


    @Test(dataProvider = "mediaTestData", groups = {"musicVideo"})
    public void testMoviesSearch(Map<String, String> testData) {

        testData.remove("testCase");

        Response response = ITunesSearchResource.search(testData);
        response.then().contentType(ContentType.JSON).body("resultCount", greaterThan(0));
        int resultCount = response.jsonPath().get("resultCount");

        Base.verifyAttributeValueExactMatchForMultipleObjects(response,resultCount,"kind","music-video");
        if(testData.containsKey("mix"))
        {
            verifyEntityMix(response,resultCount,testData.get("mix"));
        }
        if(testData.containsKey("musicArtist"))
        {
            verifyMusicArtistName(response,resultCount,testData.get("musicArtist"));
        }

    }

    public void verifyMusicArtistName(Response response,int resultCount,String expected)
    {
        String []keys={"artistName"};
        Base.verifyAttributeValuePartialMatchForExpectedArrayOfObjects(response,resultCount,keys,expected.split(" "));

    }

    public void verifyEntityMix(Response response,int resultCount,String expected)
    {
        String []keys={"collectionName","collectionCensoredName","trackCensoredName","trackName"};
        Base.verifyAttributeValuePartialMatchForExpectedArrayOfObjects(response,resultCount,keys,expected.split(" "));
    }

}
