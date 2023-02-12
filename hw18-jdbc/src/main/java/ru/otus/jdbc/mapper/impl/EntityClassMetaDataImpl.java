package ru.otus.jdbc.mapper.impl;

import ru.otus.annotation.Id;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) { this.clazz = clazz; }

    @Override
    public String getName() { return clazz.getSimpleName(); }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public Field getIdField() {
        Optional<Field> field = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst();
        if (field.isEmpty())
            throw new RuntimeException("annotation not found in class");
        return field.get();
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(clazz.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .toList();
    }
}