package Dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    @FXML
    private TableView<Dictionary> tableView;
    @FXML
    private TableColumn<Dictionary, String> english;
    @FXML
    private TableColumn<Dictionary, String> vietnamese;
    @FXML
    private TextField anh;
    @FXML
    private TextField viet;
    @FXML
    private TextField search;
    @FXML
    private TextArea textArea;

    private Dictionary dictionary;
    private ObservableList<Dictionary> data = FXCollections.observableArrayList();
    int row = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data.add(new Dictionary("Hello", "Xinchao"));
        data.add(new Dictionary("Cello", "Xinchao"));
        data.add(new Dictionary("Fello", "Xinchao"));
        data.add(new Dictionary("Hello", "Xinchao"));

        english.setCellValueFactory(new PropertyValueFactory<>("english"));
        vietnamese.setCellValueFactory(new PropertyValueFactory<>("vietnamese"));
        tableView.setItems(data);
    }
    public void Search(){
        FilteredList<Dictionary> filteredData = new FilteredList<>(data, p -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(dictionary -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (dictionary.getEnglish().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (dictionary.getVietnamese().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Dictionary> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }


    private void Disable(boolean check) {
        anh.setDisable(check);
        viet.setDisable(check);
    }


    private void Reset() {
        anh.setText("");
        viet.setText("");
    }

    @FXML
    public void Save(ActionEvent event) {
        try {
            dictionary = new Dictionary();
            dictionary.setEnglish(anh.getText());
            dictionary.setVietnamese(viet.getText());

            if (row == 0) {
                data.add(dictionary);
            } else {
                data.set(row, dictionary);
            }
            tableView.setItems(data);
            Disable(true);
            Reset();
        } catch (NumberFormatException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void Edit(ActionEvent event) {
        try {
            if (dictionary != null) {
                Disable(false);
            } else {
                Disable(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void New(ActionEvent event) {
        try {
            row = 0;
            Disable(false);
            Reset();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void tableClick(MouseEvent e) {
        if (MouseButton.PRIMARY == e.getButton() && e.getClickCount() == 2) {
            dictionary = tableView.getSelectionModel().getSelectedItem();
            anh.setText(dictionary.getEnglish());
            viet.setText(dictionary.getVietnamese());
            textArea.setText(dictionary.getEnglish());
            row = tableView.getSelectionModel().getSelectedIndex();
            Disable(true);
        }
    }

}

