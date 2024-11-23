package de.tum.vardoc;

import com.intellij.psi.PsiClass;

import java.util.HashMap;
import java.util.Map;

public abstract class VardocBaseComponent {

    private final Map<String, VardocPackage> packages;
    private final Map<String, PsiClass> classes;

    public VardocBaseComponent() {
        this.packages = new HashMap<>();
        this.classes = new HashMap<>();
    }

    public void update(VardocClassRequest updateRequest) {
        if (updateRequest.arrivedAtClass()) {
            String className = updateRequest.getClassName();
            PsiClass psiClass = this.classes.get(className);
            if (psiClass == null) {
                System.out.println("Add new class: " + className);
                this.classes.put(className, updateRequest.getPsiClass());
            } else {
                System.out.println("Update class (nop): " + className);
            }
            return;
        }

        String packageName = updateRequest.getNextPackageName();
        VardocPackage vardocPackage = this.packages.computeIfAbsent(packageName, VardocPackage::new);
        vardocPackage.update(updateRequest);
    }

    @Override
    public String toString() {
        return "VardocBaseComponent{" +
                "packages=" + packages +
                ", classes=" + classes +
                '}';
    }
}
