package View.Client;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Tools.MyTools.f1;
import static Tools.MyTools.f2;

public class ClientInfo extends JPanel implements ActionListener {
    final static String TABLENAME = "CLIENT";
    JPanel jp1, jp3, jp4, jp5;
    JLabel jp1_label, jp3_label;
    JTextField jp1_field;
    JButton jp1_button, jp4_button_info, jp4_button_insert, jp4_button_update, jp4_button_delete;
    JTable jTable;// showing table
    JScrollPane jScrollPane;
    JFrame father;

    CrudModel clientModel;


    public ClientInfo(JFrame father){
        this.father = father;
        //jp1 upper query bar
        this.setLayout(new BorderLayout());
        jp1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp1_label = new JLabel("Please input client id or client name or client cellphone: ");
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
        //need more info
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




        clientModel = new CrudModel(TABLENAME);
        jTable = new JTable(clientModel.getLatestModel());

        //result count panel
        jp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jp3_label = new JLabel("Total: "+ clientModel.getRowCount() +" records");
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
            String client_id = (String) clientModel.getValueAt(selectedRowNum, 0);//get selected client_id
            String sql = "DELETE FROM "+ TABLENAME + " WHERE CLIENT_ID = ?";
            if(clientModel.operate(sql, new String[]{client_id})){
                JOptionPane.showMessageDialog(null,"Delete successfully");

            }
            else {
                JOptionPane.showMessageDialog(null,"Delete failed");
            }

            clientModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(clientModel);
            jp3_label.setText("Total: "+ clientModel.getRowCount() +" records");
        }

        if(e.getSource() == jp4_button_insert){
            new ClientAddDialog(father, "Insert", true, clientModel);
            clientModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(clientModel);
            jp3_label.setText("Total: "+ clientModel.getRowCount() +" records");
        }

        if(e.getSource() == jp4_button_update){
            int selectedRowNum = jTable.getSelectedRow();
            if(selectedRowNum == -1){
                JOptionPane.showMessageDialog(this, "Please select one row");
                return;
            }
            new ClientUpdateDialog(father, "Update", true, clientModel, selectedRowNum);
            clientModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(clientModel);
            jp3_label.setText("Total: "+ clientModel.getRowCount() +" records");
        }

        if(e.getSource() == jp1_button){
            //query
            StringBuilder sql = new StringBuilder("SELECT * FROM "+TABLENAME);
            clientModel = new CrudModel(TABLENAME);
            String target = jp1_field.getText().trim();
            if(target.length() != 0){
                if(target.matches("cli\\d+")){
                    sql.append(" WHERE CLIENT_ID = ?");
                }
                else if(target.matches("\\D+")){
                    sql.append(" WHERE CLIENT_NAME = ?");
                }
                else if(target.matches("\\d{10,13}")){
                    sql.append(" WHERE CLIENT_PHONE = ?");
                }
                else {
                    JOptionPane.showMessageDialog(this, "Please input valid query info");
                    return;
                }
                jTable.setModel(clientModel.query(sql.toString(), new String[]{target}));
            }
            else {
                clientModel = clientModel.getLatestModel();
                jTable.setModel(clientModel);
            }
            jp3_label.setText("Total: "+ clientModel.getRowCount() +" records");
        }
    }
}
