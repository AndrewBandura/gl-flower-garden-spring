package com.flowergarden.model.bouquet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.model.flowers.GeneralFlower;
import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MarriedBouquet implements Bouquet<GeneralFlower> {

    @XmlElement
    private int id;

    @XmlElement
    private String name;

    @XmlElement
    private float assemblePrice;

    @XmlElement
    private List<GeneralFlower> flowerList = new ArrayList<>();

    public MarriedBouquet() {
        this.name = "married";
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public float getAssemblePrice() {
        return this.assemblePrice;
    }


    public void setAssembledPrice(float price) {
        assemblePrice = price;
    }


    @Override
    public float getPrice() {
        float price = assemblePrice;
        for (GeneralFlower flower : flowerList) {
            price += flower.getPrice();
        }
        return price;
    }

    @Override
    public void addFlower(GeneralFlower flower) {
        flowerList.add(flower);
    }

    @Override
    public Collection<GeneralFlower> searchFlowersByLenght(int start, int end) {
        List<GeneralFlower> searchResult = new ArrayList<>();
        for (GeneralFlower flower : flowerList) {
            if (flower.getLenght() >= start && flower.getLenght() <= end) {
                searchResult.add(flower);
            }
        }
        return searchResult;
    }

    @Override
    public void sortByFreshness() {
        Collections.sort(flowerList);
    }

    @Override
    public Collection<GeneralFlower> getFlowers() {
        return flowerList;
    }

    @Override
    public void setFlowers(Collection<Flower> flowers) {

        this.flowerList = (ArrayList) flowers;

    }
}
