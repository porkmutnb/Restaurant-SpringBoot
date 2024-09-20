package com.chermew.restaurant.service;

import com.chermew.restaurant.model.Config;
import com.chermew.restaurant.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;

@Service
public class UtilService {

    @Autowired
    private ConfigRepository configRepository;

    public List<Config> getGenderAll() {
        return configRepository.findByName("Gender");
    }

    public List<Config> getStatusOrderAll() {
        return configRepository.findByName("StatusOrder");
    }

    public List<Config> findByConfig(String configName) {
        switch (configName) {
            case "Gender":
                return getGenderAll();
            case "StatusOrder":
                return getStatusOrderAll();
            default:
                throw new ExceptionInInitializerError("Not found configName:"+configName);
        }
    }

    public String encodeMD5(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public Integer isGenderExists(Integer genderId) {
        Config model = configRepository.findById(genderId).get();
        if(model==null) {
            throw new ExceptionInInitializerError("Not found genderId");
        }else {
            return genderId;
        }
    }

    public String encodeToken(String originalToken) throws Exception {
        return Base64.getEncoder().encodeToString(originalToken.getBytes());
    }

    public String decodeToken(String token) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        return new String(decodedBytes);
    }

}
