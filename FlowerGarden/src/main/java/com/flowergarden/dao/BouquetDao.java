package com.flowergarden.dao;

import com.flowergarden.model.bouquet.Bouquet;

import java.util.List;
import java.util.Optional;

/**
 * @author Andrew Bandura
 */
public interface BouquetDao {

    String SQL_ADD = "INSERT INTO bouquet(`name`, `assemble_price`) " +
            "VALUES(?, ?)";
    String SQL_UPDATE = "Update bouquet set name = ?, assemble_price= ? where id = ?";

    String SQL_READ_LAZY = "SELECT bouquet.id as bouquet_id, bouquet.name as bouquet_name, bouquet.assemble_price FROM  bouquet WHERE id = ?";

    String SQL_READ_EAGER = "SELECT bouquet.id as bouquet_id, bouquet.name as bouquet_name, bouquet.assemble_price," +
            "flower.*, flower.bouquet_id AS flower_bouquet_id FROM bouquet" +
            " LEFT JOIN flower ON bouquet.id = flower.bouquet_id WHERE bouquet.id = ? ORDER BY bouquet_id";

    String SQL_READ_FIRST = "SELECT bouquet.id as bouquet_id, bouquet.name as bouquet_name, bouquet.assemble_price FROM bouquet ORDER BY ID ASC LIMIT 1";

    String SQL_DELETE = "DELETE FROM bouquet WHERE Id = ?";

    String SQL_DELETE_ALL = "DELETE FROM bouquet";

    String SQL_FIND_ALL_LAZY = "SELECT bouquet.id as bouquet_id, bouquet.name as bouquet_name, bouquet.assemble_price FROM  bouquet";

    String SQL_FIND_ALL_EAGER = "SELECT bouquet.id AS bouquet_id, bouquet.name AS bouquet_name, bouquet.assemble_price, " +
            "flower.*, flower.bouquet_id AS flower_bouquet_id FROM bouquet" +
            " LEFT JOIN flower ON bouquet.id = flower.bouquet_id  ORDER BY bouquet_id";

    int add(Bouquet bouquet);

    Optional<Bouquet> read(int id, FetchMode fetchMode);

    Optional<Bouquet> readFirst();

    boolean update(Bouquet bouquet);

    boolean delete(int id);

    boolean deleteAll();

    List<Bouquet> findAll(FetchMode fetchMode);

}
