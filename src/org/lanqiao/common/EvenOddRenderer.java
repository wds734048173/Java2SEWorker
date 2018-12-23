package org.lanqiao.common;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class EvenOddRenderer implements TableCellRenderer{
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        DEFAULT_RENDERER.setOpaque(false);
        Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Color foreground, background;
        if (isSelected) {
            foreground = Color.RED;
            background = Color.YELLOW;
        }  else {
            foreground = Color.BLACK;
            background = Color.WHITE;
        }
        renderer.setForeground(foreground);
        renderer.setBackground(background);
        return renderer;
    }
}
