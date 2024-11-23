package de.tum.vardoc;

import com.intellij.codeInsight.completion.AllClassesGetter;
import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;

@Service
public final class InitialisationVardocService {


    public VardocProject initialize(Project project) {
        VardocProject p = new VardocProject();

        AllClassesGetter.processJavaClasses(
                new PlainPrefixMatcher(""),
                project,
                GlobalSearchScope.projectScope(project),
                (PsiClass psiClass) -> {
                    p.update(new VardocClassRequest(psiClass));
                    return true;
                });


        System.out.println(p);
        return p;
    }

}
