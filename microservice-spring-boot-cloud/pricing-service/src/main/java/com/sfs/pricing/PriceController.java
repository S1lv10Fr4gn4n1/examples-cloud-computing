package com.sfs.pricing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PriceController {

    private final List<Price> priceList = new ArrayList<>();

    @GetMapping("/price/{productId}")
    public Price getProductPrice(@PathVariable Long productId) {
        // get price from pricing-service
        return getPriceInfo(productId);
    }

    private Price getPriceInfo(Long productId) {
        populatePriceList();

        for (Price price : priceList) {
            if (productId.equals(price.getProductId())) {
                return price;
            }
        }

        return null;
    }

    private void populatePriceList() {
        priceList.add(new Price(201L, 101L, 19, 11));
        priceList.add(new Price(202L, 102L, 40, 32));
        priceList.add(new Price(203L, 103L, 1200, 999));
    }
}
