package com.flowergarden.dao;

import com.flowergarden.bouquet.Bouquet;

import java.util.List;

/**
 * @author Andrew Bandura
 */
public interface BouquetDao {

    int create(Bouquet bouquet);
    Bouquet read(int id);
    boolean update(Bouquet bouquet);
    boolean delete(Bouquet bouquet);
    List<Bouquet> findAll();

}
