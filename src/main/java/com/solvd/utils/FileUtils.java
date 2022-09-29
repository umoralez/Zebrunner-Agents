package com.solvd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileUtils {
    private static final Logger LOGGER = LogManager.getLogger(FileUtils.class);

    public static Map<String, String> propertyValue(File propertiesFile, String... keys) {
        Map<String, String> properties = new HashMap<>();

        try(Reader reader = new FileReader(propertiesFile);
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

}
