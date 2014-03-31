package ca.uwo.csd.cs2212.team10;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
        return new BigDecimal(Double.valueOf(field.getText())).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {

        field.setHorizontalAlignment(JTextField.RIGHT);
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
