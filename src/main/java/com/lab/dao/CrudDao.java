package com.lab.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T> {

    T get(Long id) throws SQLException;

    List<T> getAll() throws SQLException;

    void update(T t) throws SQLException;

    T create(T teacher) throws SQLException;

    void delete(Long id) throws SQLException;

}
