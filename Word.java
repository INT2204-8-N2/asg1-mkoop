public class Word {
    private String wordtarget;
    private String pronounce;
    private String mean;
    public String getWordtarget(){
        return this.wordtarget;
    }
    public void setWordtarget(String word){
        this.wordtarget = word;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getPronounce() {
        return pronounce;
    }

    public String getMean(){
        return this.mean;
    }
    public void setMean(String mean) {
        this.mean = mean;
    }
    public Word(){
        this.wordtarget = "Dictionary";
        this.pronounce = "Pronounce";
        this.mean = " Tu dien";
    }
    public Word(String word, String pronounce, String mean){
        this.wordtarget = word;
        this.pronounce = pronounce;
        this.mean = mean;
    }
    public Word(Word w){
        this.wordtarget = w.getWordtarget();
        this.pronounce = w.getPronounce();
        this.mean = w.getMean();
    }
    // in ra man hinh
    public void display(){
        System.out.printf("\t%s\t\t/%s\n", wordtarget, pronounce);
        String[] arr = mean.split(";");
        if(arr.length > 1){
            for(int i = 0; i < arr.length; i++)
                System.out.println(arr[i]);
        }
    }
}
