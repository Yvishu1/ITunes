package com.apple.TestCases.Limits;

import com.apple.APIManager.Base;
import com.apple.APIManager.ITunesSearchResource;
import com.apple.APIManager.PropertiesManager;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Properties;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class SearchLimit {

    Properties properties = PropertiesManager.loadProperties("src/test/resources/queryParams.properties");


    @Test(groups = {"limit"})
    public void testSearchResultsWithMinimumLimit()
    {

        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term","limit");
        queryParams1.put("limit","1");
        ITunesSearchResource.search(queryParams1).then().body("resultCount", equalTo(1));

    }

    @Test(groups = {"limit"})
    public void testSearchResultsWithValidRandomLimit()
    {

        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term","limit");
        int randomNumber = Base.generateRandomNumber(1,200);
        queryParams1.put("limit",""+randomNumber);
        ITunesSearchResource.search(queryParams1).then().body("resultCount", equalTo(randomNumber));

    }

    @Test(groups = {"limit"})
    public void testSearchResultsWithExceedLimit()
    {
        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("limit","201");
        ITunesSearchResource.search(queryParams1).then().body("resultCount", equalTo(200));
    }

    @Test(groups = {"limit"})
    public void testSearchResultsWithMaximumLimit()
    {
        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("limit","200");
        ITunesSearchResource.search(queryParams1).then().body("resultCount", equalTo(200));
    }


    @Test(groups = {"limit"})
    public void testSearchResultsWithBoundaryPositiveLimit()
    {
        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("limit","199");
        ITunesSearchResource.search(queryParams1).then().body("resultCount", equalTo(199));
    }

    @Test(groups = {"limit"})
    public void testSearchResultsWithNoLimit()
    {
        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        ITunesSearchResource.search(queryParams1).then().body("resultCount", equalTo(50));
    }

    @Test(groups = {"limit"})
    public void testSearchResultsWithZeroLimit()
    {
        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("limit","0");
        ITunesSearchResource.search(queryParams1).then().body("resultCount", equalTo(0));
    }

    @Test(groups = {"limit"})
    public void testSearchResultsWithNegativeLimit()
    {
        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("limit","-1");
        ITunesSearchResource.search(queryParams1).then().body("resultCount", equalTo(0));
    }

    @Test(groups = {"limit"})
    public void testSearchResultsWithSpecialCharLimit()
    {
        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("limit","*");
        ITunesSearchResource.search(queryParams1).then().contentType(ContentType.JSON).statusCode(400).body("errorMessage", containsString("Invalid"));
    }





}
