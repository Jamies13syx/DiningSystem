package Tools;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooserDemo extends JFrame implements ActionListener {
    JButton open=null;
    public static void main(String[] args) {
        new FileChooserDemo();
    }
    public FileChooserDemo(){
        open=new JButton("open");
        this.add(open);
        this.setBounds(400, 200, 100, 100);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        open.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showDialog(new JLabel(), "Select");
        File file=jfc.getSelectedFile();
        if(file.isDirectory()){
            String path = jfc.getSelectedFile().getAbsolutePath() + "\\report.txt";
            System.out.println("File directory:"+path);
        }else if(file.isFile()){
            System.out.println("File:"+file.getAbsolutePath());
        }
        System.out.println(jfc.getSelectedFile().getName());

    }

}
