package org.antman.binaryconverter.application.converter;

import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    private FileWriter fileWriter;
    private String nameOfOutputFile;


    public FileHandler(String nameOfOutputFile) {
        this.nameOfOutputFile = nameOfOutputFile;
    }

    public FileWriter getFileWriter() {
        return fileWriter;
    }

    public void setFileWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    public String getNameOfOutputFile() {
        return nameOfOutputFile;
    }

    public void setNameOfOutputFile(String nameOfOutputFile) {
        this.nameOfOutputFile = nameOfOutputFile;
    }

    public void writeToTheFile(String binary) {
        try {
            fileWriter = new FileWriter(nameOfOutputFile);
            System.out.println("successful");
            fileWriter.write(binary);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(" error occured" + e.getMessage());
        }
    }
}
