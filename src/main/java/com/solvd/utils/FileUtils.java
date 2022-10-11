package com.solvd.utils;

import com.solvd.domain.AgentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileUtils {
    private static final Logger LOGGER = LogManager.getLogger(FileUtils.class);

    public static Map<String, String> propertyValue(String... keys) throws AgentFileNotFound {
        Map<String, String> properties = new HashMap<>();
        File agentFile = setAgentFile();

        try (Reader reader = new FileReader(agentFile);
             BufferedReader bufferReader = new BufferedReader(reader)) {

            for (int i = 0; i < keys.length; i++) {
                final int keyIndex = i;

                Optional<String> targetLine = bufferReader.lines()
                        .filter(k -> k.contains(keys[keyIndex]))
                        .findFirst();

                if (targetLine.isPresent()) {
                    String removedIndentation = removeInitialSpaces(targetLine.get());
                    String[] keyValueProperty = removedIndentation.split("[:=]");

                    properties.put(keyValueProperty[0], removeInitialSpaces(keyValueProperty[1]));
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return properties;
    }

    public static String removeInitialSpaces(String word) {
        for (char character : word.toCharArray()) {
            if (!Character.isSpaceChar(character)) {
                word = word.substring(word.indexOf(character));
                break;
            }
        }
        return word;
    }

    public static String readValueInProperties(File file, String target) {
        String value = "";
        try (Reader reader = new FileReader(file);
             BufferedReader bufferReader = new BufferedReader(reader)) {

            Optional<String> targetLine = bufferReader.lines()
                    .filter(k -> k.contains(target))
                    .findFirst();

            if (targetLine.isPresent()) {
                String[] tokenHeader = targetLine.get().split("=");

                tokenHeader[1] = tokenHeader[1].split(target)[0];

                value = tokenHeader[1];
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return value;
    }

    private static File setAgentFile() throws AgentFileNotFound {
        String[] files = new File(System.getProperty("user.dir").concat("/src/main/resources")).list();

        try {
            for (String file : files) {
                if (file.equals("agent.yaml")) {
                    return new File(System.getProperty("user.dir").concat("/src/main/resources/agent.yaml"));
                }
                if (file.equals("agent.properties")) {
                    return new File(System.getProperty("user.dir").concat("/src/main/resources/agent.properties"));
                }
            }
            throw new AgentFileNotFound("You have to provide agent.yaml or agent.properties file.");
        } catch (NullPointerException e) {
            throw new AgentFileNotFound("You have to provide agent.yaml or agent.properties file.");
        }
    }

    public static AgentDTO setAgentDAO() throws AgentFileNotFound {

        File agentFile = setAgentFile();
        AgentDTO agentDTO = new AgentDTO();
        Map<String, String> agentItems;

        if (agentFile.getName().contains("yaml")) {
            agentItems = propertyValue("project-key", "access-token");
            agentDTO.setKeyProject(agentItems.get("project-key"));
            agentDTO.setAccessToken(agentItems.get("access-token"));
        } else {
            agentItems = propertyValue("reporting.project-key", "reporting.server.access-token");
            agentDTO.setKeyProject(agentItems.get("reporting.project-key"));
            agentDTO.setAccessToken(agentItems.get("reporting.server.access-token"));
        }
        return agentDTO;
    }
}