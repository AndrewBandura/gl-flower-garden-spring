package com.flowergarden.dao;

import com.flowergarden.dao.impl.FlowerDaoImpl;
import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.dao.impl.BouquetDaoImpl;
import com.flowergarden.model.flowers.Chamomile;
import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.model.flowers.Rose;
import com.flowergarden.model.flowers.Tulip;
import com.flowergarden.properties.FreshnessInteger;
import com.flowergarden.util.ConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author Andrew Bandura
 */
public class BouquetDaoImplTest {

    private static BouquetDao bouquetDao;
    private static FlowerDao flowerDao;

    private int firstBouquetId;

    @BeforeClass
    public static void classSetUp(){

        bouquetDao = new BouquetDaoImpl(new ConnectionFactory("test"));
        flowerDao = new FlowerDaoImpl(new ConnectionFactory("test"));
    }

    @Before
    public void setUp(){

        Bouquet bouquet = new MarriedBouquet();
        bouquet.setAssemblePrice(100);
        int id = bouquetDao.add(bouquet);

        firstBouquetId = id;

        GeneralFlower flowerRose = new Rose();
        flowerRose.setPrice(50);
        flowerRose.setFreshness(new FreshnessInteger(1));
        flowerRose.setLenght(70);
        ((Rose)flowerRose).setSpike(true);
        flowerRose.setBouquet_id(id);
        flowerDao.add(flowerRose);

        GeneralFlower flowerChamomile = new Chamomile();
        flowerChamomile.setPrice(40);
        flowerChamomile.setFreshness(new FreshnessInteger(2));
        flowerChamomile.setLenght(50);
        flowerChamomile.setBouquet_id(id);
        ((Chamomile)flowerChamomile).setPetals(95);
        flowerDao.add(flowerChamomile);

        GeneralFlower flowerTulip = new Tulip();
        flowerTulip.setPrice(30);
        flowerTulip.setFreshness(new FreshnessInteger(3));
        flowerTulip.setLenght(45);
        flowerTulip.setBouquet_id(id);
        flowerDao.add(flowerTulip);
    }

    @After
    public void tearDown(){

        bouquetDao.deleteAll();
        flowerDao.deleteAll();

    }

    @Test
    public void addTest(){

        Bouquet bouquet = new MarriedBouquet();
        int id = bouquetDao.add(bouquet);

        assertTrue(id > 0);
    }

    @Test
    public void readFirstTest(){

        Bouquet bouquet = new MarriedBouquet();
        bouquetDao.add(bouquet);
        Optional<Bouquet> bouquetFirst = bouquetDao.readFirst();

        assertTrue(bouquetFirst.isPresent());

    }

    @Test
    public void readLazyTest() {

        Optional<Bouquet> bouquet = Optional.empty();
        Optional<Bouquet> bouquetFirst = bouquetDao.readFirst();

        if (bouquetFirst.isPresent()) {
            int id = bouquetFirst.get().getId();
            bouquet = bouquetDao.read(id, FetchMode.LAZY);
        }

        assertTrue(bouquet.isPresent());

    }

    @Test
    public void readEagerTest() {

        Optional<Bouquet> bouquet = Optional.empty();
        Optional<Bouquet> bouquetFirst = bouquetDao.readFirst();

        if (bouquetFirst.isPresent()) {
            int id = bouquetFirst.get().getId();
            bouquet = bouquetDao.read(id, FetchMode.EAGER);
        }

        assertTrue(bouquet.isPresent());

    }

    @Test
    public void updateTest(){

        final float expected = 507;

        Optional<Bouquet> bouquetOpt = bouquetDao.readFirst();
        if(!bouquetOpt.isPresent()){
           assertTrue(false);
        }
        Bouquet bouquet = bouquetOpt.get();
        bouquet.setAssemblePrice(expected);
        bouquetDao.update(bouquet);

        Optional<Bouquet> updatedBouquetOpt = bouquetDao.read(bouquet.getId(), FetchMode.LAZY);
        if(!updatedBouquetOpt.isPresent()){
            assertTrue(false);
        }

        float actual = updatedBouquetOpt.get().getAssemblePrice();

        assertEquals(expected, actual, 0.0f);

    }

    @Test
    public void deleteTest(){

        Bouquet bouquet = new MarriedBouquet();
        int id = bouquetDao.add(bouquet);
        bouquetDao.delete(id);

        assertFalse(bouquetDao.read(id, FetchMode.LAZY).isPresent());

    }

    @Test
    public void deleteAllTest(){

        bouquetDao.deleteAll();

        assertTrue(bouquetDao.findAll(FetchMode.LAZY).size() == 0);

    }

    @Test
    public void findAllLazyTest(){

        Bouquet bouquet = new MarriedBouquet();
        bouquetDao.add(bouquet);
        List<Bouquet> bouquetList =  bouquetDao.findAll(FetchMode.LAZY);

        assertTrue(bouquetList.size() > 0);

    }

    @Test
    public void findAllEagerTest(){

        List<Bouquet> bouquetList =  bouquetDao.findAll(FetchMode.EAGER);

        assertTrue(bouquetList.size() > 0);

    }

    @Test
    public void getTotalPrice() {

        float expected = 220f;
        float actual = 0f;

        Optional<Bouquet> bouquet;

        bouquet = bouquetDao.read(firstBouquetId, FetchMode.EAGER);

        if (bouquet.isPresent()) {
            actual = bouquet.get().getPrice();
        }

        assertEquals(expected, actual, 0f);

    }

}
