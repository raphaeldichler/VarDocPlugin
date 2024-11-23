package de.tum.vardoc;


import com.intellij.codeInsight.completion.AllClassesGetter;
import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Processor;

import java.util.Random;

@Service
public final class VardocService {

    private final int rand = new Random().nextInt(100);

    public void print(Project project) {

        // The processor should handle PsiFile objects
        Processor<PsiClass> processor = psiClass -> {
            ClassParser myService = ApplicationManager.getApplication().getService(ClassParser.class);
            System.out.println("processing ... name:" + psiClass.getName());
            // do your actual work here

            System.out.println(myService.parse(psiClass));

            return true;
        };

        GlobalSearchScope scope = GlobalSearchScope.projectScope(project);
        PlainPrefixMatcher matcher = new PlainPrefixMatcher("");
        AllClassesGetter.processJavaClasses(matcher, project, scope, processor);


        System.out.println("[" + this +  "] your random value is " + rand);
    }

}
