package View.LoginAccess;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginAddDialog extends JDialog implements ActionListener {
    JLabel idLabel, passwordLabel;
    JTextField idField, passwordField;
    JPanel jPanel1, jPanel2, jPanel3;
    JButton jButton1, jButton2;
    CrudModel loginModel;
    /**
     *
//     * @param owner: father window
     *
     */
//    public static void main(String[] args) {
//        new LoginAddDialog(null, "Insert", true, new CrudModel("LOGIN"));
//    }
    public LoginAddDialog(Frame owner, String title, boolean modal, CrudModel loginModel){
        super(owner, title, modal);
        this.loginModel = loginModel;
        idLabel = new JLabel("User ID",SwingConstants.CENTER);
        idField = new JTextField(SwingConstants.CENTER);

        passwordLabel = new JLabel("Password",SwingConstants.CENTER);
        passwordField = new JTextField(SwingConstants.CENTER);
        //need to sanitation check
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jButton1 = new JButton("Insert");
        jButton1.addActionListener(this);
        jButton2 = new JButton("Cancel");


        jPanel1.setLayout(new GridLayout(4, 1));
        jPanel2.setLayout(new GridLayout(4, 1));

        jPanel1.add(new JLabel());
        jPanel1.add(idLabel);
        jPanel1.add(passwordLabel);
        jPanel1.add(new JLabel());


        jPanel2.add(new JLabel());
        jPanel2.add(idField);
        jPanel2.add(passwordField);
        jPanel2.add(new JLabel());


        jPanel3.add(jButton1);
        jPanel3.add(jButton2);

        this.add(jPanel1, BorderLayout.WEST);
        this.add(jPanel2, BorderLayout.CENTER);
        this.add(jPanel3, BorderLayout.SOUTH);

        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 150, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 100);

        this.setSize(250, 200);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jButton1){
            //insert
            String sql = "INSERT INTO "+ LoginInfo.TABLENAME+" VALUES ('" +idField.getText().trim()+ "','"+ passwordField.getText().trim()+"')";

            if(loginModel.operate(sql, new String[] {})){
                JOptionPane.showMessageDialog(null,"Insert successfully");

            }
            else {
                JOptionPane.showMessageDialog(null,"Insert failed");
            }
            this.dispose();
        }
        else if(e.getSource() == jButton2){
            this.dispose();
        }
    }
}
