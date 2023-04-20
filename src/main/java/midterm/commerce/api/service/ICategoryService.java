package midterm.commerce.api.service;

import java.util.List;

import midterm.commerce.api.model.Category;

public interface ICategoryService {
	public List<Category> index();
	
	public Category show(Integer id);
	
	public Category store(Category category);
	
	public Category update(Integer id, Category category);
	
	public boolean destroy(Integer id);
}
