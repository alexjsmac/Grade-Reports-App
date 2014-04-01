package ca.uwo.csd.cs2212.team10;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
import javax.swing.border.Border;

/**
 *
 * @author team10
 */
class CheckBoxTableHeader extends JCheckBox
        implements TableCellRenderer, MouseListener {

    protected CheckBoxTableHeader rendererComponent;
    protected int column;
    protected boolean mousePressed = false;

    public CheckBoxTableHeader(ItemListener itemListener) {
        rendererComponent = this;
        rendererComponent.addItemListener(itemListener);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (table != null) {
            JTableHeader header = table.getTableHeader();
            if (header != null) {
                rendererComponent.setForeground(header.getForeground());
                rendererComponent.setBackground(header.getBackground());
                rendererComponent.setFont(header.getFont());
                rendererComponent.setHorizontalAlignment(SwingConstants.CENTER);
                header.addMouseListener(rendererComponent);
            }
        }
        setColumn(column);
        Border border = UIManager.getBorder("TableHeader.cellBorder");
        setBorder(border);
        return rendererComponent;
    }

    protected void setColumn(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    protected void handleClickEvent(MouseEvent e) {
        if (mousePressed) {
            mousePressed = false;
            JTableHeader header = (JTableHeader) (e.getSource());
            JTable tableView = header.getTable();
            TableColumnModel columnModel = tableView.getColumnModel();
            int viewColumn = columnModel.getColumnIndexAtX(e.getX());
            int column = tableView.convertColumnIndexToModel(viewColumn);

            if (viewColumn == this.column && e.getClickCount() == 1 && column != -1) {
                doClick();
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        handleClickEvent(e);
        ((JTableHeader) e.getSource()).repaint();
    }

    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
