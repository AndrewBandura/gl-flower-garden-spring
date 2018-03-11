package com.flowergarden.dao;

import com.flowergarden.bouquet.Bouquet;
import com.flowergarden.dto.DtoMapper;
import com.flowergarden.dto.FlowerDto;
import com.flowergarden.flowers.Flower;
import com.flowergarden.flowers.GeneralFlower;
import com.flowergarden.util.ConnectionFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Bandura
 */
public class FlowerDaoImpl implements FlowerDao {

    private ConnectionFactory connectionFactory;

    public FlowerDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public int create(Flower flower) {
        return 0;
    }

    @Override
    public Bouquet read(int id) {
        return null;
    }

    @Override
    public boolean update(Flower flower) {
        return false;
    }

    @Override
    public boolean delete(Flower flower) {
        return false;
    }

    @Override
    public List<GeneralFlower> findAll() {

        Statement st;

        try {
            st = connectionFactory.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        try {
            ResultSet rs = st.executeQuery("select * from flower");

            List<GeneralFlower> flowers = new ArrayList<>();

            while(rs.next())
            {
                FlowerDto dto = getFlowerDto(rs);
                GeneralFlower flower = DtoMapper.getPojo(dto);
                flowers.add(flower);
            }

            return flowers;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
