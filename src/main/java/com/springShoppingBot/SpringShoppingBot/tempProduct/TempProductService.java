package com.springShoppingBot.SpringShoppingBot.tempProduct;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TempProductService {

    public final TempProductRepository tempProductRepository;

    public TempProductService(TempProductRepository tempProductRepository) {
        this.tempProductRepository = tempProductRepository;
    }

    public List<TempProduct> getAllProducts(){
        return tempProductRepository.findAll();
    }

//    public List<TempProduct> getAllTempProducts(){
//        return tempProductRepository.findAll();
//    }

    public void deleteProductById(int id){
        tempProductRepository.deleteById(id);
        System.out.println("Product by id "+id+" deleted from database.");
    }

    public TempProduct findProductById(int id){
        System.out.println("Finding product: "+id);
        return tempProductRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Product with id = "+id+" not found."
                ));
    }

    /**
     * Creates a new product and adds the product to datatable.
     *
     * @param product
     */
    public void createNewProduct(TempProduct product) {
        tempProductRepository.save(product);
    }

    /**
     * Finds product in the data table by Id.
     * If the product found in the data table delete the product from data table
     * Otherwise throw an exception.
     *
     * @param productId
     */
    public void deleteProduct(int productId){
        boolean productsExists = tempProductRepository.existsById(productId);
        if(!productsExists){
            throw new IllegalStateException(
                    "Product does not exist in data table!"
            );
        }
        tempProductRepository.deleteById(productId);
    }

    public void clearOrder(){
        tempProductRepository.deleteAll();
    }
}
