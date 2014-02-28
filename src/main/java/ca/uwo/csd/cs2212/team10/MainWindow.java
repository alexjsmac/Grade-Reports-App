package ca.uwo.csd.cs2212.team10;

import java.io.*;
import java.awt.*;
import javax.swing.*; 

/**
 * The main window of the gradebook program
 * @author Team 10
 */
public class MainWindow extends JFrame {
    
    /* Constants */
    private final String DATA_FILENAME = "gradebook.dat";
    private final String BACKUP_FILENAME = "gradebook.dat.bak";
    
    /* Attributes */
    private Gradebook gradebook;
    
    /* Main method */
    public MainWindow(){
        initComponents();
    }
    
    /* Private methods */
    
        @SuppressWarnings("unchecked")                     
    private void initComponents() {
        
        gradebook = new Gradebook();

        addDialog = new javax.swing.JDialog();
        addTitleLabel = new javax.swing.JLabel();
        addOkBtn = new javax.swing.JButton();
        addCancelBtn = new javax.swing.JButton();
        addCodeLabel = new javax.swing.JLabel();
        addTermLabel = new javax.swing.JLabel();
        addTitleTxtField = new javax.swing.JTextField();
        addWindowLabel = new javax.swing.JLabel();
        addCodeTxtField = new javax.swing.JTextField();
        addTermTxtField = new javax.swing.JTextField();
        editDialog = new javax.swing.JDialog();
        editTitleLabel = new javax.swing.JLabel();
        editOkBtn = new javax.swing.JButton();
        editCancelBtn = new javax.swing.JButton();
        editCodeLabel = new javax.swing.JLabel();
        editTermLabel = new javax.swing.JLabel();
        editTitleTxtField = new javax.swing.JTextField();
        editLabel = new javax.swing.JLabel();
        editCodeTxtField = new javax.swing.JTextField();
        editTermTxtField = new javax.swing.JTextField();
        delDialog = new javax.swing.JDialog();
        delLabel = new javax.swing.JLabel();
        delOkBtn = new javax.swing.JButton();
        delCancelBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        coursesTbl = new javax.swing.JTable();
        dropDownCourses = new javax.swing.JComboBox();
        addCourseBtn = new javax.swing.JButton();
        editCourseBtn = new javax.swing.JButton();
        delCourseBtn = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        coursesMenu = new javax.swing.JMenu();
        addMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenuItem();
        delMenuItem = new javax.swing.JMenuItem();

        addDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addDialog.setTitle("Add Course");
        addDialog.setAlwaysOnTop(true);
        addDialog.setBounds(new java.awt.Rectangle(0, 0, 350, 250));

        addTitleLabel.setText("Name:");

        addOkBtn.setText("OK");
        addOkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOkBtnActionPerformed(evt);
            }
        });

        addCancelBtn.setText("Cancel");
        addCancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCancelBtnActionPerformed(evt);
            }
        });

        addCodeLabel.setText("Code:");

        addTermLabel.setText("Term:");

        addWindowLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        addWindowLabel.setText("Add a Course");

        javax.swing.GroupLayout addDialogLayout = new javax.swing.GroupLayout(addDialog.getContentPane());
        addDialog.getContentPane().setLayout(addDialogLayout);
        addDialogLayout.setHorizontalGroup(
            addDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addDialogLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(addDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addDialogLayout.createSequentialGroup()
                        .addGroup(addDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addTitleLabel)
                            .addComponent(addCodeLabel)
                            .addComponent(addTermLabel))
                        .addGap(49, 49, 49)
                        .addGroup(addDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addTitleTxtField)
                            .addComponent(addCodeTxtField)
                            .addComponent(addTermTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(addDialogLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(addOkBtn)
                        .addGap(54, 54, 54)
                        .addComponent(addCancelBtn))
                    .addGroup(addDialogLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(addWindowLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        addDialogLayout.setVerticalGroup(
            addDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addWindowLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(addDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addTitleTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addTitleLabel))
                .addGap(18, 18, 18)
                .addGroup(addDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCodeTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCodeLabel))
                .addGap(18, 18, 18)
                .addGroup(addDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addTermTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addTermLabel))
                .addGap(18, 18, 18)
                .addGroup(addDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addOkBtn)
                    .addComponent(addCancelBtn))
                .addContainerGap())
        );

        editDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        editDialog.setTitle("Edit Course");
        editDialog.setAlwaysOnTop(true);
        editDialog.setBounds(new java.awt.Rectangle(0, 0, 350, 250));

        editTitleLabel.setText("Name:");

        editOkBtn.setText("OK");
        editOkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editOkBtnActionPerformed(evt);
            }
        });

        editCancelBtn.setText("Cancel");
        editCancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCancelBtnActionPerformed(evt);
            }
        });

        editCodeLabel.setText("Code:");

        editTermLabel.setText("Term:");

        editLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        editLabel.setText("Edit Course");

        javax.swing.GroupLayout editDialogLayout = new javax.swing.GroupLayout(editDialog.getContentPane());
        editDialog.getContentPane().setLayout(editDialogLayout);
        editDialogLayout.setHorizontalGroup(
            editDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editDialogLayout.createSequentialGroup()
                .addGroup(editDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editDialogLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(editDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editDialogLayout.createSequentialGroup()
                                .addGroup(editDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(editTitleLabel)
                                    .addComponent(editCodeLabel)
                                    .addComponent(editTermLabel))
                                .addGap(49, 49, 49)
                                .addGroup(editDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(editTitleTxtField)
                                    .addComponent(editCodeTxtField)
                                    .addComponent(editTermTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(editDialogLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(editOkBtn)
                                .addGap(54, 54, 54)
                                .addComponent(editCancelBtn))))
                    .addGroup(editDialogLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(editLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        editDialogLayout.setVerticalGroup(
            editDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(editLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(editDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editTitleTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editTitleLabel))
                .addGap(18, 18, 18)
                .addGroup(editDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editCodeTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editCodeLabel))
                .addGap(18, 18, 18)
                .addGroup(editDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editTermTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editTermLabel))
                .addGap(18, 18, 18)
                .addGroup(editDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editOkBtn)
                    .addComponent(editCancelBtn))
                .addContainerGap())
        );

        delDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        delDialog.setTitle("Delete Course");
        delDialog.setAlwaysOnTop(true);
        delDialog.setBounds(new java.awt.Rectangle(0, 0, 250, 150));

        delLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        delLabel.setText("Are you sure?");

        delOkBtn.setText("Yes");
        delOkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delOkBtnActionPerformed(evt);
            }
        });

        delCancelBtn.setText("Cancel");
        delCancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delCancelBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout delDialogLayout = new javax.swing.GroupLayout(delDialog.getContentPane());
        delDialog.getContentPane().setLayout(delDialogLayout);
        delDialogLayout.setHorizontalGroup(
            delDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(delDialogLayout.createSequentialGroup()
                .addGroup(delDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(delDialogLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(delLabel)
                        .addGap(0, 54, Short.MAX_VALUE))
                    .addGroup(delDialogLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(delOkBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(delCancelBtn)))
                .addContainerGap())
        );
        delDialogLayout.setVerticalGroup(
            delDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(delDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(delLabel)
                .addGap(18, 18, 18)
                .addGroup(delDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delOkBtn)
                    .addComponent(delCancelBtn))
                .addContainerGap(121, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gradebook");

        coursesTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"exampl",  new Integer(12),  new Float(0.3),  new Float(0.9)},
                {"exampl",  new Integer(13),  new Float(1.2),  new Float(0.4)},
                {"another",  new Integer(44),  new Float(2.3),  new Float(6.0)},
                {"exam",  new Integer(5),  new Float(4.5),  new Float(0.7)}
            },
            new String [] {
                "Name", "Number", "Average", "Deliverable"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(coursesTbl);

        dropDownCourses.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        dropDownCourses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropDownCoursesActionPerformed(evt);
            }
        });

        addCourseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/new.png"))); // NOI18N
        addCourseBtn.setMnemonic(java.awt.event.KeyEvent.VK_N);
        addCourseBtn.setToolTipText("Add a new Course (Alt+N)");
        addCourseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCourseBtnActionPerformed(evt);
            }
        });

        editCourseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        editCourseBtn.setMnemonic(java.awt.event.KeyEvent.VK_E);
        editCourseBtn.setToolTipText("Edit Selected Course (Alt+E)");
        editCourseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCourseBtnActionPerformed(evt);
            }
        });

        delCourseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/del.png"))); // NOI18N
        delCourseBtn.setMnemonic(java.awt.event.KeyEvent.VK_D);
        delCourseBtn.setToolTipText("Delete Selected Course (Alt+D)");
        delCourseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delCourseBtnActionPerformed(evt);
            }
        });

        fileMenu.setMnemonic(java.awt.event.KeyEvent.VK_F);
        fileMenu.setText("File");

        exitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exit.png"))); // NOI18N
        exitMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_E);
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar.add(fileMenu);

        coursesMenu.setMnemonic(java.awt.event.KeyEvent.VK_C);
        coursesMenu.setText("Courses");

        addMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/new.png"))); // NOI18N
        addMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_A);
        addMenuItem.setText("Add Course");
        addMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMenuItemActionPerformed(evt);
            }
        });
        coursesMenu.add(addMenuItem);

        editMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        editMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_E);
        editMenuItem.setText("Edit Course");
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        coursesMenu.add(editMenuItem);

        delMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/del.png"))); // NOI18N
        delMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_D);
        delMenuItem.setText("Delete Course");
        delMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delMenuItemActionPerformed(evt);
            }
        });
        coursesMenu.add(delMenuItem);

        jMenuBar.add(coursesMenu);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addCourseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editCourseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delCourseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(dropDownCourses, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dropDownCourses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCourseBtn)
                    .addComponent(editCourseBtn)
                    .addComponent(delCourseBtn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }                     

    // TODO
    private void dropDownCoursesActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO Change active course based on wich course is selected on the DropDown list
    }                                               

    private void addCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {                                             
        addCodeTxtField.setText(null);
        addTitleTxtField.setText(null);
        addTermTxtField.setText(null);
        addDialog.setVisible(true);
    }                                            

    // TODO Not Catching Exception
    private void addOkBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String title = null;
        String code = null;
        String term = null;
        
        //TODO Not catching exception
        try {
            title = addTitleTxtField.getText();
        } 
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Bad Title");
        }
        try {
            code = addCodeTxtField.getText();
        } 
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Bad code");
        }
        try {
            term = addTermTxtField.getText();
        } 
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Bad Term");
        }
        
        //Create a new Course and adds it to the gradebook
        Course course = new Course(title, code, term);
        gradebook.addCourse(course);
        
        //Close Dialog
        addDialog.setVisible(false);
    }                                        

    private void addCancelBtnActionPerformed(java.awt.event.ActionEvent evt) {                                             
        addDialog.setVisible(false);
    }                                            

    //TODO
    private void editOkBtnActionPerformed(java.awt.event.ActionEvent evt) {                                          
        //TODO Get the Code, Term and Title that changed
        
        //TODO Change attributes of the active course
                
        editDialog.setVisible(false);
    }                                         

    private void editCancelBtnActionPerformed(java.awt.event.ActionEvent evt) {                                              
        editDialog.setVisible(false);
    }                                             

    //TODO
    private void editCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {                                              
        
        // TODO What if there's no active course?
        //Course editCourse = gradebook.getActiveCourse();
        Course editCourse = new Course("Title", "Code", "Term");  
        
        editCodeTxtField.setText(editCourse.getCode());
        editTitleTxtField.setText(editCourse.getTitle());
        editTermTxtField.setText(editCourse.getTerm());
        editDialog.setVisible(true);
    }                                             

    //TODO
    private void delOkBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //TODO remove active Course and changes active course to ?
        
        delDialog.setVisible(false);
    }                                        

    private void delCancelBtnActionPerformed(java.awt.event.ActionEvent evt) {                                             
        delDialog.setVisible(false);
    }                                            

    private void delCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {                                             
        delDialog.setVisible(true);
    }                                            

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        System.exit(0);
    }                                            

    private void addMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                            
        addCodeTxtField.setText(null);
        addTitleTxtField.setText(null);
        addTermTxtField.setText(null);
        addDialog.setVisible(true);
    }                                           

    //TODO
    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO What if there's no active course?
        //Course editCourse = gradebook.getActiveCourse();
        Course editCourse = new Course("Title", "Code", "Term");  
        
        editCodeTxtField.setText(editCourse.getCode());
        editTitleTxtField.setText(editCourse.getTitle());
        editTermTxtField.setText(editCourse.getTerm());
        editDialog.setVisible(true);
    }                                            

    private void delMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                            
        delDialog.setVisible(true);
    }                                           
        
    private void loadGradebook(){
        ObjectInputStream in = null;
        
        try{ 
            //attempt reading from data file
            in = new ObjectInputStream(new FileInputStream(DATA_FILENAME));
            Gradebook gradebook = (Gradebook)in.readObject();
        } catch (Exception e1){
            //if that didn't work...
            try{ 
                //attempt reading from backup data file
                in = new ObjectInputStream(new FileInputStream(BACKUP_FILENAME));
                Gradebook gradebook = (Gradebook)in.readObject();
                
                //TODO: display message saying data was read from backup?
            } catch (Exception e2){
                //TODO: display message saying data couldn't be read? What about first start?
            }
        } 
        finally{
            try{
                in.close(); //clean up
            } catch (Exception e){ }
        }
    }
    
    private void storeGradebook(){
        //make a backup first
        try{
            File dataFile = new File(DATA_FILENAME);
            File backupFile = new File(BACKUP_FILENAME);
            
            backupFile.delete(); //delete the backup first
            dataFile.renameTo(backupFile); //then make a new one
        } catch (Exception e) { }
        
        //store the data
        ObjectOutputStream out = null;
        
        try{
            out = new ObjectOutputStream(new FileOutputStream(DATA_FILENAME));
            out.writeObject(gradebook);
        } catch (FileNotFoundException e){
            //TODO: message: data file could not be created
        } catch (IOException e){
            //TODO: message: error writing data file
        } catch (Exception e){ }
        finally{
            try{
                out.close(); //clean up
            } catch (Exception e){ }
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton addCancelBtn;
    private javax.swing.JLabel addCodeLabel;
    private javax.swing.JTextField addCodeTxtField;
    private javax.swing.JButton addCourseBtn;
    private javax.swing.JDialog addDialog;
    private javax.swing.JMenuItem addMenuItem;
    private javax.swing.JLabel addTitleLabel;
    private javax.swing.JTextField addTitleTxtField;
    private javax.swing.JButton addOkBtn;
    private javax.swing.JLabel addTermLabel;
    private javax.swing.JTextField addTermTxtField;
    private javax.swing.JLabel addWindowLabel;
    private javax.swing.JMenu coursesMenu;
    private javax.swing.JTable coursesTbl;
    private javax.swing.JButton delCancelBtn;
    private javax.swing.JButton delCourseBtn;
    private javax.swing.JDialog delDialog;
    private javax.swing.JLabel delLabel;
    private javax.swing.JMenuItem delMenuItem;
    private javax.swing.JButton delOkBtn;
    private javax.swing.JComboBox dropDownCourses;
    private javax.swing.JButton editCancelBtn;
    private javax.swing.JLabel editCodeLabel;
    private javax.swing.JTextField editCodeTxtField;
    private javax.swing.JButton editCourseBtn;
    private javax.swing.JDialog editDialog;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.JLabel editTitleLabel;
    private javax.swing.JTextField editTitleTxtField;
    private javax.swing.JButton editOkBtn;
    private javax.swing.JLabel editTermLabel;
    private javax.swing.JTextField editTermTxtField;
    private javax.swing.JLabel editLabel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration     
}
