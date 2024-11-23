package de.tum.vardoc;


import com.intellij.codeInsight.completion.AllClassesGetter;
import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Processor;

import java.util.Random;

@Service
public final class VardocService {

    private final int rand = new Random().nextInt(100);

    public void print(Project project) {
        Processor<PsiClass> processor = psiClass -> {
            System.out.println("processing ... name:" + psiClass.getName());
            // do your actual work here
            psiClass.getAllMethods();
            return true;
        };

        GlobalSearchScope scope = GlobalSearchScope.projectScope(project);
        PlainPrefixMatcher matcher = new PlainPrefixMatcher("");
        AllClassesGetter.processJavaClasses(matcher, project, scope, processor);


        System.out.println("[" + this +  "] your random value is " + rand);
    }

}
