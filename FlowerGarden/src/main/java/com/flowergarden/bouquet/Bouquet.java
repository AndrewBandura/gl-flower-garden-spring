package com.flowergarden.bouquet;
import java.util.Collection;

public interface Bouquet<T> {

	void setId(int id);
	void setAssemblePrice(float price);
	float getPrice();
	void addFlower(T flower);
	Collection<T> searchFlowersByLenght(int start, int end);
	void sortByFreshness();
	Collection<T> getFlowers();
}
