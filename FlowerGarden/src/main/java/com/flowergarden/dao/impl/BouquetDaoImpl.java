package com.flowergarden.dao.impl;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.dao.BouquetDao;
import com.flowergarden.dao.FetchMode;
import com.flowergarden.dao.FlowerDao;
import com.flowergarden.dto.BouquetDto;
import com.flowergarden.dto.DtoMapper;
import com.flowergarden.dto.FlowerDto;
import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.util.ConnectionFactory;

import java.sql.*;
import java.util.*;

/**
 * @author Andrew Bandura
 */
public class BouquetDaoImpl implements BouquetDao {

    private Connection connection;
    private FlowerDao flowerDao;

    public BouquetDaoImpl(Connection connection) {
        this.connection = connection;
        this.flowerDao = new FlowerDaoImpl(connection);
    }

    @Override
    public int add(Bouquet bouquet) {

        int newId = 0;

        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(SQL_ADD);
            statement.setObject(1, bouquet.getName());
            statement.setObject(2, bouquet.getAssemblePrice());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newId = generatedKeys.getInt(1);
                    bouquet.setId(newId);
                } else {
                    throw new SQLException("Creating bouquet failed, no ID obtained.");
                }
            }

            Collection<GeneralFlower> flowers = bouquet.getFlowers();
            for (GeneralFlower flower : flowers) {
                flower.setBouquet(bouquet);
                flowerDao.add(flower);
            }

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newId;
    }

    @Override
    public boolean update(Bouquet bouquet) {

        boolean updated = false;

        try {
            Bouquet storedBouquet;
            Optional<Bouquet> storedBouquetOpt = read(bouquet.getId(), FetchMode.EAGER);
            if (storedBouquetOpt.isPresent()) {
                storedBouquet = storedBouquetOpt.get();
                Collection<GeneralFlower> flowers = storedBouquet.getFlowers();
                for (GeneralFlower flower : flowers) {
                    flower.setBouquet(null);
                    flowerDao.update(flower);
                }
            }

            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setObject(1, bouquet.getName());
            statement.setObject(2, bouquet.getAssemblePrice());
            statement.setObject(3, bouquet.getId());
            updated = statement.execute();

            Collection<GeneralFlower> flowers = bouquet.getFlowers();
            for (GeneralFlower flower : flowers) {
                flower.setBouquet(bouquet);
                flowerDao.update(flower);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return updated;
    }

    @Override
    public Optional<Bouquet> read(int id, FetchMode fetchMode) {

        Optional<Bouquet> bouquet = Optional.empty();

        String query = fetchMode == FetchMode.EAGER ? SQL_READ_EAGER : SQL_READ_LAZY;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            bouquet = composeBouquet(rs, bouquet, fetchMode);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bouquet;
    }

    @Override
    public Optional<Bouquet> readFirst() {

        Optional<Bouquet> bouquet = Optional.empty();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_READ_FIRST);

            bouquet = composeBouquet(rs, bouquet, FetchMode.LAZY);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bouquet;
    }

    @Override
    public boolean delete(Bouquet bouquet) {

        boolean deleted = false;

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setObject(1, bouquet.getId());
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
    public List<Bouquet> findAll(FetchMode fetchMode) {

        List<Bouquet> bouquetList = new ArrayList<>();
        Map<Integer, List<GeneralFlower>> bouquetFlowers = new HashMap<>();
        Map<Integer, Bouquet> bouquets = new TreeMap<>();

        try {
            Statement stmt = connection.createStatement();
            String query = fetchMode == FetchMode.EAGER ? SQL_FIND_ALL_EAGER : SQL_FIND_ALL_LAZY;

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Integer bouquetId = rs.getInt("bouquet_id");
                Bouquet bouquet = bouquets.get(bouquetId);

                if (bouquet == null) {
                    BouquetDto dto = getBouquetDto(rs);
                    Optional<Bouquet> bouquetOpt = DtoMapper.getPojo(dto);
                    if (bouquetOpt.isPresent()) {
                        bouquet = bouquetOpt.get();
                        bouquets.put(bouquetId, bouquet);
                    }

                    bouquetFlowers.put(bouquetId, new ArrayList<>());
                }

                if (fetchMode == FetchMode.EAGER) {
                    if (!rs.wasNull() && !(rs.getInt("flower_bouquet_id") == 0)) {
                        List<GeneralFlower> flowers = bouquetFlowers.get(bouquetId);
                        FlowerDto flowerDto = flowerDao.getFlowerDto(rs);
                        Optional<GeneralFlower> flowerOpt = DtoMapper.getPojo(flowerDto);
                        flowerOpt.ifPresent(flowers::add);
                    }
                }
            }

            for (Bouquet bouq : bouquets.values()) {
                bouq.setFlowers(bouquetFlowers.get(bouq.getId()));
                bouquetList.add(bouq);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bouquetList;

    }

    private BouquetDto getBouquetDto(ResultSet rs) throws SQLException {

        BouquetDto dto = new BouquetDto();
        dto.setName(rs.getString("bouquet_name"));
        dto.setId(rs.getInt("bouquet_id"));
        dto.setAssemblePrice(rs.getFloat("assemble_price"));

        return dto;

    }

    private Optional<Bouquet> composeBouquet(ResultSet rs, Optional<Bouquet> bouquet, FetchMode fetchMode) throws SQLException {

        while (rs.next()) {

            if (!bouquet.isPresent()) {
                BouquetDto dto = getBouquetDto(rs);
                bouquet = DtoMapper.getPojo(dto);
            }

            if (fetchMode == FetchMode.EAGER) {
                boolean isFlowerRecordPresent = rs.getInt("flower_bouquet_id") > 0;
                if (isFlowerRecordPresent) {
                    FlowerDto flowerDto = flowerDao.getFlowerDto(rs);
                    Optional<GeneralFlower> flowerOpt = DtoMapper.getPojo(flowerDto);
                    if (bouquet.isPresent() && flowerOpt.isPresent()) {
                        GeneralFlower flower = flowerOpt.get();
                        bouquet.get().addFlower(flower);
                    }
                }
            }
        }

        return bouquet;

    }

}
