package com.project.studiomanagementsystem.service;


import com.project.studiomanagementsystem.constant.CommonMsg;
import com.project.studiomanagementsystem.constant.CommonStatus;
import com.project.studiomanagementsystem.dto.PawnDTO;
import com.project.studiomanagementsystem.entity.ContactInfo;
import com.project.studiomanagementsystem.entity.Pawn;
import com.project.studiomanagementsystem.repository.CustomerRepository;
import com.project.studiomanagementsystem.repository.Pawnrepository;
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
public class PawnService {
        private final Logger LOGGER = LoggerFactory.getLogger(com.project.studiomanagementsystem.service.InventoryService.class);

        @Autowired
        private final Pawnrepository pawnrepository;
        private final ModelMapper modelMapper;
        private final CustomerService customerService;

        @Autowired
        PawnService(Pawnrepository pawnrepository, ModelMapper modelMapper, CustomerRepository customerRepository, CustomerService customerService){
            this.pawnrepository=pawnrepository;
            this.modelMapper=modelMapper;
            this.customerService = customerService;
        }

        /**
         * ========================================================================
         * This method is responsible save  {@link Pawn} details.
         * ========================================================================
         */
        public CommonResponse save (final PawnDTO pawnDTO){
            CommonResponse commonResponse =new CommonResponse();
            Pawn pawn;

            try{
                List<String> validationList =this.PawnValidate(pawnDTO);
                if(!validationList.isEmpty()){
                    commonResponse.setErrorMessages(validationList);
                    return commonResponse;
                }
                pawn =castPawnDTOIntoPawn(pawnDTO);
                pawn=pawnrepository.save(pawn);
                commonResponse.setStatus(true );
                commonResponse.setPayload(Collections.singletonList(pawnDTO));

            }catch (Exception e){
                LOGGER.error("/**************** Exception in PawnService -> save()" + e);
            }
            return commonResponse;
        }

        /**
         * ========================================================================
         * This method is responsible update  {@link Pawn} details.
         * ========================================================================
         */

        public CommonResponse update (final PawnDTO pawnDTO){
            CommonResponse commonResponse=new CommonResponse();
            try {
                List<String>validationList=this.PawnValidate(pawnDTO);
                if(!validationList.isEmpty()){
                    commonResponse.setErrorMessages((validationList));
                    return commonResponse;
                }
                Pawn pawn =pawnrepository.findById(Long.valueOf(pawnDTO.getId())).get();
                pawn.setId(Long.valueOf(pawnDTO.getId()));
                pawn.setGrossWeight(Long.valueOf(pawnDTO.getGrossWeight()));
                pawn.setNetWeight(Long.valueOf(pawnDTO.getNetWeight()));
                pawn.setValueofItem(Long.valueOf(pawnDTO.getValueofItem()));
                pawn.setCommonStatus(pawnDTO.getCommonStatus());

                pawnrepository.save(pawn);
                commonResponse.setStatus(true);

            }catch (Exception e){
                LOGGER.error("/**************** Exception in PawnService -> save()" + e);
            }
            return commonResponse;
        }

        /**
         * ========================================================================
         * This method is responsible delete  {@link Pawn} details.
         * ========================================================================
         */

        public CommonResponse delete (final String id){
            CommonResponse commonResponse =new CommonResponse();
            Pawn pawn;
            try {
                pawn= pawnrepository.findById((Long.valueOf(id))).get();
                if (pawn!=null){
                    pawn.setCommonStatus(CommonStatus.DELETED);
                    pawnrepository.save(pawn);
                    commonResponse.setStatus(true);

                }else
                    commonResponse.setErrorMessages(Collections.singletonList(CommonMsg.NOT_FOUND));

            }catch (Exception e){
                LOGGER.error("/**************** Exception in PawnService -> save()" + e);
            }
            return commonResponse;
        }
        /**
         * ========================================================================
         * This method is responsible getById  {@link Pawn} details.
         * ========================================================================
         */

        public Pawn getById(String id){
            return pawnrepository.findById(Long.parseLong(id)).get();
        }
        /**
         * ========================================================================
         * This method is responsible getall  {@link ContactInfo} details.
         * ========================================================================
         */

        public CommonResponse getAll(){
            CommonResponse commonResponse = new CommonResponse();
            List<PawnDTO> pawnDTOList;
            try {
                Predicate<Pawn> filterOnStatus = pawn -> pawn.getCommonStatus() !=CommonStatus.DELETED;

                  pawnDTOList =pawnrepository.findAll()
                        .stream()
                        .filter(filterOnStatus)
                        .map(this::castPawnIntoPawnDTO)
                        .collect(Collectors.toList());

                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(pawnDTOList));
            }catch (Exception e){
                LOGGER.error("/**************** Exception in PawnService -> getAll()" + e);

            }
            return commonResponse;

        }




    /**
         * ========================================================================
         * This method is responsible find by id  {@link Pawn} details.
         * ========================================================================
         */
        public  CommonResponse findById(final String id){
            CommonResponse commonResponse =new CommonResponse();
            Pawn pawn;
            try {
                pawn =pawnrepository.getById(Long.parseLong(id));
                commonResponse.setPayload(Collections.singletonList(castPawnIntoPawnDTO(pawn)));
                commonResponse.setStatus(true);

            }catch (Exception e){
                LOGGER.error("/**************** Exception in PawnService -> save()" + e);

            }return commonResponse;
        }

        /**
         * ========================================================================
         * This method is responsible castContactInfoIntoConatactinfoDTO {@link ContactInfo} details.
         * ========================================================================
         */

        public PawnDTO castPawnIntoPawnDTO (Pawn pawn){
           PawnDTO pawnDTO =modelMapper.map(pawn,PawnDTO.class);
           pawnDTO.setCustomer(customerService.castCustomerIntoCustomerDTO(pawn.getCustomer()));
           return pawnDTO;
        }

        /**
         * ========================================================================
         * This method is responsible castContactInfoDTOIntoConatactinfo {@link Pawn} details.
         * ========================================================================
         */

        public Pawn castPawnDTOIntoPawn (PawnDTO pawnDTO){
            return modelMapper.map(pawnDTO,Pawn.class);

        }

        /**
         * ========================================================================
         * This method is responsible Validation {@link ContactInfo} details.
         * ========================================================================
         */
        public List<String> PawnValidate(PawnDTO pawnDTO){
            List<String>validationList =new ArrayList<>();
//        if (CommonValidation.stringNullValidation(inventoryDTO.getItemName()))
//            validationList.add(CommonMsg.CHECK_INPUT_DATA);


            return validationList;
        }
    }


