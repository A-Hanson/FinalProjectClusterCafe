package com.skilldistillery.clustercafe.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.clustercafe.entities.Category;
import com.skilldistillery.clustercafe.repositories.CategoryRepository;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public List<Category> index() {
		return categoryRepo.findAll();
	}

}
