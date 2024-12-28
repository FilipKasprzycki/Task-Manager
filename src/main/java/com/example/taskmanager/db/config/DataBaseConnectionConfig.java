package com.example.taskmanager.db.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@DataSourceDefinition(
        name = "java:app/DataSource",
        className = "org.postgresql.Driver",
        url = "jdbc:postgresql://postgres:5432/task_manager",
        user = "postgres",
        password = "vKb2Xn!!kM94BR")
@Stateless
public class DataBaseConnectionConfig {

    @Inject
    private EntityManager entityManager;
}
