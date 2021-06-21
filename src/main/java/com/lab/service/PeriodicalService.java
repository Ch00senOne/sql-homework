package com.lab.service;

import com.lab.dao.PeriodicalDAO;
import com.lab.models.Periodical;
import com.lab.models.Subscriber;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalService implements PeriodicalDAO, AutoCloseable {

    private final Statement statement;

    public PeriodicalService(Statement statement) {
        this.statement = statement;
    }

    @Override
    public Periodical get(Long id) throws SQLException {
        ResultSet resultSet = statement.executeQuery(String.format("select * from PERIODICAL where id = %d", id));
        if (!resultSet.next()) {
            throw new IllegalArgumentException("Book with ID " + id + " not found");
        }
        return new Periodical(resultSet.getInt("id"), resultSet.getString("name"));

    }


    @Override
    public List<Periodical> getAll() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from PERIODICAL");
        List<Periodical> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(new Periodical(resultSet.getInt("id"), resultSet.getString("name")));
        }
        return results;
    }


    @Override
    public void update(Periodical periodical) throws SQLException {
        statement.execute(String.format("UPDATE PERIODICAL SET name='%s' where id=%d", periodical.getTitle(),periodical.getId()));
    }

    @Override
    public Periodical create(Periodical periodical) throws SQLException {
        statement.execute(String.format("INSERT INTO PERIODICAL VALUES (NULL, '%s')", periodical.getTitle()));
        return getByTitle(periodical.getTitle());

    }

    @Override
    public void delete(Long id) throws SQLException {
        statement.execute(String.format("DELETE FROM PERIODICAL WHERE id=%d", id));
    }

    @Override
    public List<Subscriber> getSubscribers(Periodical periodical) throws SQLException {
        ResultSet resultSet = statement.executeQuery(String.format("SELECT s.* FROM SUBSCRIBERS s " +
                "JOIN PERIODCAL_TO_SUBSCRIBER d ON s.id=d.subscriber_id " +
                "JOIN PERIODICAL p ON d.publication_id=p.id WHERE p.id='%d'", periodical.getId()));

        List<Subscriber> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(new Subscriber(resultSet.getInt("id"), resultSet.getString("name")));
        }
        return results;

    }

    @Override
    public Periodical getByTitle(String title) throws SQLException {
        ResultSet resultSet = statement.executeQuery(String.format("select * from PERIODICAL where name = '%s'", title));
        if (!resultSet.next()) {
            throw new IllegalArgumentException("User with NAME " + title + " not found");
        }
        return new Periodical(resultSet.getInt("id"), resultSet.getString("name"));
    }

    @Override
    public void close() throws Exception {
        statement.close();
    }
}
