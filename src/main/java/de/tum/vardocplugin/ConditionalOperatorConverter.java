package de.tum.vardocplugin;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Consumer;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements an intention action to replace a ternary statement with if-then-else.
 */
@NonNls
final class ConditionalOperatorConverter extends PsiElementBaseIntentionAction implements IntentionAction {

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
        return true;
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
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element)
            throws IncorrectOperationException {

        // One way to show a list.
        JBPopupFactory
                .getInstance()
                .createListPopup(new MyList(
            // Another way to show a list, using the builder.
            JBPopupFactory
                    .getInstance()
                    .createPopupChooserBuilder(new ArrayList<>(List.of("one", "two", "three")))
                    .setTitle("PopupChooserBuilder")
                    .setItemChosenCallback(c -> System.out.println("Callback " + c))
              .createPopup()))
        .showInBestPositionFor(editor);

        System.out.println("Invoke");
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
        return "SDK: Convert ternary operator to if statement";
    }

}