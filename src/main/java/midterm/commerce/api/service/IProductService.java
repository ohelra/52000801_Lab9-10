package midterm.commerce.api.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import midterm.commerce.api.model.Product;

public interface IProductService {
	public List<Product> index();
	
	public Product show(Integer id);
	
	public Product store(Product product, MultipartFile imageFile);
	
	public Product update(Integer id, Product product, MultipartFile imageFile);
	
	public boolean destroy(Integer id);

	public List<Product> findProductsByCategory(Integer categoryId);

    public List<Product> sortProductsByPriceAsc();

    public List<Product> sortProductsByPriceDesc();

	public List<Product> findProductsByColor(String color);

	public List<Product> findProductsByTrademark(String trademark);
}