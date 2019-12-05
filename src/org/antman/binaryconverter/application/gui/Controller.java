package org.antman.binaryconverter.application.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.swing.*;
import java.io.*;
import java.net.URL;

import static sun.java2d.cmm.ColorTransform.In;

import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TextArea urlTextArea;
    @FXML
    public TextArea outputTextArea;
    @FXML
    public ComboBox<String> addComboBox;
    public ComboBox<Integer> comboBox2;
    public TextField textFieldOption;
    public TextArea textAreaOption;
    public ComboBox<String> varComboBox;
    public HBox buttonBox;
    public Button convertButton;
    public Button importButton;
    public Button exportButton;
    private HashMap<String, String> settingsMap;

    private String tab = "";
    @FXML
    public CheckBox editableCheckBox;

    @FXML
    public VBox vbMenu;
    FileChooser fileChooser = new FileChooser();
    ObservableList<String> optt = FXCollections.observableArrayList("Char", "Int", "Float", "Var", "Loop", "EndLoop");
    ObservableList<String> varOption = FXCollections.observableArrayList();

    @FXML
    private FileReader fileReader;

    //private boolean flag = false;
    private int counter = 0;

    @FXML
    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDragDrop(DragEvent dragEvent) {
        File file = dragEvent.getDragboard().getFiles().get(0);
//        FileHandler handler = new FileHandler();
//        try {
//            urlTextArea.appendText(handler.extractTextFromFile(file));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        urlTextArea.appendText(file.getAbsolutePath() + "\n");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addComboBox.setItems(optt);
        varComboBox.setItems(varOption);

        addComboBox.setOnAction(action -> {
            String str = addComboBox.getSelectionModel().getSelectedItem();
            if (str.equals("Loop")) {
                varComboBox.setVisible(true);
                textFieldOption.setVisible(true);
                textFieldOption.setPromptText("Enter Number Of Iteration");
            } else if (str.equals("Var")) {
                textFieldOption.setVisible(true);
                textFieldOption.setPromptText("Enter Variable Name");
                varComboBox.setVisible(false);
            } else {
                textFieldOption.setVisible(false);
                varComboBox.setVisible(false);
            }
        });
        fileChooser.setInitialDirectory(new File("c:\\"));     // save as open initial directory
    }

    public void comboBoxMouseKeyPressed(MouseEvent mouseEvent) {
    }

    public void buttonAddMouseClicked() {
        //  textAreaOption.setText(comboBox.getSelectionModel().getSelectedItem());
        String str = addComboBox.getSelectionModel().getSelectedItem();

        if (str != "Loop" && str != "Var" && str != "EndLoop" && str != null) {
            addPrimitive(addComboBox.getSelectionModel().getSelectedItem());
        } else if (str == "Loop") {
            addLoop(str);
        } else if (str == "Var") {
            addVar(str);
        } else if (str == "EndLoop") {
            addEndLoop(str);
        }
    }

    private void addEndLoop(String str) {
        if (counter >= 1) {
            tab = tab.substring(0,tab.length() - 2);
            textAreaOption.appendText(tab + addComboBox.getSelectionModel().getSelectedItem() + "\n");
            counter -=1;
        } else {
            Alert.display("Loop Must be include ");
        }
    }

    private void addVar(String str) {
        String var = textFieldOption.getCharacters().toString();
        System.out.println(var);
        if (!var.isEmpty() && !var.contains(" ") && !var.matches("^[0-9]*$")) {
            varOption.add(var);
            textAreaOption.appendText(tab + str + "(" + var + ")\n");
            textFieldOption.clear();
        } else {
            textFieldOption.clear();
        }
    }

    private void addLoop(String str) {
        String num = textFieldOption.getCharacters().toString();
        String varName = varComboBox.getValue();

        System.out.println(num);
        try {
            if (num.matches("^[0-9]*$") && Integer.parseInt(num) < 10000) {   //&& Integer.parseInt(numOrVar +"") < 10000
                textAreaOption.appendText(tab + str + "(" + num + ")\n");
            }
        } catch (NumberFormatException e) {
            if (varName != null) {
                textAreaOption.appendText(tab + str + "(" + varName + ")\n");
            }
        } finally {
            textFieldOption.clear();
            varComboBox.setAccessibleText(varComboBox.getPromptText());
            counter+=1;
            tab += "  ";
        }

    }



    private void addPrimitive(String selectedItem) {
        if(counter == 0) {
        textAreaOption.appendText(selectedItem + "\n");
        }
        else {
            textAreaOption.appendText(tab + selectedItem + "\n");
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

    public void menuOpenStructure(ActionEvent actionEvent) {
//        Window stage = vbMenu.getScene().getWindow();
        Window stage = vbMenu.getScene().getWindow();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("structure");
        File file = fileChooser.showOpenDialog(stage);
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
    }

    public void convertButtonOnMouseClicked(MouseEvent mouseEvent) {
    }

    /*
    -------------------------------------------------------------------------------------------------
            Enable Editing for Structure and Input File
    -------------------------------------------------------------------------------------------------
     */

    public void editableCheck(ActionEvent actionEvent) {
        if (editableCheckBox.isSelected()) {
            urlTextArea.setEditable(true);
            textAreaOption.setEditable(true);
        } else {
            urlTextArea.setEditable(false);
            textAreaOption.setEditable(false);
        }
    }

    /*
    -------------------------------------------------------------------------------------------------
            Clear Structure ,Input and Output text area
    -------------------------------------------------------------------------------------------------
     */
    public void clearStuctureButton(MouseEvent mouseEvent) {
        textAreaOption.clear();
    }

    public void clearAllButton(MouseEvent mouseEvent) {
        textAreaOption.clear();
        urlTextArea.clear();
        outputTextArea.clear();
    }

    public void clearOutputButton(MouseEvent mouseEvent) {
        outputTextArea.clear();
    }

    public void clearInputButton(MouseEvent mouseEvent) {
        urlTextArea.clear();
    }
}

