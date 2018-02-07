package com.xterr.tradereporting.dao;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Data Access Object based contract.
 */
public interface DAO<T> {

    List<T> getAll() throws FileNotFoundException;

}
