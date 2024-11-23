package de.tum.vardocplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.intellij.ui.components.JBScrollPane;

public class DropdownTableAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // Create a DefaultTableModel with data and column names
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][] {
                        {"1", "Alice", "Engineer"},
                        {"2", "Bob", "Designer"},
                        {"3", "Charlie", "Manager"}
                },
                new Object[] {"ID", "Name", "Role"}
        );

        // Create the JBTable with the TableModel
        JBTable table = new JBTable(tableModel, null);
        table.setAutoResizeMode(JBTable.AUTO_RESIZE_ALL_COLUMNS); // Optional: Resize columns to fit

        // Add a mouse listener for row click
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedName = table.getValueAt(selectedRow, 1).toString();
                    JOptionPane.showMessageDialog(null, "You selected: " + selectedName);
                }
            }
        });
        // Wrap the JBTable in a JBScrollPane
        JBScrollPane scrollPane = new JBScrollPane(table);


        // Display the table in a popup
        JBPopupFactory.getInstance()
                .createComponentPopupBuilder(scrollPane, null)
                .setTitle("Employee Details")
                .setResizable(true)
                .setMovable(true)
                .createPopup()
                .showInFocusCenter();
    }
}

