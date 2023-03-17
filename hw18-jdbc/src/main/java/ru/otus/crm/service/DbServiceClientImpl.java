package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Client;
import ru.otus.jdbc.mapper.impl.DataTemplateJdbc;

import java.util.Optional;

public class DbServiceClientImpl {

    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final TransactionRunner transactionRunner;
    private final DataTemplateJdbc<Client> dataTemplate;

    public DbServiceClientImpl(TransactionRunner transactionRunner, DataTemplateJdbc<Client> dataTemplate) {
        this.transactionRunner = transactionRunner;
        this.dataTemplate = dataTemplate;
    }

    public Client saveClient(Client client) {
        return transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null) {
                var clientId = dataTemplate.insert(connection, client);
                var createdClient = new Client(clientId, client.getName());
                log.info("created client: {}", createdClient);
                return createdClient;
            }
            dataTemplate.update(connection, client);
            log.info("updated client: {}", client);
            return new Client(client.getId(), client.getName());
        });
    }

    public Optional<Client> getClient(Long id) {
        return transactionRunner.doInTransaction(connection -> {
            var clientOptional = dataTemplate.findById(connection, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }
}
