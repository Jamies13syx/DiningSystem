package View.Sales;

import Model.CrudModel;
import View.Sales.SalesInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SalesUpdateDialog extends JDialog implements ActionListener {
    JLabel sale_dateLabel, saleLabel, sale_costLabel, env_costLabel, lease_costLabel, labor_costLabel;
    JTextField sale_dateField, saleField, sale_costField, env_costField, lease_costField, labor_costField;
    JPanel jPanel1, jPanel2, jPanel3;
    JButton jButton1, jButton2;
    CrudModel salesModel;

    /**
     *
     * @param owner: father window
     *
     */
//        public static void main(String[] args) {
//        new SalesAddDialog(null, "UPDATE", true, new CrudModel("sales"));
//    }
    SalesUpdateDialog(Frame owner, String title, boolean modal, CrudModel salesModel, int rowNum){

        super(owner, title, modal);
        this.salesModel = salesModel;

        sale_dateLabel = new JLabel("Sales date", SwingConstants.CENTER);
        sale_dateField = new JTextField(10);
        sale_dateField.setText((String) salesModel.getValueAt(rowNum, 0));
        sale_dateField.setEditable(false);

        saleLabel = new JLabel("Sale");
        saleField = new JTextField(10);
        saleField.setText((String) salesModel.getValueAt(rowNum, 1));

        sale_costLabel = new JLabel("Sale cost");
        sale_costField = new JTextField(10);
        sale_costField.setText((String) salesModel.getValueAt(rowNum, 2));

        env_costLabel = new JLabel("Environment cost");
        env_costField = new JTextField(10);
        env_costField.setText((String) salesModel.getValueAt(rowNum, 3));

        lease_costLabel = new JLabel("Lease cost");
        lease_costField = new JTextField(10);
        lease_costField.setText((String) salesModel.getValueAt(rowNum, 4));

        labor_costLabel = new JLabel("Labor cost");
        labor_costField = new JTextField(10);
        labor_costField.setText((String) salesModel.getValueAt(rowNum, 5));

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();

        jButton1 = new JButton("Update");
        jButton1.addActionListener(this);
        jButton2 = new JButton("Cancel");
        jButton2.addActionListener(this);

        jPanel1.setLayout(new GridLayout(6, 1));
        jPanel2.setLayout(new GridLayout(6, 1));

        jPanel1.add(sale_dateLabel);
        jPanel1.add(saleLabel);
        jPanel1.add(sale_costLabel);
        jPanel1.add(env_costLabel);
        jPanel1.add(lease_costLabel);
        jPanel1.add(labor_costLabel);

        jPanel2.add(sale_dateField);
        jPanel2.add(saleField);
        jPanel2.add(sale_costField);
        jPanel2.add(env_costField);
        jPanel2.add(lease_costField);
        jPanel2.add(labor_costField);

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
            String[] parameters ={saleField.getText().trim(), sale_costField.getText().trim(), env_costField.getText().trim(), lease_costField.getText().trim(), labor_costField.getText().trim(), sale_dateField.getText()};
            String sql = "UPDATE "+ SalesInfo.TABLENAME+" SET SALE = ?, SALE_COST = ?, ENV_COST = ?, LEASE_COST = ?, LABOR_COST = ? WHERE SALE_DATE = ?";
            if(salesModel.operate(sql, parameters)){
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
