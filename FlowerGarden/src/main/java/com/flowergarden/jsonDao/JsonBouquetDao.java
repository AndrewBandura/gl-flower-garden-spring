package com.flowergarden.jsonDao;

import com.flowergarden.model.bouquet.Bouquet;

import java.util.Map;

public interface JsonBouquetDao {

    int add(Bouquet bouquet);

    Bouquet read();

}
