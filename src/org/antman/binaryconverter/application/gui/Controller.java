package org.antman.binaryconverter.application.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

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
    private HashMap<String,String> settingsMap;

    @FXML
    public VBox vbMenu;
    FileChooser fileChooser = new FileChooser();
    ObservableList<String> optt = FXCollections.observableArrayList("Char", "Int", "Float", "Var", "Loop", "EndLoop");
    ObservableList<String> varOption = FXCollections.observableArrayList();

    @FXML
    private FileReader fileReader;

    //private BinaryFormat structure.addElementByName("loop","213");
    //ArrayList<Element> allElement
    //ArrayList<Element> variables
//    menuOpenStructureFile(e -> {
//        File selectedFile = fileChooser.showOpenDialog(primaryStage);
//    });

    @FXML
    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDragDrop(DragEvent dragEvent) {
        File file = dragEvent.getDragboard().getFiles().get(0);
//        String fileName = file.getAbsolutePath();
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(fileName));
//            String sr;
//            while ((sr = br.readLine()) != null) {
//                urlTextArea.appendText(sr + "\r\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // System.out.println(file.getAbsolutePath());
        urlTextArea.appendText(file.getAbsolutePath() + "\r\n");
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
            } else if (str.equals("Var")) {
                textFieldOption.setVisible(true);
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
        if (flag == true) {
            textAreaOption.appendText(addComboBox.getSelectionModel().getSelectedItem() + "\n");
            flag = false;
        } else {
            Alert.display("Loop Must be include ");
            flag = false;
        }
    }

    private void addVar(String str) {
        String var = textFieldOption.getCharacters().toString();
        if (!var.contains(" ") && !var.isEmpty() && var.matches(">[0-9]*$")) {
            varOption.addAll(var);
            textAreaOption.appendText(str + "(" + var + ")\n");
            textFieldOption.clear();
        } else {
            textFieldOption.clear();
        }
    }

    private boolean flag = false;

    private void addLoop(String str) {
        String num = textFieldOption.getCharacters().toString();
        String varName = varComboBox.getValue();

        System.out.println(num);
        try {
            if (num.matches("^[0-9]*$") && Integer.parseInt(num) < 10000) {   //&& Integer.parseInt(numOrVar +"") < 10000
                textAreaOption.appendText(str + "(" + num + ")\n");
                flag = true;
            }
        } catch (NumberFormatException e) {
            if (varName != null) {
                textAreaOption.appendText(str + "(" + varName + ")\n");
                textFieldOption.clear();
                flag = true;
            }
        } finally {
            varComboBox.setAccessibleText(varComboBox.getPromptText());
        }

    }

    private void addPrimitive(String selectedItem) {
        textAreaOption.appendText(selectedItem + "\n");

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

}

