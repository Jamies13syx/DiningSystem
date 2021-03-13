package View.Takeout;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Tools.MyTools.f1;
import static Tools.MyTools.f2;

/**
 * This class shows view of employee info-belongs to reg viw
 *
 */


public class TakeoutInfo extends JPanel implements ActionListener {
    /**
     * jp1 upper query bar
     * jp3 result count panel
     * jp4 lower crud option panel
     * jp5 lower container panel
     * jscrollpane table part
     */

//    public static void main(String[] args) {
//        new OrderInfo(null);
//    }
    final static String TABLENAME = "TAKEOUT";
    JPanel jp1, jp3, jp4, jp5;
    JLabel jp1_label, jp3_label;
    JTextField jp1_field;
    JButton jp1_button, jp4_button_info, jp4_button_insert, jp4_button_update, jp4_button_delete;
    JTable jTable;// showing table
    JScrollPane jScrollPane;
    JFrame father;

    CrudModel takeoutModel;


    public TakeoutInfo(JFrame father){
        this.father = father;
        //jp1 upper query bar
        this.setLayout(new BorderLayout());
        jp1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp1_label = new JLabel("Please input order id or client id: ");
        jp1_label.setFont(f1);
        jp1_field = new JTextField(20);
        jp1_button = new JButton("Submit");
        jp1_button.addActionListener(this);
        jp1_button.setFont(f2);
        jp1.add(jp1_label);
        jp1.add(jp1_field);
        jp1.add(jp1_button);
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


        takeoutModel = new CrudModel(TABLENAME);
        jTable = new JTable(takeoutModel.getLatestModel());

        //result count panel
        jp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jp3_label = new JLabel("Total: "+ takeoutModel.getRowCount() +" records");
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
        if(e.getSource() == jp4_button_delete){
            int selectedRowNum = jTable.getSelectedRow();
            if(selectedRowNum == -1){
                JOptionPane.showMessageDialog(this, "Please select one row");
                return;
            }
            String order_id = (String) takeoutModel.getValueAt(selectedRowNum, 0);//get selected id
            String client_id = (String) takeoutModel.getValueAt(selectedRowNum, 1);
            String stp = (String) takeoutModel.getValueAt(selectedRowNum, 2);
            String sql = "DELETE FROM "+ TABLENAME + " WHERE ORDER_ID = ? AND CLIENT_ID = ? AND PLACE_TIME = ?";
            if(takeoutModel.operate(sql, new String[]{order_id, client_id, stp})){
                JOptionPane.showMessageDialog(null,"Delete successfully");

            }
            else {
                JOptionPane.showMessageDialog(null,"Delete failed");
            }

            takeoutModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(takeoutModel);
            jp3_label.setText("Total: "+ takeoutModel.getRowCount() +" records");
        }

        if(e.getSource() == jp4_button_insert){
            new TakeoutAddDialog(father, "Insert", true, takeoutModel);
            takeoutModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(takeoutModel);
            jp3_label.setText("Total: "+ takeoutModel.getRowCount() +" records");
        }

        if(e.getSource() == jp4_button_update){
            int selectedRowNum = jTable.getSelectedRow();
            if(selectedRowNum == -1){
                JOptionPane.showMessageDialog(this, "Please select one row");
                return;
            }
            new TakeoutUpdateDialog(father, "Update", true, takeoutModel, selectedRowNum);
            takeoutModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(takeoutModel);
            jp3_label.setText("Total: "+ takeoutModel.getRowCount() +" records");
        }

        if(e.getSource() == jp1_button){
            //query
            StringBuilder sql = new StringBuilder("SELECT * FROM "+TABLENAME);
            takeoutModel = new CrudModel(TABLENAME);
            String target = jp1_field.getText().trim();
            if(target.length() != 0){
                if(target.matches("o\\d+")){
                    sql.append(" WHERE ORDER_ID = ?");
                }
                else if(target.matches("c\\d+")){
                    sql.append(" WHERE CLIENT_ID = ?");
                }
                else {
                    JOptionPane.showMessageDialog(this, "Please input valid query info");
                    return;
                }
                jTable.setModel(takeoutModel.query(sql.toString(), new String[]{target}));
            }
            else {
                takeoutModel = takeoutModel.getLatestModel();
                jTable.setModel(takeoutModel);
            }
            jp3_label.setText("Total: "+ takeoutModel.getRowCount() +" records");
        }
    }
}
