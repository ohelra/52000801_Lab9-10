package midterm.commerce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import midterm.commerce.api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>  {}
