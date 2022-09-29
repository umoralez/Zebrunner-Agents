package com.solvd;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

public abstract class BaseClass {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    protected static final OkHttpClient client = new OkHttpClient();
    protected static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    protected static final File propertiesPath = new File(System.getProperty("user.dir")
            + "/src/main/resources/config.properties");
    protected static final File propertiesFile = new File(System.getProperty("user.dir")
            + "/src/main/resources/agent.yaml");
    protected Gson gson = new Gson();
    protected Properties properties;

    public BaseClass (){
        propertiesReader();

    }
    private void propertiesReader() {

        try (FileInputStream fileInput = new FileInputStream(System.getProperty("user.dir")
                + "/src/main/resources/config.properties")) {

            properties = new Properties();
            properties.load(fileInput);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
