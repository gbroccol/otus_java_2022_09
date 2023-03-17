package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.model.Manager;
import ru.otus.jdbc.mapper.impl.DataTemplateJdbc;

import java.util.Optional;

public class DbServiceManagerImpl {

    private static final Logger log = LoggerFactory.getLogger(DbServiceManagerImpl.class);

    private final TransactionRunner transactionRunner;
    private final DataTemplate<Manager> dataTemplate;

    public DbServiceManagerImpl(TransactionRunnerJdbc transactionRunner,
                                DataTemplateJdbc<Manager> dataTemplateManager) {
        this.transactionRunner = transactionRunner;
        dataTemplate = dataTemplateManager;
    }

    public Manager saveManager(Manager manager) {
        return transactionRunner.doInTransaction(connection -> {
            if (manager.getId() == null) {
                var managerId = dataTemplate.insert(connection, manager);
                var createdManager = new Manager(managerId, manager.getName());
                log.info("created manager: {}", createdManager);
                return createdManager;
            }
            dataTemplate.update(connection, manager);
            log.info("updated manager: {}", manager);
            return new Manager(manager.getId(), manager.getName());
        });
    }

    public Optional<Manager> getManager(Long id) {
        return transactionRunner.doInTransaction(connection -> {
            var managerOptional = dataTemplate.findById(connection, id);
            log.info("manager: {}", managerOptional);
            return managerOptional;
        });
    }
}