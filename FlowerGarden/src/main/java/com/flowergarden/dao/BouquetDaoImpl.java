package com.flowergarden.dao;

import com.flowergarden.bouquet.Bouquet;
import com.flowergarden.bouquet.MarriedBouquet;
import com.flowergarden.util.ConnectionFactory;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Bandura
 */
public class BouquetDaoImpl implements BouquetDao {

    private ConnectionFactory connectionFactory;

    public BouquetDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public int create(Bouquet bouquet) {
        return 0;
    }

    @Override
    public Bouquet read(int id) {
        return null;
    }

    @Override
    public boolean update(Bouquet bouquet) {
        return false;
    }

    @Override
    public boolean delete(Bouquet bouquet) {
        return false;
    }

    @Override
    public List<Bouquet> findAll() {

        Statement st = null;

        try {
            st = connectionFactory.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = st.executeQuery("select * from bouquet");

            List<Bouquet> bouquets = new ArrayList<>();
            while(rs.next())
            {
                Bouquet bouquet = extractBouquetFromResultSet(rs);
                bouquets.add(bouquet);
            }
            return bouquets;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private Bouquet extractBouquetFromResultSet(ResultSet rs) throws SQLException {

        String className = rs.getString("name") + "Bouquet";
        Bouquet bouquet = null;

        try {
            Class klazz = Class.forName(className);
            try {
                bouquet = (Bouquet) klazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        bouquet.setId( rs.getInt("id") );
        bouquet.setAssemblePrice( rs.getFloat("setAssemble_price") );

        return bouquet;
    }
}
