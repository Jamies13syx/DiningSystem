package View.Orders;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class OrdersAddDialog extends JDialog implements ActionListener {
    JLabel idLabel, dish_idLabel, quantityLabel, commentLabel, takeoutLabel, client_idLabel;
    JTextField idField, dish_idField, quantityField, commentField, client_idField;
    JPanel jPanel1, jPanel2, jPanel3;
    JButton jButton1, jButton2;
    CrudModel dishModel;
    JComboBox<String> opList;
    JComboBox<String> takeoutList;
    java.sql.Date stp;
    java.util.Date utilDate;

    public static void main(String[] args) {
        new OrdersAddDialog(null,"aaa",false, new CrudModel("ORDERS"));
    }
    /**
     *
     * @param owner: father window
     *
     */
    public OrdersAddDialog(Frame owner, String title, boolean modal, CrudModel dishModel){
        super(owner, title, modal);
        this.dishModel = dishModel;
        idLabel = new JLabel("Order ID");
        idField = new JTextField(10);
        dish_idLabel = new JLabel("Dish ID");
        dish_idField = new JTextField(10);
        quantityLabel = new JLabel("Quantity");
//        quantityField = new JTextField(10);
        commentLabel = new JLabel("Comment");
        commentField = new JTextField(10);
        takeoutLabel = new JLabel("Takeout");
//        takeoutField = new JTextField(10);
        client_idLabel = new JLabel("Client id");
        client_idField = new JTextField(10);
        //need to sanitation check
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jButton1 = new JButton("Insert");
        jButton1.addActionListener(this);
        jButton2 = new JButton("Cancel");

        String[] options = new String[30];
        for(int i = 1; i <= 30; i++){
            options[i - 1] = i+"";
        }
        opList = new JComboBox<>(options);//quantity
        opList.setSelectedIndex(0);
        opList.addActionListener(this);

        takeoutList = new JComboBox<>(new String[] {"Y", "N"});//quantity
        takeoutList.setSelectedIndex(0);
        takeoutList.addActionListener(this);

        jPanel1.setLayout(new GridLayout(6, 1));
        jPanel2.setLayout(new GridLayout(6, 1));

        jPanel1.add(idLabel);
        jPanel1.add(dish_idLabel);
        jPanel1.add(client_idLabel);
        jPanel1.add(quantityLabel);
        jPanel1.add(commentLabel);
        jPanel1.add(takeoutLabel);

        jPanel2.add(idField);
        jPanel2.add(dish_idField);
        jPanel2.add(client_idField);
        jPanel2.add(opList);
        jPanel2.add(commentField);
        jPanel2.add(takeoutList);

        jPanel3.add(jButton1);
        jPanel3.add(jButton2);

        utilDate = new Date();


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
            stp = new java.sql.Date(utilDate.getTime());
            String sql = "INSERT INTO "+ OrdersInfo.TABLENAME+" VALUES ('" +idField.getText().trim() + "','" + stp + "','" + dish_idField.getText().trim()+"','"+
                    opList.getSelectedItem()+"','"+commentField.getText().trim()+"','"+client_idField.getText().trim()+"','"+takeoutList.getSelectedItem()+"')";
//            System.out.println(sql);
            if(dishModel.operate(sql, new String[] {})){
                JOptionPane.showMessageDialog(null,"Insert successfully");

            }
            else {
                JOptionPane.showMessageDialog(null,"Insert failed");
            }
//            System.out.println(sql);
            this.dispose();
//            System.out.println(sql);
        }
        else if(e.getSource() == jButton2){
            this.dispose();
        }
    }
}
