package ru.otus.jdbc.mapper.impl;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData classMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData classMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.classMetaData = classMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) { return findObject(rs); }
                return null;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
    }

    private T findObject(ResultSet rs) {
        try {
            T obj = (T) classMetaData.getConstructor().newInstance();
            List<Field> fields = classMetaData.getAllFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(obj, rs.getObject(field.getName()));
            }
            return obj;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var result = new ArrayList<T>();
            try {
                while (rs.next()) {
                    result.add(findObject(rs));
                }
                return result;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T obj) {
        try {
            List<Object> params = getParams(obj, classMetaData.getFieldsWithoutId());
            return dbExecutor.executeStatement(connection,
                    entitySQLMetaData.getInsertSql(),
                    params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T obj) {
        try {
            List<Object> params = getParams(obj, classMetaData.getAllFields());
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getParams(T obj, List<Field> fields) throws IllegalAccessException {
        List<Object> params = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true); // отменить проверки доступа (private / public / ...)
            params.add(field.get(obj));
        }
        return params;
    }
}