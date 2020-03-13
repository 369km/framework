package org.example.data.service;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @AUTHOR yan
 * @DATE 2020/2/12
 */


public class RowMapperService<T> implements RowMapper<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RowMapperService.class);
    private Class<T> clazz;

    public RowMapperService(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet == null ? null : this.oneRow(resultSet);
    }

    private T oneRow(ResultSet resultSet) {
        Method[] methodArr = this.getDeclaredMethods(this.clazz);
        Map<String, Field> fieldMap = this.getDeclaredField(this.clazz);
        return methodArr != null && methodArr.length > 0 ? this
                .getRowInfo(methodArr, fieldMap, resultSet) : null;
    }

    private Method[] getDeclaredMethods(Class clazz) {
        ArrayList methods;
        for (methods = new ArrayList(); clazz != null && clazz != Object.class;
             clazz = clazz.getSuperclass()) {
            Method[] mds = clazz.getDeclaredMethods();
            methods.addAll(Arrays.asList(mds));
        }

        return (Method[]) methods.toArray(new Method[methods.size()]);
    }

    private Map<String, Field> getDeclaredField(Class clazz) {
        HashMap fieldMap;
        for (fieldMap = new HashMap(); clazz != null && clazz != Object.class;
             clazz = clazz.getSuperclass()) {
            Field[] mds = clazz.getDeclaredFields();
            Field[] var4 = mds;
            int var5 = mds.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Field field = var4[var6];
                fieldMap.put(field.getName(), field);
            }
        }

        return fieldMap;
    }

    private T getRowInfo(Method[] methods, Map<String, Field> fieldMap, ResultSet rs) {
        T t = null;

        try {
            t = this.clazz.newInstance();
            Method[] var5 = methods;
            int var6 = methods.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Method method = var5[var7];

                try {
                    String methodName = method.getName();
                    if (methodName.startsWith("set")) {
                        String property = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                        Field field = (Field) fieldMap.get(property);
                        Column column = (Column) field.getAnnotation(Column.class);
                        if (column != null && StringUtils.isNotBlank(column.name())) {
                            property = column.name();
                        }

                        JoinColumn joinColumn = (JoinColumn) field.getAnnotation(JoinColumn.class);
                        if (joinColumn != null && StringUtils.isNotBlank(joinColumn.name())) {
                            property = joinColumn.name();
                        }
                        Object obj = rs.getObject(property);
                        Class clazz;
                        if (obj instanceof Date) {
                            clazz = method.getParameterTypes()[0];
                            if (clazz == DateTime.class) {
                                method.invoke(t, new DateTime(((Date) obj).getTime()));
                            } else if (clazz == Date.class) {
                                method.invoke(t, new Date(((Date) obj).getTime()));
                            } else {
                                method.invoke(t, obj);
                            }
                        } else if (obj instanceof String) {
                            clazz = method.getParameterTypes()[0];
                            if (clazz.isEnum()) {
                                method.invoke(t, Enum.valueOf(clazz, String.valueOf(obj)));
                            } else {
                                Type paramType = method.getGenericParameterTypes()[0];
                                if (!(paramType instanceof ParameterizedType)) {
                                    method.invoke(t, obj);
                                }
                            }
                        } else if (obj != null) {
                            clazz = method.getParameterTypes()[0];
                            if (clazz == Long.class) {
                                method.invoke(t, Long.parseLong(obj.toString()));
                            } else if (clazz == BigDecimal.class) {
                                method.invoke(t, new BigDecimal(obj.toString()));
                            } else {
                                method.invoke(t, obj);
                            }
                        }
                    }
                } catch (Exception var24) {
                    LOGGER.warn("RowMapperService analysis resultSet error : {}",
                            ExceptionUtils.getMessage(var24));
                }
            }
        } catch (IllegalAccessException | InstantiationException var25) {
            LOGGER
                    .warn("RowMapperService analysis resultSet error : {}", ExceptionUtils.getMessage(var25));
        }

        return t;
    }

    private Class getCollectionActualGenericClass(ParameterizedType type) {
        Type actualTypeArgument = type.getActualTypeArguments()[0];

        try {
            return (Class) actualTypeArgument;
        } catch (Exception var4) {
            return this.getCollectionActualGenericClassFromSuper(actualTypeArgument);
        }
    }

    private Class getCollectionActualGenericClassFromSuper(Type actualTypeArgument) {
        String argumentTypeName = actualTypeArgument.getTypeName();
        TypeVariable<? extends Class<? super T>>[] typeParameters = this.clazz.getSuperclass().getTypeParameters();
        if (ArrayUtils.isNotEmpty(typeParameters)) {
            int index = ((List) Arrays.stream(typeParameters).map(Type::getTypeName).collect(Collectors.toList())).indexOf(argumentTypeName);
            if (index >= 0) {
                ParameterizedType parameterizedType = (ParameterizedType) this.clazz.getGenericSuperclass();
                return (Class) parameterizedType.getActualTypeArguments()[index];
            }
        }

        Class<?>[] interfaces = this.clazz.getInterfaces();
        Type[] interfaceTypes = this.clazz.getGenericInterfaces();
        if (ArrayUtils.isNotEmpty(interfaces)) {
            int clazzIndex = 0;
            Class[] var7 = interfaces;
            int var8 = interfaces.length;

            for (int var9 = 0; var9 < var8; ++var9) {
                Class<?> anInterface = var7[var9];
                TypeVariable<? extends Class<?>>[] parameters = anInterface.getTypeParameters();
                if (ArrayUtils.isNotEmpty(parameters)) {
                    int index = ((List) Arrays.stream(parameters).map(Type::getTypeName).collect(Collectors.toList())).indexOf(argumentTypeName);
                    if (index >= 0) {
                        ParameterizedType pt = (ParameterizedType) interfaceTypes[clazzIndex];
                        return (Class) pt.getActualTypeArguments()[index];
                    }
                }

                ++clazzIndex;
            }
        }

        return Object.class;
    }


}


