package com.project.studiomanagementsystem.controller;


import com.project.studiomanagementsystem.dto.CustomerDTO;
import com.project.studiomanagementsystem.service.CustomerService;
import com.project.studiomanagementsystem.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/saveCustomer")
    public CommonResponse saveCustomer (@RequestBody CustomerDTO customerDTO){
        return customerService.saveCustomer(customerDTO);
    }

    @PutMapping("/updateCustomer")
    public CommonResponse updateCustomer (@RequestBody CustomerDTO customerDTO){
        return customerService.updateCustomer(customerDTO);
    }

    @GetMapping("/")
    public CommonResponse getAll(){
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable String id){
        return customerService.findById(id);
    }

    @DeleteMapping("/{id}")
    public CommonResponse delete(@PathVariable String id){
        return customerService.delete(id);
    }

}
