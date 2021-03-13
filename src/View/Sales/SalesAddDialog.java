package View.Sales;

import Model.CrudModel;
import View.Sales.SalesInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class SalesAddDialog extends JDialog implements ActionListener {
    JLabel sale_dateLabel, saleLabel, sale_costLabel, env_costLabel, lease_costLabel, labor_costLabel;
    JTextField saleField, sale_costField, env_costField, lease_costField, labor_costField;
    JPanel jPanel1, jPanel2, jPanel3;
    JButton jButton1, jButton2;
    CrudModel salesModel;
    JComboBox<String> yearBox;
    JComboBox<String> monthBox;
    JComboBox<String> dateBox;

    JPanel sale_datePanel;
    /**
     *
//     * @param owner: father window
     *
     */
    public static void main(String[] args) {
        new SalesAddDialog(null, "Insert", true, new CrudModel("sales"));
    }
    public SalesAddDialog(Frame owner, String title, boolean modal, CrudModel salesModel){
        super(owner, title, modal);
        this.salesModel = salesModel;
        sale_dateLabel = new JLabel("Sales date", SwingConstants.CENTER);
        sale_datePanel = new JPanel();

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

        sale_datePanel.add(yearBox);
        sale_datePanel.add(new JLabel("年"));
        sale_datePanel.add(monthBox);
        sale_datePanel.add(new JLabel("月"));
        sale_datePanel.add(dateBox);
        sale_datePanel.add(new JLabel("日"));


//        sale_dateField = new JTextField(10);


        saleLabel = new JLabel("Sale", SwingConstants.CENTER);
        saleField = new JTextField(10);


        sale_costLabel = new JLabel("Sale cost", SwingConstants.CENTER);
        sale_costField = new JTextField(10);

        env_costLabel = new JLabel("Environment cost", SwingConstants.CENTER);
        env_costField = new JTextField(10);

        lease_costLabel = new JLabel("Lease cost", SwingConstants.CENTER);
        lease_costField = new JTextField(10);

        labor_costLabel = new JLabel("Labor cost", SwingConstants.CENTER);
        labor_costField = new JTextField(10);

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jButton1 = new JButton("Insert");
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

        jPanel2.add(sale_datePanel);
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

        this.setSize(350, 300);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jButton1){
            //insert
            String sale_date = (String) yearBox.getSelectedItem() + "-" + (String) monthBox.getSelectedItem() + "-" + (String) dateBox.getSelectedItem();
            String sql = "INSERT INTO "+ SalesInfo.TABLENAME+" VALUES ('" +sale_date+"','"
                    + saleField.getText().trim()+ "','"+ sale_costField.getText().trim() + "','"+
                    env_costField.getText().trim()+ "','" + lease_costField.getText().trim() + "','"+labor_costField.getText().trim()+"')";
            System.out.println(sql);
            if(salesModel.operate(sql, new String[] {})){
                JOptionPane.showMessageDialog(null,"Insert successfully");

            }
            else {
                JOptionPane.showMessageDialog(null,"Insert failed");
            }
            this.dispose();
        }
        else if(e.getSource() == jButton2){
            this.dispose();
        }
    }
}
