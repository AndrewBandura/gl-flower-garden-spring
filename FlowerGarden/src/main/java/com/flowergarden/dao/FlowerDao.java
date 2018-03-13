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

    String SQL_READ = "SELECT * FROM flower where id = ?";

    String SQL_READ_FIRST = "SELECT * FROM flower ORDER BY ID ASC LIMIT 1";

    String SQL_UPDATE = "UPDATE flower SET name = ?, lenght = ?, freshness = ?, price = ?, petals =?, spike = ?, " +
            "bouquet_id = ? WHERE id = ?";

    String SQL_DELETE = "DELETE FROM flower WHERE id = ?";

    String SQL_DELETE_ALL = "DELETE FROM flower";

    String SQL_FIND_ALL = "SELECT * FROM flower";

    int add(GeneralFlower flower);

    Optional<GeneralFlower> read(int id);

    Optional<GeneralFlower> readFirst();

    boolean update(GeneralFlower flower);

    boolean delete(int id);

    boolean deleteAll();

    List<GeneralFlower> findAll();

    FlowerDto getFlowerDto(ResultSet rs) throws SQLException;

}
