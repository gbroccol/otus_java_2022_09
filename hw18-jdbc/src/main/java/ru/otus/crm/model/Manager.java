package ru.otus.crm.model;

import lombok.Data;
import ru.otus.annotation.Id;

@Data
public class Manager {

    @Id
    private Long id;
    private String name;

    public Manager() {}

    public Manager(String name) {
        this.name = name;
    }

    public Manager(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
}
