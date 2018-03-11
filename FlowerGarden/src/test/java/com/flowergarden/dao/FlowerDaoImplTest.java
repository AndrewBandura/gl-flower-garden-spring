package com.flowergarden.dao;

import com.flowergarden.util.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author Andrew Bandura
 */
public class FlowerDaoImplTest {

    private FlowerDao flowerDao;

    @Before
    public void setUp(){

        flowerDao = new FlowerDaoImpl(new ConnectionFactory("test"));

    }

    @Test
    public void findAllTest(){

        List flowersList =  flowerDao.findAll();

        assertNotNull(flowersList);

    }

}
