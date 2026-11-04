package Lab13.psu.edu.lab13.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import Lab13.psu.edu.lab13.model.Product;
import Lab13.psu.edu.lab13.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}

	public Product save(Product product) {
		if (product == null) {
			throw new IllegalArgumentException("Product cannot be null");
		}
		if (product.id() != null && product.id() <= 0) {
			throw new IllegalArgumentException("Product id must be positive");
		}
		return productRepository.save(product);
	}
}
