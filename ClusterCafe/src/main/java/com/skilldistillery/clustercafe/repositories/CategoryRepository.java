package com.skilldistillery.clustercafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.clustercafe.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
