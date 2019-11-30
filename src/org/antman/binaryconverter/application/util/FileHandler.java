package org.antman.binaryconverter.application.util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        List<String> lines = new ArrayList<>();
        try {
            lines.add(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
