package View.DishMenu;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Tools.MyTools.f1;
import static Tools.MyTools.f2;

public class DishMenuInfo extends JPanel implements ActionListener {
    final static String TABLENAME = "MENU";
    JPanel jp1, jp3, jp4, jp5;
    JLabel jp1_label, jp3_label;
    JTextField jp1_field;
    JButton jp1_button, jp4_button_info, jp4_button_insert, jp4_button_update, jp4_button_delete;
    JTable jTable;// showing table
    JScrollPane jScrollPane;
    JFrame father;

    CrudModel dishModel;


    public DishMenuInfo(JFrame father){
        this.father = father;
        //jp1 upper query bar
        this.setLayout(new BorderLayout());
        jp1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp1_label = new JLabel("Please input dish id or dish name: ");
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




        dishModel = new CrudModel(TABLENAME);
        jTable = new JTable(dishModel.getLatestModel());

        //result count panel
        jp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jp3_label = new JLabel("Total: "+ dishModel.getRowCount() +" records");
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
            String dish_id = (String) dishModel.getValueAt(selectedRowNum, 0);//get selected dish_id
            String sql = "DELETE FROM "+ TABLENAME + " WHERE DISH_ID = ?";
            if(dishModel.operate(sql, new String[]{dish_id})){
                JOptionPane.showMessageDialog(null,"Delete successfully");

            }
            else {
                JOptionPane.showMessageDialog(null,"Delete failed");
            }

            dishModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(dishModel);
            jp3_label.setText("Total: "+ dishModel.getRowCount() +" records");
        }

        if(e.getSource() == jp4_button_insert){
            new DishAddDialog(father, "Insert", true, dishModel);
            dishModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(dishModel);
            jp3_label.setText("Total: "+ dishModel.getRowCount() +" records");
        }

        if(e.getSource() == jp4_button_update){
            int selectedRowNum = jTable.getSelectedRow();
            if(selectedRowNum == -1){
                JOptionPane.showMessageDialog(this, "Please select one row");
                return;
            }
            new DishUpdateDialog(father, "Update", true, dishModel, selectedRowNum);
            dishModel = new CrudModel(TABLENAME).getLatestModel();//update
            jTable.setModel(dishModel);
            jp3_label.setText("Total: "+ dishModel.getRowCount() +" records");
        }

        if(e.getSource() == jp1_button){
            //query
            StringBuilder sql = new StringBuilder("SELECT * FROM "+TABLENAME);
            dishModel = new CrudModel(TABLENAME);
            String target = jp1_field.getText().trim();
            if(target.length() != 0){
                if(target.matches("\\d+")){
                    sql.append(" WHERE DISH_ID = ?");
                }
                else if(target.matches("\\D+")){
                    sql.append(" WHERE DISH_NAME = ?");
                }
                jTable.setModel(dishModel.query(sql.toString(), new String[]{target}));
            }
            else {
                dishModel = dishModel.getLatestModel();
                jTable.setModel(dishModel);
            }
            jp3_label.setText("Total: "+ dishModel.getRowCount() +" records");
        }
    }
}
