package com.flowergarden.bouquet;
import com.flowergarden.flowers.Flower;

import java.util.Collection;

public interface Bouquet<T> {

	void setId(int id);
	int getId();
	float getAssembledPrice();
	void setAssemblePrice(float price);
	float getPrice();
	void addFlower(T flower);
	Collection<T> searchFlowersByLenght(int start, int end);
	void sortByFreshness();
	void setFlowers(Collection<Flower> flowers);
	Collection<T> getFlowers();
}
