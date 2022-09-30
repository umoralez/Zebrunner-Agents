package com.solvd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileUtils {
    private static final Logger LOGGER = LogManager.getLogger(FileUtils.class);

    public static Map<String, String> propertyValue(String... keys) {
        Map<String, String> properties = new HashMap<>();
        File agentFile = new File(System.getProperty("user.dir") + "/src/main/resources/agent.yaml");
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

    //Add token in properties file
    public static void addValueProperties(String value, File file, String key) {

        //Append the token if the token doesn't exist in the properties file
        if(!FileUtils.replaceValueInProperties(value, file, key)) {
            try (FileWriter fileWriter = new FileWriter(file, true)) {

                fileWriter.append("Authorization=Bearer ");
                fileWriter.append(String.valueOf(value));

            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
    //Replace existent value in properties file for the new one
    private static boolean replaceValueInProperties(String value, File file, String key) {

        try(FileWriter writer = new FileWriter(file)) {
            writer.flush();
            writer.append(key);
            writer.append(String.valueOf(value));
            return true;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }
    public static Map<String, String> readValueInProperties(File file, String target) {
        Map<String, String> token = new HashMap<>();
        try (Reader reader = new FileReader(file);
             BufferedReader bufferReader = new BufferedReader(reader)) {

            Optional<String> targetLine = bufferReader.lines()
                    .filter(k -> k.contains(target))
                    .findFirst();

            if (targetLine.isPresent()) {
                String[] tokenHeader = targetLine.get().split("=");

                tokenHeader[1] = tokenHeader[1].split(target)[0];

                token.put(tokenHeader[0], tokenHeader[1]);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return token;
    }
}