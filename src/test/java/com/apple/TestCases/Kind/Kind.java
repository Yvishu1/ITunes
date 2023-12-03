package com.apple.TestCases.Kind;

import com.apple.APIManager.Base;
import com.apple.APIManager.ITunesSearchResource;
import com.apple.APIManager.PropertiesManager;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Properties;



public class Kind {

    Properties properties = PropertiesManager.loadProperties("src/test/resources/queryParams.properties");


    @Test(groups = {"kind"})
    public void testSearchResultsKindWhenMediaIsMusic()
    {

        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term","limit");
       int randomNumber= Base.generateRandomNumber(1,5);
        queryParams1.put("limit",""+randomNumber);
        queryParams1.put("media","music");
        Response response=ITunesSearchResource.search(queryParams1);
        Base.verifyAttributeValueExactMatchForMultipleObjects(response,randomNumber,"kind","song");


    }

    @Test
    public void testSearchResultsKindWhenMediaIsMovie()
    {

        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("media","movie");
        Response response=ITunesSearchResource.search(queryParams1);
        int resultCount = response.jsonPath().get("resultCount");
        Base.verifyAttributeValueExactMatchForMultipleObjects(response,resultCount,"kind","feature-movie");

    }

    @Test
    public void testSearchResultsKindWhenMediaIsMusicVideo()
    {

        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("media","musicVideo");
        Response response=ITunesSearchResource.search(queryParams1);
        int resultCount = response.jsonPath().get("resultCount");
        Base.verifyAttributeValueExactMatchForMultipleObjects(response,resultCount,"kind","music-video");

    }

    @Test
    public void testSearchResultsKindWhenMediaIsPodcast()
    {

        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("media","podcast");
        Response response=ITunesSearchResource.search(queryParams1);
        int resultCount = response.jsonPath().get("resultCount");
        Base.verifyAttributeValueExactMatchForMultipleObjects(response,resultCount,"kind","podcast");

    }

    @Test
    public void testSearchResultsKindWhenMediaIsTvShow()
    {

        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("media","tvShow");
        Response response=ITunesSearchResource.search(queryParams1);
        int resultCount = response.jsonPath().get("resultCount");
        Base.verifyAttributeValueExactMatchForMultipleObjects(response,resultCount,"kind","tv-episode");

    }

    @Test
    public void testSearchResultsKindWhenMediaIsEbook()
    {

        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("media","ebook");
        Response response=ITunesSearchResource.search(queryParams1);
        int resultCount = response.jsonPath().get("resultCount");
        Base.verifyAttributeValueExactMatchForMultipleObjects(response,resultCount,"kind","ebook");

    }

    @Test
    public void testSearchResultsKindWhenMediaIsSoftware()
    {

        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("media","software");
        Response response=ITunesSearchResource.search(queryParams1);
        int resultCount = response.jsonPath().get("resultCount");
        Base.verifyAttributeValueExactMatchForMultipleObjects(response,resultCount,"kind","software");

    }







}
