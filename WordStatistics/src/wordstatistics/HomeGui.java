package wordstatistics;

import java.util.ArrayList;
import java.awt.*;//component,container,window,frame
import javax.swing.*;//advanced gui ex=>jframe,jpanel,jbutton
import java.awt.event.MouseAdapter;//MouseListener and MouseMotionListener interfaces with default empty implementations for all methods. By extending MouseAdapter   
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;//tabular data add column or row and delete them 
import javax.swing.table.DefaultTableCellRenderer;//customize the cells of jtable ex=> allignment, background colors,fonts
import javax.swing.table.TableColumn;//adjust the properties of the column,such as width,height,resizing
import java.io.File;//represents path name to a file or directory,methods for querying file system paths and names
import java.awt.event.ActionEvent;//represents the event that ocuurs when an action is performed on a component
import java.awt.event.ActionListener;//handle action events
import java.util.concurrent.locks.ReentrantLock;//allowing exclusive access to a shared resource by multiple threads. 




public class HomeGui extends JFrame {
    
     static ReentrantLock lock =new ReentrantLock(); 
    
    
    // ----------------wrong screen------------------
    UIManager ui=new UIManager();//manages the look and feel of Swing components in a Swing-based GUI application
    //-----------variable------------------------
    int checkNameValue = 0;
    // -----screen size-----------------
    Toolkit t = Toolkit.getDefaultToolkit();// provides a common interface to access system-dependent resources like the screen size, images, fonts,
    int x = t.getScreenSize().width;
    int y = t.getScreenSize().height;

    //-----panels-------------
    JPanel background = new JPanel();// is a container that can hold other components like buttons, text fields, labels, etc

    //----textfields---------
    JTextField searchText = new JTextField("Enter your directory path");//This text field seems intended for users to input a directory path for searching files or directories. 

    //-----buttons-----------------------------
    JButton closeButton = new JButton("X");
    JButton searchButton =new JButton("Search");
    JButton  restore =new JButton("-");
    //-------------longest word & shortest word----------

    JLabel shortestWord = new JLabel("shortest word : ");//Swing component in Java used to display a single line of read-only text or an image. It's a non-interactive component
    JLabel longestWord =  new JLabel("Longest Word : ");
    JLabel numberOfWords = new JLabel("Number of all words : ");
    static JLabel acuallylongestWord = new JLabel("");
    static JLabel acuallyShortestWord = new JLabel("");
    static JLabel acuallyWord = new JLabel("");

