package com.flowergarden.bouquet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.flowergarden.flowers.Flower;
import com.flowergarden.flowers.GeneralFlower;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class MarriedBouquet implements Bouquet<GeneralFlower> {

	private int id;
	private float assemblePrice;
	private List<GeneralFlower> flowerList = new ArrayList<>();

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public float getAssembledPrice() {
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
		List<GeneralFlower> searchResult = new ArrayList<GeneralFlower>();
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

		this.flowerList = (ArrayList)flowers;

	}
}
