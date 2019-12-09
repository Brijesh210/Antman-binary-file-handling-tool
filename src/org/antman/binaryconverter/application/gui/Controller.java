package org.antman.binaryconverter.application.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import org.antman.binaryconverter.application.converter.structure.Element;
import org.antman.binaryconverter.application.converter.structure.InvalidBinaryStructureException;
import org.antman.binaryconverter.application.converter.structure.LoopElement;
import org.antman.binaryconverter.application.util.FileHandler;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    private static final int NUMBER_OF_RECENT_FILES = 20;
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
    ObservableList<String> optt = FXCollections.observableArrayList("Char", "Int", "Float", "Var", "Loop", "EndLoop");
    ObservableList<String> varOption = FXCollections.observableArrayList();

    FileHandler handler;

    //private boolean flag = false;
    private int counter = 0;
    private ArrayList<File> files;

    //------------------------------------------------
    // Recent file creator
<<<<<<< Updated upstream
    //-----------------------------------------------

    File recentFiles = new File("cfg\\recent-files.txt");
    File recentStructureFiles = new File("cfg\\recent-structure.txt");
=======
    //--------------------------
    File recentFile = new File("C:\\Users\\b___b\\Desktop\\ANTMAN-Binary\\abc\\antman_binary\\src\\org\\antman\\binaryconverter\\application\\gui\\files\\recentFile.txt");