    // ---------- The main table ( search table ) ------
    static DefaultTableModel searchModel = new DefaultTableModel();//It's used to manage the data displayed in a table,
    JTable searchTable = new JTable(searchModel){//This prevents users from modifying the table data directly by clicking and editing the cells.
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false; //Disallow the editing of any cell
        }
    };
    JScrollPane scrollTable = new JScrollPane(searchTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//allows scrolling veritical and horizontal
    //----------images-----------------------------------------------------
    ImageIcon image1=new ImageIcon(getClass().getResource("/Images/logo.jpeg"));
    Image imageSize;
    JLabel logo;
    //by loading an image file named "logo.jpeg" from a directory called "Images" in the project resources. After loading the image, it's scaled to a specific size using the Image class. Then, this image is added to a JLabel named logo.
    
    
    //-----check box-----------
    JCheckBox checkBox = new JCheckBox("Sub Directory");
    //---------calculate Sizes----------------------
    private int percentY(int percent)
    {
        return (int) (y*percent/70);
    }
    private int percentX(int percent)
    {
        return (int) (x*percent/100);
    }

    public HomeGui()
    {
        ShowIt();
    }
    public void ShowIt(){
        // ------the Frame option------------------------------------
        this.setUndecorated(true);//Removes the window's decoration, such as the title bar, borders, and buttons.
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);//Sets the window decoration style to none.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Configures the default close operation of the frame to exit the application when the frame is closed.
        this.setResizable(true);//Allows the frame to be resized by the user.
        this.setSize(x, y);
        this.setLayout(null);
        this.add(background);
        //-----background options-------------------------------
        background.setBackground(Color.decode("#F4F4F4"));
        background.setSize(x, y);
        background.setLayout(null);
        background.setVisible(true);
        background.add(searchButton);
        background.add(closeButton);
        background.add(scrollTable);
        background.add(longestWord);
        background.add(shortestWord);
        background.add(numberOfWords);
        background.add(acuallylongestWord);
        background.add(acuallyShortestWord);
        background.add(acuallyWord);
        background.add(checkBox);
        background.add(restore);
        //----------------logo-------------
        imageSize = image1.getImage();
        imageSize = imageSize.getScaledInstance(percentX(20), percentY(8), Image.SCALE_SMOOTH);
        image1 = new ImageIcon(imageSize);
        logo=new JLabel(image1);
        background.add(logo);
        logo.setBounds( 0,0,percentX(20),percentY(10));
        //---close button--------------------------------------
        closeButton.setBounds(percentX(97), 0, 50, 50);
        closeButton.setFont(new Font("", Font.BOLD, 20));
        closeButton.setForeground(Color.decode("#000000"));
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();//close the window
            }

            public void mouseEntered(MouseEvent e) {// It's triggered when the mouse cursor enters the area occupied by the component associated with the event listener.
                closeButton.setForeground(Color.decode("#00C3E3"));
                closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setContentAreaFilled(false);
                closeButton.setForeground(Color.decode("#1a1a1a"));
                closeButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        // ----------------restore button-----

        restore.setBounds(percentX(94),0,50,50);
        restore.setFont(new Font("", Font.BOLD, 40));
        restore.setForeground(Color.decode("#000000"));
        restore.setBorderPainted(false);
        restore.setContentAreaFilled(false);
        restore.setFocusPainted(false);

        restore.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                setState(ICONIFIED);
            }
        });
        restore.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                restore.setForeground(Color.decode("#00C3E3"));
                restore.setCursor(new Cursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                restore.setContentAreaFilled(false);
                restore.setForeground(Color.decode("#1a1a1a"));
                restore.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            }
        });

        //------------------------longest & shortest word labels & number of Words---------

        longestWord.setBounds(percentX(2),percentY(20),percentX(20),30);
        longestWord.setFont(new Font("", Font.CENTER_BASELINE, 25));
        shortestWord.setBounds(percentX(2),percentY(25),percentX(20),30);
        shortestWord.setFont(new Font("", Font.CENTER_BASELINE, 25));
        numberOfWords.setBounds(percentX(2),percentY(30),percentX(25),30);
        numberOfWords.setFont(new Font("",Font.CENTER_BASELINE,25));
        acuallylongestWord.setBounds(longestWord.getX()+183,percentY(20),percentX(50),30);
        acuallylongestWord.setFont(new Font("",Font.CENTER_BASELINE,25));
        acuallylongestWord.setForeground(Color.decode("#000000"));
        acuallyShortestWord.setBounds(shortestWord.getX()+183,percentY(25),percentX(20),30);
        acuallyShortestWord.setFont(new Font("",Font.CENTER_BASELINE,25));
        acuallyShortestWord.setForeground(Color.decode("#000000"));
        acuallyWord.setBounds(numberOfWords.getX()+260,percentY(30),percentX(20),30);
        acuallyWord.setFont(new Font("",Font.CENTER_BASELINE,25));
        acuallyWord.setForeground(Color.decode("#000000"));

        //-------search button------------------------------------------
        searchButton.setBounds((int) (x - percentX(48)),percentY(9), percentX(14), 50);
        searchButton.setFont(new Font("", Font.BOLD, 20));
        searchButton.setBackground(Color.decode("#000000"));
        searchButton.setForeground(Color.decode("#f2f2f2"));
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);

        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                searchButton.setForeground(Color.decode("#00C3E3"));
                searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                searchButton.setForeground(Color.decode("#f2f2f2"));
                searchButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                String directoryPath;
                boolean check;
                File dir = new File(searchText.getText());
                if (!dir.isDirectory()) {
                    ui.put("OptionPane.messageForeground", Color.decode("#000000"));
                    JOptionPane.showMessageDialog(null, "This is not directory \n please enter directory");
                } else {
                    directoryPath = searchText.getText();
                    check=checkBox.isSelected();
                    //--Call your function and send directoryPath and check to it
                    searchModel.setRowCount(0);
                    acuallylongestWord.setText("");
                    acuallyShortestWord.setText("");
                    acuallyWord.setText("");
                    Reading_Threads t=new Reading_Threads();
                    t.reading_threads(directoryPath, check);
                }
      
            }
        });





        //---------search text field------------------------------
        background.add(searchText);
        searchText.setBounds(percentX(2), percentY(9), percentX(48),50);
        searchText.setBackground(Color.decode("#00C3E3"));
        searchText.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.lightGray));
        searchText.setFont(new Font("", Font.CENTER_BASELINE, 20));
        searchText.setForeground(Color.white);
        searchText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkNameValue == 0) {
                    searchText.setText("");
                        searchText.setForeground(Color.decode("#1a1a1a"));
                    checkNameValue++;
                }
                searchText.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.black));
            }
        });
        //--------check box

        checkBox.setBounds(percentX(2), percentY(16),160,30);
        checkBox.setFont(new Font("", Font.CENTER_BASELINE, 20));
        checkBox.setFocusPainted(false);
        checkBox.setBackground(Color.decode("#00C3E3"));
        checkBox.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                checkBox.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });






        // ------------------------main table ( search table ) ----------------------


        //  ---------------scrollPane------------using in the main features of the whole table

        scrollTable.setBounds(percentX(2),percentY(35) ,  percentX(96) ,percentY(60));

        //------- model------- using in add - remove - change - get row or columns
        searchModel.addColumn("file Name");
        searchModel.addColumn("Directory Name");
        searchModel.addColumn("longest Word");
        searchModel.addColumn("Shortest Word");
        searchModel.addColumn("Is");
        searchModel.addColumn("ARE");
        searchModel.addColumn("YOU");
        searchModel.addColumn("words");


        //---------table class------------using in features of columns or Rows
        //searchTable.setCellSelectionEnabled(false);
        //searchTable.setDragEnabled(false)
        //searchTable.getCellEditor().stopCellEditing();
        searchTable.setRowHeight(50);

        searchTable.getTableHeader().setBackground(Color.decode("#00C3E3"));
        searchTable.getTableHeader().setForeground(Color.decode("#ffffff"));
        searchTable.getTableHeader().setFont(new Font("", Font.BOLD, 20));
        searchTable.setFont(new Font("", Font.BOLD, 18));
        TableColumn column = null;
        column = searchTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(percentX(21));
        column = searchTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(percentX(21));
        column = searchTable.getColumnModel().getColumn(2);
        column.setPreferredWidth(percentX(21));
        column = searchTable.getColumnModel().getColumn(3);
        column.setPreferredWidth(percentX(15));
        column = searchTable.getColumnModel().getColumn(4);
        column.setPreferredWidth(percentX(5));
        column = searchTable.getColumnModel().getColumn(5);
        column.setPreferredWidth(percentX(5));
        column = searchTable.getColumnModel().getColumn(6);
        column.setPreferredWidth(percentX(5));
        column = searchTable.getColumnModel().getColumn(7);
        column.setPreferredWidth(percentX(5));
        searchTable.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for ( int i = 0 ; i  < 8 ; i++ )
            searchTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);




        //-----visible JFrame----------
        this.setVisible(true);
    }

    //---codingteam-------
    //-- call this function while getting information of your file (this is static function)
    //-note---
    //-- this is critical section
    // there shouldn’t be two thread updating the same file name at the same time
     
    public static void update(ArrayList<String> answer)
    {
        lock.lock();
        int row,column;int sum=0;
        for(row=0;row<searchModel.getRowCount();row++)
        {
            if(answer.get(0).equals(searchModel.getValueAt(row,0))&&answer.get(1).equals(searchModel.getValueAt(row,1).toString()) )
            {
  
                break;
            }
        }
        if(row!=searchModel.getRowCount())
        {
            searchModel.setValueAt(answer.get(2),row,2);
            searchModel.setValueAt(answer.get(3),row,3);
            for(column=4;column<searchModel.getColumnCount();column++)
            {
                    sum  = Integer.parseInt(answer.get(column))+Integer.parseInt( searchModel.getValueAt(row,column).toString());
                    searchModel.setValueAt(sum,row,column);
                
            }
        }
        if(row==searchModel.getRowCount())
            searchModel.addRow(answer.toArray());
        searchModel.fireTableDataChanged();
        if(acuallylongestWord.getText().length()<answer.get(2).length())
            acuallylongestWord.setText(answer.get(2));
        if(acuallyShortestWord.getText().length()==0)
            acuallyShortestWord.setText(answer.get(3));
        else if(acuallyShortestWord.getText().length()>answer.get(3).length())
            acuallyShortestWord.setText(answer.get(3));
        int x;
        if(acuallyWord.getText().length()==0)
            x=Integer.parseInt(answer.get(7));
        else
            x=Integer.parseInt(acuallyWord.getText())+Integer.parseInt(answer.get(7));
        acuallyWord.setText(Integer.toString(x));
        lock.unlock();
    }
}
