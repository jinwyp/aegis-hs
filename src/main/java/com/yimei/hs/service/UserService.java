package com.yimei.hs.service;

import com.yimei.hs.entity.User;
import com.yimei.hs.entity.dto.UserDTO;
import com.yimei.hs.mapper.UserMapper;
import com.yimei.hs.util.BeanMapper;
import com.yimei.hs.util.Digests;
import com.yimei.hs.util.Encodes;
import com.yimei.hs.util.JsonMapper;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * Created by xiangyang on 2017/7/1.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    @Autowired
    private UserMapper userMapper;

    @Value("${jwt.secureKey}")
    private String secretKey;

    //生成jwt token
    public String genAuthorization(UserDTO userDTO) {
        return "Bearer " + Jwts.builder()
                .setSubject(JsonMapper.nonDefaultMapper().toJson(userDTO))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    @Transactional(readOnly = false)
    public User registerUser(UserDTO userDTO) {
        byte[] passwordSalt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(userDTO.getPlainPassword().getBytes(), passwordSalt, HASH_INTERATIONS);
        User user= BeanMapper.map(userDTO,User.class);
        user.setIsActive(true);
        user.setPassword(Encodes.encodeHex(hashPassword));
        user.setPasswordSalt(Encodes.encodeHex(passwordSalt));
        user.setCreateDate(LocalDateTime.now());
        user.setCreateBy("register");
        userMapper.insertSelective(user);
        return user;

    }

    /**
     * 验证密码
     * @param phone
     * @param plainPassword
     * @return
     */
    public boolean validPasswordEquals(String phone, String plainPassword) {

//        User user = userMapper.(phone);
//        Assert.notNull(user);
//        String credentials = Encodes.encodeHex(Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(user.getPasswordSalt()), HASH_INTERATIONS));

        // return user.getPassword().equals(credentials);
        return true;
    }


    public User loadBySecurePhone(String phone) {
        // return userMapper.loadByPhone(phone);
        return null;
    }


}
