package com.lab.dao;

import com.lab.models.Periodical;
import com.lab.models.Subscriber;

import java.sql.SQLException;
import java.util.List;

public interface PeriodicalDAO extends CrudDao<Periodical> {

    List<Subscriber> getSubscribers(Periodical periodical) throws SQLException;

    Periodical getByTitle(String title) throws SQLException;

}
