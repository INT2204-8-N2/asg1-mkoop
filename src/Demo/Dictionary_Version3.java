package demo;

import dictionary.DataBase;
import dictionary.Dictionary;
import java.util.Scanner;


public class Dictionary_Version3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DataBase data = new DataBase();
        Dictionary dic = data.getDataBase();
        dic.searchWord("and to and");
        data.updateDataBase();
        String w = sc.nextLine();
        dic.searchWord(w);
        
    }
    
}
