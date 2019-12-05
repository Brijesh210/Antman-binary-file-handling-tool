package org.antman.binaryconverter.application.util;

import org.apache.commons.io.IOUtils;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;


public class FileHandler {
    public FileHandler(){

    }
    private PrintWriter writer;


    public String readAll(File file) throws FileNotFoundException {
        List<String> lines = readLines(file);
        StringBuilder sb = new StringBuilder();
        for(String line : lines) {
            sb.append(line+"\n");
        }
        return sb.toString();
    }

    public List<String> readLines(File file) throws FileNotFoundException {
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
    public ByteBuffer readBytesToBuffer(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        ByteBuffer buffer = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        System.out.println(buffer.array().length);
        return buffer;
    }

    public void writeBytes(byte[] bytes,File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(bytes);
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
