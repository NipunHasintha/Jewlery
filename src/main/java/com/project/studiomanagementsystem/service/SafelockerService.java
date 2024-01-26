package com.project.studiomanagementsystem.service;

import com.project.studiomanagementsystem.constant.CommonMsg;
import com.project.studiomanagementsystem.constant.CommonStatus;
import com.project.studiomanagementsystem.dto.InventoryDTO;
import com.project.studiomanagementsystem.dto.Safe_LockerDTO;
import com.project.studiomanagementsystem.entity.ContactInfo;
import com.project.studiomanagementsystem.entity.Inventory;
import com.project.studiomanagementsystem.entity.SafeLocker;
import com.project.studiomanagementsystem.repository.Safelockerrepository;
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
public class SafelockerService {

        private final Logger LOGGER = LoggerFactory.getLogger(com.project.studiomanagementsystem.service.InventoryService.class);

        @Autowired
        private final Safelockerrepository safelockerrepository;
        private final ModelMapper modelMapper;

        @Autowired
        SafelockerService(Safelockerrepository safelockerrepository, ModelMapper modelMapper){
            this.safelockerrepository=safelockerrepository;
            this.modelMapper=modelMapper;
        }



    /**
         * ========================================================================
         * This method is responsible save  {@link SafeLocker} details.
         * ========================================================================
         */
        public CommonResponse save (final Safe_LockerDTO safe_lockerDTO){
            CommonResponse commonResponse =new CommonResponse();
            SafeLocker safeLocker;

            try{
                List<String> validationList =this.SafelockerValidate(safe_lockerDTO);
                if(!validationList.isEmpty()){
                    commonResponse.setErrorMessages(validationList);
                    return commonResponse;
                }
                safeLocker =castSafeLockerDTOIntoSafeLocker(safe_lockerDTO);
                safeLocker=safelockerrepository.save(safeLocker);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(safe_lockerDTO));

            }catch (Exception e){
                LOGGER.error("/**************** Exception in SafeLockerService -> save()" + e);
            }
            return commonResponse;
        }

        /**
         * ========================================================================
         * This method is responsible update  {@link SafeLocker} details.
         * ========================================================================
         */

        public CommonResponse update (final Safe_LockerDTO safe_lockerDTO){
            CommonResponse commonResponse=new CommonResponse();
            try {
                List<String>validationList=this.SafelockerValidate(safe_lockerDTO);
                if(!validationList.isEmpty()){
                    commonResponse.setErrorMessages((validationList));
                    return commonResponse;
                }
                SafeLocker safeLocker =safelockerrepository.findById(Long.valueOf(safe_lockerDTO.getId())).get();
                safeLocker.setId(Long.valueOf(safe_lockerDTO.getId()));
                safeLocker.setLockerName(safe_lockerDTO.getLockerName());
                safeLocker.setLocation(safe_lockerDTO.getLocation());
                safeLocker.setSize(Long.valueOf(safe_lockerDTO.getSize()));
                safeLocker.setCommonStatus(safe_lockerDTO.getCommonStatus());

                safelockerrepository.save(safeLocker);
                commonResponse.setStatus(true);

            }catch (Exception e){
                LOGGER.error("/**************** Exception in SafelockerService -> save()" + e);
            }
            return commonResponse;
        }

        /**
         * ========================================================================
         * This method is responsible delete  {@link SafeLocker} details.
         * ========================================================================
         */

        public CommonResponse delete (final String id){
            CommonResponse commonResponse =new CommonResponse();
            SafeLocker safeLocker;
            try {
                safeLocker= safelockerrepository.findById((Long.valueOf(id))).get();
                if (safeLocker!=null){
                    safeLocker.setCommonStatus(CommonStatus.DELETED);
                    safelockerrepository.save(safeLocker);
                    commonResponse.setStatus(true);

                }else
                    commonResponse.setErrorMessages(Collections.singletonList(CommonMsg.NOT_FOUND));

            }catch (Exception e){
                LOGGER.error("/**************** Exception in SafelockerService -> save()" + e);
            }
            return commonResponse;
        }
        /**
         * ========================================================================
         * This method is responsible getById  {@link SafeLocker} details.
         * ========================================================================
         */

        public SafeLocker getById(String id){
            return safelockerrepository.findById(Long.parseLong(id)).get();
        }
        /**
         * ========================================================================
         * This method is responsible getall  {@link ContactInfo} details.
         * ========================================================================
         */

        public CommonResponse getAll(){
            CommonResponse commonResponse = new CommonResponse();
            List<Safe_LockerDTO> safe_lockerDTOList;
            try {
                Predicate<SafeLocker> filterOnStatus = safeLocker -> safeLocker.getCommonStatus() !=CommonStatus.DELETED;

                safe_lockerDTOList =safelockerrepository.findAll()
                        .stream()
                        .filter(filterOnStatus)
                        .map(this::castSafelockerIntoSafelockerDTO)
                        .collect(Collectors.toList());
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(safe_lockerDTOList));
            }catch (Exception e){
                LOGGER.error("/**************** Exception in SafelockerService -> getAll()" + e);

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
            SafeLocker safeLocker;
            try {
                safeLocker =safelockerrepository.getById(Long.parseLong(id));
                commonResponse.setPayload(Collections.singletonList(castSafelockerIntoSafelockerDTO(safeLocker)));
                commonResponse.setStatus(true);

            }catch (Exception e){
                LOGGER.error("/**************** Exception in SafelockerService -> save()" + e);

            }return commonResponse;
        }

        /**
         * ========================================================================
         * This method is responsible castSafelockerIntoSafelockerDTO {@link SafeLocker} details.
         * ========================================================================
         */

        public Safe_LockerDTO castSafelockerIntoSafelockerDTO (SafeLocker safeLocker){
            return modelMapper.map(safeLocker, Safe_LockerDTO.class);

        }

        /**
         * ========================================================================
         * This method is responsible castSafelockerDTOIntoSafelocker {@link SafeLocker} details.
         * ========================================================================
         */

        public SafeLocker castSafeLockerDTOIntoSafeLocker (Safe_LockerDTO safe_lockerDTO){
            SafeLocker safeLocker =new SafeLocker();
            safeLocker.setLockerName(safe_lockerDTO.getLockerName());
            safeLocker.setSize(Long.valueOf(safe_lockerDTO.getSize()));
            safeLocker.setLocation(safe_lockerDTO.getLocation());
            safeLocker.setCommonStatus(safe_lockerDTO.getCommonStatus());
            return safeLocker;
        }

        /**
         * ========================================================================
         * This method is responsible Validation {@link SafeLocker} details.
         * ========================================================================
         */
        public List<String> SafelockerValidate (Safe_LockerDTO safe_lockerDTO){
            List<String>validationList =new ArrayList<>();
//        if (CommonValidation.stringNullValidation(inventoryDTO.getItemName()))
//            validationList.add(CommonMsg.CHECK_INPUT_DATA);


            return validationList;
        }
    }


