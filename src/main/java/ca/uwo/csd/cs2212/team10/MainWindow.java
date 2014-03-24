package ca.uwo.csd.cs2212.team10;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import java.util.List;
import java.util.ArrayList;
import au.com.bytecode.opencsv.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private JPanel statusPanel;
    private JLabel statusLabel;
    private JButton addStudentBtn, addDeliverableBtn, editDeliverableBtn, 
            emailBtn, genRepBtn;
    private JPopupMenu studentTblPopup, deliverableTblPopup;
    private JMenuItem editStudentPopupMenu, delStudentPopupMenu, 
            editDeliverablePopupMenu, delDeliverablePopupMenu;
    private JMenuBar jMenuBar;
    private JMenu fileMenu, coursesMenu, studentsMenu, deliverablesMenu, importMenu, exportMenu;
    private JMenuItem exitMenuItem, addMenuItem, editMenuItem, delMenuItem,
            addStudentMenuItem, editStudentMenuItem, delStudentMenuItem,
            addDeliverableMenuItem, editDeliverableMenuItem, delDeliverableMenuItem, impStudentsMenuItem,
            expGradesMenuItem;

    /* Constructor */
    public MainWindow() {
        loadGradebook();
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
        studentsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        //Set height for the rows
        studentsTbl.setRowHeight(22);
        
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

                if (studentsTbl.getSelectedRow() >= 0 && e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    int selectedColumn = studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn());
                    
                    if (selectedColumn >= 0 && selectedColumn <= 1)
                        studentTblPopup.show(e.getComponent(), e.getX(), e.getY());
                    else if (selectedColumn >= 1 && selectedColumn < (studentsTbl.getModel().getColumnCount() - 3))
                        deliverableTblPopup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            //Mouse Listener for Windows
            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed(e);
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
        addStudentBtn = new JButton();
        addDeliverableBtn = new JButton();
        editDeliverableBtn = new JButton();
        emailBtn = new JButton();
        genRepBtn = new JButton();
        studentTblPopup = new JPopupMenu("Students Menu");
        editStudentPopupMenu = new JMenuItem();
        delStudentPopupMenu = new JMenuItem();
        deliverableTblPopup = new JPopupMenu("Deliverables Menu");
        editDeliverablePopupMenu = new JMenuItem();
        delDeliverablePopupMenu = new JMenuItem();
        jMenuBar = new JMenuBar();
        fileMenu = new JMenu();
        importMenu = new JMenu();
        exportMenu = new JMenu();
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
        impStudentsMenuItem = new JMenuItem();
        expGradesMenuItem = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitAction();
            }
        });

        setTitle("Gradebook");

        jScrollPane1.setViewportView(studentsTbl);

        dropDownCourses.addItem("Add Course");
        dropDownCourses.setSelectedItem(gradebook.getActiveCourse());
        dropDownCourses.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                dropDownItemChanged(evt);
            }
        });
        
        

        addStudentBtn.setText("Add Student");
        addStudentBtn.setMnemonic(KeyEvent.VK_B);
        addStudentBtn.setToolTipText("Add a new Student to the active Course (Alt+B)");
        addStudentBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addStudentAction();
            }
        });

        addDeliverableBtn.setText("Add Deliverable");
        addDeliverableBtn.setMnemonic(KeyEvent.VK_N);
        addDeliverableBtn.setToolTipText("Add a new Deliverable to the active Course (Alt+N)");
        addDeliverableBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addDeliverableAction();
            }
        });

        editDeliverableBtn.setText("Edit Deliverable");
        editDeliverableBtn.setMnemonic(KeyEvent.VK_E);
        editDeliverableBtn.setToolTipText("Edit selected Deliverable (Alt+E)");
        editDeliverableBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editDeliverableAction();
            }
        });

        emailBtn.setText("Email");
        emailBtn.setMnemonic(KeyEvent.VK_M);
        emailBtn.setToolTipText("Send email (ALT+M)");
        emailBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //TODO implement email action
            }
        });

        genRepBtn.setText("Generate Reports");
        genRepBtn.setMnemonic(KeyEvent.VK_R);
        genRepBtn.setToolTipText("Generate grade reports (ALT+R)");
        genRepBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //TODO implement Generate Reports Action
            }
        });

        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setText("File");
        
        importMenu.setMnemonic(KeyEvent.VK_I);
        importMenu.setText("Import");
        fileMenu.add(importMenu);

        exportMenu.setMnemonic(KeyEvent.VK_E);
        exportMenu.setText("Export");
        fileMenu.add(exportMenu);
        
        impStudentsMenuItem.setText("Import Class List");
        impStudentsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                importStudentsAction();

            }
        });
        importMenu.add(impStudentsMenuItem);

        expGradesMenuItem.setText("Export Grades");
        expGradesMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exportGradesAction();
            }
        });
        exportMenu.add(expGradesMenuItem);

        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitAction();
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar.add(fileMenu);

        coursesMenu.setMnemonic(KeyEvent.VK_C);
        coursesMenu.setText("Courses");

        addMenuItem.setMnemonic(KeyEvent.VK_A);
        addMenuItem.setText("Add Course");
        addMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addCourseAction();
            }
        });
        coursesMenu.add(addMenuItem);

        editMenuItem.setMnemonic(KeyEvent.VK_E);
        editMenuItem.setText("Edit Course");
        editMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editCourseAction();
            }
        });
        coursesMenu.add(editMenuItem);

        delMenuItem.setMnemonic(KeyEvent.VK_D);
        delMenuItem.setText("Delete Course");
        delMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                delCourseAction();
            }
        });
        coursesMenu.add(delMenuItem);

        jMenuBar.add(coursesMenu);

        studentsMenu.setMnemonic(KeyEvent.VK_S);
        studentsMenu.setText("Students");

        addStudentMenuItem.setMnemonic(KeyEvent.VK_A);
        addStudentMenuItem.setText("Add Student");
        addStudentMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addStudentAction();
            }
        });
        studentsMenu.add(addStudentMenuItem);

        editStudentMenuItem.setMnemonic(KeyEvent.VK_E);
        editStudentMenuItem.setText("Edit Student");
        editStudentMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editStudentAction();
            }
        });
        studentsMenu.add(editStudentMenuItem);

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

        addDeliverableMenuItem.setMnemonic(KeyEvent.VK_A);
        addDeliverableMenuItem.setText("Add Deliverable");
        addDeliverableMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addDeliverableAction();
            }
        });
        deliverablesMenu.add(addDeliverableMenuItem);

        editDeliverableMenuItem.setMnemonic(KeyEvent.VK_E);
        editDeliverableMenuItem.setText("Edit Deliverable");
        editDeliverableMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editDeliverableAction();
            }
        });
        deliverablesMenu.add(editDeliverableMenuItem);

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

        //Set Status Bar
        statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(this.getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel = new JLabel();
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);
        setStatusBar(null);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(addStudentBtn)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(addDeliverableBtn)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(editDeliverableBtn)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(emailBtn)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(genRepBtn)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dropDownCourses, 250, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())
                .addComponent(statusPanel, GroupLayout.PREFERRED_SIZE, this.getWidth(), Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(addStudentBtn)
                                                .addComponent(addDeliverableBtn)
                                                .addComponent(editDeliverableBtn)
                                                .addComponent(emailBtn)
                                                .addComponent(genRepBtn)
                                                .addComponent(dropDownCourses))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 91, Short.MAX_VALUE)
                        .addGap(20)
                        .addComponent(statusPanel))
        );

        pack();
    }

    private void dropDownItemChanged(ItemEvent evt) {
        if (dropDownCourses.getSelectedItem() != null) {
            if (dropDownCourses.getSelectedItem().equals("Add Course")) {
                if (evt.getStateChange() == ItemEvent.SELECTED)
                    addCourseAction();
            } else {
                gradebook.setActiveCourse((Course) dropDownCourses.getSelectedItem());
                refreshTableModel();
                setStatusBar(null);
            }
        }
    }

    private void addCourseAction(){                     
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
            dropDownCourses.removeItem("Add Course");
            
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
                
                //Make "Add Course" the last Item on the list                
                dropDownCourses.addItem("Add Course");
                
                dropDownCourses.setSelectedItem(course);
            }
        }
    }

    private void editCourseAction() {   
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
                
                //Make "Add Course" the last Item on the list
                dropDownCourses.removeItem("Add Course");
                dropDownCourses.addItem("Add Course");
                
                dropDownCourses.setSelectedItem(activeCourse);
            }
        }
    }

    private void delCourseAction() {                                             
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
                try{
                    gradebook.getActiveCourse().addStudent(student);
                } catch (DuplicateStudentException e) {
                    JOptionPane.showMessageDialog(this, "Student info not unique.", "Error", JOptionPane.ERROR_MESSAGE);
                }
       
                //Update JTable
                refreshTableModel();
                setStatusBar("Added new Student");
            }
        }
    }

    private void editStudentAction() {
        if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (studentsTbl.getSelectedRow() < 0){
            JOptionPane.showMessageDialog(this, "No Student selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } 
        
        //Get the Student object
        int selectedRow = studentsTbl.convertRowIndexToModel(studentsTbl.getSelectedRow());
        Student student = gradebook.getActiveCourse().getStudentList().get(selectedRow);

        JTextField firstName = new JTextField(student.getFirstName());
        JTextField lastName = new JTextField(student.getLastName());
        JTextField number = new JTextField(String.valueOf(student.getNum()));
        JTextField email = new JTextField(student.getEmail());

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

                //Check that email address and student number are unique
                if (!gradebook.getActiveCourse().isUnique(student, email.getText(), number.getText())){    
                    JOptionPane.showMessageDialog(this, "Student info not unique.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                //Update student info
                student.setFirstName(firstName.getText());
                student.setLastName(lastName.getText());
                student.setEmail(email.getText());  
                student.setNum(number.getText());

                //Update JTable
                refreshTableModel();
                setStatusBar("Edited Student");
            }
        }
    }

    private void delStudentAction () {
        if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (studentsTbl.getSelectedRow() < 0){
            JOptionPane.showMessageDialog(this, "No Student selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } 
        
        //Get the Student object
        int selectedRow = studentsTbl.convertRowIndexToModel(studentsTbl.getSelectedRow());
        Student student = gradebook.getActiveCourse().getStudentList().get(selectedRow);
        
        int option = JOptionPane.showConfirmDialog(this, "Are you sure? This action cannot be undone.", "Delete Student", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            gradebook.getActiveCourse().removeStudent(student);
            refreshTableModel();
            setStatusBar("Deleted Student");
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
                setStatusBar("Added new Deliverable");
            }
        }
    }

    private void editDeliverableAction() {
        if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (studentsTbl.getSelectedColumn() < 0){
            JOptionPane.showMessageDialog(this, "No Deliverable selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } 
        
        int selectedColumn = studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn());
        if (selectedColumn <= 1 || selectedColumn > (studentsTbl.getModel().getColumnCount() - 3)){
            JOptionPane.showMessageDialog(this, "No Deliverable selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //To get the right Deliverable from the list we use column - 2 because the Deliverables start on the 3nd Column
        Deliverable deliverable = gradebook.getActiveCourse().getDeliverableList().get(selectedColumn - 2);
        
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
                setStatusBar("Edited Deliverable");
            }
        }
    }

    private void delDeliverableAction() {
        if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (studentsTbl.getSelectedColumn() < 0){
            JOptionPane.showMessageDialog(this, "No Deliverable selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } 
        
        int selectedColumn = studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn());
        if (selectedColumn <= 1 || selectedColumn > (studentsTbl.getModel().getColumnCount() - 3)){
            JOptionPane.showMessageDialog(this, "No Deliverable selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //To get the right Deliverable from the list we use column - 2 because the Deliverables start on the 3nd Column
        Deliverable deliverable = gradebook.getActiveCourse().getDeliverableList().get(selectedColumn - 2);
        
        int option = JOptionPane.showConfirmDialog(this, "Are you sure? This action cannot be undone.", "Delete Deliverable", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            gradebook.getActiveCourse().removeDeliverable(deliverable);
            
            refreshTableModel();
            setStatusBar("Deliverable Deleted");
        }
    }
    
    private void importStudentsAction(){
    	if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
    	}
        
    	JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));
        
		int option = chooser.showOpenDialog(rootPane);
		if (option == JFileChooser.APPROVE_OPTION){
            try (CSVReader reader = new CSVReader(new FileReader(chooser.getSelectedFile()))){
                gradebook.getActiveCourse().importStudents(reader);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading selected file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            refreshTableModel();
            setStatusBar(null);
		}
    }
    
    private void exportGradesAction(){
    	if (gradebook.getActiveCourse() == null) {
            JOptionPane.showMessageDialog(this, "No active course selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
    	}
        
    	CustomFileChooser chooser = new CustomFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));
        
    	int option = chooser.showSaveDialog(rootPane);
    	if (option == JFileChooser.APPROVE_OPTION){
            try (CSVWriter writer = new CSVWriter(new FileWriter(chooser.getSelectedFile()))){
                gradebook.getActiveCourse().exportGrades(writer);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error writing selected file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
    	}
    }
    
    private void setStatusBar(String status) {
        StringBuilder sb = new StringBuilder();
        if (status == null)
            sb.append("Ready");
        else sb.append(status);
        
        sb.append(" | ");
        
        if (gradebook.getActiveCourse() != null)
            sb.append(gradebook.getActiveCourse().toString());
        
            statusLabel.setText(String.valueOf(sb));
    }
    
    private void firstStartAction() {
        //TODO: OOBE code
    }
    
    private void exitAction() {
        try{
            storeGradebook();
            System.exit(0);
        } catch (IOException e){
            int option = JOptionPane.showConfirmDialog(this, "There was a problem writing the data file. Changes were not saved. Exit anyway?", "Error", JOptionPane.YES_NO_OPTION);
        
            if (option == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }
    }
    
    private void loadGradebook(){
        try{
            //try to read from main data file
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILENAME))){ 
                gradebook = Gradebook.fromObjectInputStream(in); //read the gradebook
            } catch (FileNotFoundException e){
                //!! primary data file doesn't exist
                if (new File(BACKUP_FILENAME).isFile()){
                    //!! backup data file exists
                    throw e; //go to the next catch block
                } else{
                    //!! first app startup
                    gradebook = new Gradebook(); //create an empty gradebook
                    firstStartAction(); //present OOBE to the user
                }
            }
        } catch (IOException | ClassNotFoundException e){
            //!! main data file corrupt or non-existent but backup exists
            //try to read from backup data file
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(BACKUP_FILENAME))){
                //!! backup was OK
                gradebook = Gradebook.fromObjectInputStream(in); //read the gradebook
                JOptionPane.showMessageDialog(this, "The main data file could not be read. A backup was opened instead.", "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (IOException | ClassNotFoundException ex){
                //!! both data files corrupt
                gradebook = new Gradebook(); //create an empty gradebook
                JOptionPane.showMessageDialog(this, "The data file was corrupt and could not be recovered. All data was lost.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void storeGradebook() throws IOException{
        //make a backup
        File dataFile = new File(DATA_FILENAME);
        File backupFile = new File(BACKUP_FILENAME);
        backupFile.delete(); //delete the backup first
        dataFile.renameTo(backupFile); //then make a new one
        
        //store the data
        gradebook.writeToObjectOutputStream(new ObjectOutputStream(new FileOutputStream(DATA_FILENAME)));
    }
}
