package com.yimei.hs.user.service;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.user.dto.PageUserDTO;
import com.yimei.hs.user.mapper.UserMapper;
import com.yimei.hs.util.Digests;
import com.yimei.hs.util.Encodes;
import com.yimei.hs.util.JsonMapper;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String genAuthorization(User user) {
        return "Bearer " + Jwts.builder()
                .setSubject(JsonMapper.nonDefaultMapper().toJson(user))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    @Transactional(readOnly = false)
    public User register(User user) {

        byte[] passwordSalt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), passwordSalt, HASH_INTERATIONS);

        user.setIsActive(true);
        user.setIsAdmin(false);
        user.setPassword(Encodes.encodeHex(hashPassword));
        user.setPasswordSalt(Encodes.encodeHex(passwordSalt));
        user.setCreateDate(LocalDateTime.now());
        user.setCreateBy("sys");
        userMapper.insert(user);
        return user;

    }

    /**
     * 验证密码
     *
     * @param plainPassword
     * @return
     */
    public boolean validPasswordEquals(User user, String plainPassword) {
        Assert.notNull(user);
        String credentials = Encodes.encodeHex(Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(user.getPasswordSalt()), HASH_INTERATIONS));
        return user.getPassword().equals(credentials);
    }

    public User getUserByPhone(String phone) {
        return userMapper.loadByPhone(phone);
    }


    /**
     * 查询用户 - 分页
     * @param pageUserDTO
     * @return
     */
    public Page<User> getPage(PageUserDTO pageUserDTO) {
        return userMapper.getPage(pageUserDTO);
    }

    /**
     *  创建用户
     * @param user
     * @return
     */
    public int create(User user) {
        return userMapper.insert(user);
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    public int update(User user) {
        return userMapper.updateByPrimaryKey(user);
    }
}