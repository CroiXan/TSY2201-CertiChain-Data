package com.certichain.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.certichain.data.model.UserType;
import com.certichain.data.repository.UserTypeRepository;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> getAll(){
        return userTypeRepository.findAll();
    }

    public UserType createUserType(UserType userType){
        return userTypeRepository.save(userType);
    }

    public Optional<UserType> getUserSubTypeById(String Id){
        return userTypeRepository.findById(Id);
    }

    public Optional<UserType> updateUserSubType(UserType userType){

        Optional<UserType> findedRequest = userTypeRepository.findById(userType.getId());

        if(findedRequest.isPresent()){
            return Optional.of(userTypeRepository.save(userType));
        }else{
            return Optional.empty();
        }
        
    }

    public boolean deleteUserSubTypeById(String Id){
        userTypeRepository.deleteById(Id);

        Optional<UserType> findedUserType = userTypeRepository.findById(Id);

        if(findedUserType.isPresent()){
            userTypeRepository.deleteById(Id);
            return true;
        }else{
            return false;
        }
    }

}
