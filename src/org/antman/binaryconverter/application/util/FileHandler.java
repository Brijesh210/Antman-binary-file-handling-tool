package org.antman.binaryconverter.application.util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {
    public FileHandler(){

    }

    public String extractTextFromFile(File file) throws FileNotFoundException {
        List<String> lines = extractLinesFromFile(file);
        StringBuilder sb = new StringBuilder();
        for(String line : lines) {
            sb.append(line+"\n");
        }
        return sb.toString();
    }

    public List<String> extractLinesFromFile(File file) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.lines().collect(Collectors.toList());
    }

}
