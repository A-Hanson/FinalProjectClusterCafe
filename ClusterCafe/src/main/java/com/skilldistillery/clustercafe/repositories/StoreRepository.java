package com.skilldistillery.clustercafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.clustercafe.entities.Store;

public interface StoreRepository extends JpaRepository<Store, Integer>{
	List<Store> findByEnabledTrue();
	Store findByLatitudeAndLongitudeAndName(float latitude, float longitude, String name);
}
