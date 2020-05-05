import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.regex.Pattern;

public class PaperApplication extends JFrame implements ActionListener {

    private static JPanel panel, panel1;
    private static JButton selectAll, addPaper, removeRow, updateCat, updateYear, login, exit;
    private static JTextArea JArea;
    private static PaperQueries paperQueries;
    private static String user, pass, DBOutput, name, category, temp;
    private static int iD, year;
    private static Connection connection;


    public PaperApplication() {

        super("Exam paper application");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        panel = new JPanel(new GridBagLayout());
        panel1 = new JPanel(new GridBagLayout());

        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(15, 15, 15, 15);

        //initialize all buttons and text area, and add them to panel
        login = new JButton("    Log in   ");
        gb.gridx = 0;
        gb.gridy = 1;
        login.addActionListener(this);
        panel.add(login, gb);

        selectAll = new JButton("  Select All ");
        gb.gridx = 0;
        gb.gridy = 2;
        selectAll.addActionListener(this);
        panel.add(selectAll, gb);

        addPaper = new JButton(" Add Exam ");
        gb.gridx = 0;
        gb.gridy = 3;
        addPaper.addActionListener(this);
        panel.add(addPaper, gb);

        removeRow = new JButton("RemoveRow");
        gb.gridx = 0;
        gb.gridy = 4;
        removeRow.addActionListener(this);
        panel.add(removeRow, gb);

        updateCat = new JButton("UpgradeCat");
        gb.gridx = 0;
        gb.gridy = 5;
        updateCat.addActionListener(this);
        panel.add(updateCat, gb);

        updateYear = new JButton("UpgradeYear");
        gb.gridx = 0;
        gb.gridy = 6;
        updateYear.addActionListener(this);
        panel.add(updateYear, gb);

        exit = new JButton("       Exit       ");
        gb.gridx = 0;
        gb.gridy = 7;
        exit.addActionListener(this);
        panel.add(exit, gb);

        JArea = new JTextArea(20, 30);
        panel1.add(JArea);
        JArea.setEditable(false);

        this.add(panel, BorderLayout.WEST);
        this.add(panel1, BorderLayout.EAST);
        //setting all buttons except login and exit to visible false it will force
        //user to log in or exit program
        updateYear.setVisible(false);
        selectAll.setVisible(false);
        addPaper.setVisible(false);
        removeRow.setVisible(false);
        updateCat.setVisible(false);
        updateYear.setVisible(false);


        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        //login event handler
        if (e.getSource() == login) {

            user = JOptionPane.showInputDialog("enter user");
            //will check is string is not empty by sending string to checkIfEmpty method
            user = checkIfEmpty(user);

            pass = JOptionPane.showInputDialog("enter password");
            pass = checkIfEmpty(pass);

            paperQueries = new PaperQueries(user, pass);
            connection =paperQueries.startConnection();

            //if login was succesuful will set all button visible on panel
            if(connection != null) {
                updateYear.setVisible(true);
                selectAll.setVisible(true);
                addPaper.setVisible(true);
                removeRow.setVisible(true);
                updateCat.setVisible(true);
                updateYear.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "couldn`t log in please try again");
            }
        //handles selectAll button
        } else if (e.getSource() == selectAll) {

            refreshScreen();
        //handles add paper exam button
        } else if (e.getSource() == addPaper) {

            name = JOptionPane.showInputDialog("Enter module");

            name = checkIfEmpty(name);

            temp = JOptionPane.showInputDialog("Enter exam year");

            temp= checkIfEmpty(temp);

            year = Integer.parseInt(temp);


            //checks for curect year input
            while (!checkYear(year)) {
                temp = JOptionPane.showInputDialog("Wrong format, must by 4 digits");
                temp = checkIfEmpty(temp);
                year = Integer.parseInt(temp);
            }


            category = JOptionPane.showInputDialog("Enter category");

            category = checkIfEmpty(category);

            paperQueries.addPaper(name, year, category);
            refreshScreen();
         //remove row from db button handler
        } else if (e.getSource() == removeRow) {

            temp = JOptionPane.showInputDialog("Enter id to delete");

            temp = checkIfEmpty(temp);

            iD = Integer.parseInt(temp);


            paperQueries.removeExampRow(iD);

            refreshScreen();
            //update category button
        } else if (e.getSource() == updateCat) {

            temp = JOptionPane.showInputDialog("Enter ID to upgrade");

            temp=checkIfEmpty(temp);

            iD = Integer.parseInt(temp);

            category = JOptionPane.showInputDialog("Enter new category for " + iD);

            category=checkIfEmpty(category);

            paperQueries.updateCategory(iD, category);

            refreshScreen();
            //update year button
        } else if (e.getSource() == updateYear) {

            temp = JOptionPane.showInputDialog("Enter ID to upgrade");
            temp = checkIfEmpty(temp);
            iD = Integer.parseInt(temp);

            temp = JOptionPane.showInputDialog("Enter new year for " + iD);
            temp = checkIfEmpty(temp);

            year = Integer.parseInt(temp);

            while (!checkYear(year)) {
                temp = JOptionPane.showInputDialog("Wrong year, must by 4 digits");
                temp = checkIfEmpty(temp);
                year = Integer.parseInt(temp);
            }
            paperQueries.updateYear(iD, year);

            refreshScreen();
        //exit button
        } else if (e.getSource() == exit) {

            //checks if connected, if connected it will close all praper statements, result set and connection
            //and will exit program
            if(connection !=null)
            {
                paperQueries.closeConnection();
                System.exit(0);
            }
            //if connection is connected it will just exits program
            System.exit(0);
        }
    }

    //main method
    public static void main(String[] args) {

        new PaperApplication();
    }

    /////other methods

    //check year pattern method
    private boolean checkYear(int year) {


        Pattern date = Pattern.compile("\\d{4}");
        String yearString = Integer.toString(year);

        if (date.matcher(yearString).matches()) {
            return true;
        }

        return false;
    }
    //check if id field is empty
    private String checkIfEmpty(String temp)
    {
        while (temp.isEmpty())
        {
            temp = JOptionPane.showInputDialog(null, "Field cannot by empty");

        }
        return temp;

    }
    //refresh textarea method
    private void refreshScreen()
    {
        DBOutput = paperQueries.selectAllExam().toString();
        JArea.setText(DBOutput);
    }

}
