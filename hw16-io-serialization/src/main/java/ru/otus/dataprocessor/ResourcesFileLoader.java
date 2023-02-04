package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;
    private final ObjectMapper om = new ObjectMapper();

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    //читает файл, парсит и возвращает результат
    public List<Measurement> load() {
        List<Measurement> result = new ArrayList<>();
        try (var inputStream = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            JsonNode nodes = om.readTree(inputStream);
            for (JsonNode node : nodes)
                result.add(new Measurement(node.get("name").asText(), node.get("value").asDouble()));
        } catch (IOException ex) {
            throw new FileProcessException("Не удалось прочитать файл: " + ex.getMessage());
        }
        return result;
    }
}
