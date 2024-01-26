package com.project.studiomanagementsystem.service;

import com.project.studiomanagementsystem.constant.CommonMsg;
import com.project.studiomanagementsystem.constant.CommonStatus;
import com.project.studiomanagementsystem.dto.Contact_infoDTO;
import com.project.studiomanagementsystem.entity.AuditData;
import com.project.studiomanagementsystem.entity.ContactInfo;
import com.project.studiomanagementsystem.entity.Customer;
import com.project.studiomanagementsystem.repository.Contactinforepository;
import com.project.studiomanagementsystem.util.CommonResponse;
import com.project.studiomanagementsystem.util.CommonValidation;
import com.project.studiomanagementsystem.util.DateTimeUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ContactInfoService {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);


    private final Contactinforepository contactinforepository;
    private final ModelMapper modelMapper;

    @Autowired
    ContactInfoService(Contactinforepository contactinforepository, ModelMapper modelMapper){
       this.contactinforepository=contactinforepository;
       this.modelMapper=modelMapper;
    }

    /**
     * ========================================================================
     * This method is responsible save  {@link ContactInfo} details.
     * ========================================================================
     */
    public CommonResponse save (final Contact_infoDTO contact_infoDTO){
        CommonResponse commonResponse =new CommonResponse();
        ContactInfo contactInfo;

        try{
            List<String>validationList =this.ContactInfoValidate(contact_infoDTO);
            if(!validationList.isEmpty()){
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }

//            if (CommonValidation.stringNullValidation(contact_infoDTO.getId())){
//                contactInfo = new ContactInfo();
//                AuditData auditData = AuditData
//                        .builder()
//                        .createdBy(Long.valueOf(token))
//                        .createdOn(DateTimeUtil.getSriLankaTime())
//                        .build();
//                contactInfo.setAuditData(auditData);
//            }else{
//                contactInfo = findById(contact_infoDTO.getId());
//                contactInfo.getAuditData().setUpdatedBy(Long.valueOf(token));
//                contactInfo.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
//            }
            contactInfo =castContactInfoDTOIntoConatactinfo(contact_infoDTO);
            contactInfo=contactinforepository.save(contactInfo);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(contact_infoDTO));

        }catch (Exception e){
            LOGGER.error("/**************** Exception in ContactInfoService -> save()" + e);
        }
        return commonResponse;
    }

    /**
     * ========================================================================
     * This method is responsible update  {@link ContactInfo} details.
     * ========================================================================
     */

    public CommonResponse update (final Contact_infoDTO contact_infoDTO){
        CommonResponse commonResponse=new CommonResponse();
        try {
            List<String>validationList=this.ContactInfoValidate(contact_infoDTO);
            if(!validationList.isEmpty()){
                commonResponse.setErrorMessages((validationList));
                return commonResponse;
            }
            ContactInfo contactInfo =contactinforepository.findById(Long.valueOf(contact_infoDTO.getId())).get();
            contactInfo.setId(Long.valueOf(contact_infoDTO.getId()));
            contactInfo.setAddress1(contact_infoDTO.getAddress1());
            contactInfo.setAddress2(contact_infoDTO.getAddress2());
            contactInfo.setCity(contact_infoDTO.getCity());
            contactInfo.setEmail(contact_infoDTO.getEmail());
            contactInfo.setCommonStatus(contact_infoDTO.getCommonStatus());

            contactinforepository.save(contactInfo);
            commonResponse.setStatus(true);

        }catch (Exception e){
            LOGGER.error("/**************** Exception in ContactinfoService -> save()" + e);
        }
        return commonResponse;
    }

    /**
     * ========================================================================
     * This method is responsible delete  {@link ContactInfo} details.
     * ========================================================================
     */

    public CommonResponse delete (final String id){
        CommonResponse commonResponse =new CommonResponse();
        ContactInfo contactInfo;
        try {
            contactInfo= contactinforepository.findById((Long.valueOf(id))).get();
            if (contactInfo!=null){
                contactInfo.setCommonStatus(CommonStatus.DELETED);
                contactinforepository.save(contactInfo);
                commonResponse.setStatus(true);

            }else
                commonResponse.setErrorMessages(Collections.singletonList(CommonMsg.NOT_FOUND));

        }catch (Exception e){
        LOGGER.error("/**************** Exception in ContactinfoService -> save()" + e);
    }
    return commonResponse;
    }
    /**
     * ========================================================================
     * This method is responsible getById  {@link ContactInfo} details.
     * ========================================================================
     */

    public ContactInfo getById(String id){
        return contactinforepository.findById(Long.parseLong(id)).get();
    }
    /**
     * ========================================================================
     * This method is responsible getall  {@link ContactInfo} details.
     * ========================================================================
     */

    public CommonResponse getAll(){
        CommonResponse commonResponse = new CommonResponse();
        List<Contact_infoDTO> Contact_infoDTOList;
        try {
            Predicate<ContactInfo> filterOnStatus =contactInfo -> contactInfo.getCommonStatus() !=CommonStatus.DELETED;

            Contact_infoDTOList =contactinforepository.findAll()
                    .stream()
                    .filter(filterOnStatus)
                    .map(this::castContactInfoIntoConatactinfoDTO)
                    .collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(Contact_infoDTOList));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in SlotService -> getAll()" + e);

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
        ContactInfo contactInfo;
        try {
            contactInfo =contactinforepository.getById(Long.parseLong(id));
            commonResponse.setPayload(Collections.singletonList(castContactInfoIntoConatactinfoDTO(contactInfo)));
            commonResponse.setStatus(true);

        }catch (Exception e){
            LOGGER.error("/**************** Exception in SlotService -> save()" + e);

        }return commonResponse;
    }

    /**
     * ========================================================================
     * This method is responsible castContactInfoIntoConatactinfoDTO {@link ContactInfo} details.
     * ========================================================================
     */

    public Contact_infoDTO castContactInfoIntoConatactinfoDTO (ContactInfo contactInfo){
        return modelMapper.map(contactInfo, Contact_infoDTO.class);

    }

    /**
     * ========================================================================
     * This method is responsible castContactInfoDTOIntoConatactinfo {@link ContactInfo} details.
     * ========================================================================
     */

    public ContactInfo castContactInfoDTOIntoConatactinfo (Contact_infoDTO contact_infoDTO){
        ContactInfo contactInfo =new ContactInfo();
        contactInfo.setEmail(contact_infoDTO.getEmail());
        contactInfo.setAddress1(contact_infoDTO.getAddress1());
        contactInfo.setAddress2(contact_infoDTO.getAddress2());
        contactInfo.setCity(contact_infoDTO.getCity());
        contactInfo.setCommonStatus(contact_infoDTO.getCommonStatus());
        return contactInfo;
    }

    /**
     * ========================================================================
     * This method is responsible Validation {@link ContactInfo} details.
     * ========================================================================
     */
    public List<String> ContactInfoValidate(Contact_infoDTO contact_infoDTO){
        List<String>validationList =new ArrayList<>();
//        if (CommonValidation.emailValidation(contact_infoDTO.getEmail()));
//        validationList.add(CommonMsg.Enter_Valid_Email);
//        if(CommonValidation.stringNullValidation(contact_infoDTO.getAddress1()));
//        validationList.add((CommonMsg.CHECK_INPUT_DATA));
//        if (CommonValidation.stringNullValidation(contact_infoDTO.getAddress2()));
//        validationList.add(CommonMsg.CHECK_INPUT_DATA);
//        if (CommonValidation.stringNullValidation(contact_infoDTO.getCity()));
//        validationList.add(CommonMsg.CHECK_INPUT_DATA);

        return validationList;
        }


}
