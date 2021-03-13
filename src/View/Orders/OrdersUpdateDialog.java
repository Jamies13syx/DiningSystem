package View.Orders;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdersUpdateDialog extends JDialog implements ActionListener {
    JLabel idLabel, orders_idLabel, quantityLabel, commentLabel, takeoutLabel, client_idLabel;
    JTextField idField, orders_idField, quantityField, commentField, client_idField;
    JPanel jPanel1, jPanel2, jPanel3;
    JButton jButton1, jButton2;
    CrudModel ordersModel;
    JComboBox<String> opList;
    JComboBox<String> takeoutList;

    String stp;
    String client_id;


    /**
     *
     * @param owner: father window
     *
     */
    OrdersUpdateDialog(Frame owner, String title, boolean modal, CrudModel ordersModel, int rowNum){

        super(owner, title, modal);
        this.ordersModel = ordersModel;

        String[] options = new String[30];
        for(int i = 1; i <= 30; i++){
            options[i - 1] = i+"";
        }
        opList = new JComboBox<>(options);//quantity
        opList.addActionListener(this);

        takeoutList = new JComboBox<>(new String[] {"Y", "N"});//quantity
        takeoutList.setSelectedIndex(0);
        takeoutList.addActionListener(this);

        stp = (String) ordersModel.getValueAt(rowNum, 1);
        client_id = (String) ordersModel.getValueAt(rowNum, 5);

        idLabel = new JLabel("Order ID");
        idField = new JTextField(10);
        idField.setText((String) ordersModel.getValueAt(rowNum, 0));
        idField.setEditable(false);

        orders_idLabel = new JLabel("orders_id");
        orders_idField = new JTextField(10);
        orders_idField.setText((String) ordersModel.getValueAt(rowNum, 2));

        quantityLabel = new JLabel("Quantity");
//        quantityField = new JTextField(10);
        opList.setSelectedItem(ordersModel.getValueAt(rowNum, 3));

        commentLabel = new JLabel("Comment");
        commentField = new JTextField(10);
        commentField.setText((String) ordersModel.getValueAt(rowNum, 4));

        client_idLabel = new JLabel("Client id");
        client_idField = new JTextField(10);
        client_idField.setText(client_id);
        client_idField.setEditable(false);

        takeoutLabel = new JLabel("Takeout");
        takeoutList.setSelectedItem(ordersModel.getValueAt(rowNum, 4));

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();

        jButton1 = new JButton("Update");
        jButton1.addActionListener(this);
        jButton2 = new JButton("Cancel");
        jButton2.addActionListener(this);

        jPanel1.setLayout(new GridLayout(6, 1));
        jPanel2.setLayout(new GridLayout(6, 1));

        jPanel1.add(idLabel);
        jPanel1.add(orders_idLabel);
        jPanel1.add(client_idLabel);
        jPanel1.add(quantityLabel);
        jPanel1.add(commentLabel);
        jPanel1.add(takeoutLabel);

        jPanel2.add(idField);
        jPanel2.add(orders_idField);
        jPanel2.add(client_idField);
        jPanel2.add(opList);
        jPanel2.add(commentField);
        jPanel2.add(takeoutList);

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

            String[] parameters ={(String)opList.getSelectedItem(), commentField.getText().trim(), client_idField.getText().trim(), (String) takeoutList.getSelectedItem(),idField.getText().trim(), client_id, stp};
            String sql = "UPDATE "+ OrdersInfo.TABLENAME+" SET QUANTITY = ?, COMMENT = ?, CLIENT_ID = ?, ISTAKEOUT = ? WHERE ORDER_ID = ? AND CLIENT_ID = ? AND PLACE_TIME = ?";
            System.out.println(sql);
            if(ordersModel.operate(sql, parameters)){
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
