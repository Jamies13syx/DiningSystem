package View.MainView;

import Tools.ImagePanel;
import Tools.Miscs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import static Tools.MyTools.f1;

public class Window2 extends JFrame implements ActionListener, MouseListener {

    JPanel tableView, deskPanel;
    JLabel currentTime;
    Timer timer;
    JMenu view, database, tools;
    JMenuItem view_management, view_table, database_backup, database_quit, tools_calendar, tools_reporter;

    JMenuBar jMenuBar;

    Image titleIcon, time_background, table_background, desk, desk_occupied;
    boolean[] tableStatus;
    CardLayout cardLayout;

    String operator;
    public static void main(String[] args) {
        new Window2("karen");
    }

    public Window2(String operator){
        this.operator = operator;
        Container ct = this.getContentPane();
        ct.setLayout(new BorderLayout());


        cardLayout = new CardLayout();
        tableStatus = new boolean[1];

        jMenuBar = new JMenuBar();
        view = new JMenu("View");
        view.setFont(f1);
        view_table = new JMenuItem("Switch to table");
        view_table.addActionListener(e -> JOptionPane.showMessageDialog(null, "You already in table view"));

        view_management = new JMenuItem("Switch to management");
        view_management.addActionListener(e -> switchWindow());
        view.add(view_management);
        view.add(view_table);
        database = new JMenu("Database");
        database.setFont(f1);
        database_backup = new JMenuItem("Backup");
        database_backup.addActionListener(e -> Miscs.backupDatabase());
        database_quit = new JMenuItem("Quit program and database");
        database_quit.addActionListener(e -> Miscs.quitDatabase());
        database.add(database_backup);
        database.add(database_quit);
        tools = new JMenu("Tools");
        tools.setFont(f1);
        tools_calendar = new JMenuItem("Calendar");
        tools_calendar.addActionListener(e -> Miscs.showCalendar());
        tools_reporter = new JMenuItem("Daily reporter");
        tools_reporter.addActionListener(e -> Miscs.showReporter());
        tools.add(tools_calendar);
        tools.add(tools_reporter);

        jMenuBar.add(view);
        jMenuBar.add(database);
        jMenuBar.add(tools);
        this.setJMenuBar(jMenuBar);

        try{
            titleIcon = ImageIO.read(new File("src\\image\\window1\\title.gif"));
            time_background =  ImageIO.read(new File("src\\image\\window1\\time_bg.jpg"));
            table_background = ImageIO.read(new File("src\\image\\window2\\orderindex.jpg"));
            desk = ImageIO.read(new File("src\\image\\window2\\1.jpg"));
            desk_occupied = ImageIO.read(new File("src\\image\\window2\\1a.jpg"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        tableView = new ImagePanel(table_background);
//        tableView.add(new JLabel("TABLE BACKGROUND"));
        tableView.setLayout(null);
        deskPanel = new JPanel(cardLayout);
        deskPanel.add(new ImagePanel(desk), "vacant");
        deskPanel.add(new ImagePanel(desk_occupied), "occupied");

        deskPanel.setBounds(200, 200, 150, 110);
        deskPanel.addMouseListener(this);
        deskPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tableView.add(deskPanel);

        ImagePanel time_background_panel = new ImagePanel(time_background);
        currentTime = new JLabel("Current Time: " + Calendar.getInstance().getTime().toString());
        time_background_panel.add(currentTime, BorderLayout.CENTER);
        timer = new Timer(1000, this);// evert 1 sec activate action performed
        timer.start();

        ct.add(time_background_panel, BorderLayout.NORTH);
        ct.add(tableView);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setIconImage(titleIcon);
        this.setTitle("Wangzi Chinese Taco Dining System");
        this.setSize(520, 420);
        this.setLocation(width/2 - 200, height/2 - 200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == deskPanel){
            tableStatus[0] = !tableStatus[0];
            if(tableStatus[0]){
                cardLayout.show(deskPanel, "occupied");
            }
            else {
                cardLayout.show(deskPanel, "vacant");
            }
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
        this.currentTime.setText("Current Time: " +Calendar.getInstance().getTime().toString());
    }

    public void switchWindow(){
        this.dispose();
        new Window1(operator);
    }
}
