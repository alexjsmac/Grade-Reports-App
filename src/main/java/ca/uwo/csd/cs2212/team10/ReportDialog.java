package ca.uwo.csd.cs2212.team10;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;

/**
 * Dialog to handle report saving
 *
 * @author team10
 */
public class ReportDialog extends JDialog {
    private int retval;
    private Object[] output;
    private static List<Student> students;
    
    private JButton cancel;
    private JScrollPane jScrollPane1;
    private JButton ok;
    private JButton pathBtn;
    private JLabel pathLabel;
    private JTextField pathTxt;
    private JTable studentsTbl;

    public ReportDialog(Frame parent, boolean modal, List<Student> studentsList) {
        super(parent, modal);
        students = studentsList;
        initComponents();
        initTable();

    }

    private void initComponents() {
        pathLabel = new JLabel();
        jScrollPane1 = new JScrollPane();
        studentsTbl = new JTable();
        ok = new JButton();
        cancel = new JButton();
        pathTxt = new JTextField();
        pathBtn = new JButton();

        setTitle("Save Reports");
        setMinimumSize(new Dimension(100, 100));
        setResizable(false);

        pathLabel.setText("Select destination folder:");

        jScrollPane1.setViewportView(studentsTbl);

        ok.setText("Save");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        pathTxt.setEditable(false);

        pathBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folder.png"))); // NOI18N
        pathBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pathBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ok)
                        .addGap(18, 18, 18)
                        .addComponent(cancel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pathLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pathTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pathBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pathTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pathBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel)
                    .addComponent(ok))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void initTable() {
        DialogTableModel tblModel = new DialogTableModel(students);
        studentsTbl.setModel(tblModel);

        studentsTbl.setAutoCreateRowSorter(true);
        studentsTbl.setRowSelectionAllowed(true);
        studentsTbl.getRowSorter().toggleSortOrder(1);
        studentsTbl.setGridColor(Color.gray);
        studentsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        studentsTbl.setRowHeight(22);

        TableColumn tc = studentsTbl.getColumnModel().getColumn(0);
        tc.setCellEditor(studentsTbl.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(studentsTbl.getDefaultRenderer(Boolean.class));
        tc.setHeaderRenderer(new CheckBoxTableHeader(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Object source = e.getSource();
                if (source instanceof AbstractButton == false) {
                    return;
                }
                boolean checked = e.getStateChange() == ItemEvent.SELECTED;
                for (int x = 0, y = studentsTbl.getRowCount(); x < y; x++) {
                    studentsTbl.setValueAt(new Boolean(checked), x, 0);
                }
            }
        }));
    }

    private void okActionPerformed(ActionEvent evt) {
        if (pathTxt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "You must choose a folder.");
            return;
        }
        retval = 0;
        
        List<Student> selectedStudents = new ArrayList();
        for (int i = 0; i < students.size(); i++) {
            if ((boolean)studentsTbl.getValueAt(i, 0))
                selectedStudents.add(students.get(i));
        }
        
        output = new Object[] {pathTxt.getText(), selectedStudents};
        setVisible(false);
    }

    private void cancelActionPerformed(ActionEvent evt) {
        retval = 1;
        setVisible(false);
    }

    private void pathBtnActionPerformed(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int option = chooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getCurrentDirectory();
            pathTxt.setText(file.getAbsolutePath());
        }
        
    }

    public int showDialog() {
        setVisible(true);
        return retval;
    }
    
    public Object[] getOutput() {
        return output;
    }
}
