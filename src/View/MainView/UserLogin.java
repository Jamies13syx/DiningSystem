package View.MainView;

import Model.UserModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static Tools.MyTools.f1;
import static Tools.MyTools.f2;


public class UserLogin extends JDialog implements ActionListener {

    JLabel username_label, password_label, userid_label;
    JTextField username_field;
    JPasswordField password_field;
    JButton button_confirm, button_cancel;


    public static void main(String[] args) {
        new UserLogin();
    }

    public UserLogin(){

        Container ct = this.getContentPane();
        ct.setLayout(null);

        username_label = new JLabel("Please input username");
        username_label.setBounds(40, 190, 150, 30);
        username_label.setFont(f1);
        ct.add(username_label);

        username_field = new JTextField(20);
        username_field.setBounds(180,190,120, 30);
        username_field.setFont(f1);
        username_field.setBorder(BorderFactory.createLoweredBevelBorder());
        ct.add(username_field);

        userid_label = new JLabel("(User ID)");
        userid_label.setBounds(75, 205, 100, 30);
        userid_label.setFont(f2);
        userid_label.setForeground(Color.RED);
        ct.add(userid_label);

        password_label = new JLabel("Please input password");
        password_label.setBounds(40, 240, 150, 30);
        password_label.setFont(f1);
        ct.add(password_label);
        

        password_field = new JPasswordField(20);
        password_field.setBounds(180,240,120, 30);
        password_field.setFont(f1);
        password_field.setBorder(BorderFactory.createLoweredBevelBorder());
        ct.add(password_field);
        
        button_confirm = new JButton("Confirm");
        button_confirm.addActionListener(this);
        button_confirm.setFont(f1);
        button_confirm.setBounds(80, 300, 90, 30);
        ct.add(button_confirm);

        button_cancel = new JButton("Cancel");
        button_cancel.addActionListener(this);
        button_cancel.setFont(f1);
        button_cancel.setBounds(210, 300, 90, 30);
        ct.add(button_cancel);
        
        
        BackgroundImage login_view = new BackgroundImage();
        login_view.setBounds(0, 0, 360, 360);
        ct.add(login_view);

        // empty free layout
        this.setUndecorated(true);
        this.setSize(360, 360);
        this.setVisible(true);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        this.setLocation(width / 2 - 200, height / 2 - 125);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button_confirm){
//            System.out.println("Confirm");
            String uid = username_field.getText().trim();
            String password = new String(password_field.getPassword());
            UserModel userModel = new UserModel();
            String[] ret_value = userModel.checkUserPosition(uid, password);
            String userName = ret_value[0];
            String userPosition = ret_value[1];

            if(userPosition.equals("manager") || userPosition.equals("a")){
                new Window1(userName);
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid account");
                return;//only manager or admin can enter window1()
            }

            this.dispose();
        }
        if(e.getSource() == button_cancel){
            System.out.println("Cancel");
            this.dispose();
        }
    }

    class BackgroundImage extends JPanel{
        Image login_image;

        public BackgroundImage(){
            try{
                login_image = ImageIO.read(new File("C:\\Users\\x1c\\IdeaProjects\\DiningSystem\\src\\image\\index\\login.gif"));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        public void paintComponent(Graphics g){

            g.drawImage(login_image,0, 0, 360, 360, this);
        }
    }

}

