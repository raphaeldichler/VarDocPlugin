package de.tum.vardoc;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;


import java.awt.*;

public class IdentifyAndActOnElementAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // Get the project
        Project project = event.getProject();
        if (project == null) return;

        // Get the current editor
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) return;

        // Get the current file
        VirtualFile virtualFile = FileEditorManager.getInstance(project).getSelectedFiles()[0];
        if (virtualFile == null) return;

        // Get the PSI file
        PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
        if (psiFile == null) return;

        // Find the element at the current cursor position
        int caretOffset = editor.getCaretModel().getOffset();
        PsiElement element = psiFile.findElementAt(caretOffset);
        if (element == null) {
            showHint(editor, "No element found at cursor position.");
            return;
        }

        // Highlight the element in the editor
        highlightElement(editor, element);

        // Provide feedback
        showHint(editor, "Element found: " + element.getText() + "\nClass: " + element.getClass().getSimpleName());
    }

    private void highlightElement(Editor editor, PsiElement element) {
        MarkupModel markupModel = editor.getMarkupModel();
        TextAttributes attributes = new TextAttributes();
        attributes.setBackgroundColor(new Color(255, 200, 200)); // Light red background
        attributes.setEffectColor(Color.RED);
        attributes.setEffectType(EffectType.WAVE_UNDERSCORE); // Correct usage of EffectType

        int startOffset = element.getTextRange().getStartOffset();
        int endOffset = element.getTextRange().getEndOffset();

        markupModel.addRangeHighlighter(
                startOffset,
                endOffset,
                0, // Layer (0 for default layer)
                attributes,
                HighlighterTargetArea.EXACT_RANGE
        );
    }

    private void showHint(Editor editor, String message) {
        editor.getContentComponent().getToolkit().beep(); // Optional: auditory feedback
        // Alternatively, you can use a popup or a balloon to display the message
        System.out.println(message); // For debugging purposes
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        // Enable action only if a project is open and a file is selected
        Project project = e.getProject();
        e.getPresentation().setEnabledAndVisible(project != null);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        // Choose EDT since this action updates the UI
        return ActionUpdateThread.EDT;
    }
}
