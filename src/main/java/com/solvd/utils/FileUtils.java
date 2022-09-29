package com.solvd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileUtils {
    private static final Logger LOGGER = LogManager.getLogger(FileUtils.class);
    private static File propertiesPath = new File(System.getProperty("user.dir")
            + "/src/main/resources/config.properties");

    public static Map<String, String> propertyValue(String... keys) {
        Map<String, String> properties = new HashMap<>();

        try (Reader reader = new FileReader(propertiesPath);
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

    private static String removeInitialSpaces(String word) {
        for (char character : word.toCharArray()) {
            if (!Character.isSpaceChar(character)) {
                word = word.substring(word.indexOf(character));
                break;
            }
        }
        return word;
    }


    public static void tokenProperties(String token) {


        try (FileWriter fileWriter = new FileWriter(propertiesPath, true)) {

            fileWriter.append(String.valueOf("Authorization=Bearer "));
            fileWriter.append(String.valueOf(token));


        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void replaceToken(String token) {
        try (Reader reader = new FileReader(propertiesPath);
             BufferedReader bufferReader = new BufferedReader(reader)) {

            Optional<String> targetLine = bufferReader.lines()
                    .filter(k -> k.contains("Authorization=Bearer "))
                    .findFirst();

            if (targetLine.isPresent()) {
                try (FileWriter fileWriter = new FileWriter(propertiesPath, true)) {

                    fileWriter.append(token);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static Map<String, String> readToken() {
        Map<String, String> token = new HashMap<>();

        try (Reader reader = new FileReader(propertiesPath);
             BufferedReader bufferReader = new BufferedReader(reader)) {

            Optional<String> targetLine = bufferReader.lines()
                    .filter(k -> k.contains("Authorization=Bearer "))
                    .findFirst();

            if (targetLine.isPresent()) {
                String[] tokenHeader = targetLine.get().split("=");
                token.put(tokenHeader[0], tokenHeader[1]);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        token.forEach((k, v) -> System.out.println(k + v));
        return token;
    }

}