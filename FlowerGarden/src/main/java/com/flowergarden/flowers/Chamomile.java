package com.flowergarden.flowers;

import lombok.Data;

@Data
public class Chamomile extends GeneralFlower {
	
	private int petals;

	public boolean getPetal(){
		if (petals <=0) return false;
		petals =-1;
		return true;
	}


}
