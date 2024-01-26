package com.project.studiomanagementsystem.controller;

import com.project.studiomanagementsystem.dto.PawnDTO;
import com.project.studiomanagementsystem.dto.Safe_LockerDTO;
import com.project.studiomanagementsystem.service.PawnService;
import com.project.studiomanagementsystem.service.SafelockerService;
import com.project.studiomanagementsystem.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/safelocker")
public class SafeLockerController {
    @Autowired
    private SafelockerService safelockerService;

    @PostMapping("/save")
    public CommonResponse save(@RequestBody Safe_LockerDTO safe_lockerDTO) {
        return safelockerService.save(safe_lockerDTO);
    }

    @PutMapping("/update")
    public CommonResponse update(@RequestBody Safe_LockerDTO safe_lockerDTO) {
        return safelockerService.update(safe_lockerDTO);
    }

    @GetMapping("/")
    public CommonResponse getAll() {
        return safelockerService.getAll();
    }

    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable String id) {
        return safelockerService.findById(id);
    }

    @DeleteMapping("/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return safelockerService.delete(id);

    }
}
