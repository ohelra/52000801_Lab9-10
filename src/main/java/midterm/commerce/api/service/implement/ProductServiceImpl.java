package midterm.commerce.api.service.implement;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import midterm.commerce.api.model.Product;
import midterm.commerce.api.repository.ProductRepository;
import midterm.commerce.api.service.IProductService;
import midterm.commerce.api.upload.FileUploadUtil;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> index() {
        return productRepository.findAll();
    }

    @Override
    public Product show(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product store(Product product, MultipartFile imageFile) {
        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        product.setImage(fileName);

        try {
            Product savedProduct = productRepository.save(product);
            FileUploadUtil.saveFile("src/main/resources/static/uploads/" + savedProduct.getId(), fileName, imageFile);
            return productRepository.findById(savedProduct.getId()).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Could not store product: " + product.getName(), e);
        }
    }

    @Override
    public Product update(Integer id, Product product, MultipartFile imageFile) {
        return productRepository.findById(id).map(productToUpdate -> {
            if (Objects.nonNull(imageFile) && !imageFile.isEmpty()) {
                try {
                    String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
                    FileUploadUtil.saveFile("src/main/resources/static/uploads/" + id, fileName, imageFile);
                    productToUpdate.setImage(fileName);
                } catch (Exception e) {
                    throw new RuntimeException("Could not store product image for: " + productToUpdate.getName(), e);
                }
            }

            productToUpdate.setName(product.getName());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setColor(product.getColor());
            productToUpdate.setTrademark(product.getTrademark());
            productToUpdate.setCategory(product.getCategory());
            return productRepository.save(productToUpdate);
        }).orElse(null);
    }

    @Override
    public boolean destroy(Integer id) {
        Product productDestroy = productRepository.findById(id).orElse(null);
        if (productDestroy != null) {
            productDestroy.setActive(0);
            return productRepository.save(productDestroy) != null;
        }
        return false;
    }

    @Override
    public List<Product> findProductsByCategory(Integer categoryId) {
        return productRepository.findProductsByCategory(categoryId);
    }

    @Override
    public List<Product> sortProductsByPriceAsc() {
        return productRepository.sortProductsByPriceAsc();
    }

    @Override
    public List<Product> sortProductsByPriceDesc() {
        return productRepository.sortProductsByPriceDesc();
    }

    @Override
    public List<Product> findProductsByColor(String color) {
        return productRepository.findProductsByColor(color);
    }

    @Override
    public List<Product> findProductsByTrademark(String trademark) {
        return productRepository.findProductsByTrademark(trademark);
    }
}