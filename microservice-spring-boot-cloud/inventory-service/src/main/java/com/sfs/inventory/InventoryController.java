package com.sfs.inventory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InventoryController {

    private final List<Inventory> inventoryList = new ArrayList<>();

    @GetMapping("/inventory/{productId}")
    public Inventory getInventoryDetais(@PathVariable Long productId) {
        // get price from pricing-service
        return getInventoryInfo(productId);
    }

    private Inventory getInventoryInfo(Long productId) {
        populateInventoryList();

        for (Inventory inventory : inventoryList) {
            if (productId.equals(inventory.getProductId())) {
                return inventory;
            }
        }
        return null;
    }

    private void populateInventoryList() {
        inventoryList.add(new Inventory(301L, 101L, true));
        inventoryList.add(new Inventory(302L, 102L, true));
        inventoryList.add(new Inventory(303L, 103L, false));
    }
}
