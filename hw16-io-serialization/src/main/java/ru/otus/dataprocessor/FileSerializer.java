package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;
    private final ObjectMapper mapper = new ObjectMapper();

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    //формирует результирующий json и сохраняет его в файл
    public void serialize(Map<String, Double> data) {
        var file = new File(fileName);
        try {
            mapper.writeValue(file, data);
        } catch (IOException e) {
            throw new FileProcessException("Не удалось записать в файл: " + e.getMessage());
        }
    }
}
