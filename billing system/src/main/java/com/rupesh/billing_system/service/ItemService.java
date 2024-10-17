package com.rupesh.billing_system.service;

import com.rupesh.billing_system.entities.Bill;
import com.rupesh.billing_system.entities.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {

    Item createItem(Item item);

    List<Item> getAllItems();
}
