package psu.se411.Lab13.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import psu.se411.Lab13.model.Product;

@Repository
public interface ProductRepository
		extends CrudRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {

}
