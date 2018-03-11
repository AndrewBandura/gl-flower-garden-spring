package com.flowergarden.flowers;

import javax.xml.bind.annotation.XmlRootElement;

import com.flowergarden.properties.FreshnessInteger;
import lombok.Data;

@XmlRootElement
@Data
public class Rose extends GeneralFlower {
	
	private boolean spike;

}
