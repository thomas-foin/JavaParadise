package com.supinfo.javaparadise.dao;

import com.supinfo.javaparadise.model.Place;

public interface PlaceDao {
	
	Long createPlace(Place place);
	Place findPlaceById(Long id);
	boolean updatePlace(Place place);
	boolean removePlace(Place p);

}
