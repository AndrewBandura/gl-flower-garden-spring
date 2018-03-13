package com.flowergarden.model.flowers;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement
@Getter
@Setter
public class Rose extends GeneralFlower {

    private boolean spike;

    public Rose() {
        super.name = "rose";
    }
}
