package Lab13.psu.edu.lab13.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import Lab13.psu.edu.lab13.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>,
		PagingAndSortingRepository<Product, Integer> {
}
