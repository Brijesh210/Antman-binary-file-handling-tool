package org.antman.binaryconverter.application.util;


import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class providing basic file I/O methods
 *
 * @author Ismoil Atajanov
 * @version 2.0
 */
public class FileHandler {
    public FileHandler() {

    }

    private PrintWriter writer;

    public String readAll(File file) throws FileNotFoundException {
        List<String> lines = readLines(file);
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public List<String> readLines(File file) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.lines().collect(Collectors.toList());
    }


    /**
     * Create new file
     *
     * @param path file to create
     * @return true if file created, false if it already exists
     * @throw IOException when you have issue with file
     */
    public boolean createFile(File path) throws IOException {
        return path.createNewFile();
    }

    /**
     * Read all bytes from a file
     *
     * @param file file to read from
     * @return a ByteBuffer containing all the bytes
     * @throws IOException if the file is not found
     */
    public ByteBuffer readBytesToBuffer(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        return ByteBuffer.wrap(bytes);
    }

    public void writeBytes(byte[] bytes, File file) throws IOException {
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
        for (String s : strings) {
            writer.println(s);
        }
        writer.close();
    }
}
