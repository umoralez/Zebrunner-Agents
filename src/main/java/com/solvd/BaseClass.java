package com.solvd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.solvd.domain.AgentDTO;
import com.solvd.utils.AgentFileNotFound;
import com.solvd.utils.FileUtils;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;


public abstract class BaseClass {

	protected static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	protected static final OkHttpClient client = new OkHttpClient();
	protected static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	protected Properties properties;
	protected static final File endpointPath = new File("./src/main/resources/endpoint.properties");
	protected static AgentDTO agentConfigs;
	protected final StringBuilder endpointTestStart;
	protected Gson gson = new Gson();

	public BaseClass() throws AgentFileNotFound {
		propertiesReader();
		agentConfigs = FileUtils.setAgentDAO();
		endpointTestStart = new StringBuilder(properties.getProperty("URL"));
	}

	private void propertiesReader() {

		try (FileInputStream fileInput = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/resources/endpoint.properties")) {

			properties = new Properties();
			properties.load(fileInput);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
