package ca.uwo.csd.cs2212.team10;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;

/**
 * Dialog to handle email sending
 *
 * @author team10
 */
public class EmailDialog extends JDialog {
    private int retval;
    private Object[] output;
    private static List<Student> students;
    
    private JButton cancel;
    private JTextField email;
    private JLabel emailLabel;
    private JScrollPane jScrollPane1;
    private JButton ok;
    private JTextField smtp;
    private JLabel smtpLabel;
    private JTextField smtpPassword;
    private JTextField smtpPort;
    private JLabel smtpPortLabel;
    private JLabel smtpPsswdLabel;
    private JLabel smtpUserLabel;
    private JTextField smtpUsername;
    private JTable studentsTbl;

    public EmailDialog(Frame parent, boolean modal, List<Student> studentsList) {
        super(parent, modal);
        students = studentsList;
        initComponents();
        initTable();
    }

    private void initComponents() {
        emailLabel = new JLabel();
        smtpLabel = new JLabel();
        smtpUserLabel = new JLabel();
        smtpPassword = new JTextField();
        smtp = new JTextField();
        smtpPort = new JTextField();
        smtpPsswdLabel = new JLabel();
        smtpUsername = new JTextField();
        jScrollPane1 = new JScrollPane();
        studentsTbl = new JTable();
        ok = new JButton();
        cancel = new JButton();
        smtpPortLabel = new JLabel();
        email = new JTextField();

        setTitle("Send Email");
        setMinimumSize(new Dimension(100, 100));
        setResizable(false);

        emailLabel.setText("From Email Address:");

        smtpLabel.setText("SMTP Server:");

        smtpUserLabel.setText("SMTP Username:");

        smtpPsswdLabel.setText("SMTP Password:");

        jScrollPane1.setViewportView(studentsTbl);

        ok.setText("Send Email");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        smtpPortLabel.setText("SMTP Port:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ok)
                        .addGap(18, 18, 18)
                        .addComponent(cancel))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(smtpLabel)
                                .addComponent(smtpPortLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(smtp, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(smtpUserLabel))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(smtpPort, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(smtpPsswdLabel)))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(smtpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(smtpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(emailLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(smtpLabel)
                    .addComponent(smtp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(smtpUserLabel)
                    .addComponent(smtpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(smtpPortLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(smtpPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(smtpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(smtpPsswdLabel)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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

    private void okActionPerformed(java.awt.event.ActionEvent evt) {
        retval = 0;
        
        List<Student> selectedStudents = new ArrayList();
        for (int i = 0; i < students.size(); i++) {
            if ((boolean)studentsTbl.getValueAt(i, 0))
                selectedStudents.add(students.get(i));
        }
        
        output = new Object[] {email.getText(), smtp.getText(), smtpPort.getText(), smtpUsername.getText(), smtpPassword.getText(), selectedStudents};
        
        setVisible(false);
    }

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
        retval = 1;
        setVisible(false);
    }

    public int showDialog() {
        setVisible(true);
        return retval;
    }
    
    public Object[] getOutput() {
        return output;
    }
}
