package org.antman.binaryconverter.application.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import static sun.java2d.cmm.ColorTransform.In;

public class Controller implements Initializable {
    @FXML
    public TextArea urlTextArea;
    @FXML
    public ComboBox<String> comboBox;
    public ComboBox<Integer> comboBox2;
    public TextField textFieldOption;
    public TextArea textAreaOption;

    @FXML
    public VBox vbMenu;
    FileChooser fileChooser = new FileChooser();
    ObservableList<String> optt = FXCollections.observableArrayList("Int", "Float", "Double", "Loop");

    @FXML
    private FileReader fileReader;

    @FXML
    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDragDrop(DragEvent dragEvent) {
        File file = dragEvent.getDragboard().getFiles().get(0);
        String fileName = file.getAbsolutePath();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String sr;
            while ((sr = br.readLine()) != null) {
                urlTextArea.appendText(sr + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getAbsolutePath());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.setItems(optt);
        comboBox.setOnAction(action -> {
            String str = comboBox.getSelectionModel().getSelectedItem();
            if (str.equals("Loop")) {
                textFieldOption.setVisible(true);
            } else {
                textFieldOption.setVisible(false);
            }
        });

        fileChooser.setInitialDirectory(new File("c:\\"));     // save as open initial directory
    }

    public void comboBoxMouseKeyPressed(MouseEvent mouseEvent) {
    }

    public void buttonAddMouseClicked() {
        //  textAreaOption.setText(comboBox.getSelectionModel().getSelectedItem());
        String str = comboBox.getSelectionModel().getSelectedItem();
        if (str != "Loop") {
            textAreaOption.appendText(comboBox.getSelectionModel().getSelectedItem() + "\n");
        } else {
            String number = textFieldOption.getCharacters().toString();
            if (number.matches("^[0-9]*$") && Integer.parseInt(number) < 1000) {
                textAreaOption.appendText(str + "(" + number + ")\n");
            } else {

            }
        }
    }

    public void saveAsStructureOnMouseClicked(MouseEvent mouseEvent) {
        Window stage = vbMenu.getScene().getWindow();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("structure");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text", "*.txt"));
        try {
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile()); // save chosen directory

            StringBuilder sb = new StringBuilder();
            sb.append(textAreaOption.getText());
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void onDragDroppedStructure(DragEvent dragEvent) {
        File file = dragEvent.getDragboard().getFiles().get(0);
        String fileName = file.getAbsolutePath();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String sr;
            while ((sr = br.readLine()) != null) {
                textAreaOption.appendText(sr + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getAbsolutePath());
    }

    public void onDragOverStructure(DragEvent dragEvent) {     // Drag Drop structure file
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

}
