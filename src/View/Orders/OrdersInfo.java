package View.Orders;

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


public class OrdersInfo extends JPanel implements ActionListener {
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
    final static String TABLENAME = "ORDERS";
    JPanel jp1, jp3, jp4, jp5;
    JLabel jp1_label, jp3_label;
    JTextField jp1_field;
    JButton jp1_button, jp4_button_info, jp4_button_insert, jp4_button_update, jp4_button_delete;
    JTable jTable;// showing table
    JScrollPane jScrollPane;
    JFrame father;

    CrudModel orderModel;


    public OrdersInfo(JFrame father){
        this.father = father;
        //jp1 upper query bar
        this.setLayout(new BorderLayout());
        jp1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp1_label = new JLabel("Please input order id or cellphone number: ");
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



        
        orderModel = new CrudModel(TABLENAME);
        jTable = new JTable(orderModel.getLatestModel());

        //result count panel
        jp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jp3_label = new JLabel("Total: "+ orderModel.getRowCount() +" records");
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
            String uid = (String) orderModel.getValueAt(selectedRowNum, 0);//get selected uid
            String sql = "DELETE FROM "+ TABLENAME + " WHERE ORDER_ID = ?";
            if(orderModel.operate(sql, new String[]{uid})){
                JOptionPane.showMessageDialog(null,"Delete successfully");

            }
            else {
                JOptionPane.showMessageDialog(null,"Delete failed");
            }

            orderModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(orderModel);
            jp3_label.setText("Total: "+ orderModel.getRowCount() +" records");
        }

        if(e.getSource() == jp4_button_insert){
            new OrdersAddDialog(father, "Insert", true, orderModel);
            orderModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(orderModel);
            jp3_label.setText("Total: "+ orderModel.getRowCount() +" records");
        }

        if(e.getSource() == jp4_button_update){
            int selectedRowNum = jTable.getSelectedRow();
            if(selectedRowNum == -1){
                JOptionPane.showMessageDialog(this, "Please select one row");
                return;
            }
            new OrdersUpdateDialog(father, "Update", true, orderModel, selectedRowNum);
            orderModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(orderModel);
            jp3_label.setText("Total: "+ orderModel.getRowCount() +" records");
        }

        if(e.getSource() == jp1_button){
            //query
            StringBuilder sql = new StringBuilder("SELECT * FROM "+TABLENAME);
            orderModel = new CrudModel(TABLENAME);
            String target = jp1_field.getText().trim();
            if(target.length() != 0){
                if(target.matches("o\\d+")){
                    sql.append(" WHERE ORDER_ID = ?");
                }
                else if(target.matches("\\d{10,13}")){
                    sql.append(" O, CLIENT_ID C WHERE O.CLIENT_ID = C.CLIENT_ID AND C.CLIENT_PHONE = ?");
                }
                else {
                    JOptionPane.showMessageDialog(this, "Please input valid query info");
                    return;
                }
                jTable.setModel(orderModel.query(sql.toString(), new String[]{target}));
            }
            else {
                orderModel = orderModel.getLatestModel();
                jTable.setModel(orderModel);
            }
            jp3_label.setText("Total: "+ orderModel.getRowCount() +" records");
        }
    }
}
