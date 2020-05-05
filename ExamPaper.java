public class ExamPaper {

    private String module;
    private int year;
    private String category;
    private int iD;


    //default constructor no args
    public ExamPaper() {
    }
    //constructor
    public ExamPaper(int iD, String module, int year, String category) {
        this.iD = iD;
        this.module = module;
        this.year = year;
        this.category = category;

    }

    //Getters for class
    public String getModule() {
        return module;
    }

    public int getID() {
        return iD;
    }

    public int getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }
    //Setters for class

    public void setID(int iD) {
        this.iD = iD;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {

        return  "\nID: "+iD+
                "\nModule: "+this.module+
                "\nYear: "+this.year+
                "\nCategory: "+this.category+
                "\n";
    }

}
