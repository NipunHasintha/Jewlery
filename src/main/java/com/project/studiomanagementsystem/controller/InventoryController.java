package com.project.studiomanagementsystem.controller;


import com.project.studiomanagementsystem.dto.InventoryDTO;

import com.project.studiomanagementsystem.service.InventoryService;
import com.project.studiomanagementsystem.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/save")
    public CommonResponse save (@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.save(inventoryDTO);
    }

    @PutMapping("/update")
    public CommonResponse update (@RequestBody  InventoryDTO inventoryDTO){
        return inventoryService.update(inventoryDTO);
    }

    @GetMapping("/")
    public CommonResponse getAll(){
        return inventoryService.getAll();
    }

    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable String id){
        return inventoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    public CommonResponse delete(@PathVariable String id){
        return inventoryService.delete(id);
    }
}
