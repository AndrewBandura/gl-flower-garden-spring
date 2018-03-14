package com.flowergarden.dao;

import com.flowergarden.dao.impl.FlowerDaoImpl;
import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.model.flowers.Rose;
import com.flowergarden.util.ConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Andrew Bandura
 */
public class FlowerDaoImplTest {

    private static FlowerDao flowerDao;

    @BeforeClass
    public static void classSetUp(){

        flowerDao = new FlowerDaoImpl(ConnectionFactory.getConnection("test"));
    }

    @Before
    public void setUp(){

        GeneralFlower flower = new Rose();
        flowerDao.add(flower);

    }

    @After
    public void tearDown(){

        flowerDao.deleteAll();

    }

    @Test
    public void addTest(){

        GeneralFlower flower = new GeneralFlower();
        int id = flowerDao.add(flower);

        assertTrue(id > 0);
    }

    @Test
    public void readFirstTest(){

        GeneralFlower flower = new GeneralFlower();
        flowerDao.add(flower);
        Optional<GeneralFlower> flowerFirst = flowerDao.readFirst();

        assertTrue(flowerFirst.isPresent());

    }

    @Test
    public void updateTest(){

        final float expected = 50;

        Optional<GeneralFlower> flowerOpt = flowerDao.readFirst();

        if(!flowerOpt.isPresent()){
            assertTrue(false);
        }

        GeneralFlower flower = flowerOpt.get();
        flower.setPrice(expected);
        flowerDao.update(flower);

        Optional<GeneralFlower> updatedFlowerOpt = flowerDao.read(flower.getId());

        if(!updatedFlowerOpt.isPresent()){
            assertTrue(false);
        }

        float actual = updatedFlowerOpt.get().getPrice();

        assertEquals(expected, actual, 0.0f);

    }

    @Test
    public void deleteTest(){

        GeneralFlower flower = new Rose();
        int id = flowerDao.add(flower);
        flowerDao.delete(id);

        assertFalse(flowerDao.read(id).isPresent());

    }

    @Test
    public void deleteAllTest(){

        flowerDao.deleteAll();

        assertTrue(flowerDao.findAll().size() == 0);

    }


    @Test
    public void findAllTest(){

        List flowersList =  flowerDao.findAll();

        assertNotNull(flowersList);

    }

}
