package de.tum.vardoc;

public class JavaCodeWrapperStrategy implements CodeWrapperStrategy {

    @Override
    public String wrap(String code) {
        return "```java" +
                '\n' +
                code +
                '\n' +
                "```";
    }
}
