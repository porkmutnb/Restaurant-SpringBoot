package com.chermew.restaurant.service;

import com.chermew.restaurant.model.User;
import com.chermew.restaurant.model.auth.AuthenModel;
import com.chermew.restaurant.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UtilService utilService;

    public static Integer userId;

    public Boolean tokenIsAvailable(String token) throws Exception {
        if(token==null) {
            throw new ExceptionInInitializerError("Not found login, please login try again");
        }
        AuthenModel model = new AuthenModel();
        model.setToken(token);
        login(model);
        return true;
    }

    public Integer getUserId() throws Exception {
        return userId;
    }

    public User login(AuthenModel req) throws Exception {
        Date nowdate = new Date();
        if(req.getToken()==null || req.getToken().isEmpty()) {
            List<User> users = authRepository.login(req.getUsername(), utilService.encodeMD5(req.getPassword()));
            if(users.size()==0) {
                throw new ExceptionInInitializerError("Not found login, please login try again");
            }else {
                User user = users.get(0);
                String originalToken = user.getId().toString()+"|"+user.getUsername()+"|"+new SimpleDateFormat("yyyy-MM-dd H:m:s").format(nowdate);
                user.setToken(utilService.encodeToken(originalToken));
                authRepository.save(user);
                userId = user.getId();
                return user;
            }
        }else {
            List<User> users = authRepository.findByToken(req.getToken());
            if(users.size()==0) {
                throw new ExceptionInInitializerError("Not found login, please login try again");
            }else {
                User user = users.get(0);
                userId = user.getId();
                String checkToken = utilService.decodeToken(user.getToken());
                String[] splitToken = checkToken.split("\\|");
                Date tokenDate = new SimpleDateFormat("yyyy-MM-dd H:m:s", Locale.US).parse(splitToken[2]);
                LocalDate localDate = tokenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                localDate = localDate.plusDays(1);
                Date expireDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                if(nowdate.before(expireDate)) {
                    return user;
                }else {
                    throw new ExceptionInInitializerError("login is expire, please login try again");
                }
            }
        }
    }

    public User register(AuthenModel req) throws Exception {
        Date nowdate = new Date();
        User model = new User();
        model.setUsername(req.getUsername());
        model.setPassword(utilService.encodeMD5(req.getPassword()));
        model.setFullname(req.getFullname());
        model.setGenderId(utilService.isGenderExists(req.getGenderId()));
        model.setBirthdate(new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(req.getBirthdate()));
        model.setCreateDate(nowdate);
        return authRepository.save(model);
    }

    public User logout(AuthenModel req) throws Exception {
        List<User> userList = authRepository.findByToken(req.getToken());
        if(userList.size()==0) {
            throw new ExceptionInInitializerError("please login try again");
        }
        User model = authRepository.findById(userList.get(0).getId()).get();
        model.setToken(null);
        model.setUpdateDate(new Date());
        authRepository.save(model);
        return model;
    }
}
