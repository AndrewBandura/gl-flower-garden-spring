package com.flowergarden.dao;

import com.flowergarden.bouquet.Bouquet;
import com.flowergarden.bouquet.MarriedBouquet;
import com.flowergarden.dto.BouquetDto;
import com.flowergarden.dto.DtoMapper;
import com.flowergarden.dto.FlowerDto;
import com.flowergarden.flowers.GeneralFlower;
import com.flowergarden.util.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author Andrew Bandura
 */
public class BouquetDaoImpl implements BouquetDao {

    private ConnectionFactory connectionFactory;

    public BouquetDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public int add(Bouquet bouquet) {

        String name;
        boolean inserted = false;
        int newId = 0;

        if(bouquet instanceof MarriedBouquet){
            name = "married";
        }
        else {
            String className = bouquet.getClass().getName();
            throw new IllegalArgumentException("Class "+className+" is not supported");
        }


        try (PreparedStatement statement = connectionFactory.getConnection().prepareStatement(
                "INSERT INTO bouquet(`name`, `assemble_price`) " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, name);
            statement.setObject(2, ((MarriedBouquet) bouquet).getAssemblePrice());
            inserted = statement.execute();


            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newId = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating bouquet failed, no ID obtained.");
                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }


        return newId;
    }

    @Override
    public boolean update(Bouquet bouquet) {

        String name;
        boolean updated = false;

        if(bouquet instanceof MarriedBouquet){
            name = "married";
        }
        else {
            String className = bouquet.getClass().getName();
            throw new IllegalArgumentException("Class "+className+" is not supported");
        }

        try (PreparedStatement statement = connectionFactory.getConnection().prepareStatement(
                "Update bouquet set name = ?, assemble_price= ? where id = ?")) {
            statement.setObject(1, name);
            statement.setObject(2, ((MarriedBouquet) bouquet).getAssemblePrice());
            statement.setObject(3, bouquet.getId());
            updated = statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updated;
    }

    @Override
    public Bouquet read(int id) {

        FlowerDao flowerDao = new FlowerDaoImpl(connectionFactory);
        Bouquet bouquet = null;

        Map<Integer, List<GeneralFlower>> bouquetFlowers = new HashMap<>();
        Map<Integer, Bouquet> bouquets = new TreeMap<>();

        try (Statement stmt = connectionFactory.getConnection().createStatement()){

            String query =  "Select bouquet.id as id_bouquet, bouquet.name as name_bouquet, bouquet.assemble_price, flower.* from bouquet" +
                    " left join flower on id_bouquet = flower.bouquet_id where id_bouquet = " + id + " order by id_bouquet";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                if(bouquet == null) {
                    BouquetDto dto = getBouquetDto(rs);
                    bouquet = DtoMapper.getPojo(dto);
                }

                FlowerDto flowerDto = flowerDao.getFlowerDto(rs);
                GeneralFlower flower = DtoMapper.getPojo(flowerDto);
                bouquet.addFlower(flower);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bouquet;
    }

    @Override
    public boolean delete(int id) {

        boolean deleted = false;

        try (PreparedStatement statement = connectionFactory.getConnection().prepareStatement(
                "Delete from bouquet where id = ?")) {

            statement.setObject(1, id);
            deleted = statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    @Override
    public List<Bouquet> findAll() {

        FlowerDao flowerDao = new FlowerDaoImpl(connectionFactory);

        Map<Integer, List<GeneralFlower>> bouquetFlowers = new HashMap<>();
        Map<Integer, Bouquet> bouquets = new TreeMap<>();

        try (Statement stmt = connectionFactory.getConnection().createStatement()){

            String query =  "Select bouquet.id as id_bouquet, bouquet.name as name_bouquet, bouquet.assemble_price, flower.* from bouquet" +
                    " left join flower on id_bouquet = flower.bouquet_id  order by id_bouquet";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Integer bouquetId = rs.getInt("id_bouquet");
                Bouquet bouquet = bouquets.get(bouquetId);

                if (bouquet == null) {
                    BouquetDto dto = getBouquetDto(rs);
                    bouquet = DtoMapper.getPojo(dto);
                    bouquets.put(bouquetId, bouquet);
                    bouquetFlowers.put(bouquetId, new ArrayList<GeneralFlower>());

                }

                if (!rs.wasNull() && !(rs.getInt("id")==0)) {

                    List<GeneralFlower> flowers = bouquetFlowers.get(bouquetId);
                    FlowerDto dto = flowerDao.getFlowerDto(rs);
                    GeneralFlower flower = DtoMapper.getPojo(dto);
                    flowers.add(flower);

                }
            }

            List<Bouquet> result = new ArrayList<>();
            for (Bouquet bouq : bouquets.values()) {
                bouq.setFlowers(bouquetFlowers.get(bouq.getId()));
                result.add(bouq);
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    private BouquetDto getBouquetDto(ResultSet rs) throws SQLException {

        BouquetDto dto = new BouquetDto();
        dto.setName(rs.getString("name_bouquet"));
        dto.setId(rs.getInt("id_bouquet"));
        dto.setAssemblePrice(rs.getFloat("assemble_price"));

        return dto;

    }

}
