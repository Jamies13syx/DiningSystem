package Tools;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class CalendarPanel extends JFrame {

//    private static final long serialVersionUID = 1L;
    Calendar cal;
    JPanel operationPanel = null;
    JPanel dateContainerPanel = null;
    JButton pMonth = new JButton("<");
    JButton nMonth = new JButton(">");
    JButton pYear = new JButton("<<");
    JButton nYear = new JButton(">>");

    JLabel monthLabel = new JLabel();

    private int year;
    private int month;
    private final int date;

    public CalendarPanel() {
        cal = Calendar.getInstance();
        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH);
        this.date = cal.get(Calendar.DAY_OF_MONTH);
        buildJFrame(cal);
    }

    public void buildJFrame(Calendar cal) {
        Container contentPane = getContentPane();
        contentPane.add(getOprPanel(), BorderLayout.NORTH);
        contentPane.add(getPanel(cal), BorderLayout.CENTER);
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

//    public static void main(String args[]) {
//        new CalendarPanel();
//    }

    public JPanel getOprPanel() {
        if (operationPanel == null) {
            operationPanel = new JPanel();
        }

        Box hBox = Box.createHorizontalBox();
        monthLabel.setText(this.year + "年 " + (this.month + 1) + "月");
        hBox.add(pYear);
        hBox.add(Box.createHorizontalStrut(20));
        hBox.add(pMonth);
        hBox.add(Box.createHorizontalStrut(20));
        hBox.add(monthLabel);
        hBox.add(Box.createHorizontalStrut(20));
        hBox.add(nMonth);
        hBox.add(Box.createHorizontalStrut(20));
        hBox.add(nYear);
        pYear.addActionListener(new previousYear());
        nYear.addActionListener(new nextYear());
        pMonth.addActionListener(new previousMonth());
        nMonth.addActionListener(new nextMonth());
        operationPanel.add(hBox);
        return operationPanel;
    }

    // date panel
    public JPanel getPanel(Calendar cal) {
        if (dateContainerPanel == null) {
            dateContainerPanel = new JPanel();
        }
        dateContainerPanel.removeAll();
        cal.set(Calendar.DAY_OF_MONTH, 1);//set to 1
        cal.add(Calendar.MONTH, 1);//adjust to first day of next month
        cal.add(Calendar.DAY_OF_MONTH, -1);// set to last day of this month

        int weeks = cal.get(Calendar.WEEK_OF_MONTH);//Month's nth week

        GridLayout grid = new GridLayout(weeks + 1, 7);
        dateContainerPanel.setLayout(grid);
        cal.set(Calendar.DAY_OF_MONTH, 1);//set to first day of this month
        int weekday = cal.get(Calendar.DAY_OF_WEEK);// get the week day of first day of this month
        cal.add(Calendar.DAY_OF_MONTH, 1 - weekday); // get the first day( some day in last month) in this panel

        String[] weekTitle = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

        for (int i = 0; i < weeks + 1; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0) {
                    JLabel label = new JLabel(weekTitle[j], JLabel.CENTER);
                    label.setBorder(BorderFactory.createEtchedBorder());
                    dateContainerPanel.add(label);
                } else {
                    JLabel label = new JLabel(cal.get(Calendar.DAY_OF_MONTH) + "", JLabel.CENTER);
                    if (cal.get(Calendar.MONTH) != month) {
                        label.setFont(MyTools.f1);
                    }
                    else {
                        if (cal.get(Calendar.MONTH) == month && cal.get(Calendar.DAY_OF_MONTH) == date) {
                            label.setForeground(Color.red);
                        }
                        label.setFont(MyTools.f3);
                    }
                    label.setBorder(BorderFactory.createDashedBorder(Color.black, 1.5f, 1f));
                    dateContainerPanel.add(label);
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        }
        return dateContainerPanel;
    }

    class nextMonth implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Calendar cal = new GregorianCalendar(year, month, 1);
            cal.add(Calendar.MONTH, 1);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            monthLabel.setText(year + "年 " + (month + 1) + "月");
            getPanel(cal);
        }
    }

    class previousMonth implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Calendar cal = new GregorianCalendar(year, month, 1);
            cal.add(Calendar.MONTH, -1);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            monthLabel.setText(year + "年 " + (month + 1) + "月");
            getPanel(cal);
        }
    }

    class nextYear implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Calendar cal = new GregorianCalendar(year, month, 1);
            cal.add(Calendar.YEAR, 1);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            monthLabel.setText(year + "年 " + (month + 1) + "月");
            getPanel(cal);
        }
    }

    class previousYear implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Calendar cal = new GregorianCalendar(year, month, 1);
            cal.add(Calendar.YEAR, -1);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            monthLabel.setText(year + "年 " + (month + 1) + "月");
            getPanel(cal);
        }
    }
}
