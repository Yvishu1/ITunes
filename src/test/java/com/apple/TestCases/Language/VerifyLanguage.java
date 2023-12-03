package com.apple.TestCases.Language;

import com.apple.APIManager.Base;
import com.apple.APIManager.ITunesSearchResource;
import com.apple.APIManager.PropertiesManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Properties;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class VerifyLanguage {

    Properties properties = PropertiesManager.loadProperties("src/test/resources/queryParams.properties");

    @Test(groups = {"language"})
    public void testSearchResultsForValidLanguages() {
        Map<String, String> map = PropertiesManager.extractKeyValueSet("src/test/resources/languages.properties");
        for (Map.Entry<String, String> mp : map.entrySet()) {
            if (mp.getKey().length() == 3) {
                Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
                queryParams1.put("lang", mp.getValue());
                Response response = ITunesSearchResource.search(queryParams1);
                response.then().contentType(ContentType.JSON).body("resultCount", greaterThan(0));
            }
        }

    }

    @Test(groups = {"language"})
    public void testSearchResultsForInvalidLanguage() {
        Map<String, String> map = PropertiesManager.extractKeyValueSet("src/test/resources/languages.properties");
        Map<String, String> queryParams1 = PropertiesManager.extractQueryParams(properties, "term");
        queryParams1.put("lang", map.get("invalidLanguage"));
        Response response = ITunesSearchResource.searchAndIgnoreStatusCode(queryParams1);
        response.then().contentType(ContentType.JSON).statusCode(400).body("errorMessage", equalTo("Invalid value(s) for key(s): [language]"));


    }


}
