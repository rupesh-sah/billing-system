package com.rupesh.billing_system.service.impl;

import com.rupesh.billing_system.entities.Item;
import com.rupesh.billing_system.repo.ItemRepo;
import com.rupesh.billing_system.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Override
    public Item createItem(Item item) {
        return itemRepo.save(item);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }
}