>>>>>>> Stashed changes

    private void structureOpen() {
        Window stage = vbMenu.getScene().getWindow();
        fileChooser.setTitle("Open File");
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

    private void inputFileOpen() {
        Window stage = vbMenu.getScene().getWindow();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(stage);
<<<<<<< Updated upstream
        importFile(file);
=======
        inputTextArea.appendText(file.getAbsolutePath() + "\n");
>>>>>>> Stashed changes
    }

    private void saveFile(String str) {
        Window stage = vbMenu.getScene().getWindow();
        fileChooser.setTitle("Save File");
        if (str.equals("Structure")) {
<<<<<<< Updated upstream
            fileChooser.setInitialFileName("Structure");
=======
            fileChooser.setInitialFileName("structure");
>>>>>>> Stashed changes
        } else if (str.equals("Output")) {
            fileChooser.setInitialFileName("Decoded_File");
        }
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text", "*.txt"));

        try {
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile()); // save chosen directory
            StringBuilder sb = new StringBuilder();
<<<<<<< Updated upstream
            if (str.equals("Structure")) {
                sb.append(structureInputArea.getText());
            } else if (str.equals("Output")) {
                sb.append(outputTextArea.getText());
            }
=======
            sb.append(structureInputArea.getText());
>>>>>>> Stashed changes
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

<<<<<<< Updated upstream
=======
    @FXML
>>>>>>> Stashed changes
    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDragDrop(DragEvent dragEvent) {
<<<<<<< HEAD
        inputTextArea.appendText(dragEvent.getDragboard().getFiles().stream().map(File::getAbsolutePath).collect(Collectors.joining("\n")) + "\n");
        files.addAll(dragEvent.getDragboard().getFiles());
        //updateRecentFiles();
=======
        File file = dragEvent.getDragboard().getFiles().get(0);
        files.add(file);
        inputTextArea.appendText(file.getAbsolutePath() + "\n");
>>>>>>> 1f83efc61beef70159ecddfa4adaf0e402e0958c
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        MenuItem item = new MenuItem("No recent files");
//        item.setDisable(true);
//        item.setVisible(false);
//        openRecentMenu.getItems().add(item);

        handler = new FileHandler();
        addComboBox.setItems(optt);
        varComboBox.setItems(varOption);
        files = new ArrayList<>();
        // updateRecentFiles();

        Scanner s = null;
        try {
            s = new Scanner(new File("C:\\Users\\b___b\\Desktop\\ANTMAN-Binary\\abc\\antman_binary\\src\\org\\antman\\binaryconverter\\application\\gui\\files\\recentFile.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<String>();
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();

        for (String str : list) {
            openRecentMenu.getItems().addAll(new MenuItem(str));
        }

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

<<<<<<< Updated upstream
=======

    public void saveAsStructureOnMouseClicked(MouseEvent mouseEvent) {
        saveFile("Structure");
    }

    @FXML
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    /*
    -------------------------------------------------------------------
                    Menu Bar
    -------------------------------------------------------------------
     */
=======
>>>>>>> Stashed changes
    public void inputFileOpenMenu(ActionEvent actionEvent) {
        inputFileOpen();
    }

    public void menuOpenStructure(ActionEvent actionEvent) {
<<<<<<< Updated upstream
<<<<<<< HEAD
=======
>>>>>>> Stashed changes
        String structureFile = "Structure";
        structureOpen();
    }

<<<<<<< Updated upstream
    public void saveStructureMenu(ActionEvent actionEvent) {
        saveFile("Structure");
    }

    public void saveOutputMenu(ActionEvent actionEvent) {
        saveFile("Output");
    }

    public void clearAllMenu(ActionEvent actionEvent) {

        structureInputArea.clear();
        inputTextArea.clear();
        outputTextArea.clear();
        files.clear();
    }

    public void helpMenuAction(ActionEvent actionEvent) {
                Runtime runtime = Runtime.getRuntime();
=======
        Window stage = vbMenu.getScene().getWindow();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("structure");
        File file = fileChooser.showOpenDialog(stage);
        String fileName = file.getAbsolutePath();
>>>>>>> 1f83efc61beef70159ecddfa4adaf0e402e0958c
        try {
            runtime.exec("cmd /c test-data\\file.pdf");
            Process pwd = runtime.exec("pwd");
            System.out.println(new Scanner(pwd.getInputStream()).nextLine());
        } catch (IOException e) {
            e.printStackTrace();
=======



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
    }

    public void clearOutputButton(MouseEvent mouseEvent) {
        outputTextArea.clear();
    }

    public void clearInputButton(MouseEvent mouseEvent) {
        inputTextArea.clear();
    }

    /*
    -----------------------------------------------------------------------
                             Verify Button
    ----------------------------------------------------------------------
     */
    public void verifyButtonClicked(MouseEvent mouseEvent) {

        Decoder decoder = new Decoder();
        List<String> structureList = Arrays.asList(structureInputArea.getText().split("\n"));
        boolean success = false;
        try {
            BinaryStructure structure = BinaryStructure.getInstance(structureList);
            success = true;
        } catch (InvalidBinaryStructureException e) {
            Alert.display(e.getMessage());
            verifyButton.setText("Verify");
>>>>>>> Stashed changes
        }
        if (success) {
            verifyButton.setText("Verified");
            success = false;
        }
    }

    @FXML
    public Menu openRecentMenu;

    public void openRecentMenuOnAction(ActionEvent actionEvent) throws IOException {

//        FileWriter fileWriter = null;
//        try {
//            fileWriter = new FileWriter(recentFile,true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        BufferedWriter writer = new BufferedWriter(fileWriter);
//
//        String message = "New Content in the file";
//        try {
//            writer.append("  ");
//            writer.append(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            writer.close();
//        }
    }

    public void importButtonClicked(MouseEvent mouseEvent) {
        inputFileOpen();
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
    }

    public void clearOutputButton(MouseEvent mouseEvent) {
        outputTextArea.clear();
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

        Decoder decoder = new Decoder();
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
        saveFile("Structure");
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
                        Alert.display(e.getMessage());
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

    public void exportButtonClicked(MouseEvent mouseEvent) {
        saveFile("Output");
<<<<<<< Updated upstream
    }

    /*
    --------------------------------------------------
                    Menu recent
    -------------------------------------------------
     */
    @FXML
    public Menu openRecentMenu;

    public void openRecentMenuOnAction(ActionEvent actionEvent) throws IOException {

    }

    /**
     * every time we add a file call this method
     * save all files from files list
     * Create List<Files> recentFiles
     * if files.size() < 20, append
     * if >= 20 delete last, append
     * recentFiles
     * save
     */
    private void updateRecentFiles() {

        List<String> recentFilesList = null;
        //load recent files
        try {
            recentFilesList = handler.readLines(recentFiles);
        } catch (FileNotFoundException e) {
            try {
                new File("cfg").mkdir();
                handler.createFile(recentFiles);
            } catch (IOException ex) {
                System.out.println("file not created");
                ex.printStackTrace();
            }
        }
        for (File file : files) {
            if (recentFilesList.contains(file.getAbsolutePath())) {
                recentFilesList.remove(file.getAbsolutePath());
                recentFilesList.add(file.getAbsolutePath());
            }
            if (recentFilesList.size() >= NUMBER_OF_RECENT_FILES) {
                recentFilesList.remove(0);
                recentFilesList.add(file.getAbsolutePath());
            }
        }
        // no recent file
        boolean visible = false;
        if (recentFilesList == null || recentFilesList.isEmpty()) visible = true;
        else {
            openRecentMenu.getItems().clear();
            for (int i = 0; i < recentFilesList.size(); i++) {
                String str = recentFilesList.get(i);
                MenuItem mi = new MenuItem(str);
                mi.setOnAction(event -> {
                    File file = new File(str);
                    files.add(file);
                    inputTextArea.appendText(file.getAbsolutePath() + "\n");
                });

                openRecentMenu.getItems().add(mi);
            }
        }
        openRecentMenu.getItems().get(0).setVisible(visible);
        //save recentfileslist

        try {
            handler.write(recentFilesList, recentFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(recentFilesList.size() + " 1");
        System.out.println(files.size() + " 2");
    }

    private void importFile(File file) {
        files.add(file);
        inputTextArea.appendText(file.getAbsolutePath() + "\n");
//        updateRecentFiles();
    }


}
=======
    }


    /*
    --------------------------------------------------
                    Menu recent
    -------------------------------------------------
  //

     */

}


>>>>>>> Stashed changes




