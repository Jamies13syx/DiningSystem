package View.Client;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUpdateDialog extends JDialog implements ActionListener {
    JLabel idLabel, nameLabel, addressLabel, phoneLabel;
    JTextField idField, nameField, addressField, phoneField;
    JPanel jPanel1, jPanel2, jPanel3;
    JButton jButton1, jButton2;
    CrudModel clientModel;
    JComboBox<String> opList;

    /**
     *
     * @param owner: father window
     *
     */
    ClientUpdateDialog(Frame owner, String title, boolean modal, CrudModel clientModel, int rowNum){

        super(owner, title, modal);
        this.clientModel = clientModel;

        idLabel = new JLabel("Client ID");
        idField = new JTextField(10);
        idField.setText((String) clientModel.getValueAt(rowNum, 0));
        idField.setEditable(false);

        nameLabel = new JLabel("Client name");
        nameField = new JTextField(10);
        nameField.setText((String) clientModel.getValueAt(rowNum, 1));

        addressLabel = new JLabel("Address");
        addressField = new JTextField(10);
        addressField.setText((String) clientModel.getValueAt(rowNum, 2));

        phoneLabel = new JLabel("Phone");
        phoneField = new JTextField(10);
        phoneField.setText((String) clientModel.getValueAt(rowNum, 3));

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jButton1 = new JButton("Update");
        jButton1.addActionListener(this);
        jButton2 = new JButton("Cancel");
        jButton2.addActionListener(this);


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
            //update
            String[] parameters ={nameField.getText().trim(), addressField.getText().trim(), phoneField.getText().trim(), idField.getText()};
            String sql = "UPDATE "+ ClientInfo.TABLENAME+" SET CLIENT_NAME = ?, CLIENT_ADDRESS = ?, CLIENT_PHONE = ? WHERE CLIENT_ID = ?";

            if(clientModel.operate(sql, parameters)){
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
