package com.apple.TestCases.ValuesExistance;

import com.apple.APIManager.Base;
import com.apple.APIManager.ITunesSearchResource;
import com.apple.APIManager.TestDataProvider;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

public class VerifyNames extends TestDataProvider {

    @BeforeTest(groups = {"Names"})
    public void setTestDataFile()
    {
        setMediaType("mixed");
    }

    @Test(dataProvider = "mediaTestData", groups = {"Names"})
    public void testNames(Map<String, String> testData) {

        testData.remove("testCase");
        Response response = ITunesSearchResource.search(testData);
        int resultCount = response.jsonPath().get("resultCount");
        Base.verifyMatchedAttributeValueNotNull(response,resultCount,"Name");


    }
}
