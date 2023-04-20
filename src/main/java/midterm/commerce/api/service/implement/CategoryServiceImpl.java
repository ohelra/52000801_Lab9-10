package midterm.commerce.api.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import midterm.commerce.api.model.Category;
import midterm.commerce.api.repository.CategoryRepository;
import midterm.commerce.api.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> index() {
		return categoryRepository.findAll();
	}

	@Override
	public Category show(Integer id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	public Category store(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category update(Integer id, Category category) {
		return categoryRepository.findById(id).map(categoryUpdate -> {
			categoryUpdate.setName(category.getName());
			categoryUpdate.setDescription(category.getDescription());
			return categoryRepository.save(categoryUpdate);
		}).orElse(null);
	}

	@Override
	public boolean destroy(Integer id) {
		Category categoryDestroy = categoryRepository.findById(id).orElse(null);
		if (categoryDestroy != null) {
			categoryDestroy.setActive(0);
			return categoryRepository.save(categoryDestroy) != null;
		}
		return false;
	}
}
