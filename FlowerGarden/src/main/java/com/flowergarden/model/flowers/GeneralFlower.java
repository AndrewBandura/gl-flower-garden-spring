package com.flowergarden.model.flowers;

import javax.xml.bind.annotation.*;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.properties.FreshnessInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralFlower implements Flower<Integer>, Comparable<GeneralFlower> {

    @XmlAttribute
    int id;

    @XmlAttribute
    String name;

    @XmlElement
    FreshnessInteger freshness;

    @XmlAttribute
    float price;

    @XmlAttribute
    int lenght;

    Bouquet bouquet;

    public void setFreshness(FreshnessInteger fr) {
        freshness = fr;
    }

    @Override
    public FreshnessInteger getFreshness() {
        return freshness;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public int getLenght() {
        return lenght;
    }

    @Override
    public int compareTo(GeneralFlower compareFlower) {
        int compareFresh = compareFlower.getFreshness().getFreshness();
        return this.getFreshness().getFreshness() - compareFresh;
    }

}
