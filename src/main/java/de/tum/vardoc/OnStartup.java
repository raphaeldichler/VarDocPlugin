package de.tum.vardoc;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OnStartup implements ProjectActivity {


    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        new FileChangeListener(project);

        ApplicationManager.getApplication().invokeAndWait(() -> {
            InitialisationVardocService initialisationVardocService =
                    ApplicationManager.getApplication().getService(InitialisationVardocService.class);

            ApplicationManager.getApplication().runReadAction(() -> {
                VardocProject vardocProject = initialisationVardocService.initialize(project);

            });
        });

        return null;
    }
}
