package com.project.studiomanagementsystem.controller;

import com.project.studiomanagementsystem.dto.InventoryDTO;
import com.project.studiomanagementsystem.dto.PawnDTO;
import com.project.studiomanagementsystem.service.InventoryService;
import com.project.studiomanagementsystem.service.PawnService;
import com.project.studiomanagementsystem.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pawn")
public class PawnController {
    @Autowired
    private PawnService pawnService;

    @PostMapping("/save")
    public CommonResponse save (@RequestBody PawnDTO pawnDTO){
        return pawnService.save(pawnDTO);
    }

    @PutMapping("/update")
    public CommonResponse update (@RequestBody  PawnDTO pawnDTO){
        return pawnService.update(pawnDTO);
    }

    @GetMapping("/")
    public CommonResponse getAll(){
        return pawnService.getAll();
    }

    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable String id){
        return pawnService.findById(id);
    }

    @DeleteMapping("/{id}")
    public CommonResponse delete(@PathVariable String id){
        return pawnService.delete(id);

    }
}
