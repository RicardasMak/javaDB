import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PaperQueries {

    private String url;
    private String user;
    private String pass;
    private PreparedStatement selectAll, updateCategory, updateYear, insert, delete;
    private Connection conn;

    //Default constructorserv
    public PaperQueries() {
    }

    public PaperQueries(String user, String pass) {
        this.url = url = "jdbc:mysql://localhost/papersDB";
        this.user = user;
        this.pass = pass;
        this.conn = startConnection();
        try {

            selectAll = conn.prepareStatement("select * from examPaper");
            updateCategory = conn.prepareStatement("update examPaper set category=? "
                    + "where ID=? ");
            updateYear = conn.prepareStatement("update examPaper set year=? "
                    + "where ID=? ");
            insert = conn.prepareStatement("insert into examPaper ("
                    + "module, year, category)"
                    + " values(?, ?, ?)");
            delete = conn.prepareStatement("delete from examPaper where ID=?");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Connecting to DB
    public Connection startConnection() {
        if (conn == null) {

            try {
                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("Connected\n");

                return conn;

            } catch (SQLException ex) {

                System.out.println("failed to connect: " + ex);
            }
        }
        return conn;
    }
    //display content of table
    public ArrayList<ExamPaper> selectAllExam() {

        ArrayList<ExamPaper> examPapers = new ArrayList<>();

        try {
            ResultSet rs = selectAll.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt(1);
                String module = rs.getString(2);
                int year = rs.getInt(3);
                String category = rs.getString(4);

                examPapers.add(new ExamPaper(ID, module, year, category));
            }
        } catch (SQLException e) {
            System.out.println("select all" + e);
        }
        return examPapers;
    }

    //add exam paper
    public void addPaper(String name, int year, String category) {
        if (conn == null) {
            System.out.println("not connected");
        } else {
            try {

                insert.setString(1, name);
                insert.setInt(2, year);
                insert.setString(3, category);
                insert.executeUpdate();



            } catch (SQLException e) {
                System.out.println("error inserting " + e);

            }
        }

    }

    //remove exam paper
    public boolean removeExampRow(int id) {

        if (conn == null) {
            return false;
        }
        try {
            delete.setInt(1, id);
            delete.executeUpdate();

            return true;
        } catch (SQLException ex) {
            System.out.println("failed to delete " + ex);
            return false;
        }
    }

    public boolean updateCategory(int id, String category) {
        if (conn == null) {
            return false;
        }
        try {
            updateCategory.setString(1, category);
            updateCategory.setInt(2, id);
            updateCategory.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("update category error " + e);
            return false;
        }
    }

    public boolean updateYear(int id, int year) {
        if (conn == null) {
            return false;
        }
        try{
                updateYear.setInt(1, year);
                updateYear.setInt(2, id);
                updateYear.executeUpdate();

                return true;
            } catch (SQLException e) {
                System.out.println("error updating year " + e);
                return false;
            }
    }
    //close connection
    public boolean closeConnection() {
        if (conn == null) {
            return false;
        }
        try {
            conn.close();
            selectAll.close();
            updateCategory.close();
            updateYear.close();
            insert.close();
            delete.close();

            return true;
        } catch (SQLException e) {
            System.out.println("failed to close " + e);
            return false;
        }

    }


}
