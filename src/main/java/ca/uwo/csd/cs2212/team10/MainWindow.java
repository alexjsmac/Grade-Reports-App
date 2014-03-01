package ca.uwo.csd.cs2212.team10;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import java.util.ArrayList;
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
    private JTable coursesTbl;
    private JComboBox dropDownCourses;
    private JButton addCourseBtn, editCourseBtn, delCourseBtn;
    private JMenuBar jMenuBar;
    private JMenu fileMenu, coursesMenu;
    private JMenuItem exitMenuItem, addMenuItem, editMenuItem, delMenuItem;
    
    /* Constructor */
    public MainWindow(){
		gradebook = loadGradebook();
        initComponents();
    }
    
    /* Private methods */             
    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        coursesTbl = new JTable();
        dropDownCourses = new JComboBox(gradebook.getCourseList().toArray());
        addCourseBtn = new JButton();
        editCourseBtn = new JButton();
        delCourseBtn = new JButton();
        jMenuBar = new JMenuBar();
        fileMenu = new JMenu();
        exitMenuItem = new JMenuItem();
        coursesMenu = new JMenu();
        addMenuItem = new JMenuItem();
        editMenuItem = new JMenuItem();
        delMenuItem = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                onExit();
            }
        });
		
        setTitle("Gradebook");

        coursesTbl.setModel(new DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
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

        setJMenuBar(jMenuBar);

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
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }                     

    private void dropDownItemChanged(ItemEvent evt) {
        gradebook.setActiveCourse((Course)dropDownCourses.getSelectedItem());
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
