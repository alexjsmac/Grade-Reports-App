package ca.uwo.csd.cs2212.team10;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class to set the Model of the Student's Table
 * @author team10
 */

public class TableModel extends AbstractTableModel {
    private final static int IDX_NAME = 0;
    private final static int IDX_NUMBER = 1;
    
    private int IDX_AVG;
    private int IDX_ASSIG_AVG;
    private int IDX_EXAM_AVG;
    private int COLUMN_COUNT;
    private final List<Student> students;
    private final List<Deliverable> deliverables;

    public TableModel(List<Student> studentsList, List<Deliverable> deliverablesList) {
        students = studentsList;
        deliverables = deliverablesList;
        COLUMN_COUNT = 5 + (deliverables.size());
        IDX_AVG = COLUMN_COUNT - 1;
        IDX_EXAM_AVG = IDX_AVG - 1;        
        IDX_ASSIG_AVG = IDX_EXAM_AVG - 1;
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
        if (columnIndex > IDX_NUMBER)
            return Double.class;
        else 
            return String.class;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == IDX_NAME)
            return "Student Name";
        else if (columnIndex == IDX_NUMBER)
            return "Student Number";
        else if (columnIndex == IDX_AVG)
            return "Overall Average";
        else if (columnIndex == IDX_ASSIG_AVG)
            return "Assignments Average";
        else if (columnIndex == IDX_EXAM_AVG)
            return "Exams Average";
        else
            return deliverables.get(columnIndex - 2).toString();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student selectedStudent = students.get(rowIndex);
        
        if (columnIndex == IDX_NAME)
            return (selectedStudent.getLastName() + ", " + selectedStudent.getFirstName());
        else if (columnIndex == IDX_NUMBER)
            return selectedStudent.getNum();
        else if (columnIndex == IDX_ASSIG_AVG)
            return selectedStudent.calcAverage(Deliverable.ASSIGNMENT_TYPE);
        else if (columnIndex == IDX_EXAM_AVG)
            return selectedStudent.calcAverage(Deliverable.EXAM_TYPE);
        else if (columnIndex == IDX_AVG)
            return selectedStudent.calcAverage();
        else
            return selectedStudent.getGrade(deliverables.get(columnIndex - 2));
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Student selectedStudent = students.get(rowIndex);
        Deliverable deliverable = deliverables.get(columnIndex - 2);
        selectedStudent.setGrade(deliverable, (Double)aValue);

        fireTableCellUpdated(rowIndex, columnIndex);
        fireTableDataChanged();
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex > IDX_NUMBER && columnIndex < IDX_ASSIG_AVG)
            return true;
        else
            return false;
    }
 
    public boolean isDeliverableColumn (int columnIndex) {
        return isCellEditable(0, columnIndex);
    }
    
    public boolean isStudentColumn (int columnIndex) {
        if (columnIndex == IDX_NUMBER || columnIndex == IDX_NAME)
            return true;
        else
            return false;
    }
    
    public int getDeliverableIndex(int columnIndex) {
        return columnIndex - 2;
    }
}

