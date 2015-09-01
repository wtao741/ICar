package com.icar.sortListView;

import java.util.Comparator;

import com.icar.bean.CityEntity;

public class PinyinComparator implements Comparator<CityEntity> {

	public int compare(CityEntity o1, CityEntity o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
