package com.flowergarden.dao;

import com.flowergarden.bouquet.Bouquet;

import java.util.List;

/**
 * @author Andrew Bandura
 */
public interface BouquetDao {

    int add(Bouquet bouquet);
    Bouquet read(int id);
    boolean update(Bouquet bouquet);
    boolean delete(int id);
    List<Bouquet> findAll();

}
