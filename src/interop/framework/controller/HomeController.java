package interop.framework.controller;

import interop.framework.Framework;
import interop.log.model.LASList;
import interop.log.model.ParsedLAS;
import interop.log.util.LASParser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Lucas Hagen
 */

public class HomeController implements Controller, Initializable {

    /**
     * Training Buttons
     */
    @FXML
    private Button addTLAS;
    @FXML
    private Button addTXML;
    @FXML
    private Button removeT;

    /**
     * Validation Buttons
     */
    @FXML
    private Button addVLAS;
    @FXML
    private Button addVXML;
    @FXML
    private Button removeV;
    @FXML
    private Button editV;

    @FXML
    private TreeView<String> trainingFilesTree;
    @FXML
    private TreeView<String> validationFilesTree;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> tRoot = new TreeItem<>();
        tRoot.setExpanded(true);

        trainingFilesTree.setRoot(tRoot);
        trainingFilesTree.setShowRoot(false);

        TreeItem<String> vRoot = new TreeItem<>();
        vRoot.setExpanded(true);

        validationFilesTree.setRoot(vRoot);
        validationFilesTree.setShowRoot(false);
    }

    @FXML
    public void addLAS(ActionEvent event) {
        FileChooser chooser = new FileChooser();

        chooser.setTitle("Select a LAS File");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LAS Files (*.las)", "*.las"));
        File file = chooser.showOpenDialog(Framework.getInstance().getWindow());

        ParsedLAS las = null;

        try {
            las = new LASParser().parseLAS(file.getAbsolutePath());
        } catch (Exception ignored) {

        }

        if(las != null) {
            if(event.getSource() == this.addTLAS) {
                Framework.getInstance().getTrainingLASList().add(las);
                this.branchLAS(las.getWellName(), this.trainingFilesTree.getRoot());
            } else if(event.getSource() == this.addVLAS) {
                Framework.getInstance().getValidationLASList().add(las);
                this.branchLAS(las.getWellName(), this.validationFilesTree.getRoot());
            }
        }
    }

    public void addXML() {

    }

    public void removeFile(ActionEvent event) {
        TreeView<String> tree = null;
        LASList lasList = null;

        if(event.getSource() == this.removeV) {
            tree = validationFilesTree;
            lasList = Framework.getInstance().getValidationLASList();
        } else if(event.getSource() == this.removeT) {
            tree = trainingFilesTree;
            lasList = Framework.getInstance().getTrainingLASList();
        }

        TreeItem<String> item = getSelectedItem(tree);

        lasList.removeLAS(item.getValue());
        item.getParent().getChildren().remove(item);
    }

    public void editLAS() {
        if(getSelectedItem(this.validationFilesTree) != null) {
            String wellName = getSelectedItem(this.validationFilesTree).getValue();
            ParsedLAS toEdit = null;

            for (ParsedLAS las : Framework.getInstance().getValidationLASList()) {
                if (las.getWellName().equalsIgnoreCase(wellName))
                    toEdit = las;
            }


            try {
                Framework.getInstance().getMainController().setPageFXML(getClass().getResource("../fxml/VLASEditor.fxml"), true, toEdit, VLASEditorController.class);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private TreeItem<String> branchLAS(String las, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(las);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    private TreeItem<String> branchXML(String xml, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(xml);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    public void updateTrainingButtons() {
        ObservableList<TreeItem<String>> tItems = this.trainingFilesTree.getSelectionModel().getSelectedItems();
        if(tItems.size() == 0) {
            addTXML.setDisable(true);
            removeT.setDisable(true);
        } else if(tItems.size() == 1) {
            removeT.setDisable(false);
            addTXML.setDisable(tItems.get(0).getParent() != trainingFilesTree.getRoot());
        } else {
            addTXML.setDisable(true);
            removeT.setDisable(false);
        }
    }

    public void updateValidationButtons() {
        ObservableList<TreeItem<String>> tItems = this.validationFilesTree.getSelectionModel().getSelectedItems();
        if(tItems.size() == 0) {
            addVXML.setDisable(true);
            removeV.setDisable(true);
            editV.setDisable(true);
        } else if(tItems.size() == 1) {
            removeV.setDisable(false);
            addVXML.setDisable(tItems.get(0).getParent() != validationFilesTree.getRoot());
            editV.setDisable(tItems.get(0).getParent() != validationFilesTree.getRoot());
        } else {
            addVXML.setDisable(true);
            removeV.setDisable(false);
            editV.setDisable(true);
        }
    }

    private TreeItem<String> getSelectedItem(TreeView<String> tree) {
        ObservableList<TreeItem<String>> items = this.validationFilesTree.getSelectionModel().getSelectedItems();

        if(items.size() == 0) {
            System.out.println("NULL");
            return null;
        } else
            return items.get(0);

    }



}
