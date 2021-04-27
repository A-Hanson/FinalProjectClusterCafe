package com.skilldistillery.clustercafe.services;

import java.util.List;

import com.skilldistillery.clustercafe.entities.Store;

public interface StoreService {
	List<Store> index();
	List<Store> indexEnabled();
	Store show(int id);
	Store create(Store store);
	Store update(int id, Store store);
	boolean softDelete(int id);
}
