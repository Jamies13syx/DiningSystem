package View.LoginAccess;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUpdateDialog extends JDialog implements ActionListener {
    JLabel idLabel, passwordLabel;
    JTextField idField, passwordField;
    JPanel jPanel1, jPanel2, jPanel3;
    JButton jButton1, jButton2;
    CrudModel loginModel;

    /**
     *
     * @param owner: father window
     *
     */
    LoginUpdateDialog(Frame owner, String title, boolean modal, CrudModel loginModel, int rowNum){

        super(owner, title, modal);
        this.loginModel = loginModel;

        idLabel = new JLabel("User ID", SwingConstants.CENTER);
        idField = new JTextField(10);
        idField.setText((String) loginModel.getValueAt(rowNum, 0));
        idField.setEditable(false);

        passwordLabel = new JLabel("Password", SwingConstants.CENTER);
        passwordField = new JTextField(10);
        passwordField.setText((String) loginModel.getValueAt(rowNum, 1));

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jButton1 = new JButton("Update");
        jButton1.addActionListener(this);
        jButton2 = new JButton("Cancel");
        jButton2.addActionListener(this);

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
            //update
            String[] parameters ={passwordField.getText().trim(), idField.getText()};
            String sql = "UPDATE "+ LoginInfo.TABLENAME+" SET PASSWORD = ? WHERE UID = ?";
            if(loginModel.operate(sql, parameters)){
                JOptionPane.showMessageDialog(null,"Update successfully");

            }
            else {
                JOptionPane.showMessageDialog(null,"Update failed");
            }

            this.dispose();
        }
        else if(e.getSource() == jButton2){
            this.dispose();
        }
    }
}
