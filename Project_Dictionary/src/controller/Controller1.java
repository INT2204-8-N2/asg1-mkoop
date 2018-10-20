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
import dictionary.TranslateAPI;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author DUC KHIEM
 */
public class Controller1 implements Initializable {

    protected static DataBase data;
    protected static Dictionary dic;

    public static DataBase getData() {
        return data;
    }

    public static Dictionary getDic() {
        return dic;
    }

    ObservableList <String> list = FXCollections.observableArrayList();

    @FXML
    private TextField word;
    @FXML
    private WebView webview1;
    private WebEngine webengine1;
    @FXML
    private ListView<String> listview;

    private String KeyWord;
    @FXML
    private TextField textTranslate;
    @FXML
    private WebView webview2;
    private WebEngine webengine2;

    public Controller1() {
        data = new DataBase();
        dic = data.getDataBase();
        KeyWord = dic.WordRandom();
    }

    @FXML
    private void SearchWord(ActionEvent event) {
        boolean check = true;
        KeyWord = word.getText();
        String html = dic.getMap().get(KeyWord);
        if (html == null) {
            html = "<html>this word didn't exist in dictionary</html>";
            check = false;
        }
        webengine1.loadContent(html);
        if (check) {
            listview.scrollTo(KeyWord);
        }
    }

    /**
     * hiển thị list từ và nghĩa của từ
     * hiển thị nghĩa của từ khi tra bằng translate api
     * @param location
     * @param resources
     */   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadListWord();
        webengine1 = webview1.getEngine();
        webengine2 = webview2.getEngine();
    }

    private void loadListWord() {
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
        sound(KeyWord);
    }

    void sound(String word) {
        VoiceManager vm;
        Voice v;
        System.setProperty("mbrola.base", "mbrola");
        vm = VoiceManager.getInstance();
        v = vm.getVoice("mbrola_us1");
        v.allocate();
        v.speak(word);
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
        grid.setPadding(new Insets(20, 150, 10, 10));
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
        result.ifPresent((String deleteWord1) -> {
            if (dic.removeWord(deleteWord1)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Delele");
                alert1.setContentText("Delete word success!");
                alert1.show();
                data.updateDataBase();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Delele");
                alert2.setContentText("Delete word Error!");
                alert2.show();
            }
        });
    }

    /**
     * show randomWord
     *
     * @param event
     */
    @FXML
    private void showWordRandom(MouseEvent event) {
        webengine1.loadContent(dic.getMap().get(KeyWord));
    }

    @FXML
    private void add(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("add word");
        Parent parentBack = FXMLLoader.load(
                getClass().getResource("../frame/insert.fxml"));
        Scene sceneInsert = new Scene(parentBack);
        stage.setScene(sceneInsert);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());
        stage.show();
    }

    @FXML
    private void Replace(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent parentBack = FXMLLoader.load(
                getClass().getResource("../frame/replace.fxml"));
        Scene sceneInsert = new Scene(parentBack);
        stage.setScene(sceneInsert);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());
        stage.show();
    }

    @FXML
    private void translate(ActionEvent event) throws IOException {
        String text = TranslateAPI.translate("en", "vi", textTranslate.getText());
        webengine2.loadContent(text);
    }

    @FXML
    private void playSound(ActionEvent event) {
        sound(textTranslate.getText());
    }

    @FXML
    private void inputWord() throws Exception {
        word.textProperty().addListener((observable, oldValue, newValue) -> {
            int indext = 0;
            for(String s: dic.getKey()) {
                if(s.startsWith(newValue)) {
                    indext = dic.getKey().indexOf(s);
                    break;
                }
            }
            listview.scrollTo(indext);
        });
    }
}
