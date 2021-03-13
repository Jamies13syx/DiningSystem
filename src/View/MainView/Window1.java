package View.MainView;

import Tools.ImagePanel;
import Tools.Miscs;
import View.Client.ClientInfo;
import View.DishMenu.DishMenuInfo;
import View.LoginAccess.LoginInfo;
import View.Orders.OrdersInfo;
import View.Register.EmpInfo;
import View.Sales.SalesInfo;
import View.Takeout.TakeoutInfo;

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
import javax.swing.Timer;

import static Tools.MyTools.f1;

/**
 * This window view is available to admin/manager
 */

public class Window1 extends JFrame implements ActionListener, MouseListener {

    Image titleIcon, time_background;
    JMenuBar jMenuBar;
    JMenu view, database, tools;
    JMenuItem view_management, view_table, database_backup, database_quit, tools_calendar, tools_reporter;

    JToolBar jToolBar;
    JButton jb1, jb2, jb3, jb4, jb5, jb6, jb7, jb8, jb9, jb10;// tool bar icon
    /**
     * splitpane - main panel
     * jp1 - left selection panel /sub main panel
     * jp2 - slide bar
     * jp3 - sub content card panel
     * jp4 - container contains jp2 & jp3
     * jp5 - state panel show datetime
     */
    JPanel jp1, jp2, jp3, jp4, jp5;
    JLabel jp1_label_1;
    JLabel jp1_label_2;
    JLabel jp1_label_3;
    JLabel jp1_label_4;
    JLabel jp1_label_5;
    JLabel jp1_label_6;
    JLabel jp1_label_7;
    JLabel jp1_label_8;
    
    JLabel currentTime;
    Timer timer;

    CardLayout cardLayout_jp3;
    CardLayout cardLayout_jp2;

    JLabel left_arrow_jp2;
    JLabel right_arrow_jp2;


    Image background_p1;
    Image main_view_image_jp3;

    JSplitPane jSplitPane;

    String operator;


    public static void main(String[] args) {
        new Window1("karen");

    }

