package com.flowergarden.dao;

import com.flowergarden.dto.FlowerDto;
import com.flowergarden.model.flowers.GeneralFlower;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrew Bandura
 */
public interface FlowerDao {

    String SQL_ADD = "INSERT INTO flower(`name`, `lenght`, `freshness`, `price`, `petals`, `spike`, `bouquet_id`) " +
            "VALUES(?,?,?,?,?,?,?)";

    String SQL_READ_LAZY = "SELECT * FROM flower where id = ?";

    String SQL_READ_EAGER = "SELECT flower.id, flower.name, flower.lenght, flower.freshness, flower.price," +
            " flower.petals, flower.spike, bouquet.id AS bouquet_id, bouquet.name AS bouquet_name," +
            " bouquet.assemble_price AS bouquet_assemble_price FROM flower left join bouquet on" +
            " flower.bouquet_id = bouquet.id where flower.id = ?";

    String SQL_READ_FIRST = "SELECT * FROM flower ORDER BY ID ASC LIMIT 1";

    String SQL_UPDATE = "UPDATE flower SET name = ?, lenght = ?, freshness = ?, price = ?, petals =?, spike = ?," +
            " bouquet_id = ? WHERE id = ?";

    String SQL_DELETE = "DELETE FROM flower WHERE id = ?";

    String SQL_DELETE_ALL = "DELETE FROM flower";

    String SQL_FIND_ALL_LAZY = "SELECT * FROM flower";

    String SQL_FIND_ALL_EAGER = "SELECT flower.id, flower.name, flower.lenght, flower.freshness, flower.price," +
            " flower.petals, flower.spike, bouquet.id AS bouquet_id, bouquet.name AS bouquet_name," +
            " bouquet.assemble_price AS bouquet_assemble_price FROM flower left join bouquet on" +
            " flower.bouquet_id = bouquet.id";

    int add(GeneralFlower flower);

    Optional<GeneralFlower> read(int id, FetchMode fetchMode);

    Optional<GeneralFlower> readFirst();

    boolean update(GeneralFlower flower);

    boolean delete(GeneralFlower flower);

    boolean deleteAll();

    List<GeneralFlower> findAll(FetchMode fetchMode);

    FlowerDto getFlowerDto(ResultSet rs) throws SQLException;

}
