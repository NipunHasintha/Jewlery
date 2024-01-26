package com.project.studiomanagementsystem.controller;

import com.project.studiomanagementsystem.dto.Contact_infoDTO;
import com.project.studiomanagementsystem.service.ContactInfoService;
import com.project.studiomanagementsystem.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactInfoController {
    private ContactInfoService contactInfoService;

    @Autowired
    private ContactInfoController(ContactInfoService contactInfoService) {this.contactInfoService =contactInfoService; }

    @PostMapping("/save")
    public CommonResponse save (@RequestBody Contact_infoDTO contact_infoDTO) {return contactInfoService.save(contact_infoDTO);}

    @PutMapping("/update")
    public CommonResponse update(@RequestBody Contact_infoDTO contact_infoDTO) {return contactInfoService.update(contact_infoDTO);}

    @DeleteMapping("/{id}")
    public CommonResponse delete(@PathVariable String id){return contactInfoService.delete(id);}

    @GetMapping("/")
    public CommonResponse getAll(){return contactInfoService.getAll();}

    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable String id)
    {return contactInfoService.findById(id);}
}

