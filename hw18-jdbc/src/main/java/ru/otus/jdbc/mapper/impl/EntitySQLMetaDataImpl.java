package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> classMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> classMetaData) {
        this.classMetaData = classMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", classMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select * from %s where %s = ?",
                classMetaData.getName(), // table_name
                classMetaData.getIdField().getName()); // id_column_name
    }

    @Override
    public String getInsertSql() {

        List<Field> fields = classMetaData.getFieldsWithoutId();
        String fieldNames = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));

        StringJoiner questionMarks = new StringJoiner(",");
        for (Field ignored : fields) questionMarks.add("?");

        return String.format("insert into %s(%s) values(%s)",
                classMetaData.getName(),
                fieldNames,
                questionMarks);
    }

    @Override
    public String getUpdateSql() {
        String updateFields = classMetaData.getFieldsWithoutId().stream()
                .map(field -> String.format("%s = ?", field.getName()))
                .collect(Collectors.joining(","));
        return String.format("update %s set %s",
                classMetaData.getName(),
                updateFields);
    }
}