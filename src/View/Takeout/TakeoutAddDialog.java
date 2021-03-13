package View.Takeout;

import Model.CrudModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TakeoutAddDialog extends JDialog implements ActionListener {
    JLabel orders_idLabel, client_idLabel, fetch_timeLabel, delivered_timeLabel;
    JTextField orders_idField, client_idField, fetch_timeField, delivered_timeField;
    JPanel jPanel1, jPanel2, jPanel3, fetch_panel, delivered_panel;
    JButton jButton1, jButton2, fetch_button, delivered_button;
    CrudModel takeoutModel;
    String stp;//place time
    java.util.Date utilDate;

//    public static void main(String[] args) {
//        new TakeoutAddDialog(null,"aaa",false, new CrudModel("TAKEOUT"));
//    }
    /**
     *
     * @param owner: father window
     *
     */
    public TakeoutAddDialog(Frame owner, String title, boolean modal, CrudModel takeoutModel){
        super(owner, title, modal);
        this.takeoutModel = takeoutModel;


        orders_idLabel = new JLabel("Orders id");
        orders_idField = new JTextField(10);


        client_idLabel = new JLabel("Client id");
        client_idField = new JTextField(10);


        fetch_timeLabel = new JLabel("Fetch time");
        fetch_button = new JButton("Get time");
        fetch_button.addActionListener(this);
        fetch_panel = new JPanel(new GridLayout(1, 2));
        fetch_timeField = new JTextField();
        fetch_timeField.setEditable(false);
        fetch_panel.add(fetch_timeField);
        fetch_panel.add(fetch_button);

        delivered_timeLabel = new JLabel("Delivered time");
        delivered_button = new JButton("Get time");
        delivered_button.addActionListener(this);
        delivered_panel = new JPanel(new GridLayout(1, 2));
        delivered_timeField = new JTextField();
        delivered_timeField.setEditable(false);
        delivered_panel.add(delivered_timeField);
        delivered_panel.add(delivered_button);

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();

        jButton1 = new JButton("Update");
        jButton1.addActionListener(this);
        jButton2 = new JButton("Cancel");
        jButton2.addActionListener(this);

        jPanel1.setLayout(new GridLayout(4, 1));
        jPanel2.setLayout(new GridLayout(4, 1));

        jPanel1.add(orders_idLabel);
        jPanel1.add(client_idLabel);
        jPanel1.add(fetch_timeLabel);
        jPanel1.add(delivered_timeLabel);

        jPanel2.add(orders_idField);
        jPanel2.add(client_idField);
        jPanel2.add(fetch_panel);
        jPanel2.add(delivered_panel);

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

        if(e.getSource() == fetch_button){
            utilDate = new Date();
            fetch_timeField.setText(new java.sql.Date(utilDate.getTime()).toString());

        }
        if(e.getSource() == delivered_button){
            utilDate = new Date();
            delivered_timeField.setText(new java.sql.Date(utilDate.getTime()).toString());
        }

        if(e.getSource() == jButton1){
            //insert
            utilDate = new Date();
            stp = new java.sql.Date(utilDate.getTime()).toString();//place time
            String sql = "INSERT INTO "+ TakeoutInfo.TABLENAME+" VALUES ('" + orders_idField.getText().trim()+ "','" + client_idField.getText().trim()+"','" + stp + "','" +
                    fetch_timeField.getText()+"','"+delivered_timeField.getText()+"')";
            //System.out.println(sql);
            if(takeoutModel.operate(sql, new String[] {})){
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
