package View.MainView;

import javax.swing.*;
import java.awt.*;

/**
 * This module shows loading window view with progressbar
 */

public class Index extends JWindow implements Runnable{
    JProgressBar index_progressBar;
    JLabel index_label;
    public static void main(String[] args) {
        Index index = new Index();
        Thread t = new Thread(index);
        t.start();

    }

    public Index(){

        index_label = new JLabel(new ImageIcon("C:\\Users\\x1c\\IdeaProjects\\DiningSystem\\src\\image\\index\\index.gif"));// label for loading anime
        index_progressBar = new JProgressBar();

        index_progressBar.setStringPainted(true);//show current loading progress
        index_progressBar.setIndeterminate(false);// not indeterminate
        index_progressBar.setBounds(new Rectangle(400, 10));
        index_progressBar.setBackground(Color.pink);

        this.add(index_label, BorderLayout.NORTH);
        this.add(index_progressBar, BorderLayout.SOUTH);

        this.setSize(400, 261);
        this.setVisible(true);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        this.setLocation(width / 2 - 200, height / 2 - 125);

    }

    @Override
    public void run() {

        int[] progressValue={0,1,5,8,14,17,26,35,38,43,49,56,65,71,75,78,86,94,98,99,100};

        for(int i = 0; i < progressValue.length; i++){
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            index_progressBar.setValue(progressValue[i]);
        }
        while (true){
            try{
                Thread.sleep(1000);//after one sec, quit loading view
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
//            System.out.println("Load login");

            new UserLogin();
            this.dispose();
            break;
        }
    }
}