    public Window1(String operator){
        this.operator = operator;
        try{
            titleIcon = ImageIO.read(new File("src\\image\\window1\\title.gif"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //initialize menu bar
        jMenuBar = new JMenuBar();

        view = new JMenu("View");
        view.setFont(f1);
        view_management = new JMenuItem("Switch to management");
        view_management.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You already in management view");
            }
        });
        view_table = new JMenuItem("Switch to table");
        view_table.addActionListener(e -> switchWindow(operator));
        view.add(view_management);
        view.add(view_table);
        database = new JMenu("Database");
        database.setFont(f1);
        database_backup = new JMenuItem("Backup Database");
        database_backup.addActionListener(e -> Miscs.backupDatabase());
        database_quit = new JMenuItem("Quit program database");
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

        Container ct = this.getContentPane();

        jMenuBar.add(view);
        jMenuBar.add(database);
        jMenuBar.add(tools);

        this.setJMenuBar(jMenuBar);


        jToolBar = new JToolBar();
        jb1 = new JButton(new ImageIcon("src\\image\\toolbar\\jb1.jpg"));
        jb1.addMouseListener(this);
        //link to reg
        jb2 = new JButton(new ImageIcon("src\\image\\toolbar\\jb2.jpg"));
        jb2.addMouseListener(this);
        //link to login
        jb3 = new JButton(new ImageIcon("src\\image\\toolbar\\jb3.jpg"));
        jb3.addMouseListener(this);
        //link to dish_menu
        jb4 = new JButton(new ImageIcon("src\\image\\toolbar\\jb4.jpg"));
        jb4.addMouseListener(this);
        //link to client
        jb5 = new JButton(new ImageIcon("src\\image\\toolbar\\jb5.jpg"));
        jb5.addMouseListener(this);
        //link to order
        jb6 = new JButton(new ImageIcon("src\\image\\toolbar\\jb6.jpg"));
        jb6.addMouseListener(this);
        //link to takeout
        jb7 = new JButton(new ImageIcon("src\\image\\toolbar\\jb7.jpg"));
        jb7.addMouseListener(this);
        //link to sales

        jToolBar.add(jb1);
        jToolBar.add(jb2);
        jToolBar.add(jb3);
        jToolBar.add(jb4);
        jToolBar.add(jb5);
        jToolBar.add(jb6);
        jToolBar.add(jb7);
        jToolBar.setFloatable(false);
        ct.add(jToolBar, BorderLayout.NORTH);
        //--------------------------------------------------


        //------------------------------------------------
        // sub main panel jp1 part
        jp1 = new JPanel(new BorderLayout());//main panel
        try {
            background_p1 = ImageIO.read(new File("src\\image\\window1\\jp1_bg.jpg"));
            //default background pic
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImagePanel imagePanel_jp1 = new ImagePanel(background_p1);
        imagePanel_jp1.setLayout(new GridLayout(8, 1));
        //setting background panel

        jp1_label_1 = new JLabel(new ImageIcon("src\\image\\window1\\label_1.gif"));
        imagePanel_jp1.add(jp1_label_1);
        jp1_label_1.addMouseListener(this);
        //brand logo label
        
        jp1_label_2 = new JLabel("Registration ", new ImageIcon("src\\image\\window1\\label_2.jpg"), SwingConstants.CENTER);
        imagePanel_jp1.add(jp1_label_2);
        jp1_label_2.setEnabled(false);
        jp1_label_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jp1_label_2.addMouseListener(this);
        // registration label

        jp1_label_3 = new JLabel("Login Access ", new ImageIcon("src\\image\\window1\\label_3.jpg"), SwingConstants.CENTER);
        imagePanel_jp1.add(jp1_label_3);
        jp1_label_3.setEnabled(false);
        jp1_label_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jp1_label_3.addMouseListener(this);
        // Login label

        jp1_label_4 = new JLabel("Dish Menu ", new ImageIcon("src\\image\\window1\\label_4.jpg"), SwingConstants.CENTER);
        imagePanel_jp1.add(jp1_label_4);
        jp1_label_4.setEnabled(false);
        jp1_label_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jp1_label_4.addMouseListener(this);
        // dish label

        jp1_label_5 = new JLabel("Client", new ImageIcon("src\\image\\window1\\label_5.jpg"),SwingConstants.CENTER);
        imagePanel_jp1.add(jp1_label_5);
        jp1_label_5.setEnabled(false);
        jp1_label_5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jp1_label_5.addMouseListener(this);
        // client label

        jp1_label_6 = new JLabel("Order", new ImageIcon("src\\image\\window1\\label_6.jpg"),SwingConstants.CENTER);
        imagePanel_jp1.add(jp1_label_6);
        jp1_label_6.setEnabled(false);
        jp1_label_6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jp1_label_6.addMouseListener(this);
        // order label

        jp1_label_7 = new JLabel("Takeout", new ImageIcon("src\\image\\window1\\label_7.jpg"),SwingConstants.CENTER);
        imagePanel_jp1.add(jp1_label_7);
        jp1_label_7.setEnabled(false);
        jp1_label_7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jp1_label_7.addMouseListener(this);
        // takeout label

        jp1_label_8 = new JLabel("Sales", new ImageIcon("src\\image\\window1\\label_8.jpg"),SwingConstants.CENTER);
        imagePanel_jp1.add(jp1_label_8);
        jp1_label_8.setEnabled(false);
        jp1_label_8.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jp1_label_8.addMouseListener(this);
        // sales label

        //-----------------------------------------------
        //jp4 part contains jp2(slide bar card) and jp3 (card layer)
        cardLayout_jp2 = new CardLayout();
        cardLayout_jp3 = new CardLayout();

        jp4 = new JPanel(new BorderLayout());
        // initializing container

        jp2 = new JPanel(cardLayout_jp2);//slide bar
        left_arrow_jp2 = new JLabel(new ImageIcon("src\\image\\window1\\jp2_left.jpg"));
        left_arrow_jp2.addMouseListener(this);
        right_arrow_jp2 = new JLabel(new ImageIcon("src\\image\\window1\\jp2_right.jpg"));
        right_arrow_jp2.addMouseListener(this);
        jp2.add(left_arrow_jp2, "left");
        jp2.add(right_arrow_jp2, "right");

        jp3 = new JPanel(cardLayout_jp3);//card panel
        try {
            main_view_image_jp3 = ImageIO.read(new File("src\\image\\window1\\jp3_main_view.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DefaultWelcomePanel imagePanel_main_view_jp3 = new DefaultWelcomePanel(main_view_image_jp3);
        imagePanel_main_view_jp3.setUserName(operator);
        jp3.add(imagePanel_main_view_jp3, "main");//default welcome panel

        EmpInfo empInfo = new EmpInfo(this);
        jp3.add(empInfo, "reg");

        LoginInfo loginInfo = new LoginInfo(this);
        jp3.add(loginInfo, "login");

        DishMenuInfo dishMenuInfo = new DishMenuInfo(this);
        jp3.add(dishMenuInfo, "dish");

        ClientInfo clientInfo = new ClientInfo(this);
        jp3.add(clientInfo, "client");

        OrdersInfo ordersInfo = new OrdersInfo(this);
        jp3.add(ordersInfo, "order");

        TakeoutInfo takeoutInfo = new TakeoutInfo(this);
        jp3.add(takeoutInfo, "takeout");

        SalesInfo salesInfo = new SalesInfo(this);
        jp3.add(salesInfo, "sales");

        jp4.add(jp2, "West");
        jp4.add(jp3, "Center");

        jp1.add(imagePanel_jp1);

        //-----------------------------------------------
        // set split pane
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jp1, jp4);
        jSplitPane.setDividerLocation(140);
        jSplitPane.setDividerSize(0);
        ct.add(jSplitPane);// add to big container
        //center part finished

        //-----------------------------------------------
        // time panel
        jp5 = new JPanel(new BorderLayout());
        try {
            time_background =  ImageIO.read(new File("src\\image\\window1\\time_bg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImagePanel time_background_panel = new ImagePanel(time_background);
        time_background_panel.setLayout(new BorderLayout());
        currentTime = new JLabel("Current Time: " + Calendar.getInstance().getTime().toString());
        time_background_panel.add(currentTime, "East");

        jp5.add(time_background_panel);
        ct.add(jp5, "South");//state panel on the south side

        timer = new Timer(1000, this);// evert 1 sec activate action performed
        timer.start();




        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        this.setIconImage(titleIcon);
        this.setTitle("Wangzi Chinese Taco Dining System");
        this.setSize(width - 100, height - 100);
        this.setLocation(50, 50);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.currentTime.setText("Current Time: " +Calendar.getInstance().getTime().toString());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == this.jp1_label_1){
            cardLayout_jp3.show(jp3, "main");
        }

        if(e.getSource() == this.jp1_label_2 || e.getSource() == this.jb1){
            cardLayout_jp3.show(jp3, "reg");
        }
        if(e.getSource() == this.jp1_label_3 || e.getSource() == this.jb2){
            cardLayout_jp3.show(jp3, "login");
        }
        if(e.getSource() == this.jp1_label_4 || e.getSource() == this.jb3){
            cardLayout_jp3.show(jp3, "dish");
        }
        if(e.getSource() == this.jp1_label_5 || e.getSource() == this.jb4){
            cardLayout_jp3.show(jp3, "client");
        }
        if(e.getSource() == this.jp1_label_6 || e.getSource() == this.jb5){
            cardLayout_jp3.show(jp3, "order");
        }
        if(e.getSource() == this.jp1_label_7 || e.getSource() == this.jb6){
            cardLayout_jp3.show(jp3, "takeout");
        }
        if(e.getSource() == this.jp1_label_8 || e.getSource() == this.jb7){
            cardLayout_jp3.show(jp3, "sales");
        }


        if(e.getSource() == this.left_arrow_jp2){
            cardLayout_jp2.show(jp2, "right");
            this.jSplitPane.setDividerLocation(0);

        }
        if(e.getSource() == this.right_arrow_jp2){
            cardLayout_jp2.show(jp2, "left");
            this.jSplitPane.setDividerLocation(140);
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
        if(e.getSource() == jp1_label_2){
            jp1_label_2.setEnabled(true);
        }
        if(e.getSource() == jp1_label_3){
            jp1_label_3.setEnabled(true);
        }
        if(e.getSource() == jp1_label_4){
            jp1_label_4.setEnabled(true);
        }
        if(e.getSource() == jp1_label_5){
            jp1_label_5.setEnabled(true);
        }
        if(e.getSource() == jp1_label_6){
            jp1_label_6.setEnabled(true);
        }
        if(e.getSource() == jp1_label_7){
            jp1_label_7.setEnabled(true);
        }
        if(e.getSource() == jp1_label_8){
            jp1_label_8.setEnabled(true);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == jp1_label_2){
            jp1_label_2.setEnabled(false);
        }
        if(e.getSource() == jp1_label_3){
            jp1_label_3.setEnabled(false);
        }
        if(e.getSource() == jp1_label_4){
            jp1_label_4.setEnabled(false);
        }
        if(e.getSource() == jp1_label_5){
            jp1_label_5.setEnabled(false);
        }
        if(e.getSource() == jp1_label_6){
            jp1_label_6.setEnabled(false);
        }
        if(e.getSource() == jp1_label_7){
            jp1_label_7.setEnabled(false);
        }
        if(e.getSource() == jp1_label_8){
            jp1_label_8.setEnabled(false);
        }
    }

    public void switchWindow(String operator){
        this.dispose();
        new Window2(operator);
    }
}

class DefaultWelcomePanel extends ImagePanel {
    private String userName;

    public DefaultWelcomePanel(Image image) {
        super(image);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLUE);
        this.setFont(new Font("华文新魏", Font.BOLD, 50));
        g.drawString("Welcome to manage system " + userName, this.getX() + 150, this.getY() + 130);

    }
}
