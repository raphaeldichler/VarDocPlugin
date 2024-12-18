package de.tum.vardoc;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PriorityAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.impl.ImaginaryEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.IconLoader;

import com.intellij.psi.*;
import com.intellij.psi.PsiVariable;

import com.intellij.refactoring.rename.RenameProcessor;

import com.intellij.ui.components.JBList;

import com.intellij.util.IncorrectOperationException;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Implements an intention action to replace a ternary statement with if-then-else.
 */
@NonNls
final class ConditionalOperatorConverter extends PsiElementBaseIntentionAction implements IntentionAction, PriorityAction {

    private static final Logger log = Logger.getInstance(ConditionalOperatorConverter.class);

    /**
     * Checks whether this intention is available at the caret offset in file - the caret must sit just before a "?"
     * character in a ternary statement. If this condition is met, this intention's entry is shown in the available
     * intentions list.
     *
     * <p>Note: this method must do its checks quickly and return.</p>
     *
     * @param project a reference to the Project object being edited.
     * @param editor  a reference to the object editing the project source
     * @param element a reference to the PSI element currently under the caret
     * @return {@code true} if the caret is in a literal string element, so this functionality should be added to the
     * intention menu or {@code false} for all other types of caret positions
     */
    public boolean isAvailable(@NotNull Project project, Editor editor, @Nullable PsiElement element) {
        if (element == null) {
            return false;
        }
        return (element.getParent() instanceof PsiVariable) ||
                (element.getParent() instanceof PsiMethod) ||
                (element.getParent() instanceof PsiReferenceExpression);
    }

    /**
     * Modifies the PSI to change a ternary expression to an if-then-else statement.
     * If the ternary is part of a declaration, the declaration is separated and moved above the if-then-else statement.
     * Called when user selects this intention action from the available intentions list.
     *
     * @param project a reference to the Project object being edited.
     * @param editor  a reference to the object editing the project source
     * @param element a reference to the PSI element currently under the caret
     * @throws IncorrectOperationException Thrown by underlying (PSI model) write action context
     *                                     when manipulation of the PSI tree fails.
     */
    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        if (editor instanceof ImaginaryEditor) {
            return;
        }

        String[] suggestions = {"Suggestion1", "Suggestion2", "Suggestion3", "Suggestion4"};

        JBList<String> suggestionList = new JBList<>(suggestions);
        suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestionList.setSelectedIndex(0); // Default selection


        JBPopup popup = JBPopupFactory.getInstance()
                .createListPopupBuilder(suggestionList)
                .setRequestFocus(true)
                .setResizable(false)
                .setMovable(false)
                .createPopup();


        suggestionList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                VardocService myService = ApplicationManager.getApplication().getService(VardocService.class);



                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    myService.print(project);
                    String selectedValue = suggestionList.getSelectedValue();
                    if (selectedValue == null) {
                        popup.cancel();
                    }

                    var parent = element.getParent();
                    if (parent instanceof PsiVariable variable) {
                        renameElement(variable, selectedValue, project);
                    }
                    if (parent instanceof PsiMethod method) {
                        renameElement(method, selectedValue, project);

                    }
                    if (parent instanceof PsiReferenceExpression referenceExpression) {
                        renameElement(referenceExpression.resolve(), selectedValue, project);

                    }
                }
            }


        });

        popup.showInBestPositionFor(editor);
    }

    private void renameElement(PsiElement element, String newName, Project project) {
        ApplicationManager.getApplication().invokeLater(() -> {
            WriteCommandAction.runWriteCommandAction(project, () -> {
                try {
                    RenameProcessor renameProcessor = new RenameProcessor(
                            project,
                            element,
                            newName,
                            true,
                            true
                    );
                    ApplicationManager.getApplication().invokeLater(renameProcessor);
                } catch (Exception ex) {
                    log.warn(ex.getMessage());
                }
            });
        });
    }


    /**
     * If this action is applicable, returns the text to be shown in the list of intention actions available.
     */
    @NotNull
    public String getText() {
        return getFamilyName();
    }

    /**
     * Returns text for name of this family of intentions.
     * It is used to externalize "auto-show" state of intentions.
     * It is also the directory name for the descriptions.
     *
     * @return the intention family name.
     */
    @NotNull
    public String getFamilyName() {
        return "VarDoc";
    }

    @Override
    public @NotNull Priority getPriority() {
        return Priority.HIGH;
    }
}
