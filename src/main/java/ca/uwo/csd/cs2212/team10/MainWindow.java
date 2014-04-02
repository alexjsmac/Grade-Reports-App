package ca.uwo.csd.cs2212.team10;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import java.util.List;
import java.util.ArrayList;
import au.com.bytecode.opencsv.*;
import java.text.DecimalFormat;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;
import javax.mail.internet.AddressException;
import javax.mail.MessagingException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;
import net.sf.jasperreports.engine.JRException;
import org.jdesktop.swingx.*;

/**
 * The main window of the gradebook program
 * @author Team 10
 */
public class MainWindow extends JFrame {
    /* Constants */
    private final String DATA_FILENAME = "gradebook.dat";
    private final String BACKUP_FILENAME = "gradebook.dat.bak";
    private final int COLUMN_PADDING = 5;
    
    /* Attributes */
    private Gradebook gradebook;
    private ReportGenerator reportGenerator;
    private boolean callFirstStart = false;

    private JScrollPane jScrollPane1;
    private JXTable studentsTbl;
    private JComboBox dropDownCourses;
    private JPanel statusPanel;
    private JLabel statusLabel, courseAvgTxtLabel, courseAvgLabel,
            assignmentAvgTxtLabel, assignmentAvgLabel, examAvgTxtLabel, examAvgLabel;
    private JButton addStudentBtn, addDeliverableBtn,
            emailBtn, genRepBtn;
    private JPopupMenu studentTblPopup, deliverableTblPopup;
    private JMenuItem editStudentPopupMenu, delStudentPopupMenu,
            editDeliverablePopupMenu, delDeliverablePopupMenu;
    private JMenuBar jMenuBar;
    private JMenu fileMenu, coursesMenu, studentsMenu, deliverablesMenu, importMenu, exportMenu;
    private JMenuItem exitMenuItem, addMenuItem, editMenuItem, delMenuItem,
            addStudentMenuItem, editStudentMenuItem, delStudentMenuItem,
            addDeliverableMenuItem, editDeliverableMenuItem, delDeliverableMenuItem, impStudentsMenuItem,
            impGradesMenuItem, expGradesMenuItem, emailMenuItem, genRepMenuItem;

    /* Constructor */
    public MainWindow() {
        loadGradebook();
        //reportGenerator = new ReportGenerator();
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initTable();
        setVisible(true);
        
        if (callFirstStart)
            firstStartAction();
    }

