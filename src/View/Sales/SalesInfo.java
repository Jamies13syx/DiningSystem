package View.Sales;

import Model.CrudModel;
import View.Register.EmpAddDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static Tools.MyTools.f1;
import static Tools.MyTools.f2;

/**
 * now only take care of closed status
 */

public class SalesInfo extends JPanel implements ActionListener {

    final static String TABLENAME = "SALES";
    JPanel jp1, jp3, jp4, jp5;
    JLabel jp1_label, jp3_label;
    JButton jp1_button,jp1_button_1, jp4_button_info, jp4_button_insert, jp4_button_update, jp4_button_delete;
    JTable jTable;// showing reg table
    JScrollPane jScrollPane;
    JFrame father;
    CrudModel salesModel;
    JComboBox<String> yearBox;
    JComboBox<String> monthBox;
    JComboBox<String> dateBox;

    public SalesInfo(JFrame father) {
        this.father = father;

        //jp1 upper query bar
        this.setLayout(new BorderLayout());
        jp1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp1_label = new JLabel("Please select date: ");
        jp1_label.setFont(f1);

        jp1_button = new JButton("Submit");
        jp1_button.addActionListener(this);
        jp1_button.setFont(f2);

        jp1.add(jp1_label);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);

        String[] years = new String[10];
        String[] months = new String[month];
        String[] dates = new String[date];

        for(int i = 0; i < 10; i++){
            years[i] = (year - i)+"";
        }

        for(int i = 0; i < months.length; i++){
            months[i] =  (month - i)+"";
        }

        for(int i = 0; i < dates.length; i++){
            dates[i] = (date - i)+"";
        }

        yearBox = new JComboBox<>(years);
        yearBox.setSelectedIndex(0);
        yearBox.addActionListener(this);
        monthBox = new JComboBox<>(months);
        monthBox.setSelectedIndex(0);
        monthBox.addActionListener(this);
        dateBox = new JComboBox<>(dates);
        dateBox.setSelectedIndex(0);
        dateBox.addActionListener(this);

        jp1.add(yearBox);
        jp1.add(new JLabel("年"));
        jp1.add(monthBox);
        jp1.add(new JLabel("月"));
        jp1.add(dateBox);
        jp1.add(new JLabel("日"));



        jp1_button_1 = new JButton("Query top 10");
        jp1_button_1.setFont(f2);
        jp1_button_1.addActionListener(this);
        jp1.add(jp1_button);
        jp1.add(jp1_button_1);

        this.add(jp1, "North");

        jp5 = new JPanel(new BorderLayout());
        //crud option panel

        jp4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        jp4_button_info = new JButton("More info");
        jp4_button_insert = new JButton("Insert");
        jp4_button_update = new JButton("Update");
        jp4_button_delete = new JButton("Delete");
        jp4_button_insert.addActionListener(this);
        jp4_button_delete.addActionListener(this);
        jp4_button_update.addActionListener(this);

//        jp4.add(jp4_button_info);
        jp4.add(jp4_button_insert);
        jp4.add(jp4_button_update);
        jp4.add(jp4_button_delete);

        salesModel = new CrudModel(TABLENAME);
        jTable = new JTable(salesModel.getLatestModel());

        //result count panel
        jp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jp3_label = new JLabel("Total: " + salesModel.getRowCount() + " records");
        jp3.add(jp3_label);

        // apply to lower container panel
        jp5.add(jp3, "West");
        jp5.add(jp4, "East");

        jScrollPane = new JScrollPane(jTable);
        this.add(jScrollPane, "Center");
        this.add(jp5, "South");

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jp1_button){
            String sale_date = (String) yearBox.getSelectedItem() + "-" + (String) monthBox.getSelectedItem() + "-" + (String) dateBox.getSelectedItem();
            StringBuilder sql = new StringBuilder("SELECT * FROM "+TABLENAME);
            salesModel = new CrudModel(TABLENAME);
            sql.append(" WHERE SALE_DATE = ?");
            jTable.setModel(salesModel.query(sql.toString(), new String[]{sale_date}));
            jp3_label.setText("Total: "+ salesModel.getRowCount() +" records");
        }
        if(e.getSource() == jp1_button_1){
            salesModel = new CrudModel(TABLENAME);
            jTable.setModel(salesModel.query("SELECT TOP 10 * FROM " + TABLENAME, new String[]{}));
            jp3_label.setText("Total: "+ salesModel.getRowCount() +" records");
        }
        if(e.getSource() == jp4_button_insert){
            new SalesAddDialog(father, "Insert", true, salesModel);
            salesModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(salesModel);
            jp3_label.setText("Total: "+ salesModel.getRowCount() +" records");
        }
        if(e.getSource() == jp4_button_update){
            int selectedRowNum = jTable.getSelectedRow();
            if(selectedRowNum == -1){
                JOptionPane.showMessageDialog(this, "Please select one row");
                return;
            }
            new SalesUpdateDialog(father, "Insert", true, salesModel, selectedRowNum);
            salesModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(salesModel);
            jp3_label.setText("Total: "+ salesModel.getRowCount() +" records");
        }
        if(e.getSource() == jp4_button_delete){
            int selectedRowNum = jTable.getSelectedRow();
            if(selectedRowNum == -1){
                JOptionPane.showMessageDialog(this, "Please select one row");
                return;
            }
            String sale_date = (String) salesModel.getValueAt(selectedRowNum, 0);//get selected date
            String sql = "DELETE FROM "+ TABLENAME + " WHERE SALE_DATE = ?";
            if(salesModel.operate(sql, new String[]{sale_date})){
                JOptionPane.showMessageDialog(null,"Delete successfully");

            }
            else {
                JOptionPane.showMessageDialog(null,"Delete failed");
            }

            salesModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(salesModel);
            jp3_label.setText("Total: "+ salesModel.getRowCount() +" records");
        }

    }
}
