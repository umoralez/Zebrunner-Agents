package com.solvd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileUtils {
    private static final Logger LOGGER = LogManager.getLogger(FileUtils.class);
    protected static final File tokenPath = new File(System.getProperty("user.dir")
            + "/src/main/resources/token.properties");

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

    private static String removeInitialSpaces(String word) {
        for (char character : word.toCharArray()) {
            if (!Character.isSpaceChar(character)) {
                word = word.substring(word.indexOf(character));
                break;
            }
        }
        return word;
    }

    //Add token in properties file
    public static void tokenProperties(String token) {

        //Append the token if the token doesn't exist in the properties file
        if(!FileUtils.replaceToken(token)) {
            try (FileWriter fileWriter = new FileWriter(tokenPath, true)) {

                fileWriter.append("Authorization=Bearer ");
                fileWriter.append(String.valueOf(token));

            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
    //Replace existent token in properties file for the new one
    private static boolean replaceToken(String token) {

        try (Reader reader = new FileReader(tokenPath);
             BufferedReader bufferReader = new BufferedReader(reader);
             FileChannel channel = FileChannel.open(tokenPath.toPath(), StandardOpenOption.WRITE, StandardOpenOption.READ)) {

            BufferedReader copyLines = new BufferedReader(new FileReader(tokenPath));

            //Collect the token's index line
            Optional<Long> indexLine = findTextLinePosition(copyLines);

            //Collect the token's line
            Optional<String> targetLine = bufferReader.lines()
                    .filter(k -> k.contains("Authorization=Bearer "))
                    .findFirst();


            if (targetLine.isPresent() && indexLine.isPresent()) {
                //Move the file's prompt in the index line
                channel.position(indexLine.get());
                //Write the line with the token's bytes representation (replace the old token for the new one)
                channel.write(ByteBuffer.wrap(("Authorization=Bearer " + token).getBytes()));
                return true;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return false;
    }


    private static Optional<Long> findTextLinePosition(BufferedReader bufferedReader) {

        List<String> listLines = bufferedReader.lines().collect(Collectors.toList());

        //Return the index line
        for (int i = 0; i < listLines.size(); i++) {
            if (listLines.get(i).contains("Authorization")) {
                return Optional.of((long) i);
            }
        }
        return Optional.empty();
    }
    public static Map<String, String> readToken() {
        Map<String, String> token = new HashMap<>();

        try (Reader reader = new FileReader(tokenPath);
             BufferedReader bufferReader = new BufferedReader(reader)) {

            Optional<String> targetLine = bufferReader.lines()
                    .filter(k -> k.contains("Authorization=Bearer "))
                    .findFirst();

            if (targetLine.isPresent()) {
                String[] tokenHeader = targetLine.get().split("=");

                tokenHeader[1] = tokenHeader[1].split("Authorization")[0];

                token.put(tokenHeader[0], tokenHeader[1]);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return token;
    }

}