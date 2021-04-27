package com.skilldistillery.clustercafe.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.clustercafe.entities.Store;
import com.skilldistillery.clustercafe.repositories.StoreRepository;
@Service
@Transactional
public class StoreServiceImpl implements StoreService {
	@Autowired
	private StoreRepository storeRepo;

	@Override
	public List<Store> index() {
		return storeRepo.findAll();
	}

	@Override
	public List<Store> indexEnabled() {
		return storeRepo.findByEnabledTrue();
	}

	@Override
	public Store show(int id) {
		Store store = null;
		if (storeRepo.findById(id).isPresent()) {
			store = storeRepo.findById(id).get();
		}
		return store;
	}

	@Override
	public Store create(Store store) {
		if (store.isEnabled() == null) {
			store.setEnabled(true);
		}
		return storeRepo.save(store);
	}

	@Override
	public Store update(int id, Store store) {
		Store managedStore = null;
		if (storeRepo.findById(id).isPresent()) {
			managedStore = storeRepo.findById(id).get();
			if (store.getAddress() != null) {
				managedStore.setAddress(store.getAddress());
			}
			if (store.getCategory() != null) {
				managedStore.setCategory(store.getCategory());
			}
			if (store.getCity() != null) {
				managedStore.setCity(store.getCity());
			}
			if (store.getName() != null) {
				managedStore.setName(store.getName());
			}
			if (store.getPostalCode() != null) {
				managedStore.setPostalCode(store.getPostalCode());
			}
			if (store.getState() != null) {
				managedStore.setState(store.getState());
			}
			managedStore.setLatitude(store.getLatitude());
			managedStore.setLongtitude(store.getLongtitude());
		}
		return managedStore;
	}

	@Override
	public boolean softDelete(int id) {
		boolean deleted = false;
		if (storeRepo.findById(id).isPresent()) {
			Store store = storeRepo.findById(id).get();
			store.setEnabled(false);
			deleted = true;
		}
		return deleted;
	}

}
