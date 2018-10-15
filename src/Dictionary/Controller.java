package Dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import jdk.jshell.spi.ExecutionControl;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller implements Initializable {
    @FXML
    private TableView<Dictionary> tableView;
    @FXML
    private TableColumn<Dictionary,String> english;
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
    private List<Dictionary> list = new ArrayList<Dictionary>();
    private ObservableList<Dictionary> data = FXCollections.observableArrayList(list);

    int row = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String fileName = "C:\\Users\\ADMIN\\Downloads\\anhviet109K.txt";//bạn hãy thay đổi đường dẫn tới file của bạn
        String content = null;//đưa về chuẩn utf-8
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)),
                    StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] word = content.split("\n\n");
        ArrayList<String> anh = new ArrayList<String>();
        for(String w:word){
            String[] b;
            b=w.split("/");
            anh.add(b[0]);
        }
        for (int i=0;i<word.length;i++){
            Dictionary a = new Dictionary();
            a.setVietnamese(word[i]) ;
            a.setEnglish(anh.get(i));
            data.add(a);
        }
        english.setCellValueFactory(new PropertyValueFactory<>("english"));
       // vietnamese.setCellValueFactory(new PropertyValueFactory<>("vietnamese"));
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
//                else if (dictionary.getVietnamese().toLowerCase().contains(lowerCaseFilter)) {
//                    return true;
//                }
                return false;
            });
        });
        SortedList<Dictionary> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }


    private void Disable(boolean check) {
        anh.setDisable(check);
//        viet.setDisable(check);
//        textArea.setDisable(check);

    }


    private void Reset() {
          anh.setText("");
//        viet.setText("");
        textArea.setText("");
    }

    @FXML
    public void Save(ActionEvent event) {
        try {
            dictionary = new Dictionary();
            dictionary.setEnglish(anh.getText());
            dictionary.setVietnamese(textArea.getText());

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
//    @FXML
//    public void New(ActionEvent event) {
//        try {
//            Disable(false);
//            Reset();
//        } catch (Exception ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void Add (ActionEvent e){
        Dictionary dictionary = new Dictionary();
        dictionary.setVietnamese(textArea.getText());
        dictionary.setEnglish(anh.getText());
        data.add(dictionary);
        Disable(false);
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            String newdata = "\n\n" + textArea.getText();

            File file = new File("C:\\Users\\ADMIN\\Downloads\\anhviet109K.txt");

            // kiểm tra nếu file chưa có thì tạo file mới
            if (!file.exists()) {
                file.createNewFile();
            }
            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(newdata);
            System.out.println("Xong");
        } catch (IOException ee) {
            ee.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    @FXML
    public void tableClick(MouseEvent e) throws IOException {
        if (MouseButton.PRIMARY == e.getButton() && e.getClickCount() == 1) {
            dictionary = tableView.getSelectionModel().getSelectedItem();
            anh.setText(dictionary.getEnglish());
        //    viet.setText(dictionary.getVietnamese());
            textArea.setText(dictionary.getVietnamese());
            row = tableView.getSelectionModel().getSelectedIndex();
            Disable(true);
        }
    }
    public void Delete (ActionEvent e){
        Dictionary selected = tableView.getSelectionModel().getSelectedItem();
        data.remove(selected);
    }
    public void Speech(ActionEvent event) {
        dictionary = tableView.getSelectionModel().getSelectedItem();
        Speak.Speak(dictionary.getEnglish());

    }
}



