package com.certichain.data.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
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
        ObjectId oid = new ObjectId(Id);
        return userTypeRepository.findById(oid);
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
        ObjectId oid = new ObjectId(Id);
        userTypeRepository.deleteById(oid);

        Optional<UserType> findedUserType = userTypeRepository.findById(oid);

        if(findedUserType.isPresent()){
            userTypeRepository.deleteById(oid);
            return true;
        }else{
            return false;
        }
    }

}
