package org.example.common.token;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Token {
    private Long id;
    private Long timestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();

    public Token(Long id) {
        this.id = id;
    }

    public Token() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Token(String token) throws InvocationTargetException, IllegalAccessException {
        String[] fieldValues = token.split(";");
        List<Method> methods = this.filterMethod(this.getClass().getMethods(), "set");

        for (int i = 0; i < fieldValues.length; ++i) {
            Method method = (Method) methods.get(i);
            Class typeClass = method.getParameters()[0].getType();
            if (typeClass.toString().equals(Long.class.toString())) {
                method.invoke(this, Long.valueOf(fieldValues[i]));
            } else if (typeClass.isEnum()) {
                method.invoke(this, Enum.valueOf(typeClass, fieldValues[i]));
            } else if (typeClass.toString().equals(Integer.class.toString())) {
                method.invoke(this, Integer.valueOf(fieldValues[i]));
            } else if (typeClass.toString().equals(Boolean.class.toString())) {
                method.invoke(this, Boolean.valueOf(fieldValues[i]));
            } else {
                method.invoke(this, fieldValues[i]);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Method> methods = this.filterMethod(this.getClass().getMethods(), "get");
        Iterator var3 = methods.iterator();

        while (var3.hasNext()) {
            Method method = (Method) var3.next();

            try {
                Object fieldValue = method.invoke(this);
                stringBuilder.append(fieldValue).append(";");
            } catch (InvocationTargetException | IllegalAccessException var6) {
                throw new RuntimeException(var6);
            }
        }

        return stringBuilder.toString().substring(0, stringBuilder.length() - 1);
    }

    private List<Method> filterMethod(Method[] methods, String key) {
        return Arrays.stream(methods)
                .filter((method) -> !method.toString().contains("Object") && method.getName().startsWith(key))
                .sorted(Comparator.comparing(Method::getName))
                .collect(Collectors.toList());
    }
}

