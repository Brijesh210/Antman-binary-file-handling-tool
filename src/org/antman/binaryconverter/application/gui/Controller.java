package org.antman.binaryconverter.application.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.antman.binaryconverter.application.converter.Decoder;
import org.antman.binaryconverter.application.converter.structure.BinaryStructure;
import org.antman.binaryconverter.application.converter.structure.InvalidBinaryStructureException;
import org.antman.binaryconverter.application.util.FileHandler;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {
    @FXML
    public TextArea inputTextArea;
    @FXML
    public TextArea outputTextArea;
    @FXML
    public ComboBox<String> addComboBox;
    public ComboBox<Integer> comboBox2;
    public TextField textFieldOption;
    public TextArea structureInputArea;
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


    FileHandler handler;

    //private boolean flag = false;
    private int counter = 0;
    private ArrayList<File> files;

    @FXML
    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDragDrop(DragEvent dragEvent) {
        File file = dragEvent.getDragboard().getFiles().get(0);
        files.add(file);
        inputTextArea.appendText(file.getAbsolutePath() + "\n");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = new FileHandler();
        files = new ArrayList<>();
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
        String str = addComboBox.getSelectionModel().getSelectedItem();
        if (!str.equals("Loop") && !str.equals("Var") && !str.equals("EndLoop") && str != null) {
            addPrimitive(addComboBox.getSelectionModel().getSelectedItem());
        } else if (str.equals("Loop")) {
            addLoop(str);
        } else if (str.equals("Var")) {
            addVar(str);
        } else if (str.equals("EndLoop")) {
            addEndLoop(str);
        }
    }

    private void addEndLoop(String str) {
        if (counter >= 1) {
            tab = tab.substring(0, tab.length() - 2);
            structureInputArea.appendText(tab + addComboBox.getSelectionModel().getSelectedItem() + "\n");
            counter -= 1;
        } else {
            Alert.display("Loop Must be include ");
        }
    }

    private void addVar(String str) {
        String var = textFieldOption.getCharacters().toString();
        System.out.println(var);
        if (!var.isEmpty() && !var.contains(" ") && !var.matches("^[0-9]*$")) {
            varOption.add(var);
            structureInputArea.appendText(tab + str + "(" + var + ")\n");
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
                structureInputArea.appendText(tab + str + "(" + num + ")\n");
            }
        } catch (NumberFormatException e) {
            if (varName != null) {
                structureInputArea.appendText(tab + str + "(" + varName + ")\n");
            }
        } finally {
            textFieldOption.clear();
            varComboBox.setAccessibleText(varComboBox.getPromptText());
            counter += 1;
            tab += "  ";
        }

    }


    private void addPrimitive(String selectedItem) {
        if (counter == 0) {
            structureInputArea.appendText(selectedItem + "\n");
        } else {
            structureInputArea.appendText(tab + selectedItem + "\n");
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
            sb.append(structureInputArea.getText());
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
                structureInputArea.appendText(sr + "\r\n");
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
        Window stage = vbMenu.getScene().getWindow();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("structure");
        File file = fileChooser.showOpenDialog(stage);
        String fileName = file.getAbsolutePath();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String sr;
            while ((sr = br.readLine()) != null) {
                structureInputArea.appendText(sr + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convertButtonOnMouseClicked(MouseEvent mouseEvent) {
        List<String> structureList = Arrays.asList(structureInputArea.getText().split("\n"));
        try {
            System.out.println(files.size());
            CountDownLatch latch = new CountDownLatch(files.size());
            ArrayList<String> results = new ArrayList<>();
            for (File file : files) {
                new Thread(() -> {
                    try {
                        Decoder decoder = new Decoder();
                        BinaryStructure structure = BinaryStructure.getInstance(structureList);
                        System.out.println("Thread ");
                        ByteBuffer buffer = handler.readBytesToBuffer(file);
                        results.add(decoder.decode(structure, buffer));
                        latch.countDown();

                        System.out.println("Thread 2");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidBinaryStructureException e) {
                        //todo show error dialog
                        System.out.println(e.getMessage());
                    }
                }).start();
            }
            latch.await(2 * files.size(), TimeUnit.SECONDS);
            for (int i = 0; i < files.size(); i++) {
                outputTextArea.appendText("===============Decoded file - " + files.get(i).toString() + "===============\n");
                outputTextArea.appendText(results.get(i) + "\n\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    -------------------------------------------------------------------------------------------------
            Enable Editing for Structure and Input File
    -------------------------------------------------------------------------------------------------
     */

    public void editableCheck(ActionEvent actionEvent) {
        if (editableCheckBox.isSelected()) {
            inputTextArea.setEditable(true);
            structureInputArea.setEditable(true);
        } else {
            inputTextArea.setEditable(false);
            structureInputArea.setEditable(false);
        }
    }

    /*
    -------------------------------------------------------------------------------------------------
            Clear Structure ,Input and Output text area
    -------------------------------------------------------------------------------------------------
     */
    public void clearStuctureButton(MouseEvent mouseEvent) {
        structureInputArea.clear();
    }

    public void clearAllButton(MouseEvent mouseEvent) {
        structureInputArea.clear();
        inputTextArea.clear();
        outputTextArea.clear();
    }

    public void clearOutputButton(MouseEvent mouseEvent) {
        outputTextArea.clear();
    }

    public void clearInputButton(MouseEvent mouseEvent) {
        inputTextArea.clear();
    }
}

