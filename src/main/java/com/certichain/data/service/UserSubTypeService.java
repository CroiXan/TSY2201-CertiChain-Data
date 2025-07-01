package com.certichain.data.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.certichain.data.model.UserSubType;
import com.certichain.data.repository.UserSubTypeRepository;

@Service
public class UserSubTypeService {

    private final UserSubTypeRepository userSubTypeRepository; 

    public UserSubTypeService(UserSubTypeRepository userSubTypeRepository) {
        this.userSubTypeRepository = userSubTypeRepository;
    }

    public List<UserSubType> getAll(){
        return userSubTypeRepository.findAll();
    }

    public UserSubType createUserSubType(UserSubType userSubType){
        return userSubTypeRepository.save(userSubType);
    }

    public Optional<UserSubType> getUserSubTypeById(String Id){
        ObjectId oid = new ObjectId(Id);
        return userSubTypeRepository.findById(oid);
    }

    public Optional<UserSubType> updateUserSubType(UserSubType userSubType){

        Optional<UserSubType> findedRequest = userSubTypeRepository.findById(userSubType.getId());

        if(findedRequest.isPresent()){
            return Optional.of(userSubTypeRepository.save(userSubType));
        }else{
            return Optional.empty();
        }
        
    }

    public boolean deleteUserSubTypeById(String Id){
        ObjectId oid = new ObjectId(Id);
        userSubTypeRepository.deleteById(oid);

        Optional<UserSubType> findedUserSubType = userSubTypeRepository.findById(oid);

        if(findedUserSubType.isPresent()){
            userSubTypeRepository.deleteById(oid);
            return true;
        }else{
            return false;
        }
    }

}
