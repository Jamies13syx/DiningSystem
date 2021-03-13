package View.Register;

import Model.CrudModel;
import View.Sales.SalesAddDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmpUpdateDialog extends JDialog implements ActionListener {
    JLabel idLabel, nameLabel, sexLabel, positionLabel;
    JTextField idField, nameField, sexField, positionField;
    JPanel jPanel1, jPanel2, jPanel3;
    JButton jButton1, jButton2;
    CrudModel empModel;
    JComboBox<String> opList;

    /**
     *
     * @param owner: father window
     *
     */
    EmpUpdateDialog(Frame owner, String title, boolean modal, CrudModel empModel, int rowNum){

        super(owner, title, modal);
        this.empModel = empModel;

        opList = new JComboBox<>(new String[] {"M", "F"});
        opList.setSelectedIndex(0);
        opList.addActionListener(this);

        idLabel = new JLabel("Employee ID");
        idField = new JTextField(10);
        idField.setText((String) empModel.getValueAt(rowNum, 0));
        idField.setEditable(false);

        nameLabel = new JLabel("Employee name");
        nameField = new JTextField(10);
        nameField.setText((String) empModel.getValueAt(rowNum, 1));


        sexLabel = new JLabel("Sex");
//        sexField = new JTextField(10);
//        sexField.setText((String) empModel.getValueAt(rowNum, 2));

        positionLabel = new JLabel("Position");
        positionField = new JTextField(10);
        positionField.setText((String) empModel.getValueAt(rowNum, 3));

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
        jPanel1.add(sexLabel);
        jPanel1.add(positionLabel);

        jPanel2.add(idField);
        jPanel2.add(nameField);
        opList.setSelectedItem(empModel.getValueAt(rowNum, 2));
        jPanel2.add(opList);
        jPanel2.add(positionField);

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
            String[] parameters ={nameField.getText().trim(), sexField.getText().trim(), positionField.getText().trim(), idField.getText()};
            String sql = "UPDATE "+EmpInfo.TABLENAME+" SET NAME = ?, SEX = ?, POSITION = ? WHERE UID = ?";
            if(empModel.operate(sql, parameters)){
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
