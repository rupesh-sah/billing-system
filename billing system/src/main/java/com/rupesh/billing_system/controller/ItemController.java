package com.rupesh.billing_system.controller;

import com.rupesh.billing_system.entities.Bill;
import com.rupesh.billing_system.entities.Item;
import com.rupesh.billing_system.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> createBill(@RequestBody Item item){
        Item item1 = itemService.createItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item1);

    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems(){
        List<Item> getAllItems = itemService.getAllItems();
        return  ResponseEntity.status(HttpStatus.OK).body(getAllItems);
    }
}
