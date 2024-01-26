package com.project.studiomanagementsystem.service;

import com.project.studiomanagementsystem.constant.CommonMsg;
import com.project.studiomanagementsystem.constant.CommonStatus;
import com.project.studiomanagementsystem.dto.CustomerDTO;
import com.project.studiomanagementsystem.entity.Customer;
import com.project.studiomanagementsystem.repository.CustomerRepository;
import com.project.studiomanagementsystem.util.CommonResponse;
import com.project.studiomanagementsystem.util.CommonValidation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CustomerService {
    private final Logger LOGGER = getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * ========================================================================
     * This method is responsible save  {@link Customer} details.
     * ========================================================================
     */


    public CommonResponse saveCustomer(CustomerDTO customerDTO) {
        CommonResponse commonResponse = new CommonResponse();
        // Customer customer =new Customer();
        try {
            List<String> validation = validateCustomerDTO(customerDTO);
            if (!CollectionUtils.isEmpty(validation)) {
                commonResponse.setErrorMessages(validation);
                return commonResponse;
            }

            //customer =castCustomerDTOIntoCustomer(customer,customerDTO);
            Customer customer = modelMapper.map(customerDTO, Customer.class);
            customerRepository.save(customer);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(customerDTO));

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in CustomerService -> saveCustomer()" + e);
        }
        return commonResponse;
    }


    /**
     * ========================================================================
     * This method is responsible update  {@link Customer} details.
     * ========================================================================
     */

    public CommonResponse updateCustomer(CustomerDTO customerDTO) {
        CommonResponse commonResponse = new CommonResponse();
        //  Customer customer;
        try {
            List<String> validations = validateCustomerDTO(customerDTO);
            if (!CollectionUtils.isEmpty(validations)) {
                commonResponse.setErrorMessages(validations);
                return commonResponse;
            }
            Customer customer = this.getById(customerDTO.getCustomer_id());
            customer = modelMapper.map(customerDTO, Customer.class);
            customerRepository.save(customer);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(customerDTO));


        } catch (Exception e) {
            LOGGER.error("/**************** Exception in CustomerService -> updateCustomer()" + e);
        }
        return commonResponse;
    }

    /**
     * ========================================================================
     * This method is responsible GetAll  {@link Customer} details.
     * ========================================================================
     */
    public CommonResponse getAll() {
        CommonResponse commonResponse = new CommonResponse();
        List<CustomerDTO> customerDTOList;
        try {
            Predicate<Customer> filterOnStatus = customer -> customer.getCommonStatus() != CommonStatus.DELETED;
            customerDTOList = customerRepository.findAll()
                    .stream()
                    .filter(filterOnStatus)
                    .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                    .collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(customerDTOList));

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in CustomerService -> save()" + e);
        }
        return commonResponse;
    }


    /**
     * ========================================================================
     * This method is responsible getId  {@link Customer} details.
     * ========================================================================
     */

    private Customer getById(String customer_id) {
        return customerRepository.findById(Long.valueOf(customer_id)).get();
    }
//
//    public CommonResponse findById(String customer_id) {
//        CommonResponse commonResponse = new CommonResponse();
//        Customer customer;
//        try{
//            customer = customerRepository.getById(Long.valueOf(customer_id));
//            commonResponse.setPayload(Collections.singletonList (getCustomerDTO(customer)));
//            commonResponse.setStatus(true);
//        }catch (Exception e){
//            LOGGER.error("/**************** Exception in CustomerService -> save()" + e);
//        }
//        return commonResponse;
//    }


    /**
     * ========================================================================
     * This method is responsible Validate  {@link Customer} details.
     * ========================================================================
     */


    private List<String> validateCustomerDTO(CustomerDTO customerDTO) {
        List<String> validations = new ArrayList<>();
        if (customerDTO == null) {
            validations.add(CommonMsg.CHECK_INPUT_DATA);
        }
        if (CommonValidation.stringNullValidation(customerDTO.getFirstName())) {
            validations.add(CommonMsg.ENTER_YOUR_NAME);
        }
        if (CommonValidation.stringNullValidation(customerDTO.getLastName())) {
            validations.add(CommonMsg.ENTER_YOUR_NAME);
        }

        if (CommonValidation.stringNullValidation(customerDTO.getIncome())) {
            validations.add(CommonMsg.CHECK_INPUT_DATA);
        }
        if (CommonValidation.stringNullValidation(customerDTO.getMartialstatus())) {
            validations.add(CommonMsg.CHECK_INPUT_DATA);
        }
        if (CommonValidation.stringNullValidation(customerDTO.getCustomerstatus())) {
            validations.add(CommonMsg.CHECK_INPUT_DATA);
        }

        return validations;
    }

    /**
     * ========================================================================
     * This method is responsible findById {@link Customer} details.
     * ========================================================================
     */

//    public Customer findbyId(String id) {
//
//        return customerRepository.getById(Long.parseLong(id));
//
//    }

    public CommonResponse findById(final String id) {
        CommonResponse commonResponse = new CommonResponse();
        Customer customer;
        try {
            customer = customerRepository.getById(Long.parseLong(id));
            commonResponse.setPayload(Collections.singletonList(castCustomerIntoCustomerDTO(customer)));
            commonResponse.setStatus(true);

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in CustomerService -> find()" + e);
        }
        return commonResponse;
    }

    /**
     * ========================================================================
     * This method is responsible castCustomerIntoCustomerDTO {@link Customer} details.
     * ========================================================================
     * @return
     */


        public CustomerDTO castCustomerIntoCustomerDTO (Customer customer){
            return modelMapper.map(customer, CustomerDTO.class);
        }


    /**
     * ========================================================================
     * This method is responsible Delete {@link Customer} details.
     * ========================================================================
     */

    public CommonResponse delete(final String id){
        CommonResponse commonResponse=new CommonResponse();
        Customer customer;
        try {
            customer = customerRepository.findById(Long.valueOf(id)).get();
            if (customer != null) {
                customer.setCommonStatus(CommonStatus.DELETED);
                customerRepository.save(customer);
                commonResponse.setStatus(true);


            } else
                commonResponse.setErrorMessages(Collections.singletonList(CommonMsg.NOT_FOUND));
        }catch (Exception e) {
            LOGGER.error("/**************** Exception in CustomerService -> save()" + e);
        }
        return commonResponse;
    }



}
