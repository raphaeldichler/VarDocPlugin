package de.tum.vardocplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class PopupDialogAction extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("My silly action");
        int foo = 1;

    }
}
