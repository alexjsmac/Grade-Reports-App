package ca.uwo.csd.cs2212.team10;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Class to set the Model of the Student's Table
 * @author team10
 */

public class TableModel extends AbstractTableModel {
       
    private final static int IDX_FIRST_NAME = 0;
    private final static int IDX_LAST_NAME = 1;
    private final static int IDX_STUDENT_NUMBER = 2;
    private final static int IDX_EMAIL = 3;
    private final static int IDX_AVG = 4;
    private final static int IDX_ASSIG_AVG = 5;
    private final static int IDX_EXAM_AVG = 6;
    
    private int COLUMN_COUNT;
    private final ArrayList<Student> students;
    private final ArrayList<Deliverable> deliverables;

    public TableModel(ArrayList<Student> studentsList, ArrayList<Deliverable> deliverablesList) {
        students = studentsList;
        deliverables = deliverablesList;
        COLUMN_COUNT = 7 + (deliverables.size());
    }
    
    public List<Student> getStudentsList() {
        return students;
    }
    
    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex > 3)
            return Double.class;
        else 
            return String.class;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == IDX_FIRST_NAME)
            return "First Name";
        else if (columnIndex == IDX_LAST_NAME)
            return "Last Name";
        else if (columnIndex == IDX_STUDENT_NUMBER)
            return "Student Number";
        else if (columnIndex == IDX_EMAIL)
            return "Email";
        else if (columnIndex == IDX_AVG)
            return "Total Average";
        else if (columnIndex == IDX_ASSIG_AVG)
            return "Assignments Average";
        else if (columnIndex == IDX_EXAM_AVG)
            return "Exams Average";
        else if ((columnIndex >= 7) && columnIndex < COLUMN_COUNT)
            return deliverables.get((columnIndex - 7)).getName();
        else 
            return null;      
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if ((rowIndex < 0) || (rowIndex >= students.size()))
            return null;
        
        Student selectedStudent = students.get(rowIndex);
        if (columnIndex == IDX_FIRST_NAME)
            return selectedStudent.getFirstName();
        else if (columnIndex == IDX_LAST_NAME)
            return selectedStudent.getLastName();
        else if (columnIndex == IDX_STUDENT_NUMBER)
            return selectedStudent.getNum();
        else if (columnIndex == IDX_EMAIL)
            return selectedStudent.getEmail();
        else if (columnIndex == IDX_AVG)
            return selectedStudent.calcAverage();
        else if (columnIndex == IDX_ASSIG_AVG)
            return selectedStudent.calcAverage(Deliverable.ASSIGNMENT_TYPE);
        else if (columnIndex == IDX_EXAM_AVG)
            return selectedStudent.calcAverage(Deliverable.EXAM_TYPE);
        else if ((columnIndex >= 5) && columnIndex < COLUMN_COUNT) {
            Deliverable deliverable = deliverables.get((columnIndex - 7));
            return selectedStudent.getGrade(deliverable);
        }
        else 
            return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if ((rowIndex < 0) || (rowIndex >= students.size()) || columnIndex <= IDX_EXAM_AVG || columnIndex >= COLUMN_COUNT)
            return;
            
        Student selectedStudent = students.get(rowIndex);
        Deliverable deliverable = deliverables.get((columnIndex - 7));
        selectedStudent.setGrade(deliverable, (Double)aValue);

        fireTableCellUpdated(rowIndex, columnIndex);
        fireTableDataChanged();
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      if (columnIndex > IDX_EXAM_AVG && columnIndex < COLUMN_COUNT)
          return true;
      else
          return false;
    }
 
}
