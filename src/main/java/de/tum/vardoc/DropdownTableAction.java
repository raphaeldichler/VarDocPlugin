package de.tum.vardoc;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DropdownTableAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // Create a DefaultTableModel with data and column names
        DefaultTableModel tableModel = new DefaultTableModel(
                new String[][] {
                        {"1", "Alice", "Engineer"},
                        {"2", "Bob", "Designer"},
                        {"3", "Charlie", "Manager"}
                },
                new String[] {"ID", "Name", "Role"}
        );

        // Create the JBTable with the TableModel
        JBTable table = new JBTable(tableModel, null);
        table.setAutoResizeMode(JBTable.AUTO_RESIZE_ALL_COLUMNS); // Optional: Resize columns to fit
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); // Single row selection

        // Add a ListSelectionListener to handle selection changes
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                }
            }
        });

        // Wrap the JBTable in a JBScrollPane
        JBScrollPane scrollPane = new JBScrollPane(table);

        // Create and show the popup with focus on the table
        JBPopupFactory.getInstance()
                .createComponentPopupBuilder(scrollPane, null)
                .setTitle("Employee Details")
                .setFocusable(true) // Allow focus
                .setRequestFocus(true) // Request focus for keyboard navigation
                .setResizable(true)
                .setMovable(true)
                .createPopup()
                .showInFocusCenter();

        // Request focus for the table explicitly
        table.requestFocusInWindow();
    }
}
