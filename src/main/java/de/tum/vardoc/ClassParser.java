package de.tum.vardoc;


import com.intellij.openapi.components.Service;
import com.intellij.psi.*;

@Service
public final class ClassParser {

    public String parse(PsiClass psclass) {
        StringBuilder details = new StringBuilder();

        // Class name
        String className = psclass.getQualifiedName();
        details.append("Class Name: ").append(className).append("\n");

        // List of Extends and Type
        PsiReferenceList extendList = psclass.getExtendsList();
        PsiClassType[] extendListType = psclass.getExtendsListTypes();
        details.append("Extends List: ").append(extendList != null ? extendList.getText() : "None").append("\n");
        details.append("Extends List Types: ").append(arrayToString(extendListType)).append("\n");

        // List of Implements and Type
        PsiReferenceList implementList = psclass.getImplementsList();
        PsiClassType[] implementListType = psclass.getImplementsListTypes();
        details.append("Implements List: ").append(implementList != null ? implementList.getText() : "None").append("\n");
        details.append("Implements List Types: ").append(arrayToString(implementListType)).append("\n");

        // List of fields
        PsiField[] fields = psclass.getAllFields();
        details.append("Fields: ").append(arrayToString(fields)).append("\n");

        // List of methods
        PsiMethod[] methods = psclass.getAllMethods();
        details.append("Methods: ").append(arrayToString(methods)).append("\n");

        // List of constructors
        PsiMethod[] constructors = psclass.getConstructors();
        details.append("Constructors: ").append(arrayToString(constructors)).append("\n");

        // List of inner classes
        PsiClass[] innerClasses = psclass.getAllInnerClasses();
        details.append("Inner Classes: ").append(arrayToString(innerClasses)).append("\n");

        return details.toString();
    }

    // Helper method to convert arrays to a string
    private <T> String arrayToString(T[] array) {
        if (array == null || array.length == 0) {
            return "None";
        }
        StringBuilder sb = new StringBuilder();
        for (T item : array) {
            sb.append(item.toString()).append(", ");
        }
        // Remove trailing comma and space
        return sb.substring(0, sb.length() - 2);
    }


}
