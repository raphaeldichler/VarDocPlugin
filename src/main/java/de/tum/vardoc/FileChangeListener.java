package de.tum.vardoc;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.AsyncFileListener;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FileChangeListener implements Disposable {

    public FileChangeListener(Project project) {
        VirtualFileManager.getInstance().addAsyncFileListener(new FileListener(project), this);
    }

    @Override
    public void dispose() {
    }

    private record FileListener(Project project) implements AsyncFileListener {

        private static final Logger log = Logger.getInstance(FileListener.class);

        @Override
        public @Nullable ChangeApplier prepareChange(@NotNull List<? extends @NotNull VFileEvent> events) {
            for (VFileEvent event : events) {
                if (!event.isValid()) {
                    System.out.println("File not failed, but saved...");
                }


                if (event.getFile() != null) {
                    VirtualFile virtualFile = event.getFile();
                    PsiFile psiFile = PsiManager.getInstance(this.project).findFile(virtualFile);

                    if (psiFile instanceof PsiJavaFile f) {
                        StringBuilder sb = new StringBuilder();
                        for (var c : f.getClasses()) {
                            sb.append(c.getName());

                            for (var m : c.getMethods()) {
                                sb.append(m.getBody()).append('\n');
                            }

                            System.out.println(sb);

                        }
                    }


                    System.out.println("VFile name: " + event.getFile().getName());
                } else {
                    System.out.println("VFile name is null");
                }
            }
            return null;
        }
    }

}
