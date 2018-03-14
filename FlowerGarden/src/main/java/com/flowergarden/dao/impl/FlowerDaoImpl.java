package com.flowergarden.dao.impl;

import com.flowergarden.dao.FlowerDao;
import com.flowergarden.dto.DtoMapper;
import com.flowergarden.dto.FlowerDto;
import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flowers.Chamomile;
import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.model.flowers.Rose;
import com.flowergarden.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrew Bandura
 */
public class FlowerDaoImpl implements FlowerDao {

    private Connection connection;

    public FlowerDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int add(GeneralFlower flower) {

        int newId = 0;

        try {
            PreparedStatement stmt = connection.prepareStatement(SQL_ADD);
            stmt.setObject(1, flower.getName());
            stmt.setObject(2, flower.getLenght());
            stmt.setObject(3, flower.getFreshness());
            stmt.setObject(4, flower.getPrice());
            Bouquet bouquet = flower.getBouquet();
            if (!(bouquet == null)) {
                stmt.setObject(7, bouquet.getId());
            }

            if (flower instanceof Rose) {
                stmt.setObject(5, null);
                stmt.setObject(6, ((Rose) flower).isSpike());
            } else if (flower instanceof Chamomile) {
                stmt.setObject(5, ((Chamomile) flower).getPetals());
                stmt.setObject(6, null);
            }

            stmt.execute();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newId = generatedKeys.getInt(1);
                    flower.setId(newId);
                } else {
                    throw new SQLException("Adding flower failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newId;
    }

    @Override
    public Optional<GeneralFlower> read(int id) {

        Optional<GeneralFlower> flower = Optional.empty();

        try {
            PreparedStatement stmt = connection.prepareStatement(SQL_READ);
            stmt.setObject(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                FlowerDto flowerDto = getFlowerDto(rs);
                flower = DtoMapper.getPojo(flowerDto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flower;

    }

    @Override
    public Optional<GeneralFlower> readFirst() {

        Optional<GeneralFlower> flower = Optional.empty();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_READ_FIRST);

            if (rs.next()) {
                FlowerDto flowerDto = getFlowerDto(rs);
                flower = DtoMapper.getPojo(flowerDto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flower;
    }

    @Override
    public boolean update(GeneralFlower flower) {

        boolean updated = false;

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setObject(1, flower.getName());
            statement.setObject(2, flower.getLenght());
            statement.setObject(3, flower.getFreshness());
            statement.setObject(4, flower.getPrice());
            Bouquet bouquet = flower.getBouquet();
            if (!(bouquet == null)) {
                statement.setObject(7, bouquet.getId());
            }
            statement.setObject(8, flower.getId());

            if (flower instanceof Rose) {
                statement.setObject(5, null);
                statement.setObject(6, ((Rose) flower).isSpike());
            } else if (flower instanceof Chamomile) {
                statement.setObject(5, ((Chamomile) flower).getPetals());
                statement.setObject(6, null);
            }

            updated = statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updated;
    }

    @Override
    public boolean delete(int id) {

        boolean deleted = false;

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setObject(1, id);
            deleted = statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleted;

    }

    @Override
    public boolean deleteAll() {

        boolean deleted = false;

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(SQL_DELETE_ALL);
            deleted = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    @Override
    public List<GeneralFlower> findAll() {

        List<GeneralFlower> flowerList = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_FIND_ALL);

            if (rs.next()) {
                FlowerDto flowerDto = getFlowerDto(rs);
                Optional<GeneralFlower> flowerOpt = DtoMapper.getPojo(flowerDto);
                if(flowerOpt.isPresent()){
                    GeneralFlower flower = flowerOpt.get();
                    flowerList.add(flower);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flowerList;
    }

    public FlowerDto getFlowerDto(ResultSet rs) throws SQLException {

        FlowerDto dto = new FlowerDto();
        dto.setName(rs.getString("name"));
        dto.setId(rs.getInt("id"));
        dto.setLenght(rs.getInt("lenght"));
        dto.setPrice(rs.getInt("price"));
        dto.setFreshness(rs.getInt("freshness"));
        dto.setSpike(rs.getBoolean("spike"));
        dto.setPetals(rs.getInt("petals"));

        return dto;

    }

}
