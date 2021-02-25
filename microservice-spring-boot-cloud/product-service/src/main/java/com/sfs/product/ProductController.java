package com.sfs.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private List<ProductInfo> productInfoList = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/product/details/{productId}")
    public Product getProductDetails(@PathVariable Long productId) {
        // get name and description from product-service
        ProductInfo productInfo = getProductInfo(productId);
        // get price from pricing-service
        Price price = restTemplate.getForObject("http://localhost:8002/price/" + productId, Price.class);
        // get stock availability from inventory-service
        Inventory inventory = restTemplate.getForObject("http://localhost:8003/inventory/" + productId, Inventory.class);

        return new Product(
                productInfo.getId(),
                productInfo.getName(),
                productInfo.getDescription(),
                price.getOriginalPrice(),
                inventory.getInStock());
    }

    private ProductInfo getProductInfo(Long productId) {
        populateProductList();

        for (ProductInfo productInfo : productInfoList) {
            if (productId.equals(productInfo.getId())) {
                return productInfo;
            }
        }

        return null;
    }

    private void populateProductList() {
        productInfoList.add(new ProductInfo(101L, "Avocato", "Avocato Azul"));
        productInfoList.add(new ProductInfo(102L, "Book", "The best book ever"));
        productInfoList.add(new ProductInfo(103L, "TV", "55 inches"));
    }
}
