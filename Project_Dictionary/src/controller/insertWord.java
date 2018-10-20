package controller;

import frame.Controller1;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DUC KHIEM
 */
public class insertWord implements Initializable {
    
    @FXML
    private TextField addWord;
    @FXML
    private TextField noun;
    @FXML
    private TextField verb;
    @FXML
    private TextField adjective;
    @FXML
    private TextField other;

    private String wordExplain;

    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Insert(ActionEvent event) {
        wordExplain = Controller1.getDic().setExlain(noun.getText() , verb.getText() , adjective.getText(), other.getText());
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Insert Word");
        if( Controller1.getDic().AddWord(addWord.getText(), wordExplain) )
        {
            alert1.setContentText("Add success!");
            Controller1.getData().updateDataBase();
        }
        else alert1.setContentText("Add error!");       
        alert1.show();
        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    
    @FXML
    private void Cancel(ActionEvent event) {
        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    public String getExplain() {
        return "<html>" + wordExplain + "</html>";
    }
}
