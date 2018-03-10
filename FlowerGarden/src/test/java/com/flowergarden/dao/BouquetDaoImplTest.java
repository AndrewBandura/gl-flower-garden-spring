package com.flowergarden.dao;

import com.flowergarden.util.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Andrew Bandura
 */
public class BouquetDaoImplTest {

    private BouquetDao bouquetDao;

    @Before
    public void setUp(){

        bouquetDao = new BouquetDaoImpl(new ConnectionFactory());

    }

    @Test
    public void findAllTest(){

        List bouquetsList =  bouquetDao.findAll();

        System.out.println(bouquetsList.size());

    }

}
