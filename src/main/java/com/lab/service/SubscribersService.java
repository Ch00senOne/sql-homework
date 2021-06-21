package com.lab.service;

import com.lab.dao.SubscriberDAO;
import com.lab.models.Periodical;
import com.lab.models.Subscriber;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubscribersService implements SubscriberDAO, AutoCloseable {

    private final Statement statement;

    public SubscribersService(Statement statement) {
        this.statement = statement;
    }




    @Override
    public Subscriber get(Long id) throws SQLException {
        ResultSet resultSet = statement.executeQuery(String.format("select * from SUBSCRIBERS where id = %d", id));
        if (!resultSet.next()) {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
        return new Subscriber(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public List<Subscriber> getAll() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from SUBSCRIBERS");
        List<Subscriber> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(new Subscriber(resultSet.getInt("id"), resultSet.getString("name")));
        }
        return results;
    }

    @Override
    public void update(Subscriber subscriber) throws SQLException {
        statement.execute(String.format("UPDATE SUBSCRIBERS SET name='%s' where id=%d", subscriber.getName(), subscriber.getId()));
    }

    @Override
    public Subscriber create(Subscriber subscriber) throws SQLException {
        statement.execute(String.format("INSERT INTO SUBSCRIBERS VALUES (NULL, '%s')", subscriber.getName()));
        return getByName(subscriber.getName());
    }

    @Override
    public void delete(Long id) throws SQLException {
        statement.execute(String.format("DELETE FROM SUBSCRIBERS WHERE id=%d", id));
    }

    @Override
    public List<Periodical> getSubscriptions(Subscriber subscriber) throws SQLException {
        ResultSet resultSet = statement.executeQuery(String.format("SELECT p.* FROM SUBSCRIBERS s " +
                "JOIN PERIODCAL_TO_SUBSCRIBER d ON s.id=d.subscriber_id " +
                "JOIN PERIODICAL p ON d.publication_id=p.id WHERE s.id='%d'", subscriber.getId()));

        List<Periodical> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(new Periodical(resultSet.getInt("id"), resultSet.getString("name")));
        }
        return results;
    }

    @Override
    public Subscriber getByName(String name) throws SQLException {
        ResultSet resultSet = statement.executeQuery(String.format("select * from SUBSCRIBERS where name = '%s'", name));
        if (!resultSet.next()) {
            throw new IllegalArgumentException("User with NAME " + name + " not found");
        }
        return new Subscriber(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public void close() throws Exception {
        statement.close();
    }
}
