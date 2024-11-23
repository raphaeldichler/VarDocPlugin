package de.tum.vardoc;

import com.intellij.psi.PsiClass;

import java.util.Objects;

public class VardocClassRequest {

    private final String[] qualifiedNames;
    private final PsiClass psiClass;
    private final String className;
    private int idx;

    public VardocClassRequest(PsiClass psiClass) {
        this.psiClass = psiClass;
        this.qualifiedNames = Objects.requireNonNull(psiClass.getQualifiedName()).split("\\.");
        this.idx = 0;
        this.className = psiClass.getName();
    }

    public PsiClass getPsiClass() {
        return this.psiClass;
    }

    public String getClassName() {
        return this.className;
    }

    public String getNextPackageName() {
        return this.qualifiedNames[this.idx++];
    }

    public boolean arrivedAtClass() {
        return this.idx == this.qualifiedNames.length - 2;
    }

}
