package de.tum.vardoc;


import com.intellij.openapi.components.Service;
import com.intellij.psi.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public final class ClassParser {

    public String parse(PsiClass psclass) {
        return Arrays
                .stream(psclass.getChildren())
                .filter(element -> !(element instanceof PsiComment))
                .map(PsiElement::getText)
                .collect(Collectors.joining());
    }
}