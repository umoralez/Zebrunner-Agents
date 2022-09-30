package com.solvd.requests.post;

import com.solvd.BaseClass;
import com.solvd.utils.FileUtils;
import com.solvd.utils.RequestUpdate;

public class TestExcecutionStart extends BaseClass{
    
    private StringBuilder endpointTestStart = new StringBuilder(properties.getProperty("URL"));

    public void testExcecutionStartRequest(){
        String projectId = FileUtils.readValueInProperties(idPath, "id").get("id") + "/tests";

        endpointTestStart.append(RequestUpdate.addQueryParamsValues("ENP_RUN_FINISH", projectId));
    
    }
}