    /* Private methods */
    private void initTable() {
        refreshTableModel();

        //Set cell editors and renderers for grades
        studentsTbl.setDefaultEditor(Double.class, new GradeCellEditor());
        studentsTbl.setDefaultRenderer(Double.class, new GradeCellRenderer());

        studentsTbl.setAutoCreateRowSorter(true);
        studentsTbl.setCellSelectionEnabled(true);
        studentsTbl.setGridColor(Color.gray);
        studentsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        studentsTbl.setHorizontalScrollEnabled(true);

        //Set height for the rows
        studentsTbl.setRowHeight(22);

        studentsTbl.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "startEditing");
        studentsTbl.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "deleteStudent");
        studentsTbl.getActionMap().put("deleteStudent", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                int selectedColumn = studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn());
                if (selectedColumn >= 1 && selectedColumn < (studentsTbl.getModel().getColumnCount() - 3)) {
                    delDeliverableAction();
                } else if (studentsTbl.getSelectedRow() != -1) {
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

                    if (selectedColumn >= 0 && selectedColumn <= 1) {
                        studentTblPopup.show(e.getComponent(), e.getX(), e.getY());
                    } else if (selectedColumn >= 2 && selectedColumn < (studentsTbl.getModel().getColumnCount() - 3)) {
                        deliverableTblPopup.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }

            //Mouse Listener for Windows
            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed(e);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                studentsTbl.clearSelection();
                int row = studentsTbl.rowAtPoint(e.getPoint());
                int column = studentsTbl.columnAtPoint(e.getPoint());
                if (row >= 0 && row < studentsTbl.getRowCount()) {
                    studentsTbl.changeSelection(row, column, false, false);
                } else {
                    studentsTbl.clearSelection();
                }

                if ((studentsTbl.getSelectedColumn() == studentsTbl.getModel().getColumnCount() - 1)
                        && (e.getButton() == MouseEvent.BUTTON1) && e.getComponent() instanceof JTable) {
                    editStudentAction();
                }
            }
        });

        //Right-Click on a Deliverable column header open a Popup menu
        studentsTbl.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    studentsTbl.clearSelection();
                    int column = studentsTbl.columnAtPoint(e.getPoint());
                    
                    if (studentsTbl.convertColumnIndexToModel(column) >= 2 
                            && studentsTbl.convertColumnIndexToModel(column) < (studentsTbl.getModel().getColumnCount() - 3)) {
                        studentsTbl.changeSelection(-1, column, false, false);
                        deliverableTblPopup.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }

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
        studentsTbl.getRowSorter().toggleSortOrder(0);
        studentsTbl.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                updateClassAvgLabel();
            }
        });
        updateClassAvgLabel();
        updateColumnSize();
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane(studentsTbl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        studentsTbl = new JXTable() {
            public Component prepareEditor(TableCellEditor editor, int row, int column) {
                Component c = super.prepareEditor(editor, row, column);
                if (c instanceof JTextComponent) {
                    ((JTextComponent) c).selectAll();
                }
                return c;
            }
        };
        dropDownCourses = new JComboBox(gradebook.getCourseList().toArray());
        addStudentBtn = new JButton();
        addDeliverableBtn = new JButton();
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
        impGradesMenuItem = new JMenuItem();
        expGradesMenuItem = new JMenuItem();
        emailMenuItem = new JMenuItem();
        genRepMenuItem = new JMenuItem();
        courseAvgTxtLabel = new JLabel("Overall Class Average:");
        courseAvgLabel = new JLabel("--");
        assignmentAvgTxtLabel = new JLabel("Assignments Average:");
        assignmentAvgLabel = new JLabel("--");
        examAvgTxtLabel = new JLabel("Exams Average:");
        examAvgLabel = new JLabel("--");

        courseAvgTxtLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        assignmentAvgTxtLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        examAvgTxtLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        courseAvgLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
        assignmentAvgLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
        examAvgLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));

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
        dropDownCourses.setRenderer(new ListCellRenderer() {
            private ListCellRenderer delegate;
            private JPanel separatorPanel = new JPanel(new BorderLayout());
            private JSeparator separator = new JSeparator();

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component comp = delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index != -1 && "Add Course".equals(value)) {
                    separatorPanel.removeAll();
                    separatorPanel.add(separator, BorderLayout.NORTH);
                    separatorPanel.add(comp, BorderLayout.CENTER);
                    return separatorPanel;
                } else {
                    return comp;
                }
            }

            private ListCellRenderer init(ListCellRenderer delegate) {
                this.delegate = delegate;
                return this;
            }
        }.init(dropDownCourses.getRenderer()));

        //addStudentBtn.setText("Add Student");
        addStudentBtn.setIcon(new ImageIcon(getClass().getResource("/addStudent.png")));
        addStudentBtn.setMnemonic(KeyEvent.VK_B);
        addStudentBtn.setToolTipText("Add a new Student to the active Course (Alt+B)");
        addStudentBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addStudentAction();
            }
        });

        //addDeliverableBtn.setText("Add Deliverable");
        addDeliverableBtn.setIcon(new ImageIcon(getClass().getResource("/addDeliverable.png")));
        addDeliverableBtn.setMnemonic(KeyEvent.VK_N);
        addDeliverableBtn.setToolTipText("Add a new Deliverable to the active Course (Alt+N)");
        addDeliverableBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addDeliverableAction();
            }
        });

        //emailBtn.setText("Email");
        emailBtn.setIcon(new ImageIcon(getClass().getResource("/email.png")));
        emailBtn.setMnemonic(KeyEvent.VK_M);
        emailBtn.setToolTipText("Send email (ALT+M)");
        emailBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sendEmailAction();
            }
        });

        //genRepBtn.setText("Generate Reports");
        genRepBtn.setIcon(new ImageIcon(getClass().getResource("/genRep.png")));
        genRepBtn.setMnemonic(KeyEvent.VK_R);
        genRepBtn.setToolTipText("Generate grade reports (ALT+R)");
        genRepBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                genReportsAction();
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

        fileMenu.addSeparator();

        impStudentsMenuItem.setText("Import students from CSV file");
        impStudentsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                importStudentsAction();
            }
        });
        importMenu.add(impStudentsMenuItem);

        impGradesMenuItem.setText("Import grades from CSV file");
        impGradesMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                importGradesAction();
            }
        });
        importMenu.add(impGradesMenuItem);

        expGradesMenuItem.setText("Export grades to CSV file");
        expGradesMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exportGradesAction();
            }
        });
        exportMenu.add(expGradesMenuItem);

        emailMenuItem.setText("Send grade reports by email");
        emailMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sendEmailAction();
            }
        });
        exportMenu.add(emailMenuItem);

        genRepMenuItem.setText("Save grade reports as PDF");
        genRepMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                genReportsAction();
            }
        });
        exportMenu.add(genRepMenuItem);

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
        updateStatusBar();

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
                                        .addComponent(emailBtn)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(genRepBtn)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(courseAvgTxtLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(courseAvgLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(assignmentAvgTxtLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(assignmentAvgLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(examAvgTxtLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(examAvgLabel)
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
                                                .addComponent(emailBtn)
                                                .addComponent(genRepBtn)
                                                .addComponent(courseAvgTxtLabel)
                                                .addComponent(courseAvgLabel)
                                                .addComponent(examAvgTxtLabel)
                                                .addComponent(examAvgLabel)
                                                .addComponent(assignmentAvgTxtLabel)
                                                .addComponent(assignmentAvgLabel)
                                                .addComponent(dropDownCourses))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 91, Short.MAX_VALUE)
                        .addGap(20)
                        .addComponent(statusPanel))
        );

        pack();
    }

    private void dropDownItemChanged(ItemEvent evt) {
        if (dropDownCourses.getSelectedItem() == null) {
            gradebook.setActiveCourse(null);

            refreshTableModel();
            updateStatusBar();
        } else if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (dropDownCourses.getSelectedItem().equals("Add Course")) {
                addCourseAction();
                if (gradebook.getCourseList().size() == 0) {
                    dropDownCourses.setSelectedItem(null);
                } else {
                    dropDownCourses.setSelectedItem(gradebook.getActiveCourse());
                }

                refreshTableModel();
                updateStatusBar();
            } else {
                gradebook.setActiveCourse((Course) dropDownCourses.getSelectedItem());
            }

            refreshTableModel();
            updateStatusBar();
        }
    }

    private void updateClassAvgLabel() {
        DecimalFormat df = new DecimalFormat("0.##'%'");
        if (gradebook.getActiveCourse() != null) {
            courseAvgLabel.setText(df.format(gradebook.getActiveCourse().calcAverage()));
            assignmentAvgLabel.setText(df.format(gradebook.getActiveCourse().calcAverage(0)));
            examAvgLabel.setText(df.format(gradebook.getActiveCourse().calcAverage(1)));
        } else {
            courseAvgLabel.setText("0%");
            assignmentAvgLabel.setText("0%");
            examAvgLabel.setText("0%");
        }
    }

    private void addCourseAction() {
        UserEntryPrompter prompt = new UserEntryPrompter();
        prompt.showAddCourseDialog(this, gradebook);

        if (prompt.getReturnValue() == UserEntryPrompter.OK_PRESSED) {
            String[] output = (String[]) prompt.getOutput();

            //Create a new Course and add it to the gradebook
            Course course = new Course(output[0], output[1], output[2]);
            gradebook.addCourse(course);

            //Add the entry to the dropdown list
            dropDownCourses.addItem(course);

            //Make "Add Course" the last item in the list   
            dropDownCourses.removeItem("Add Course");
            dropDownCourses.addItem("Add Course");

            //Make the new course selected
            dropDownCourses.setSelectedItem(course);
        }
    }

    private void editCourseAction() {
        if (gradebook.getActiveCourse() == null) {
            showErrorMessage("There are no courses in the gradebook.");
            return;
        }

        Course activeCourse = gradebook.getActiveCourse();

        UserEntryPrompter prompt = new UserEntryPrompter();
        prompt.showEditCourseDialog(this, activeCourse, gradebook);

        int retval = prompt.getReturnValue();
        if (retval == UserEntryPrompter.OK_PRESSED) {
            String[] output = (String[]) prompt.getOutput();

            //Set the attributes
            activeCourse.setTitle(output[0]);
            activeCourse.setCode(output[1]);
            activeCourse.setTerm(output[2]);

            //Refresh the dropdown list and status bar
            dropDownCourses.revalidate();
            updateStatusBar();
        } else if (retval == UserEntryPrompter.DELETE_PRESSED) {
            delCourseAction();
        }
    }

    private void delCourseAction() {
        if (gradebook.getActiveCourse() == null) {
            showErrorMessage("There are no courses in the gradebook.");
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Are you sure? This action cannot be undone.", "Delete Course", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            //get the active course
            Course activeCourse = gradebook.getActiveCourse();

            //remove it from the gradebook
            gradebook.removeCourse(activeCourse);

            //remove it from the dropdown list
            if (gradebook.getCourseList().size() == 0) {
                dropDownCourses.setSelectedItem(null);
            }
            dropDownCourses.removeItem(activeCourse);
        }
    }

    private void addStudentAction() {
        if (gradebook.getActiveCourse() == null) {
            int option = JOptionPane.showConfirmDialog(this, "You must create a course first. Create one now?", "Question", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                addCourseAction();
            }
            return;
        }

        Course activeCourse = gradebook.getActiveCourse();

        UserEntryPrompter prompt = new UserEntryPrompter();
        prompt.showAddStudentDialog(this, activeCourse);

        if (prompt.getReturnValue() == UserEntryPrompter.OK_PRESSED) {
            String[] output = (String[]) prompt.getOutput();

            //Create a new Student and add it to the gradebook
            Student student = new Student(output[0], output[1], output[2], output[3]);
            gradebook.getActiveCourse().addStudent(student);

            //Update JTable
            refreshTableModel();
            updateStatusBar();
        }
    }

    private void editStudentAction() {
        if (gradebook.getActiveCourse() == null) {
            showErrorMessage("Select a student first.");
            return;
        } else if (studentsTbl.getSelectedRow() < 0) {
            showErrorMessage("Select a student first.");
            return;
        }

        //Get the Student object
        int selectedRow = studentsTbl.convertRowIndexToModel(studentsTbl.getSelectedRow());
        Student student = gradebook.getActiveCourse().getStudentList().get(selectedRow);
        Course activeCourse = gradebook.getActiveCourse();

        UserEntryPrompter prompt = new UserEntryPrompter();
        prompt.showEditStudentDialog(this, student, activeCourse);

        int retval = prompt.getReturnValue();
        if (retval == UserEntryPrompter.OK_PRESSED) {
            String[] output = (String[]) prompt.getOutput();

            //Update student info
            student.setFirstName(output[0]);
            student.setLastName(output[1]);
            student.setNum(output[2]);
            student.setEmail(output[3]);

            //Update JTable
            refreshTableModel();
        } else if (retval == UserEntryPrompter.DELETE_PRESSED) {
            delStudentAction();
        }
    }

    private void delStudentAction() {
        if (gradebook.getActiveCourse() == null) {
            showErrorMessage("Select a student first.");
            return;
        } else if (studentsTbl.getSelectedRow() < 0) {
            showErrorMessage("Select a student first.");
            return;
        }

        //Get the Student object
        int selectedRow = studentsTbl.convertRowIndexToModel(studentsTbl.getSelectedRow());
        Student student = gradebook.getActiveCourse().getStudentList().get(selectedRow);

        int option = JOptionPane.showConfirmDialog(this, "Are you sure? This action cannot be undone.", "Delete Student", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            gradebook.getActiveCourse().removeStudent(student);
            refreshTableModel();
            updateStatusBar();
        }

    }

    private void addDeliverableAction() {
        if (gradebook.getActiveCourse() == null) {
            int option = JOptionPane.showConfirmDialog(this, "You must create a course first. Create one now?", "Question", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                addCourseAction();
            }
            return;
        }

        Course activeCourse = gradebook.getActiveCourse();

        UserEntryPrompter prompt = new UserEntryPrompter();
        prompt.showAddDeliverableDialog(this, activeCourse);

        if (prompt.getReturnValue() == UserEntryPrompter.OK_PRESSED) {
            Object[] output = prompt.getOutput();

            //Create a new Deliverable and add it to the Course
            Deliverable deliverable = new Deliverable((String) output[0], (int) output[1], (int) output[2]);
            gradebook.getActiveCourse().addDeliverable(deliverable);

            //Update JTable
            refreshTableModel();
        }
    }

    private void editDeliverableAction() {
        if (gradebook.getActiveCourse() == null) {
            showErrorMessage("Select a deliverable first.");
            return;
        } else if (studentsTbl.getSelectedColumn() < 0) {
            showErrorMessage("Select a deliverable first.");
            return;
        }

        int selectedColumn = studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn());
        if (selectedColumn <= 1 || selectedColumn > (studentsTbl.getModel().getColumnCount() - 3)) {
            showErrorMessage("Select a deliverable first.");
            return;
        }

        //To get the right Deliverable from the list we use column - 2 because the Deliverables start on the 3nd Column
        Deliverable deliverable = gradebook.getActiveCourse().getDeliverableList().get(selectedColumn - 2);
        Course activeCourse = gradebook.getActiveCourse();

        UserEntryPrompter prompt = new UserEntryPrompter();
        prompt.showEditDeliverableDialog(this, deliverable, activeCourse);

        int retval = prompt.getReturnValue();
        if (retval == UserEntryPrompter.OK_PRESSED) {
            Object[] output = prompt.getOutput();

            //Update Deliverable
            deliverable.setName((String) output[0]);
            deliverable.setType((int) output[1]);
            deliverable.setWeight((int) output[2]);

            //Update JTable
            refreshTableModel();
        } else if (retval == UserEntryPrompter.DELETE_PRESSED) {
            delDeliverableAction();
        }
    }

    private void delDeliverableAction() {
        if (gradebook.getActiveCourse() == null) {
            showErrorMessage("Select a deliverable first.");
            return;
        } else if (studentsTbl.getSelectedColumn() < 0) {
            showErrorMessage("Select a deliverable first.");
            return;
        }

        int selectedColumn = studentsTbl.convertColumnIndexToModel(studentsTbl.getSelectedColumn());
        if (selectedColumn <= 1 || selectedColumn > (studentsTbl.getModel().getColumnCount() - 3)) {
            showErrorMessage("Select a deliverable first.");
            return;
        }

        //To get the right Deliverable from the list we use column - 2 because the Deliverables start on the 3nd Column
        Deliverable deliverable = gradebook.getActiveCourse().getDeliverableList().get(selectedColumn - 2);

        int option = JOptionPane.showConfirmDialog(this, "Are you sure? This action cannot be undone.", "Delete Deliverable", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            gradebook.getActiveCourse().removeDeliverable(deliverable);

            refreshTableModel();
        }
    }

    private void importStudentsAction() {
        if (gradebook.getActiveCourse() == null) {
            showErrorMessage("You must create a course first.");
            return;
        }

        CustomFileChooser chooser = new CustomFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));

        int option = chooser.showOpenDialog(rootPane);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (CSVReader reader = new CSVReader(new FileReader(chooser.getSelectedFile()))) {
                gradebook.getActiveCourse().importStudents(reader);
            } catch (IOException e) {
                showErrorMessage("The selected file could not be read.");
            } catch (CSVException e) {
                int numLines = e.getNumBadLines();
                showErrorMessage("Problems were encountered on " + numLines
                        + " line" + (numLines == 1 ? "" : "s")
                        + " of the file. They may not have been imported fully.");
            }

            refreshTableModel();
            updateStatusBar();
        }
    }

    private void importGradesAction() {
        if (gradebook.getActiveCourse() == null) {
            showErrorMessage("You must create a course first.");
            return;
        }

        CustomFileChooser chooser = new CustomFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));

        int option = chooser.showOpenDialog(rootPane);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (CSVReader reader = new CSVReader(new FileReader(chooser.getSelectedFile()))) {
                gradebook.getActiveCourse().importGrades(reader);
            } catch (IOException e) {
                showErrorMessage("The selected file could not be read.");
            } catch (CSVException e) {
                int numLines = e.getNumBadLines();
                if (numLines == CSVException.BAD_FORMAT) {
                    showErrorMessage("The file's header is invalid. No grades could be imported.");
                } else {
                    showErrorMessage("Problems were encountered on " + numLines
                            + " line" + (numLines == 1 ? "" : "s")
                            + " of the file. They may not have been imported fully.");
                }
            }

            refreshTableModel();
        }
    }

    private void exportGradesAction() {
        if (gradebook.getActiveCourse() == null) {
            showErrorMessage("You must create a course first.");
            return;
        }

        CustomFileChooser chooser = new CustomFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));

        int option = chooser.showSaveDialog(rootPane);
        String file_name = chooser.getSelectedFile().toString();
        if (!file_name.toLowerCase().endsWith(".csv"))
            file_name += ".csv";
        if (option == JFileChooser.APPROVE_OPTION) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file_name))) {
                gradebook.getActiveCourse().exportGrades(writer);
            } catch (IOException e) {
                showErrorMessage("The selected file could not be written. Try a different filename.");
                return;
            }
        }
    }

    private void sendEmailAction() {
        if (gradebook.getActiveCourse() == null) {
            showErrorMessage("You must create a course first.");
            return;
        }

        UserEntryPrompter prompt = new UserEntryPrompter();
        prompt.showEmailDialog(this, gradebook.getActiveCourse().getStudentList());

        if (prompt.getReturnValue() == UserEntryPrompter.OK_PRESSED) {
            Object[] output = prompt.getOutput();
            List<Student> stuList = (List<Student>) output[5];
            int errorCount = 0;
            int progress = 0;
            ProgressMonitor progressMonitor = new ProgressMonitor(this, "Sending reports by email",
                                                    "", 0, stuList.size());
            
            for (Student s : stuList){
                progressMonitor.setProgress(progress);
                progressMonitor.setNote("Completed " + progress + " out of " + stuList.size());
                if (progressMonitor.isCanceled())
                    break;
                
                try{
                    reportGenerator.sendByEmail((String)output[1], (String)output[2], (String)output[3], 
                        (String)output[4], (String)output[0], gradebook.getActiveCourse(), s);
                } catch(AddressException e){
                    errorCount++;
                } catch(MessagingException e){
                    errorCount++;
                } catch(JRException e){
                    errorCount++;
                }
                progress++;
            }
            progressMonitor.close();
            
            if (errorCount > 0)
                showErrorMessage("<html>Problems were encountered while processing "
                    + errorCount + " student" + (errorCount == 1 ? "." : "s.")
                    + "<br>Please check your SMTP credentials and email addresses.</html>");
        }
    }

    private void genReportsAction(){
        if (gradebook.getActiveCourse() == null) {
            showErrorMessage("You must create a course first.");
            return;
        }
        
        UserEntryPrompter prompt = new UserEntryPrompter();
        prompt.showReportDialog(this, gradebook.getActiveCourse().getStudentList());

        if (prompt.getReturnValue() == UserEntryPrompter.OK_PRESSED) {
            Object[] output = prompt.getOutput();
            List<Student> stuList = (List<Student>) output[1];
            int errorCount = 0;
            int progress = 0;
            ProgressMonitor progressMonitor = new ProgressMonitor(this, "Exporting PDF reports",
                                                    "", 0, stuList.size());
            
            for (Student s : stuList){
                progressMonitor.setProgress(progress);
                progressMonitor.setNote("Completed " + progress + " out of " + stuList.size());
                if (progressMonitor.isCanceled())
                    break;
                
                try{
                    reportGenerator.exportToPDF((String)output[0], gradebook.getActiveCourse(), s);
                } catch(JRException e){
                    errorCount++;
                }
                progress++;
            }
            progressMonitor.close();
            
            if (errorCount > 0)
                showErrorMessage("<html>Problems were encountered while processing "
                    + errorCount + " student" + (errorCount == 1 ? "." : "s.")
                    + "<br>Try a different folder, and check your free disk space.</html>");
        }
    }
    
    private void updateStatusBar() {
        if (gradebook.getActiveCourse() == null)
            statusLabel.setText("No course selected");
        else{
            int numStudents = gradebook.getActiveCourse().getStudentList().size();
            
            statusLabel.setText(gradebook.getActiveCourse().toString() + " | " + 
                numStudents + " student" + (numStudents == 1 ? "" : "s"));
        }
    }
    
    private void firstStartAction() {
        int option = JOptionPane.showConfirmDialog(this, 
            "<html>Welcome to the gradebook program!<br>Would you like to create a new course now?</html>", 
            "Question", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            addCourseAction();
        }
        return;
    }
    
    private void exitAction() {
        try{
            storeGradebook();
            System.exit(0);
        } catch (IOException e){
            int option = JOptionPane.showConfirmDialog(this, "There was a problem writing the data file. Changes were not saved. Exit anyway?", "Error", JOptionPane.YES_NO_OPTION);
        
            if (option == JOptionPane.YES_OPTION) {
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
                    callFirstStart = true; //present OOBE to the user
                }
            }
        } catch (IOException | ClassNotFoundException e){
            //!! main data file corrupt or non-existent but backup exists
            //try to read from backup data file
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(BACKUP_FILENAME))){
                //!! backup was OK
                gradebook = Gradebook.fromObjectInputStream(in); //read the gradebook
                showWarningMessage("The main data file could not be read. A backup was opened instead.");
            } catch (IOException | ClassNotFoundException ex){
                //!! both data files corrupt
                gradebook = new Gradebook(); //create an empty gradebook
                showErrorMessage("The data file was corrupt and could not be recovered. All data was lost.");
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
        gradebook.toObjectOutputStream(new ObjectOutputStream(new FileOutputStream(DATA_FILENAME)));
    }
    
    
    private void showErrorMessage(String text){
        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showWarningMessage(String text){
        JOptionPane.showMessageDialog(this, text, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    private int getMaxColumnSize(int colNumber){
    	//The default width will be the size of the header.
        int width = getHeaderSize(colNumber);
        //Loop through all the cells in a column
        for(int row=0; row< studentsTbl.getRowCount();row++){
        	//prefWidth is the width of the renderered component within the cell
        	//(the amount of space the string within the cell takes up) + additional spacing
            int prefWidth = (int)studentsTbl.getCellRenderer(row, colNumber).getTableCellRendererComponent(studentsTbl, studentsTbl.getValueAt(row, colNumber), false, false, row, colNumber).getPreferredSize().getWidth() + studentsTbl.getIntercellSpacing().width + COLUMN_PADDING;
            //The width of the column will then be the greater of the header width, or the width of the cell with the largest string).
            width = Math.max(width, prefWidth);
        }
        return width;
    }

    private void updateColumnSize(){
    	//Loop through all columns and update the width of the columns.
        for(int col=0;col<studentsTbl.getColumnCount();col++){
            studentsTbl.getColumnModel().getColumn(col).setPreferredWidth(getMaxColumnSize(col));
        }
    }
    
    private int getHeaderSize(int colNumber){
    	//Get the header value for the specified column
        Object value = studentsTbl.getColumnModel().getColumn(colNumber).getHeaderValue();
        //Get the tablecellrenderer that is used to draw the header of the specified column
        TableCellRenderer renderer = studentsTbl.getColumnModel().getColumn(colNumber).getHeaderRenderer();
        //If the renderer is null, then we get the default renderer
        if(renderer == null){
            renderer = studentsTbl.getTableHeader().getDefaultRenderer();
        }
        //Here we get the rendered component within the cell
        //-1 refers to the header within the JTable
        Component comp = renderer.getTableCellRendererComponent(studentsTbl, value, false, false, -1, colNumber);
        //Return the width of this component (the amount of space the header string takes up) + spacing
        return (int)(comp.getPreferredSize().width + studentsTbl.getIntercellSpacing().width + COLUMN_PADDING);
    }
}
