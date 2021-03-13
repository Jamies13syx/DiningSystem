package View.DishMenu;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DishUpdateDialog extends JDialog implements ActionListener {
    JLabel idLabel, nameLabel, categoryLabel, priceLabel, availableLabel, costLabel;
    JTextField idField, nameField, categoryField, priceField, costField;
    JPanel jPanel1, jPanel2, jPanel3;
    JButton jButton1, jButton2;
    CrudModel dishModel;
    JComboBox<String> opList;

    /**
     *
     * @param owner: father window
     *
     */
    DishUpdateDialog(Frame owner, String title, boolean modal, CrudModel dishModel, int rowNum){

        super(owner, title, modal);
        this.dishModel = dishModel;

        idLabel = new JLabel("Dish ID");
        idField = new JTextField(10);
        idField.setText((String) dishModel.getValueAt(rowNum, 0));
        idField.setEditable(false);

        nameLabel = new JLabel("Dish name");
        nameField = new JTextField(10);
        nameField.setText((String) dishModel.getValueAt(rowNum, 1));

        categoryLabel = new JLabel("Category");
        categoryField = new JTextField(10);
        categoryField.setText((String) dishModel.getValueAt(rowNum, 2));

        priceLabel = new JLabel("Price");
        priceField = new JTextField(10);
        priceField.setText((String) dishModel.getValueAt(rowNum, 3));

        availableLabel = new JLabel("Available");
        opList = new JComboBox<>();
        opList.setSelectedItem((String) dishModel.getValueAt(rowNum, 4));

        costLabel = new JLabel("Cost");
        costField = new JTextField(10);
        costField.setText((String) dishModel.getValueAt(rowNum, 5));


        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jButton1 = new JButton("Update");
        jButton1.addActionListener(this);
        jButton2 = new JButton("Cancel");
        jButton2.addActionListener(this);
        String[] options = { "Y", "N" };
        opList = new JComboBox<>(options);
        opList.setSelectedIndex(0);
        opList.addActionListener(this);


        jPanel1.setLayout(new GridLayout(6, 1));
        jPanel2.setLayout(new GridLayout(6, 1));

        jPanel1.add(idLabel);
        jPanel1.add(nameLabel);
        jPanel1.add(categoryLabel);
        jPanel1.add(priceLabel);
        jPanel1.add(availableLabel);
        jPanel1.add(costLabel);

        jPanel2.add(idField);
        jPanel2.add(nameField);
        jPanel2.add(categoryField);
        jPanel2.add(priceField);
        jPanel2.add(opList);
        jPanel2.add(costField);

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
            String[] parameters ={nameField.getText().trim(), categoryField.getText().trim(), priceField.getText().trim(), (String)opList.getSelectedItem(), costField.getText().trim(), idField.getText()};
            String sql = "UPDATE "+DishMenuInfo.TABLENAME+" SET DISH_NAME = ?, CATEGORY = ?, PRICE = ?, AVAILABLE = ?, COST = ? WHERE DISH_ID = ?";
            System.out.println(sql);
            if(dishModel.operate(sql, parameters)){
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
