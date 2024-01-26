package com.project.studiomanagementsystem.controller;

import com.project.studiomanagementsystem.dto.PawnDTO;
import com.project.studiomanagementsystem.dto.SellingDTO;
import com.project.studiomanagementsystem.service.PawnService;
import com.project.studiomanagementsystem.service.SellingService;
import com.project.studiomanagementsystem.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/selling")
public class SellingController {
    @Autowired
    private SellingService sellingService;

    @PostMapping("/save")
    public CommonResponse save(@RequestBody SellingDTO sellingDTO) {
        return sellingService.save(sellingDTO);
    }

    @PutMapping("/update")
    public CommonResponse update(@RequestBody SellingDTO sellingDTO) {
        return sellingService.update(sellingDTO);
    }

    @GetMapping("/")
    public CommonResponse getAll() {
        return sellingService.getAll();
    }

    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable String id) {
        return sellingService.findById(id);
    }

    @DeleteMapping("/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return sellingService.delete(id);

    }
}