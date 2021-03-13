package View.DishMenu;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DishAddDialog extends JDialog implements ActionListener {
    JLabel idLabel, nameLabel, categoryLabel, priceLabel, availableLabel, costLabel;
    JTextField idField, nameField, categoryField, priceField, costField;
    JPanel jPanel1, jPanel2, jPanel3;
    JButton jButton1, jButton2;
    CrudModel dishModel;
    JComboBox<String> opList;

    public static void main(String[] args) {
        new DishAddDialog(null,"aaa",false, new CrudModel("MENU"));
    }
    /**
     *
     * @param owner: father window
     *
     */
    public DishAddDialog(Frame owner, String title, boolean modal, CrudModel dishModel){
        super(owner, title, modal);
        this.dishModel = dishModel;
        idLabel = new JLabel("Dish ID");
        idField = new JTextField(10);
        nameLabel = new JLabel("Dish name");
        nameField = new JTextField(10);
        categoryLabel = new JLabel("Category");
        categoryField = new JTextField(10);
        priceLabel = new JLabel("Price");
        priceField = new JTextField(10);
        availableLabel = new JLabel("Available");
//        availableField = new JTextField(10);
        costLabel = new JLabel("Cost");
        costField = new JTextField(10);
        //need to sanitation check
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jButton1 = new JButton("Insert");
        jButton1.addActionListener(this);
        jButton2 = new JButton("Cancel");

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
            //insert
            String sql = "INSERT INTO "+DishMenuInfo.TABLENAME+" VALUES ('" +idField.getText().trim()+ "','"+ nameField.getText().trim()+"','"+
                    categoryField.getText().trim()+"','"+priceField.getText().trim()+"','"+opList.getSelectedItem()+"','"+costField.getText().trim()+"')";

            if(dishModel.operate(sql, new String[] {})){
                JOptionPane.showMessageDialog(null,"Insert successfully");

            }
            else {
                JOptionPane.showMessageDialog(null,"Insert failed");
            }


            this.dispose();
//            System.out.println(sql);
        }
        else if(e.getSource() == jButton2){
            this.dispose();
        }
    }
}
