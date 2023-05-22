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
import javafx.stage.DirectoryChooser;
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
import java.util.stream.Collectors;

/**
 * @author Brijesh varsani
 * @version 1.2
 */
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
    public Button verifyButton;
    @FXML
    public VBox vbMenu;

    FileChooser fileChooser = new FileChooser();
    DirectoryChooser directoryChooser = new DirectoryChooser();

    ObservableList<String> optt = FXCollections.observableArrayList("Char", "Int", "Float", "Var", "Loop", "EndLoop");
    ObservableList<String> varOption = FXCollections.observableArrayList();

    FileHandler handler;

    private int counter = 0;
    private ArrayList<File> files;
    private ArrayList<String> outputStrings = new ArrayList<>();

    /*------------------------------------------------
     Recent file creator
    -------------------------------------------------
     */

    File recentFiles = new File("cfg\\recent-structure.txt");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        handler = new FileHandler();
        addComboBox.setItems(optt);
        varComboBox.setItems(varOption);
        files = new ArrayList<>();

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
    }

    /*
    -----------------------------------------------
                Private method
    --------------------------------------------
     */

    private void structureOpen() {

        getRecentPath(0);
        Window stage = vbMenu.getScene().getWindow();
        fileChooser.setTitle("Open File");

        File file = fileChooser.showOpenDialog(stage);
        String fileName = file.getAbsolutePath();
        try {
            List<String> strings = handler.readLines(recentFiles);

            strings.set(0, file.getParentFile().toString());
            handler.write(strings, recentFiles);

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String sr;
            while ((sr = br.readLine()) != null) {
                structureInputArea.appendText(sr + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 0 - recent struct
     * 1 - recent input
     * 2 - recent output
     */
    private File getRecentPath(int which) {
        File path = null;
        try {
            path = new File(handler.readLines(recentFiles).get(which));
            if (path != null && path.isDirectory()) fileChooser.setInitialDirectory(path);
            else fileChooser.setInitialDirectory(new File("c://"));
        } catch (FileNotFoundException e) {
            fileChooser.setInitialDirectory(new File("c://"));
        } catch (Exception e) {
            fileChooser.setInitialDirectory(new File("c://"));
        } finally {
            return path;
        }

    }

    private void inputFileOpen() {
        getRecentPath(1);

        Window stage = vbMenu.getScene().getWindow();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(stage);
        try {
            List<String> strings = handler.readLines(recentFiles);

            strings.set(1, file.getParentFile().toString());
            handler.write(strings, recentFiles);

        } catch (IOException e) {
            e.printStackTrace();
        }
        importFile(file);
    }

    private void saveFile() {

        Window stage = vbMenu.getScene().getWindow();

        getRecentPath(0);
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("Structure");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Structure", "*.struc"));


        try {
            List<String> strings = handler.readLines(recentFiles);
            File file = fileChooser.showSaveDialog(stage);

            StringBuilder sb = new StringBuilder();

            sb.append(structureInputArea.getText());

            strings.set(0, file.getParentFile().toString());
            handler.write(strings, recentFiles);


            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void importFile(File file) {
        files.add(file);
        inputTextArea.appendText(file.getAbsolutePath() + "\n");
    }

    /*
    ------------------------------------
                Drag Drop
    -----------------------------------

     */
    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDragDrop(DragEvent dragEvent) {
        inputTextArea.appendText(dragEvent.getDragboard().getFiles().stream().map(File::getAbsolutePath).collect(Collectors.joining("\n")) + "\n");
        files.addAll(dragEvent.getDragboard().getFiles());
    }

    public void onDragDroppedStructure(DragEvent dragEvent) {
        structureInputArea.clear();
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

    /*
    ---------------------------------------------------
                Add structure
    ----------------------------------------------------
     */

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

    /*
    -------------------------------------------------------------------
                    Menu Bar
    -------------------------------------------------------------------
     */

    public void inputFileOpenMenu(ActionEvent actionEvent) {
        inputFileOpen();
    }

    public void menuOpenStructure(ActionEvent actionEvent) {
        structureOpen();
    }

    public void saveStructureMenu(ActionEvent actionEvent) {
        saveFile();
    }

    public void saveOutputMenu(ActionEvent actionEvent) {
        try {
            exportButtonClicked(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearAllMenu(ActionEvent actionEvent) {

        structureInputArea.clear();
        inputTextArea.clear();
        outputTextArea.clear();
        files.clear();
        outputStrings.clear();
    }

    public void helpMenuAction(ActionEvent actionEvent) {
        Runtime runtime = Runtime.getRuntime();

        try {
            runtime.exec("cmd /c cfg\\file.pdf");
            Process pwd = runtime.exec("pwd");
            System.out.println(new Scanner(pwd.getInputStream()).nextLine());
        } catch (IOException e) {
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
    public void clearStructureButton(MouseEvent mouseEvent) {
        structureInputArea.clear();
    }

    public void clearAllButton(MouseEvent mouseEvent) {
        structureInputArea.clear();
        inputTextArea.clear();
        outputTextArea.clear();
        files.clear();
        outputStrings.clear();
    }

    public void clearOutputButton(MouseEvent mouseEvent) {
        outputTextArea.clear();
        outputStrings.clear();
        outputStrings.clear();
    }

    public void clearInputButton(MouseEvent mouseEvent) {
        inputTextArea.clear();
        files.clear();
    }

    /*
    -----------------------------------------------------------------------
                             Verify Button and Save structure button
    ----------------------------------------------------------------------
     */
    public void verifyButtonClicked(MouseEvent mouseEvent) {

        List<String> structureList = Arrays.asList(structureInputArea.getText().split("\n"));
        boolean success = false;
        try {
            BinaryStructure structure = BinaryStructure.getInstance(structureList);
            success = true;
        } catch (InvalidBinaryStructureException e) {
            Alert.display(e.getMessage());
            verifyButton.setText("Verify");
        }
        if (success) {
            verifyButton.setText("Verified");
            success = false;
        }
    }

    public void saveAsStructureOnMouseClicked(MouseEvent mouseEvent) {
        saveFile();
    }

    /*
    ------------------------------------------------------------------------
                Import , Export and Convert Button
    -------------------------------------------------------------------------
     */
    public void importButtonClicked(MouseEvent mouseEvent) {
        inputFileOpen();
    }

    public void convertButtonOnMouseClicked(MouseEvent mouseEvent) {
        outputTextArea.clear();
        HashMap<File, String> encodedDecoded = new HashMap<>();
        List<String> structureList = Arrays.asList(structureInputArea.getText().split("\n"));
        try {
            System.out.println(files.size());
            CountDownLatch latch = new CountDownLatch(files.size());
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                outputStrings.add("");
                final int notFinal = i;
                new Thread(() -> {
                    try {
                        Decoder decoder = new Decoder();
                        BinaryStructure structure = BinaryStructure.getInstance(structureList);
                        System.out.println("Thread ");
                        ByteBuffer buffer = handler.readBytesToBuffer(file);
                        outputStrings.set(notFinal, decoder.decode(structure, buffer));
                        latch.countDown();

                        System.out.println(outputStrings);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidBinaryStructureException e) {
                        Alert.display(e.getMessage());
                        System.out.println(e.getMessage());
                    }
                }).start();
            }
            latch.await(2 * files.size(), TimeUnit.SECONDS);
            for (int i = 0; i < files.size(); i++) {
                outputTextArea.appendText("-----Decoded file - " + files.get(i).toString() + "-----\n");
                outputTextArea.appendText(outputStrings.get(i) + "\n\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exportButtonClicked(MouseEvent mouseEvent) throws IOException {
        if (outputStrings == null || outputStrings.size() < 1) return;
        List<File> filesToExport = new ArrayList<>();
        Window stage = vbMenu.getScene().getWindow();
        directoryChooser.setTitle("Select a folder to export to...");
        directoryChooser.setInitialDirectory(getRecentPath(2));
        File exportDir = directoryChooser.showDialog(stage);

        List<String> strings = handler.readLines(recentFiles);

        strings.set(2, exportDir.toString());

        handler.write(strings, recentFiles);

        for (int i = 0; i < files.size(); i++) {
            File file = new File(exportDir + "\\" + files.get(i).getName().split("[.]")[0] + "-decoded.txt");
            System.out.println(file.getName());
            handler.createFile(file);
            handler.write(outputStrings.get(i), file);
        }
    }
}





