package ca.uwo.csd.cs2212.team10;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * The ButtonColumn class provides a renderer and an editor that looks like a
 * JButton.
 *
 * @author team10
 */
public class ButtonColumn extends AbstractCellEditor
                            implements TableCellRenderer {

    private final JTable table;
    private final Border originalBorder;
    private final Border focusBorder;
    private final JButton renderButton;
    private final JButton editButton;

    private Object editorValue;

    public ButtonColumn(JTable table) {
        this.table = table;

        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        originalBorder = editButton.getBorder();
        focusBorder = new LineBorder(Color.BLUE);

    }

    @Override
    public Object getCellEditorValue() {
        return editorValue;
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        if (hasFocus) {
            renderButton.setBorder(focusBorder);
        } else {
            renderButton.setBorder(originalBorder);
        }

        renderButton.setText((value == null) ? "" : value.toString());
        return renderButton;
    }
}
