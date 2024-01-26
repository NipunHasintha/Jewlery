package com.project.studiomanagementsystem.service;

import com.project.studiomanagementsystem.constant.CommonMsg;
import com.project.studiomanagementsystem.constant.CommonStatus;

import com.project.studiomanagementsystem.dto.InventoryDTO;
import com.project.studiomanagementsystem.entity.ContactInfo;
import com.project.studiomanagementsystem.entity.Inventory;

import com.project.studiomanagementsystem.repository.Inventoryrepository;
import com.project.studiomanagementsystem.util.CommonResponse;
import com.project.studiomanagementsystem.util.CommonValidation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    private final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private final Inventoryrepository inventoryrepository;
    private final ModelMapper modelMapper;

    @Autowired
    InventoryService(Inventoryrepository inventoryrepository, ModelMapper modelMapper){
        this.inventoryrepository=inventoryrepository;
        this.modelMapper=modelMapper;
    }

    /**
     * ========================================================================
     * This method is responsible save  {@link Inventory} details.
     * ========================================================================
     */
    public CommonResponse save (final InventoryDTO inventoryDTO){
        CommonResponse commonResponse =new CommonResponse();
        Inventory inventory;

        try{
            List<String> validationList =this.InventoryValidate(inventoryDTO);
            if(!validationList.isEmpty()){
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            inventory =castInventoryDTOIntoInventory(inventoryDTO);
            inventory=inventoryrepository.save(inventory);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(inventoryDTO));

        }catch (Exception e){
            LOGGER.error("/**************** Exception in InventoryService -> save()" + e);
        }
        return commonResponse;
    }

    /**
     * ========================================================================
     * This method is responsible update  {@link Inventory} details.
     * ========================================================================
     */

    public CommonResponse update (final InventoryDTO inventoryDTO){
        CommonResponse commonResponse=new CommonResponse();
        try {
            List<String>validationList=this.InventoryValidate(inventoryDTO);
            if(!validationList.isEmpty()){
                commonResponse.setErrorMessages((validationList));
                return commonResponse;
            }
            Inventory inventory =inventoryrepository.findById(Long.valueOf(inventoryDTO.getId())).get();
            inventory.setId(Long.valueOf(inventoryDTO.getId()));
            inventory.setItemName(inventoryDTO.getItemName());
            inventory.setGoldRate(Long.valueOf(inventoryDTO.getGoldRate()));
            inventory.setCarrots(Long.valueOf(inventoryDTO.getCarrots()));
            inventory.setDiscount(Long.valueOf(inventoryDTO.getDiscount()));
            inventory.setPrice(Long.valueOf(inventoryDTO.getPrice()));
            inventory.setWeight(Long.valueOf(inventoryDTO.getWeight()));
            inventory.setCommonStatus(inventoryDTO.getCommonStatus());

            inventoryrepository.save(inventory);
            commonResponse.setStatus(true);

        }catch (Exception e){
            LOGGER.error("/**************** Exception in InventoryService -> save()" + e);
        }
        return commonResponse;
    }

    /**
     * ========================================================================
     * This method is responsible delete  {@link Inventory} details.
     * ========================================================================
     */

    public CommonResponse delete (final String id){
        CommonResponse commonResponse =new CommonResponse();
        Inventory inventory;
        try {
            inventory= inventoryrepository.findById((Long.valueOf(id))).get();
            if (inventory!=null){
                inventory.setCommonStatus(CommonStatus.DELETED);
                inventoryrepository.save(inventory);
                commonResponse.setStatus(true);

            }else
                commonResponse.setErrorMessages(Collections.singletonList(CommonMsg.NOT_FOUND));

        }catch (Exception e){
            LOGGER.error("/**************** Exception in InventoryService -> save()" + e);
        }
        return commonResponse;
    }
    /**
     * ========================================================================
     * This method is responsible getById  {@link Inventory} details.
     * ========================================================================
     */

    public Inventory getById(String id){
        return inventoryrepository.findById(Long.parseLong(id)).get();
    }
    /**
     * ========================================================================
     * This method is responsible getall  {@link ContactInfo} details.
     * ========================================================================
     */

    public CommonResponse getAll(){
        CommonResponse commonResponse = new CommonResponse();
        List<InventoryDTO> inventoryDTOList;
        try {
            Predicate<Inventory> filterOnStatus = inventory -> inventory.getCommonStatus() !=CommonStatus.DELETED;

            inventoryDTOList =inventoryrepository.findAll()
                    .stream()
                    .filter(filterOnStatus)
                    .map(this::castInventoryIntoInventoryDTO)
                    .collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(inventoryDTOList));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in InventoryService -> getAll()" + e);

        }
        return commonResponse;

    }
    /**
     * ========================================================================
     * This method is responsible find by id  {@link ContactInfo} details.
     * ========================================================================
     */
    public  CommonResponse findById(final String id){
        CommonResponse commonResponse =new CommonResponse();
        Inventory inventory;
        try {
            inventory =inventoryrepository.getById(Long.parseLong(id));
            commonResponse.setPayload(Collections.singletonList(castInventoryIntoInventoryDTO(inventory)));
            commonResponse.setStatus(true);

        }catch (Exception e){
            LOGGER.error("/**************** Exception in InventoryService -> save()" + e);

        }return commonResponse;
    }

    /**
     * ========================================================================
     * This method is responsible castContactInfoIntoConatactinfoDTO {@link ContactInfo} details.
     * ========================================================================
     */

    public InventoryDTO castInventoryIntoInventoryDTO (Inventory inventory){
        return modelMapper.map(inventory, InventoryDTO.class);

    }

    /**
     * ========================================================================
     * This method is responsible castContactInfoDTOIntoConatactinfo {@link ContactInfo} details.
     * ========================================================================
     */

    public Inventory castInventoryDTOIntoInventory (InventoryDTO inventoryDTO){
        Inventory inventory =new Inventory();
        inventory.setPrice(Long.valueOf(inventoryDTO.getPrice()));
        inventory.setWeight(Long.valueOf(inventoryDTO.getWeight()));
        inventory.setDiscount(Long.valueOf(inventoryDTO.getDiscount()));
        inventory.setCarrots(Long.valueOf(inventoryDTO.getCarrots()));
        inventory.setGoldRate(Long.valueOf(inventoryDTO.getGoldRate()));
        inventory.setItemName(inventoryDTO.getItemName());
        inventory.setCommonStatus(inventoryDTO.getCommonStatus());
        return inventory;
    }

    /**
     * ========================================================================
     * This method is responsible Validation {@link ContactInfo} details.
     * ========================================================================
     */
    public List<String> InventoryValidate(InventoryDTO inventoryDTO){
        List<String>validationList =new ArrayList<>();
//        if (CommonValidation.stringNullValidation(inventoryDTO.getItemName()))
//            validationList.add(CommonMsg.CHECK_INPUT_DATA);


        return validationList;
    }
}
