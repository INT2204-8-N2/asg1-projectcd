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
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
        String fileName = "anhviet109K.txt";//bạn hãy thay đổi đường dẫn tới file của bạn
        String content = null;//đưa về chuẩn utf-8
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)),
                    StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] word = content.split("@");
        ArrayList<String> anh = new ArrayList<String>();
        for(String w:word){
            String[] b;
            String[] a;
            b=w.split("/");
            anh.add(b[0]);
        }
        for (int i=0;i<word.length;i++){
            Dictionary a = new Dictionary();
            a.setVietnamese(word[i]) ;
            a.setEnglish(anh.get(i));
            data.add(a);
            list.add(a);
        }
//        Dictionary dictionary = new Dictionary();
//        dictionary.setEnglish("Hello");
//        dictionary.setVietnamese("Xin Chao");
//        data.add(dictionary);
        english.setCellValueFactory(new PropertyValueFactory<>("english"));
        tableView.setItems(data);
        search.setDisable(true);
    }
    public void Search(){
        search.setDisable(false);
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
                return false;
            });
        });
        SortedList<Dictionary> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }

    @FXML
    public void Add(ActionEvent event) {
        dictionary = new Dictionary();
        dictionary.setEnglish(anh.getText());
        dictionary.setVietnamese(textArea.getText());
        data.add(dictionary);
        list.add(dictionary);

        try {

            File f = new File("anhviet109K.txt");
            //    FileWriter fw = new FileWriter(f);
            BufferedWriter fw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(f), "UTF8"));
            for(int i = 0 ;i < list.size()-1;i++){
                fw.write(list.get(i).getVietnamese()+"@");
            }
            fw.write(list.get(list.size()-1).getEnglish() + "/\n" + list.get(list.size()-1).getVietnamese()+"@");
            fw.close();
            System.out.println("Viet file xong!");

        } catch (IOException ex) {

            ex.printStackTrace();

        }
    }
    @FXML
    public void Edit(ActionEvent event) {
        dictionary = new Dictionary();
        dictionary.setEnglish(anh.getText());
        dictionary.setVietnamese(textArea.getText());
        data.set(row, dictionary);
        list.set(row, dictionary);

        try {

            File f = new File("anhviet109K.txt");
        //    FileWriter fw = new FileWriter(f);
            BufferedWriter fw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(f), "UTF8"));
            for(int i = 0 ;i < list.size();i++){
                fw.write(list.get(i).getVietnamese()+"@");
            }
            fw.close();
            System.out.println("Viet file xong!");

        } catch (IOException ex) {

            ex.printStackTrace();

        }
    }

    public void New (ActionEvent e){
        anh.setDisable(false);
        anh.setText("");
        textArea.setText("");
    }
    @FXML
    public void tableClick(MouseEvent e) throws IOException {
        if (MouseButton.PRIMARY == e.getButton() && e.getClickCount() == 1) {
            dictionary = tableView.getSelectionModel().getSelectedItem();
            anh.setText(dictionary.getEnglish());
            textArea.setText(dictionary.getVietnamese());
            row = tableView.getSelectionModel().getSelectedIndex();
        }
    }
    public void Delete (ActionEvent e){
        Dictionary selected = tableView.getSelectionModel().getSelectedItem();
        data.remove(selected);
        list.remove(selected);

        try {

            File f = new File("anhviet109K.txt");
           // FileWriter fw = new FileWriter(f);
            BufferedWriter fw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(f), "UTF8"));
            for(int i = 0 ;i < list.size();i++){
                fw.write(list.get(i).getVietnamese()+"@");
            }
            fw.close();
            System.out.println("Viet file xong!");

        } catch (IOException ex) {

            ex.printStackTrace();

        }
    }
    public void Speech(ActionEvent event) {
   //     dictionary = tableView.getSelectionModel().getSelectedItem();
        Speak.Speak(anh.getText());

    }
    public void Translator(ActionEvent event) throws IOException, ParseException {
        textArea.setText(Translate.Translate(anh.getText()));
    }
}



