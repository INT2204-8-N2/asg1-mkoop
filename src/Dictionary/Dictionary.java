package dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author DUC KHIEM
 */
public class Dictionary {

    private String path;
    private TreeMap<String, String> datamap;
    private ArrayList<String> key;
    Scanner sc = new Scanner(System.in);

    public TreeMap<String, String> getMap() {
        return datamap;
    }

    public ArrayList<String> getKey() {
        return key;
    }

    public void setKey(ArrayList<String> key) {
        this.key = key;
    }

    public void setMap(TreeMap<String, String> data) {
        this.datamap = datamap;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Dictionary() {
        datamap = new TreeMap<>();
        key = new ArrayList();
    }

    public void readDataFromFile() {
        try (BufferedReader readfile = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = readfile.readLine()) != null) {
                int index = line.indexOf("<html>");

                if (index != -1) {
                    String target = line.substring(0, index);
                    String keyWord = target.trim();
                    String meaning = line.substring(index);

                    datamap.put(keyWord, meaning);
                    key.add(keyWord);
                }
            }
            readfile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void writeData() {
        try (BufferedWriter writefile = new BufferedWriter(new FileWriter(path))) {
            // Lay mot tap hop cac entry
            Set set = datamap.entrySet();
            // Lay mot iterator
            Iterator i = set.iterator();
            // Hien thi cac phan tu
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry) i.next();
                writefile.write((String) me.getKey());
                writefile.write((String) me.getValue());
                writefile.newLine();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * tìm kiếm nhị phân
     * @param word
     * @param left
     * @param right
     * @return 
     */
    int benarySearch(String word, int left, int right) {
        if (key.get(left).compareTo(key.get(right)) > 0) {
            return -1;
        } else {
            int mid = (left + right) / 2;
            
            if (key.get(mid).compareTo(word) > 0) {
                return benarySearch(word, left, mid - 1);
            } else if (key.get(mid).compareTo(word) < 0) {
                return benarySearch(word, mid + 1, right);
            } else {
                return mid;
            }
        }
    }

    public String Search(String word) {
        int high = key.size() - 1;
        int low = 0;
        int index = benarySearch(word, low, high);
        if (index < 0) {
            return "";
        } else {
            return key.get(index);
        }
    }
    
    /**
     * random to show word in webview
     * @return 
     */
    public String WordRandom() {
        Random random = new Random();
        int index = random.nextInt(key.size()-1);
        return key.get(index);     
    }
    
    public void searchWord(String Word) {
        
        System.out.println(datamap.get(Search(Word)));
    }

    public boolean removeWord(String word) {
        if (Search(word).equals("")) {
            return false;
        } else {
            key.remove(word);
            datamap.remove(word);
            return true;
        }
    }

    public boolean replaceWord(String Word, String newKey) {
        if (Search(Word).equals("")) {
            return false;
        } else {
            String meaning = datamap.get(Word);
            key.remove(Word);
            datamap.remove(Word);
            key.add(newKey);
            datamap.put(newKey, meaning);
            return true;
        }
    }

    public void replaceExlain(String Word) {
        if (Search(Word).equals("")) {
            System.out.println("this word isn't in dictionary");
        } else {
            System.out.print("nhap nghia: ");
            String explain = "<html>" + sc.nextLine() + "</html>";
            datamap.replace(Word, explain);
        }
    }

    public boolean addWord(String newWord, String explain) {
        if (!Search(newWord).equals("")) {
            return false;
        } else {
            key.add(newWord);
            datamap.put(newWord, "<html>"+ explain + "</html>");
            return true;
        }
    }

    public void show() {
        Set set = datamap.entrySet();
        // Lay mot iterator
        Iterator i = set.iterator();
        // Hien thi cac phan tu
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            System.out.print(me.getKey() + " : ");
            System.out.println(me.getValue());
        }
    }
}
