package com.flowergarden.bouquet;

import com.flowergarden.flowers.*;
import com.flowergarden.properties.FreshnessInteger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;


/**
 * @author Andrew Bandura
 */
public class MarriedBouquetTest {

    private MarriedBouquet marriedBouquet;
    private GeneralFlower rose;

    @Before
    public void setUp() {

        rose = new Rose(true,50,10, new FreshnessInteger(1));

        marriedBouquet = new MarriedBouquet();
        marriedBouquet.addFlower(new Chamomile(50,10, 7, new FreshnessInteger(2)));
        marriedBouquet.addFlower(new Chamomile(40,12, 7, new FreshnessInteger(2)));
        marriedBouquet.addFlower(rose);
    }

    @Test
    public void getPrice() {

        float actual = marriedBouquet.getPrice();
        float expected = 144;
        assertEquals(actual, expected);
    }

    @Test
    public void addFlower(){

        int expected = marriedBouquet.getFlowers().size() + 1;
        marriedBouquet.addFlower(new Rose(true,50,10, new FreshnessInteger(1)));
        int actual = marriedBouquet.getFlowers().size();
        assertEquals(actual, expected);

    }

    @Test
    public void searchFlowersByLenght() {

        int expected = 2;
        int actual = marriedBouquet.searchFlowersByLenght(10,30).size();
        assertEquals(actual, expected);
    }

    @Test
    public void sortByFreshness() {

        GeneralFlower expected = rose;
        marriedBouquet.sortByFreshness();
        GeneralFlower actual = (GeneralFlower) marriedBouquet.getFlowers().toArray()[0];

        assertEquals(actual, expected);
    }

    @Test
    public void getFlowers() {

        int expected = 3;
        int actual = marriedBouquet.getFlowers().size();

        assertEquals(actual, expected);

    }

    @Test
    public void setAssembledPrice() throws NoSuchFieldException, IllegalAccessException {

        float expected = 150;
        marriedBouquet.setAssembledPrice(150);
        Field field = marriedBouquet.getClass().getDeclaredField("assemblePrice");
        field.setAccessible(true);
        float actual = field.getFloat(marriedBouquet);

        assertEquals(actual, expected);
    }
}
