package View.Client;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientAddDialog extends JDialog implements ActionListener {
    JLabel idLabel, nameLabel, addressLabel, phoneLabel;
    JTextField idField, nameField, addressField, phoneField;
    JPanel jPanel1, jPanel2, jPanel3;
    JButton jButton1, jButton2;
    CrudModel clientModel;

//    public static void main(String[] args) {
//        new ClientAddDialog(null,"aaa",false, new CrudModel("CLIENT"));
//    }
    /**
     *
     * @param owner: father window
     *
     */
    public ClientAddDialog(Frame owner, String title, boolean modal, CrudModel clientModel){
        super(owner, title, modal);
        this.clientModel = clientModel;
        idLabel = new JLabel("Client ID");
        idField = new JTextField(10);
        nameLabel = new JLabel("Client name");
        nameField = new JTextField(10);
        addressLabel = new JLabel("Address");
        addressField = new JTextField(10);
        phoneLabel = new JLabel("Phone");
        phoneField = new JTextField(10);

        //need to sanitation check
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jButton1 = new JButton("Insert");
        jButton1.addActionListener(this);
        jButton2 = new JButton("Cancel");

        jPanel1.setLayout(new GridLayout(4, 1));
        jPanel2.setLayout(new GridLayout(4, 1));

        jPanel1.add(idLabel);
        jPanel1.add(nameLabel);
        jPanel1.add(addressLabel);
        jPanel1.add(phoneLabel);

        jPanel2.add(idField);
        jPanel2.add(nameField);
        jPanel2.add(addressField);
        jPanel2.add(phoneField);

        jPanel3.add(jButton1);
        jPanel3.add(jButton2);

        this.add(jPanel1, BorderLayout.WEST);
        this.add(jPanel2, BorderLayout.CENTER);
        this.add(jPanel3, BorderLayout.SOUTH);

        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 150, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 100);

        this.setSize(300, 200);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jButton1){
            //insert
            String sql = "INSERT INTO "+ ClientInfo.TABLENAME+" VALUES ('" +idField.getText().trim()+ "','"+ nameField.getText().trim()+"','"+
                    addressField.getText().trim()+"','"+phoneField.getText().trim()+"')";

            if(clientModel.operate(sql, new String[] {})){
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
