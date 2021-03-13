package Tools;

import Model.CrudModel;
import Model.ReportModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.Calendar;
import java.util.Vector;

public class Reporter extends JFrame implements MouseListener, ActionListener {

    JPanel jPanel_header;
    JLabel header_label;
    JScrollPane jScrollPane;
    JPanel jPanel_bottom;
    JButton export, cancel, recent;
    JFileChooser jFileChooser;
    JTable jTable;
    Timer timer;
    Calendar cal;
    ReportModel reportModel;

//    public static void main(String[] args) {
//        new Reporter();
//    }

    public Reporter(){
        Container ct = this.getContentPane();
        jPanel_header = new JPanel();
        header_label = new JLabel("Daily Report");
        header_label.addMouseListener(this);
        header_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header_label.setFont(MyTools.f3);
        jPanel_header.add(header_label, BorderLayout.CENTER);

        reportModel = updateTable();
        jTable = new JTable(reportModel);

        jScrollPane = new JScrollPane(jTable);

        jPanel_bottom = new JPanel(new GridLayout(1, 3));
        export = new JButton("Export");
        export.addMouseListener(this);
        cancel = new JButton("Cancel");
        cancel.addMouseListener(this);
        recent = new JButton("Recent 5 days");
        recent.addMouseListener(this);
        jPanel_bottom.add(export);
        jPanel_bottom.add(cancel);
        jPanel_bottom.add(recent);

        ct.add(jPanel_header, BorderLayout.NORTH);
        ct.add(jScrollPane, BorderLayout.CENTER);
        ct.add(jPanel_bottom, BorderLayout.SOUTH);

        timer = new Timer(60000, this);// evert 1 sec activate action performed
        timer.start();

//        System.out.println(getSaveContent());

        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 150, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 100);
        this.setSize(400, 330);
        this.setVisible(true);


    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == header_label){
            reportModel = updateTable();
            jTable.setModel(reportModel);
        }
        if(e.getSource() == export){

            String path =null;
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            StringReader stringReader = null;

            jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
            jFileChooser.showDialog(new JLabel(), "Save to...");
            if(jFileChooser.getSelectedFile() != null){
                path = jFileChooser.getSelectedFile().getAbsolutePath() + "\\report.txt";
                try {
                    fileWriter = new FileWriter(path);
                    bufferedWriter = new BufferedWriter(fileWriter);
                    stringReader = new StringReader(getSaveContent());
                    char[] buffer = new char[512];
                    while ((stringReader.read(buffer))!= -1){
                        bufferedWriter.write(buffer);
                    }
                    JOptionPane.showMessageDialog(null,"Complete!");
                } catch (IOException e1) {

                    System.out.println("Saving failed.");
                } finally {
                    try {
                        assert stringReader != null;
                        stringReader.close();
                        bufferedWriter.close();
                        fileWriter.close();

                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }

        }
        if(e.getSource() == cancel){
            this.dispose();
        }
        if(e.getSource() == recent){
//            System.out.println("Update");
            reportModel = updateRecent();
            jTable.setModel(reportModel);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        jTable.setModel(updateTable());

    }

    public ReportModel updateTable(){

        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String sales;
        String cost;
        String orderCount;
        String sale_date = year + "-" + month + "-" + date;
        String sql_sales = "SELECT SALE, SALE_COST+ENV_COST+LEASE_COST+LABOR_COST TOTAL FROM SALES WHERE SALE_DATE = ?";
        try{
            CrudModel salesModel = new CrudModel("SALES").query(sql_sales, new String[]{sale_date});
            sales = (String)salesModel.getValueAt(0, 0);
            cost = (String)salesModel.getValueAt(0, 1);
        }
        catch (Exception e){
            sales = "0";
            cost = "0";
        }
        String sql_orders = "SELECT COUNT(*) TOTAL FROM ORDERS WHERE PLACE_TIME = ?";
        try{
            CrudModel ordersModel = new CrudModel("ORDERS").query(sql_orders, new String[]{sale_date});
            orderCount = (String)ordersModel.getValueAt(0, 0);

        }
        catch (Exception e){
            orderCount = "0";
        }

        String [] columnName = {
                "Date",
                "Sales",
                "Cost",
                "Order Count"
        };

        Vector<String[]> data = new Vector<>();
        data.add(new String[]{sale_date, sales,cost, orderCount});

        return new ReportModel(columnName, data);



    }
    public ReportModel updateRecent(){

        String[] dates = new String[5];
        String[] sales = new String[5];
        String[] cost = new String[5];
        String[] orderCount = new String[5];

        String sql_sales = "SELECT TOP 5 SALE_DATE, SALE, SALE_COST+ENV_COST+LEASE_COST+LABOR_COST TOTAL FROM SALES ORDER BY SALE_DATE DESC";
        try{
            CrudModel salesModel = new CrudModel("SALES").query(sql_sales, new String[]{});
            for(int i = 0; i < 5; i++){
                try{
                    dates[i] = (String)salesModel.getValueAt(i, 0);
                    sales[i] = (String)salesModel.getValueAt(i, 1);
                    cost[i] = (String)salesModel.getValueAt(i, 2);
                }
                catch (Exception e){
                    dates[i] = "0000-00-00";
                    sales[i] = "0";
                    cost[i] = "0";
                }
            }
        }
        catch (Exception e){
            System.out.println("Error!");
        }
        String sql_orders = "SELECT TOP 5 COUNT(*) TOTAL FROM ORDERS GROUP BY PLACE_TIME ORDER BY PLACE_TIME DESC";
        try{
            CrudModel ordersModel = new CrudModel("ORDERS").query(sql_orders, new String[]{});
            for(int i = 0; i < 5; i++){
                try{
                    orderCount[i] = (String)ordersModel.getValueAt(i, 0);
                }
                catch (Exception e){
                    orderCount[i] = "0";
                }
            }

        }
        catch (Exception e){
            System.out.println("Error!");
        }

        String [] columnName = {
                "Date",
                "Sales",
                "Cost",
                "Order Count"
        };

        Vector<String[]> data = new Vector<>();
        data.add(new String[]{dates[0].length() > 10? dates[0].substring(0, 11):dates[0], sales[0],cost[0], orderCount[0]});
        data.add(new String[]{dates[1].length() > 10? dates[1].substring(0, 11):dates[1], sales[1],cost[1], orderCount[1]});
        data.add(new String[]{dates[2].length() > 10? dates[2].substring(0, 11):dates[2], sales[2],cost[2], orderCount[2]});
        data.add(new String[]{dates[3].length() > 10? dates[3].substring(0, 11):dates[3], sales[3],cost[3], orderCount[3]});
        data.add(new String[]{dates[4].length() > 10? dates[4].substring(0, 11):dates[4], sales[4],cost[4], orderCount[4]});

        return new ReportModel(columnName, data);

    }

    public String getSaveContent(){
        StringBuilder saveContent = new StringBuilder();
        for(int i = 0; i < reportModel.getColumnCount(); i ++){
            saveContent.append(reportModel.getColumnName(i)).append("\t\t");
        }
        saveContent.append("\r\n");
        for(int i = 0; i < reportModel.getRowCount(); i ++){
            for(int j = 0; j < reportModel.getColumnCount(); j ++){
                saveContent.append(reportModel.getValueAt(i, j)).append("\t");
            }
            saveContent.append("\r\n");
        }
        return saveContent.toString();
    }


}
