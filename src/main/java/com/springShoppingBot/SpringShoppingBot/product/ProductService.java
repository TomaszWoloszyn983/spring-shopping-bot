package com.springShoppingBot.SpringShoppingBot.product;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Service
public class ProductService {

    public final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void deleteProductById(int id){
        productRepository.deleteById(id);
        System.out.println("Product by id "+id+" deleted from database.");
    }

    public Product findProductById(int id){
        System.out.println("Finding product: "+id);
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Product with id = "+id+" not found."
                ));
    }

    /**
     * Creates a new product and adds the product to datatable.
     *
     * @param product
     */
    public void createNewProduct(Product product) {
        productRepository.save(product);
    }

    /**
     * Finds product in the data table by Id.
     * If the product found in the data table delete the product from data table
     * Otherwise throw an exception.
     *
     * @param productId
     */
    public void deleteProduct(int productId){
        boolean productsExists = productRepository.existsById(productId);
        if(!productsExists){
            throw new IllegalStateException(
                    "Product does not exist in data table!"
            );
        }
        productRepository.deleteById(productId);
    }

    public void clearOrder(){
        productRepository.deleteAll();
    }
}
