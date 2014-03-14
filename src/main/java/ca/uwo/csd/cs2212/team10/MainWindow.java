package ca.uwo.csd.cs2212.team10;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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
    
    private JScrollPane jScrollPane1;
    private JTable studentsTbl;
    private JComboBox dropDownCourses;
    private JButton addCourseBtn, editCourseBtn, delCourseBtn;
    private JPopupMenu studentTblPopup, deliverableTblPopup;
    private JMenuItem addStudentPopupMenu, editStudentPopupMenu, delStudentPopupMenu, 
            addDeliverablePopupMenu, editDeliverablePopupMenu, delDeliverablePopupMenu;
    private JMenuBar jMenuBar;
    private JMenu fileMenu, coursesMenu, studentsMenu, deliverablesMenu;
    private JMenuItem exitMenuItem, addMenuItem, editMenuItem, delMenuItem,
            addStudentMenuItem, editStudentMenuItem, delStudentMenuItem,
            addDeliverableMenuItem, editDeliverableMenuItem, delDeliverableMenuItem;

    /* Constructor */
    public MainWindow() {
        gradebook = loadGradebook();
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initTable();
        setVisible(true);
    }

    /* Private methods */
    private void initTable() {
        refreshTableModel();

        //Set custom Cell Editor for Columns with type Double (Correspond to the cells with the grades)
        studentsTbl.setDefaultEditor(Double.class, new DoubleCellEditor());

        studentsTbl.setAutoCreateRowSorter(true);
        studentsTbl.setCellSelectionEnabled(true);
        studentsTbl.setGridColor(Color.gray);
        studentsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        //Set MinWidth for the first Column
        studentsTbl.getColumnModel().getColumn(0).setMinWidth(200);
        //Set width for the rest of the columns
        for (int i = 1; i < studentsTbl.getModel().getColumnCount(); i++)
            studentsTbl.getColumnModel().getColumn(i).setMinWidth(120);
        
        studentsTbl.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "startEditing");
        studentsTbl.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "deleteStudent");
        studentsTbl.getActionMap().put("deleteStudent", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                if (studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn()) > 4) {
                    delDeliverableAction();
                } else {
                    delStudentAction();
                }
            }
        });

        studentsTbl.addMouseListener(new MouseAdapter() {

            //Mouse Listener for Linux/Mac
            @Override
            public void mousePressed(MouseEvent e) {
                studentsTbl.clearSelection();
                int row = studentsTbl.rowAtPoint(e.getPoint());
                int column = studentsTbl.columnAtPoint(e.getPoint());
                if (row >= 0 && row < studentsTbl.getRowCount()) {
                    studentsTbl.changeSelection(row, column, false, false);
                } else {
                    studentsTbl.clearSelection();
                }

                int rowindex = studentsTbl.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }

                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    if (studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn()) == 0) {
                        studentTblPopup.show(e.getComponent(), e.getX(), e.getY());
                    } else if ((studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn()) > 0)
                            && (studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn()) <= (studentsTbl.getModel().getColumnCount() - 3))) {

                        deliverableTblPopup.show(e.getComponent(), e.getX(), e.getY());
                    } else {
                        return;
                    }

                }
            }

        //Mouse LIstener fr Windows
        @Override
        public void mouseReleased(MouseEvent e) {
            studentsTbl.clearSelection();
            int row = studentsTbl.rowAtPoint(e.getPoint());
            int column = studentsTbl.columnAtPoint(e.getPoint());
            if (row >= 0 && row < studentsTbl.getRowCount()) {
                studentsTbl.changeSelection(row, column, false, false);
            } else {
                studentsTbl.clearSelection();
            }

            int rowindex = studentsTbl.getSelectedRow();
            if (rowindex < 0) {
                return;
            }

            if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                if (studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn()) == 0) {
                    studentTblPopup.show(e.getComponent(), e.getX(), e.getY());
                } else if ((studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn()) > 0)
                        && (studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn()) <= (studentsTbl.getModel().getColumnCount() - 3))) {

                    deliverableTblPopup.show(e.getComponent(), e.getX(), e.getY());
                } else {
                    return;
                }

            }
        }
        });
    }

    private void refreshTableModel() {
        List<Student> studentsList;
        List<Deliverable> deliverablesList;

        //Get Students and Deliverables list. Creates empty lists if they don't exist
        if (gradebook.getActiveCourse() != null) {
            studentsList = gradebook.getActiveCourse().getStudentList();
            deliverablesList = gradebook.getActiveCourse().getDeliverableList();
        } else {
            deliverablesList = new ArrayList<Deliverable>();
            studentsList = new ArrayList<Student>();
        }

        TableModel tblModel = new TableModel(studentsList, deliverablesList);
        studentsTbl.setModel(tblModel);
    }

    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        studentsTbl = new JTable();
        dropDownCourses = new JComboBox(gradebook.getCourseList().toArray());
        addCourseBtn = new JButton();
        editCourseBtn = new JButton();
        delCourseBtn = new JButton();
        studentTblPopup = new JPopupMenu ("Students Menu");
        addStudentPopupMenu = new JMenuItem();
        editStudentPopupMenu = new JMenuItem();
        delStudentPopupMenu = new JMenuItem();
        deliverableTblPopup = new JPopupMenu ("Deliverables Menu");
        addDeliverablePopupMenu = new JMenuItem();
        editDeliverablePopupMenu = new JMenuItem();
        delDeliverablePopupMenu = new JMenuItem();
        jMenuBar = new JMenuBar();
        fileMenu = new JMenu();
        exitMenuItem = new JMenuItem();
        coursesMenu = new JMenu();
        addMenuItem = new JMenuItem();
        editMenuItem = new JMenuItem();
        delMenuItem = new JMenuItem();
        studentsMenu = new JMenu();
        addStudentMenuItem = new JMenuItem();
        editStudentMenuItem = new JMenuItem();
        delStudentMenuItem = new JMenuItem();
        deliverablesMenu = new JMenu();
        addDeliverableMenuItem = new JMenuItem();
        editDeliverableMenuItem = new JMenuItem();
        delDeliverableMenuItem = new JMenuItem();        

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                onExit();
            }
        });
        
        setTitle("Gradebook");

        jScrollPane1.setViewportView(studentsTbl);

        dropDownCourses.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                dropDownItemChanged(evt);
            }
        });
        dropDownCourses.setSelectedItem(gradebook.getActiveCourse());

        addCourseBtn.setIcon(new ImageIcon(getClass().getResource("/new.png"))); // NOI18N
        addCourseBtn.setMnemonic(KeyEvent.VK_N);
        addCourseBtn.setToolTipText("Add a new Course (Alt+N)");
        addCourseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addCourseAction(evt);
            }
        });

        editCourseBtn.setIcon(new ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        editCourseBtn.setMnemonic(KeyEvent.VK_E);
        editCourseBtn.setToolTipText("Edit Selected Course (Alt+E)");
        editCourseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editCourseAction(evt);
            }
        });

        delCourseBtn.setIcon(new ImageIcon(getClass().getResource("/del.png"))); // NOI18N
        delCourseBtn.setMnemonic(KeyEvent.VK_D);
        delCourseBtn.setToolTipText("Delete Selected Course (Alt+D)");
        delCourseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                delCourseAction(evt);
            }
        });

        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setText("File");

        exitMenuItem.setIcon(new ImageIcon(getClass().getResource("/exit.png"))); // NOI18N
        exitMenuItem.setMnemonic(KeyEvent.VK_E);
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onExit();
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar.add(fileMenu);

        coursesMenu.setMnemonic(KeyEvent.VK_C);
        coursesMenu.setText("Courses");

        addMenuItem.setIcon(new ImageIcon(getClass().getResource("/new.png"))); // NOI18N
        addMenuItem.setMnemonic(KeyEvent.VK_A);
        addMenuItem.setText("Add Course");
        addMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addCourseAction(evt);
            }
        });
        coursesMenu.add(addMenuItem);

        editMenuItem.setIcon(new ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        editMenuItem.setMnemonic(KeyEvent.VK_E);
        editMenuItem.setText("Edit Course");
        editMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editCourseAction(evt);
            }
        });
        coursesMenu.add(editMenuItem);

        delMenuItem.setIcon(new ImageIcon(getClass().getResource("/del.png"))); // NOI18N
        delMenuItem.setMnemonic(KeyEvent.VK_D);
        delMenuItem.setText("Delete Course");
        delMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                delCourseAction(evt);
            }
        });
        coursesMenu.add(delMenuItem);

        jMenuBar.add(coursesMenu);

        studentsMenu.setMnemonic(KeyEvent.VK_S);
        studentsMenu.setText("Students");

        //TODO Get icon?
        //addStudentMenuItem.setIcon(new ImageIcon(getClass().getResource("/newStudent.png"))); // NOI18N
        addStudentMenuItem.setMnemonic(KeyEvent.VK_A);
        addStudentMenuItem.setText("Add Student");
        addStudentMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addStudentAction();
            }
        });
        studentsMenu.add(addStudentMenuItem);

        //TODO Get Icon
        //editStudentMenuItem.setIcon(new ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        editStudentMenuItem.setMnemonic(KeyEvent.VK_E);
        editStudentMenuItem.setText("Edit Student");
        editStudentMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editStudentAction();
            }
        });
        studentsMenu.add(editStudentMenuItem);

        //TODO get icon
        //delStudentMenuItem.setIcon(new ImageIcon(getClass().getResource("/del.png"))); // NOI18N
        delStudentMenuItem.setMnemonic(KeyEvent.VK_D);
        delStudentMenuItem.setText("Delete Student");
        delStudentMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                delStudentAction();
            }
        });
        studentsMenu.add(delStudentMenuItem);

        jMenuBar.add(studentsMenu);

        deliverablesMenu.setMnemonic(KeyEvent.VK_D);
        deliverablesMenu.setText("Deliverables");

        //TODO Get icon?
        //addDeliverableMenuItem.setIcon(new ImageIcon(getClass().getResource("/newStudent.png"))); // NOI18N
        addDeliverableMenuItem.setMnemonic(KeyEvent.VK_A);
        addDeliverableMenuItem.setText("Add Deliverable");
        addDeliverableMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addDeliverableAction();
            }
        });
        deliverablesMenu.add(addDeliverableMenuItem);

        //TODO Get Icon
        //editDeliverableMenuItem.setIcon(new ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        editDeliverableMenuItem.setMnemonic(KeyEvent.VK_E);
        editDeliverableMenuItem.setText("Edit Deliverable");
        editDeliverableMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editDeliverableAction();
            }
        });
        deliverablesMenu.add(editDeliverableMenuItem);

        //TODO get icon
        //delDeliverableMenuItem.setIcon(new ImageIcon(getClass().getResource("/del.png"))); // NOI18N
        delDeliverableMenuItem.setMnemonic(KeyEvent.VK_D);
        delDeliverableMenuItem.setText("Delete Deliverable");
        delDeliverableMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                delDeliverableAction();
            }
        });
        deliverablesMenu.add(delDeliverableMenuItem);

        jMenuBar.add(deliverablesMenu);

        setJMenuBar(jMenuBar);

        addStudentPopupMenu.setText("Add Student");
        addStudentPopupMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addStudentAction();
            }
        });
        studentTblPopup.add(addStudentPopupMenu);

        editStudentPopupMenu.setText("Edit Student");
        editStudentPopupMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editStudentAction();
            }
        });
        studentTblPopup.add(editStudentPopupMenu);

        delStudentPopupMenu.setText("Delete Student");
        delStudentPopupMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                delStudentAction();
            }
        });
        
        studentTblPopup.add(delStudentPopupMenu);

        addDeliverablePopupMenu.setText("Add Deliverable");
        addDeliverablePopupMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addDeliverableAction();
            }
        });
        deliverableTblPopup.add(addDeliverablePopupMenu);

        editDeliverablePopupMenu.setText("Edit Deliverable");
        editDeliverablePopupMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editDeliverableAction();
            }
        });
        deliverableTblPopup.add(editDeliverablePopupMenu);

        delDeliverablePopupMenu.setText("Delete Deliverable");
        delDeliverablePopupMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                delDeliverableAction();
            }
        });
        
        deliverableTblPopup.add(delDeliverablePopupMenu);

        
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addCourseBtn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editCourseBtn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delCourseBtn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(dropDownCourses, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(addCourseBtn)
                            .addComponent(editCourseBtn)
                            .addComponent(delCourseBtn))
                        .addGap(24, 24, 24))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dropDownCourses, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 91, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }
    
    private void dropDownItemChanged(ItemEvent evt) {
        gradebook.setActiveCourse((Course)dropDownCourses.getSelectedItem());
        refreshTableModel();
    }

    private void addCourseAction(ActionEvent evt){                                             
        JTextField title = new JTextField();
        JTextField code = new JTextField();
        JTextField term = new JTextField();
        
        Object[] message = {
            "Course title:", title,
            "Course code:", code,
            "Term:", term
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Course", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            if (title.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No title entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (code.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No code entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (term.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No term entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else{
                //Create a new Course and add it to the gradebook
                Course course = new Course(title.getText(), code.getText(), term.getText());
                gradebook.addCourse(course);
        
                //Add the entry to the dropdown list
                dropDownCourses.addItem(course);
                dropDownCourses.setSelectedItem(course);
            }
        }
    }

    private void editCourseAction(ActionEvent evt) {   
        if (gradebook.getActiveCourse() == null){
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        JTextField title = new JTextField(gradebook.getActiveCourse().getTitle());
        JTextField code = new JTextField(gradebook.getActiveCourse().getCode());
        JTextField term = new JTextField(gradebook.getActiveCourse().getTerm());
        
        Object[] message = {
            "Course title:", title,
            "Course code:", code,
            "Term:", term
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Course", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            if (title.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No title entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (code.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No code entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (term.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No term entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else{
                Course activeCourse = gradebook.getActiveCourse();
            
                //Set the attributes
                activeCourse.setTitle(title.getText());
                activeCourse.setCode(code.getText());
                activeCourse.setTerm(term.getText());
                
                //Refresh the dropdown list entry
                dropDownCourses.removeItem(activeCourse);
                dropDownCourses.addItem(activeCourse);
                dropDownCourses.setSelectedItem(activeCourse);
            }
        }
    }

    private void delCourseAction(ActionEvent evt) {                                             
        if (gradebook.getActiveCourse() == null){
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int option = JOptionPane.showConfirmDialog(this, "Are you sure? This action cannot be undone.", "Delete Course", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            //remove the course
            gradebook.removeCourse(gradebook.getActiveCourse());
            dropDownCourses.removeItem(gradebook.getActiveCourse());
        }
    }
    
    private void addStudentAction() {
        if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField firstName = new JTextField();
        JTextField lastName = new JTextField();
        JTextField number = new JTextField();
        JTextField email = new JTextField();
        int num;

        Object[] message = {
            "Student First Name:", firstName,
            "Student Last Name:", lastName,
            "Student Number:", number,
            "Student Email:", email
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Student", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if (firstName.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No First Name entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (lastName.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No Last Name entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (number.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No Student number entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (email.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No Student email entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //Create a new Student and add it to the gradebook
                Student student = new Student(firstName.getText(), lastName.getText(), email.getText(), number.getText());
                gradebook.getActiveCourse().addStudent(student);
                
                //Update JTable
                refreshTableModel();
            }
        }
    }

    private void editStudentAction() {
        if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (studentsTbl.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "No student selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
            
        //Get current selected Student
        int row = studentsTbl.convertRowIndexToModel(studentsTbl.getSelectedRow());
        Student student = gradebook.getActiveCourse().getStudentList().get(row);

        
        JTextField firstName = new JTextField(student.getFirstName());
        JTextField lastName = new JTextField(student.getLastName());
        JTextField number = new JTextField(String.valueOf(student.getNum()));
        JTextField email = new JTextField(student.getEmail());
        int num;

        Object[] message = {
            "Student First Name:", firstName,
            "Student Last Name:", lastName,
            "Student Number:", number,
            "Student Email:", email
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Student", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if (firstName.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No First Name entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (lastName.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No Last Name entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (number.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No Student number entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (email.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No Student email entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    num = Integer.parseInt(number.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Not a valid Student number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                //Edit current selected Student
                student.setFirstName(firstName.getText());
                student.setLastName(lastName.getText());
                student.setEmail(email.getText());
                student.setNum(number.getText());                
                
                //Update JTable
                refreshTableModel();
            }
        }
    }

    private void delStudentAction () {
        if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (studentsTbl.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "No student selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Get current selected Student
        int row = studentsTbl.convertRowIndexToModel(studentsTbl.getSelectedRow());
        Student student = gradebook.getActiveCourse().getStudentList().get(row);
        
        int option = JOptionPane.showConfirmDialog(this, "Are you sure? This action cannot be undone.", "Delete Student", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            gradebook.getActiveCourse().removeStudent(student);
            refreshTableModel();
        }
        
    }
    
    private void addDeliverableAction() {
        if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField name = new JTextField();
        JComboBox type = new JComboBox(Deliverable.TYPES);
        JTextField weight = new JTextField();
        int weightInt;

        Object[] message = {
            "Deliverable Name:", name,
            "Deliverable Type:", type,
            "Deliverable Weight:", weight
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Deliverable", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if (name.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No name entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (weight.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No weight entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    weightInt = Integer.parseInt(weight.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "The weight must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Create a new Deliverable and add it to the Course
                Deliverable deliverable = new Deliverable(name.getText(), type.getSelectedIndex(), weightInt);
                gradebook.getActiveCourse().addDeliverable(deliverable);

                //Update JTable
                refreshTableModel();
            }
        }
    }

    private void editDeliverableAction() {
        if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if ((studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn()) == 0) &&
                (studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn()) >= (studentsTbl.getModel().getColumnCount() - 3))) {
            JOptionPane.showMessageDialog(this, "No Deliverable selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
            
        //Get current selected Deliverable
        int column = studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn());
        //To get the right Deliverable from the list we use column - 1 because the Deliverables start on the 2nd Column
        Deliverable deliverable = gradebook.getActiveCourse().getDeliverableList().get(column - 1);
        
        JTextField name = new JTextField(deliverable.getName());
        JComboBox type = new JComboBox(Deliverable.TYPES);
        type.setSelectedIndex(deliverable.getType());
        JTextField weight = new JTextField(String.valueOf(deliverable.getWeight()));
        int weightInt;

        Object[] message = {
            "Deliverable Name:", name,
            "Deliverable Type:", type,
            "Deliverable Weight:", weight
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Deliverable", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if (name.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No name entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (weight.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "No weight entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    weightInt = Integer.parseInt(weight.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "The weight must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Updates Deliverable
                deliverable.setName(name.getText());
                deliverable.setType(type.getSelectedIndex());
                deliverable.setWeight(weightInt);

                //Update JTable
                refreshTableModel();
            }
        }
    }

    private void delDeliverableAction () {
        if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if ((studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn()) == 0)
                && (studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn()) >= (studentsTbl.getModel().getColumnCount() - 3))) {
            JOptionPane.showMessageDialog(this, "No Deliverable selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Get current selected Deliverable
        int column = studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn());
        //To get the right Deliverable from the list we use (column - 1) because the Deliverables start on the 2nd Column
        Deliverable deliverable = gradebook.getActiveCourse().getDeliverableList().get(column - 1);
        
        int option = JOptionPane.showConfirmDialog(this, "Are you sure? This action cannot be undone.", "Delete Deliverable", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            gradebook.getActiveCourse().removeDeliverable(deliverable);
            
            refreshTableModel();
        }
        
    }    
    
    private int onExit() {
        storeGradebook();
        System.exit(0);
        return 0;
    }
    
    private Gradebook loadGradebook(){
        ObjectInputStream in = null;
        Gradebook gradebook = null;
        
        try{ 
            //attempt reading from data file
            in = new ObjectInputStream(new FileInputStream(DATA_FILENAME));
            gradebook = (Gradebook)in.readObject();
        } catch (Exception e1){
            //if that didn't work...
            try{ 
                //attempt reading from backup data file
                in = new ObjectInputStream(new FileInputStream(BACKUP_FILENAME));
                gradebook = (Gradebook)in.readObject();
                
                //show a warning message
                JOptionPane.showMessageDialog(this, "The data file could not be read. A backup was opened instead.", "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e2){
                gradebook = new Gradebook(); //return an empty gradebook
            }
        } 
        finally{
            try{
                in.close(); //clean up
            } catch (Exception e){ }
        }
        
        return gradebook;
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
            //show a message: data file could not be created
            JOptionPane.showMessageDialog(this, "The data file could not be created. Changes have not been saved.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e){
            //show a message: error writing data file
            JOptionPane.showMessageDialog(this, "The data file could not be written. Changes have not been saved.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){ }
        finally{
            try{
                out.close(); //clean up
            } catch (Exception e){ }
        }
    } 
}
