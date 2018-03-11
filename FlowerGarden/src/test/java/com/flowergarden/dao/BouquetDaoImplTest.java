package com.flowergarden.dao;

import com.flowergarden.bouquet.Bouquet;
import com.flowergarden.bouquet.MarriedBouquet;
import com.flowergarden.util.ConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Andrew Bandura
 */
public class BouquetDaoImplTest {

    private static BouquetDao bouquetDao;

    @BeforeClass
    public static void classSetUp(){

        bouquetDao = new BouquetDaoImpl(new ConnectionFactory("test"));
    }

    @Before
    public void setUp(){

        Bouquet bouquet = new MarriedBouquet();
        bouquet.setAssemblePrice(100);
        bouquetDao.add(bouquet);

    }

    @After
    public void tearDown(){

        List<Bouquet> bouquetList = bouquetDao.findAll();
        bouquetList.forEach(bouquet -> bouquetDao.delete(bouquet.getId()));

    }

    @Test
    public void addTest(){

       final float price = 100;
        Bouquet bouquet = new MarriedBouquet();
        bouquet.setAssemblePrice(price);

        int id = bouquetDao.add(bouquet);

        assertTrue(id > 0);
    }

    @Test
    public void readTest(){

        Bouquet bouquet = bouquetDao.read(1);

    }

    @Test
    public void updateTest(){
        final int id = 1;
        final float expected = 507;

        Bouquet bouquet = bouquetDao.read(id);
        bouquet.setAssemblePrice(expected);
        bouquetDao.update(bouquet);
        float actual = bouquetDao.read(id).getAssembledPrice();

        assertEquals(expected, actual, 0.0f);

    }

    @Test
    public void deleteTest(){

        Bouquet bouquet = new MarriedBouquet();
        bouquet.setAssemblePrice(333);

        int id = bouquetDao.add(bouquet);
        bouquetDao.delete(id);

        Bouquet deletedBouquet = bouquetDao.read(id);

        assertNull(deletedBouquet);

    }

    @Test
    public void findAllTest(){

        List bouquetList =  bouquetDao.findAll();

        assertNotNull(bouquetList);

    }

}
