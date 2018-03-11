package com.flowergarden.dto;

import com.flowergarden.bouquet.Bouquet;
import com.flowergarden.bouquet.MarriedBouquet;
import com.flowergarden.dto.BouquetDto;
import com.flowergarden.dto.FlowerDto;
import com.flowergarden.flowers.*;
import com.flowergarden.properties.FreshnessInteger;

/**
 * @author Andrew Bandura
 */
public class DtoMapper {

    public static  Bouquet getPojo(BouquetDto dto) {

        String name = dto.getName();

            if(name.equals("married")){

                Bouquet bouquet = new MarriedBouquet();
                bouquet.setId(dto.getId());
                bouquet.setAssemblePrice(dto.getAssemblePrice());

                return bouquet;

            }
            else{

                return null;
            }
    }

    public static GeneralFlower getPojo(FlowerDto dto) {

        String name = dto.getName();

        if(name.equals("rose")){

            Rose flower = new Rose();
            flower.setSpike(dto.isSpike());

            flower.setId(dto.getId());
            flower.setLenght(dto.getLenght());
            flower.setPrice(dto.getPrice());
            flower.setFreshness(new FreshnessInteger(dto.getFreshness()));

            return flower;

        }
        else if(name.equals("chamomile")){

            Chamomile flower = new Chamomile();
            flower.setPetals(dto.getPetals());

            flower.setId(dto.getId());
            flower.setLenght(dto.getLenght());
            flower.setPrice(dto.getPrice());
            flower.setFreshness(new FreshnessInteger(dto.getFreshness()));

            return flower;

        }
        else if(name.equals("tulip")){
            Tulip flower = new Tulip();

            flower.setId(dto.getId());
            flower.setLenght(dto.getLenght());
            flower.setPrice(dto.getPrice());
            flower.setFreshness(new FreshnessInteger(dto.getFreshness()));

            return flower;
        }
        else{
            return null;
        }

    }
}
