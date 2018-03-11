package com.flowergarden.dao;

import com.flowergarden.bouquet.Bouquet;
import com.flowergarden.dto.FlowerDto;
import com.flowergarden.flowers.Flower;
import com.flowergarden.flowers.GeneralFlower;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Andrew Bandura
 */
public interface FlowerDao {

    int create(Flower flower);
    Bouquet read(int id);
    boolean update(Flower flower);
    boolean delete(Flower flower);
    List<GeneralFlower> findAll();

    FlowerDto getFlowerDto(ResultSet rs) throws SQLException;

}
