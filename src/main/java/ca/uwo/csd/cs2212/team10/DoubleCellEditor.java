package ca.uwo.csd.cs2212.team10;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Implements the Cell Editor used in the JTable
 *
 * @author team10
 */
public class DoubleCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JTextField field;

    public DoubleCellEditor() {
        this.field = new JTextField();
    }

    @Override
    public Object getCellEditorValue() {
        return Double.valueOf(field.getText());
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {

        field.setText(String.valueOf(value));
        field.requestFocus();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                field.selectAll();
            }
        });

        return field;
    }

}
