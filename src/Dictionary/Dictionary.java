package Dictionary;

import java.util.ArrayList;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class Dictionary {
    public static ArrayList<Word> word = new ArrayList<Word>(100);

    public static void main(String[] args){
        DictionaryCommandline y = new DictionaryCommandline();
//        y.dictionaryBasic();
 //       DictionaryManagement x = new DictionaryManagement();
        y.dictionaryAdvanced();
        y.dictionarySearcher();
    }
}
class Word {
    private String word_target;
    private String word_explain;

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_target() {
        return word_target;
    }

    Word(String target, String explain){
        word_target = target;
        word_explain = explain;
    }
}
class DictionaryManagement{
    Dictionary x = new Dictionary();
    void insertFromCommandline() {
        Scanner input = new Scanner(System.in);
        System.out.println("Số lượng từ : ");
        int n;
        n = input.nextInt();
        input.nextLine();
        for (int i = 0; i < n ; i++) {
            String anh = input.nextLine();
            String viet = input.nextLine();
            Word w = new Word(anh, viet);
            x.word.add(w);
        }
    }
    void insertFromFile() {
        Dictionary x = new Dictionary();
        String fileName = "C:\\Users\\ADMIN\\IdeaProjects\\Dictionary\\src\\Dictionary\\dictionaries.txt";//bạn hãy thay đổi đường dẫn tới file của bạn
//		int []i ={0};//i là biến đếm xem chúng ta đã in tới dòng nào
        try (Stream<String> stream = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {//đưa về dạng chuẩn utf8
            stream.forEach(line -> {
                String[] words = line.split("\t");
                //line là từng dòng trong file, tại đây bạn có thể tương tác với nội dung của file. Ở đây, mình chỉ in ra nội dung của từng dòng
                String anh = words[0];
                String viet = words[1];
                Word w = new Word(anh, viet);
                x.word.add(w);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void dictionaryLookup(){
        Dictionary x = new Dictionary();
        Scanner input = new Scanner(System.in);
        System.out.println("Nhap tu can tra: ");
        String find_word = input.nextLine();
        for(int i=0; i<x.word.size(); i++){
            if(x.word.get(i).getWord_target().equals(find_word))
                System.out.println(x.word.get(i).getWord_explain());
        }
    }

}
class DictionaryCommandline{

    void showAllWord(){
        Dictionary a = new Dictionary();
        System.out.println("| STT" + "      " + "| Anh" + "     " + "| Viet");
        for(int i=0;i< a.word.size();i++){
            System.out.println( "   " + i + "   " + "| " + a.word.get(i).getWord_target() + "   | " + a.word.get(i).getWord_explain());
        }
    }
    void dictionaryBasic(){
        DictionaryManagement x = new DictionaryManagement();
        x.insertFromCommandline();
        showAllWord();
    }
    void dictionaryAdvanced(){
        DictionaryManagement x = new DictionaryManagement();
        x.insertFromFile();
        showAllWord();
        x.dictionaryLookup();
    }
    void dictionarySearcher(){
        Scanner input = new Scanner(System.in);
        System.out.println("Nhập từ :");
        String s = input.nextLine();
        int c=0;
        for(int i=0;i<Dictionary.word.size();i++){
            if(Dictionary.word.get(i).getWord_target().startsWith(s))
                System.out.println(Dictionary.word.get(i).getWord_target());
        }
    }
}
class DictionaryData{
    void AddData(){
        Scanner input = new Scanner(System.in);
        System.out.println("Số lượng từ cần thêm : ");
        int n;
        n = input.nextInt();
        input.nextLine();
        for (int i = 0; i < n ; i++) {
            String anh = input.nextLine();
            String viet = input.nextLine();
            Word w = new Word(anh, viet);
            Dictionary.word.add(w);
        }
    }
    void RemoveData(){
        Scanner input = new Scanner(System.in);
        System.out.println("Nhập sô thứ tự từ cần xóa : ");
        int n;
        n = input.nextInt();
        for (int i = 0; i < Dictionary.word.size() ; i++) {
            if(i==n)
                Dictionary.word.remove(Dictionary.word.get(i));
        }
    }
    void EditData() {
        RemoveData();
        AddData();
    }
}