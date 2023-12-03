package com.apple.TestCases.CountryITunesStore;

import com.apple.APIManager.Base;
import com.apple.APIManager.ITunesSearchResource;
import com.apple.APIManager.PropertiesManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Properties;

import static org.hamcrest.Matchers.equalTo;

public class SearchContentByCountry {


    Properties properties = PropertiesManager.loadProperties("src/test/resources/queryParams.properties");


    @Test(groups = {"countries"})
    public void testSearchResultsForDifferentCountries() {
        Map<String, String> map = PropertiesManager.extractKeyValueSet("src/test/resources/countries.properties");
        for (Map.Entry<String, String> mp : map.entrySet()) {
            if (mp.getKey().length() == 2) {
                Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
                queryParams1.put("country", mp.getKey());
                Response response = ITunesSearchResource.search(queryParams1);
                int resultCount = response.jsonPath().get("resultCount");
                Base.verifyAttributeValueExactMatchForMultipleObjects(response, resultCount, "country", mp.getValue());
            }
        }

    }

    @Test(groups = {"countries"})
    public void testSearchResultsForInvalidCountry() {
        Map<String, String> map = PropertiesManager.extractKeyValueSet("src/test/resources/countries.properties");
        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("country", map.get("invalidCountryCode"));
        Response response = ITunesSearchResource.searchAndIgnoreStatusCode(queryParams1);
        response.then().contentType(ContentType.JSON).statusCode(400).body("errorMessage", equalTo("Invalid value(s) for key(s): [country]"));


    }

    @Test(groups = {"countries"})
    public void testSearchResultsForDefaultCountry() {
        Map<String, String> map = PropertiesManager.extractKeyValueSet("src/test/resources/countries.properties");
        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("country", map.get("defaultCountry"));
        Response response = ITunesSearchResource.searchAndIgnoreStatusCode(queryParams1);
        int resultCount = response.jsonPath().get("resultCount");
        Base.verifyAttributeValueExactMatchForMultipleObjects(response, resultCount, "country", map.get(map.get("defaultCountry")));


    }


}
