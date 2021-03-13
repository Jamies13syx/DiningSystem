package Tools;

import Model.SqlHelper;

import javax.swing.*;
import java.io.File;

public class Miscs{

//    public static void main(String[] args) {
//        showCalendar();
//    }

    public static void backupDatabase(){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.showDialog(new JLabel(), "Select");
        try{
            File dir = jFileChooser.getSelectedFile();
            String path = dir.getPath() + "\\backup.bak";
            System.out.println(path);
            File backupFile = new File(path);
            if(backupFile.exists()){
                backupFile.delete();
            }
            else{
                backupFile.createNewFile();
            }
            String sql = "BACKUP DATABASE LearningTest TO DISK = '" +path +"'";
            SqlHelper sqlHelper = new SqlHelper();
            sqlHelper.operate(sql, new String[]{});
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Sorry, something went wrong");
        }
    }

    public static void quitDatabase(){
        JOptionPane.showMessageDialog(null, "Bye Bye");
        System.exit(0);
    }

    public static void showCalendar(){

        new CalendarPanel();
    }

    public static void showReporter(){
        new Reporter();
    }

}
