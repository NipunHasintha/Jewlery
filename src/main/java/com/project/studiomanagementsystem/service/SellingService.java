package com.project.studiomanagementsystem.service;

import com.project.studiomanagementsystem.constant.CommonMsg;
import com.project.studiomanagementsystem.constant.CommonStatus;
import com.project.studiomanagementsystem.dto.PawnDTO;
import com.project.studiomanagementsystem.dto.SellingDTO;
import com.project.studiomanagementsystem.entity.ContactInfo;
import com.project.studiomanagementsystem.entity.Pawn;
import com.project.studiomanagementsystem.entity.Selling;
import com.project.studiomanagementsystem.repository.CustomerRepository;
import com.project.studiomanagementsystem.repository.Sellingrepository;
import com.project.studiomanagementsystem.util.CommonResponse;
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
public class SellingService {
    private final Logger LOGGER = LoggerFactory.getLogger(com.project.studiomanagementsystem.service.SellingService.class);

    @Autowired
    private final Sellingrepository sellingrepository;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;

    @Autowired
    SellingService(Sellingrepository sellingrepository, ModelMapper modelMapper, CustomerRepository customerRepository, CustomerService customerService){
        this.sellingrepository=sellingrepository;
        this.modelMapper=modelMapper;
        this.customerService = customerService;
    }

    /**
     * ========================================================================
     * This method is responsible save  {@link Selling} details.
     * ========================================================================
     */
    public CommonResponse save (final SellingDTO sellingDTO){
        CommonResponse commonResponse =new CommonResponse();
        Selling selling;

        try{
            List<String> validationList =this.SellingValidate(sellingDTO);
            if(!validationList.isEmpty()){
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            selling =castSellingDTOIntoSelling(sellingDTO);
            selling=sellingrepository.save(selling);
            commonResponse.setStatus(true );
            commonResponse.setPayload(Collections.singletonList(sellingDTO));

        }catch (Exception e){
            LOGGER.error("/**************** Exception in SellingService -> save()" + e);
        }
        return commonResponse;
    }

    /**
     * ========================================================================
     * This method is responsible update  {@link Selling} details.
     * ========================================================================
     */

    public CommonResponse update (final SellingDTO sellingDTO){
        CommonResponse commonResponse=new CommonResponse();
        try {
            List<String>validationList=this.SellingValidate(sellingDTO);
            if(!validationList.isEmpty()){
                commonResponse.setErrorMessages((validationList));
                return commonResponse;
            }
            Selling selling =sellingrepository.findById(Long.valueOf(sellingDTO.getId())).get();
            selling.setId(Long.valueOf(sellingDTO.getId()));
            selling.setGoldRate(Long.valueOf(sellingDTO.getGoldRate()));
            selling.setWeight(Long.valueOf(sellingDTO.getWeight()));
            selling.setPrice(Long.valueOf(sellingDTO.getPrice()));
            selling.setCarrots(Long.valueOf(sellingDTO.getCarrots()));
            selling.setCommonStatus(sellingDTO.getCommonStatus());

            sellingrepository.save(selling);
            commonResponse.setStatus(true);

        }catch (Exception e){
            LOGGER.error("/**************** Exception in SellingService -> save()" + e);
        }
        return commonResponse;
    }

    /**
     * ========================================================================
     * This method is responsible delete  {@link Selling} details.
     * ========================================================================
     */

    public CommonResponse delete (final String id){
        CommonResponse commonResponse =new CommonResponse();
        Selling selling;
        try {
            selling= sellingrepository.findById((Long.valueOf(id))).get();
            if (selling!=null){
                selling.setCommonStatus(CommonStatus.DELETED);
                sellingrepository.save(selling);
                commonResponse.setStatus(true);

            }else
                commonResponse.setErrorMessages(Collections.singletonList(CommonMsg.NOT_FOUND));

        }catch (Exception e){
            LOGGER.error("/**************** Exception in SellingService -> save()" + e);
        }
        return commonResponse;
    }
    /**
     * ========================================================================
     * This method is responsible getById  {@link Selling} details.
     * ========================================================================
     */

    public Selling getById(String id){
        return sellingrepository.findById(Long.parseLong(id)).get();
    }
    /**
     * ========================================================================
     * This method is responsible getall  {@link Selling} details.
     * ========================================================================
     */

    public CommonResponse getAll(){
        CommonResponse commonResponse = new CommonResponse();
        List<SellingDTO> sellingDTOList;
        try {
            Predicate<Selling> filterOnStatus = selling -> selling.getCommonStatus() !=CommonStatus.DELETED;

            sellingDTOList =sellingrepository.findAll()
                    .stream()
                    .filter(filterOnStatus)
                    .map(this::castSellingIntoSellingDTO)
                    .collect(Collectors.toList());

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(sellingDTOList));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in SellingService -> getAll()" + e);

        }
        return commonResponse;

    }




    /**
     * ========================================================================
     * This method is responsible find by id  {@link Selling} details.
     * ========================================================================
     */
    public  CommonResponse findById(final String id){
        CommonResponse commonResponse =new CommonResponse();
        Selling selling;
        try {
            selling =sellingrepository.getById(Long.parseLong(id));
            commonResponse.setPayload(Collections.singletonList(castSellingIntoSellingDTO(selling)));
            commonResponse.setStatus(true);

        }catch (Exception e){
            LOGGER.error("/**************** Exception in SellingService -> save()" + e);

        }return commonResponse;
    }

    /**
     * ========================================================================
     * This method is responsible castSellingIntoSellingDTO {@link Selling} details.
     * ========================================================================
     */

    public SellingDTO castSellingIntoSellingDTO (Selling selling){
        SellingDTO sellingDTO =modelMapper.map(selling,SellingDTO.class);
        sellingDTO.setCustomer(customerService.castCustomerIntoCustomerDTO(selling.getCustomer()));
        return sellingDTO;
    }

    /**
     * ========================================================================
     * This method is responsible castSellingDTOIntoSelling {@link Selling} details.
     * ========================================================================
     */

    public Selling castSellingDTOIntoSelling (SellingDTO sellingDTO){
        return modelMapper.map(sellingDTO,Selling.class);

    }

    /**
     * ========================================================================
     * This method is responsible Validation {@link Selling} details.
     * ========================================================================
     */
    public List<String> SellingValidate(SellingDTO sellingDTO){
        List<String>validationList =new ArrayList<>();
//        if (CommonValidation.stringNullValidation(inventoryDTO.getItemName()))
//            validationList.add(CommonMsg.CHECK_INPUT_DATA);


        return validationList;
    }
}
