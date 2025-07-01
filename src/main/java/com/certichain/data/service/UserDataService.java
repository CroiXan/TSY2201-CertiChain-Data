package com.certichain.data.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.certichain.data.model.UserData;
import com.certichain.data.repository.UserDataRepository;

@Service
public class UserDataService {

    private final UserDataRepository userDataRepository;

    public UserDataService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public List<UserData> findAll() {
        return userDataRepository.findAll();
    }

    public Optional<UserData> findById(String id) {
        ObjectId oid = new ObjectId(id);
        return userDataRepository.findById(oid);
    }

    public Optional<UserData> findByUserID(String userID) {
        return userDataRepository.findByUserID(userID);
    }

    public UserData create(UserData userData) {
        return userDataRepository.save(userData);
    }

    public Optional<UserData> update(String id, UserData newData) {
        ObjectId oid = new ObjectId(id);
        Optional<UserData> findedUserData = userDataRepository.findById(oid);

        if(findedUserData.isPresent()){
            return Optional.of(userDataRepository.save(newData));
        }else{
            return Optional.empty();
        }
    }

    public void deleteById(String id) {
        ObjectId oid = new ObjectId(id);
        userDataRepository.deleteById(oid);
    }

    public List<UserData> findByUserTypeId(String userTypeId) {
        return userDataRepository.findAllByUserTypeId(userTypeId);
    }

}
