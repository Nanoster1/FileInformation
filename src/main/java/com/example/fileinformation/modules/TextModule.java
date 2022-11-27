package com.example.fileinformation.modules;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Component
public class TextModule extends Module {
    
    private static Map<String, String> searchNgram(String[][] parsedText, int count) {
        Map<String, Map<String, Integer>> counter = new HashMap<>();
        for (String[] strings : parsedText) {
            for (int i = 0; i < strings.length - count + 1; i++) {
                StringBuilder ngramBuilder = new StringBuilder();
                for (int c = 0; c < count - 1; c++) {
                    ngramBuilder.append(strings[i + c]).append(" ");
                }
                String ngram = ngramBuilder.substring(0, ngramBuilder.length() - 1);
                Map<String, Integer> integerMap = counter.getOrDefault(ngram, new HashMap<>());
                integerMap.put(strings[i + count - 1], integerMap.getOrDefault(strings[i + count - 1], 0) + 1);
                counter.put(ngram, integerMap);
            }
        }
        Map<String, String> ngrams = new HashMap<>();
        counter.forEach((key, value) -> value.entrySet().stream().max(Map.Entry.comparingByValue()).ifPresent(entry1 -> ngrams.put(key, entry1.getKey())));
        return ngrams;
    }
    
    private static String[][] readParsedText(File file) {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parseText(text.toString());
    }
    
    private static String[][] parseText(String text) {
        String[] sentences = Arrays.stream(text.toLowerCase().split("[.!?;:()\n]")).filter(s -> s.length() > 0).toArray(String[]::new);
        String[][] parsedText = new String[sentences.length][];
        int i = 0;
        for (String sentence : sentences) {
            parsedText[i] = Arrays.stream(sentence.trim().split("[^a-zA-Zа-яА-ЯёЁ']")).filter(s -> s.length() > 0).toArray(String[]::new);
            i++;
        }
        return parsedText;
    }
    
    @Override
    public boolean isSuitableExtension(File file) {
        String extension = FilenameUtils.getExtension(file.getName());
        return extension.equals("txt");
    }
    
    @Override
    public String getDesc() {
        return "Выводит кол-во строк";
    }
    
    @Override
    public void execCommand(File file) {
        printCountLines(file);
    }
    
    private void printCountLines(File file) {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                lines++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Количество строк: " + lines);
    }
}
