package util;

import java.util.Comparator;

import classifier.Categorie;

public class CategorieComparator implements Comparator<Categorie>{

	@Override
	public int compare(Categorie o1, Categorie o2) {
		if (o1.getQuantidade() < o2.getQuantidade()) {
            return -1;
        }
        if (o1.getQuantidade() > o2.getQuantidade()) {
            return 1;
        }
		return 0;
	}

	

}
