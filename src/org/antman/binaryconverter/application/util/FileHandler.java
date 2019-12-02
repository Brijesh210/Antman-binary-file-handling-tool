package org.antman.binaryconverter.application.util;


import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {
    public FileHandler(){

    }
    private PrintWriter writer;


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


    /**
     * Create new file
     * @param path file to create
     * @return true if file created, false if it already exists
     * @throws IOException
     */
    public boolean createFile(File path) throws IOException {
        if(path.createNewFile()){
            return true;
        } else return false;
    }

    public void write(String text, File file) throws IOException {
        writer = new PrintWriter(new FileWriter(file));
        writer.println(text);
        writer.close();
    }

    public void write(Collection<String> strings, File file) throws IOException {
        writer = new PrintWriter(new FileWriter(file));
        for(String s : strings){
            writer.println(s);
        }
        writer.close();
    }
}
