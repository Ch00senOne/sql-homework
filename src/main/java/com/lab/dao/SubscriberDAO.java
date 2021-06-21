package com.lab.dao;

import com.lab.models.Periodical;
import com.lab.models.Subscriber;

import java.sql.SQLException;
import java.util.List;

public interface SubscriberDAO extends CrudDao<Subscriber>{
    List<Periodical> getSubscriptions(Subscriber subscriber) throws SQLException;
    Subscriber getByName(String name) throws SQLException;
}
