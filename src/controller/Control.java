package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import dictionary.DataBase;
import dictionary.Dictionary;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.awt.Insets;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 *
 * @author DUC KHIEM
 */
public class Control implements Initializable {

    private final DataBase data;
    private final Dictionary dic;

    ObservableList list = FXCollections.observableArrayList();

    @FXML
    private TextField word;
    @FXML
    private WebView webview1;
    private WebEngine webengine1;
    @FXML
    private ListView<String> listview;

    private String KeyWord;

    public Control() {
        data = new DataBase();
        dic = data.getDataBase();
        KeyWord = dic.WordRandom();
    }

    @FXML
    private void SearchWord(ActionEvent event) {
        KeyWord = word.getText();
        String html = dic.getMap().get(KeyWord);
        if (html == null) {
            html = "<html>this word isn't in dictionary</html>";
        }
        webengine1.loadContent(html);
    }

    /**
     * show listView and Webview
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadListWord();
        webengine1 = webview1.getEngine();
    }

    private void loadListWord() {
        list.removeAll(list);
        list.addAll(dic.getKey());
        listview.getItems().addAll(list);
    }

    /**
     * handle a event button cliked on listview
     *
     * @param event
     */
    @FXML
    private void DisplayExplain(MouseEvent event) {
        String key = listview.getSelectionModel().getSelectedItem();
        KeyWord = key;
        webengine1.loadContent(dic.getMap().get(key));
    }

    /**
     * used TTS when handle event button click on sound
     *
     * @param event
     */
    @FXML
    private void Speak(ActionEvent event) {
        VoiceManager vm;
        Voice v;
        System.setProperty("mbrola.base", "mbrola");
        vm = VoiceManager.getInstance();
        v = vm.getVoice("mbrola_us1");
        v.allocate();
        v.speak(KeyWord);
    }

    @FXML
    private void Close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void deleteWord(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Delete Word");

        ButtonType deleteButtonTpye = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButtonTpye, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(javafx.geometry.Insets.EMPTY);
        final TextField deleteWord = new TextField();
        deleteWord.setPromptText("Delete Word");

        grid.add(new javafx.scene.control.Label("DeleteWord: "), 0, 0);
        grid.add(deleteWord, 1, 0);

        final Node Delete = dialog.getDialogPane().lookupButton(deleteButtonTpye);
        Delete.setDisable(true);

        deleteWord.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Delete.setDisable(newvalue.trim().isEmpty());
        });
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonTpye) {
                return deleteWord.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(new Consumer<String>() {
            @Override
            public void accept(String deleteWord) {
                if (dic.removeWord(deleteWord)) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Delele");
                    alert1.setContentText("Delete word success!");
                    alert1.show();
                    data.updateDataBase();
                }
                else {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Delele");
                    alert2.setContentText("Delete word Error!");
                    alert2.show();
                } 
            }
        });
    }

    @FXML
    private void replaceWord(ActionEvent event) {
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        dialog.setTitle("Replace Word");

        ButtonType ReplaceButtonType = new ButtonType("Replace", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ReplaceButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(javafx.geometry.Insets.EMPTY);
        
        final TextField oldTarget = new TextField();
        oldTarget.setPromptText("Old Target");

        final TextField newTarget = new TextField();
        newTarget.setPromptText("New Target");
        
        grid.add(new javafx.scene.control.Label("Old Target: "), 0, 0);
        grid.add(oldTarget, 1, 0);
        grid.add(new javafx.scene.control.Label("New Target: "), 0, 1);
        grid.add(newTarget, 1, 1);
        
        final Node Replace = dialog.getDialogPane().lookupButton(ReplaceButtonType);
        Replace.setDisable(true);

        oldTarget.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Replace.setDisable(newvalue.trim().isEmpty());
        });
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ReplaceButtonType) {
                return new Pair<String, String>(oldTarget.getText(), newTarget.getText());
            }
            return null;
        });

        Optional<Pair<String,String>> result = dialog.showAndWait();
        result.ifPresent(oldTargetnewTarget -> {
            if(dic.replaceWord(oldTargetnewTarget.getKey(),oldTargetnewTarget.getValue())) {
                 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Replace Word");
                    alert1.setContentText("Replace success!");
                    alert1.show();
                    data.updateDataBase();
            }          
            else {
                 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Replace Word");
                    alert1.setContentText("Replace error!");
                    alert1.show();
            }
        });
    }

    @FXML
    private void insertWord(ActionEvent event) {
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        dialog.setTitle("Insert Word");

        ButtonType InsertButtonType = new ButtonType("Insert", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(InsertButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(javafx.geometry.Insets.EMPTY);
        
        final TextField newWord = new TextField();
        newWord.setPromptText("Target");

        final TextField explain = new TextField();
        explain.setPromptText("Explain");
        
        grid.add(new javafx.scene.control.Label("Word: "), 0, 0);
        grid.add(newWord, 1, 0);
        grid.add(new javafx.scene.control.Label("Meaning: "), 0, 1);
        grid.add(explain, 1, 1);
        
        final Node Add = dialog.getDialogPane().lookupButton(InsertButtonType);
        Add.setDisable(true);

        newWord.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Add.setDisable(newvalue.trim().isEmpty());
        });
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == InsertButtonType) {
                return new Pair<String, String>(newWord.getText(), explain.getText());
            }
            return null;
        });

        Optional<Pair<String,String>> result = dialog.showAndWait();
        result.ifPresent(oldTargetnewTarget -> {
            if(dic.addWord(oldTargetnewTarget.getKey(),oldTargetnewTarget.getValue())) {
                 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Insert Word");
                    alert1.setContentText("Add success!");
                    alert1.show();
                    data.updateDataBase();
            }          
            else {
                 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Insert Word");
                    alert1.setContentText("Add error!");
                    alert1.show();
            }
        });
    }
    
    
    
}
