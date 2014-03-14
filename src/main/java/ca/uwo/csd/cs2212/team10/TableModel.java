package ca.uwo.csd.cs2212.team10;

import java.util.List;
import javax.swing.table.AbstractTableModel;

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
        IDX_ASSIG_AVG = IDX_AVG - 1;
        IDX_EXAM_AVG = IDX_ASSIG_AVG - 1;
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
            return "Total Average";
        else if (columnIndex == IDX_ASSIG_AVG)
            return "Assignments Average";
        else if (columnIndex == IDX_EXAM_AVG)
            return "Exams Average";
        else if ((columnIndex > IDX_NUMBER) && columnIndex < IDX_AVG)
            return (deliverables.get((columnIndex - 2)).getName() + " (" + String.valueOf(deliverables.get(columnIndex - 2).getWeight()) + ")");
        else 
            return null;      
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if ((rowIndex < 0) || (rowIndex >= students.size()))
            return null;
        
        Student selectedStudent = students.get(rowIndex);
        if (columnIndex == IDX_NAME)
            return (selectedStudent.getLastName() + ", " + selectedStudent.getFirstName());
        else if (columnIndex == IDX_NUMBER)
            return selectedStudent.getNum();
        else if (columnIndex == IDX_AVG)
            return selectedStudent.calcAverage();
        else if (columnIndex == IDX_ASSIG_AVG)
            return selectedStudent.calcAverage(Deliverable.ASSIGNMENT_TYPE);
        else if (columnIndex == IDX_EXAM_AVG)
            return selectedStudent.calcAverage(Deliverable.EXAM_TYPE);
        else if ((columnIndex > IDX_NUMBER) && columnIndex < IDX_AVG) {
            Deliverable deliverable = deliverables.get((columnIndex - 2));
            return selectedStudent.getGrade(deliverable);
        }
        else 
            return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if ((rowIndex < 0) || (rowIndex >= students.size()) || (this.isCellEditable(rowIndex, columnIndex)))
            return;
            
        Student selectedStudent = students.get(rowIndex);
        Deliverable deliverable = deliverables.get((columnIndex - 1));
        selectedStudent.setGrade(deliverable, (Double)aValue);

        fireTableCellUpdated(rowIndex, columnIndex);
        fireTableDataChanged();
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if ((columnIndex > IDX_NUMBER) && (columnIndex < IDX_EXAM_AVG))
            return true;
        else
            return false;
    }
 
}
