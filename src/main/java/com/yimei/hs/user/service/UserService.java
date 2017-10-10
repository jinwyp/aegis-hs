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
import java.util.List;

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
                .setSubject(JsonMapper.nonEmptyMapper().toJson(user))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    @Transactional(readOnly = false)
    public int create(User user, User admin) {

        byte[] passwordSalt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), passwordSalt, HASH_INTERATIONS);

        user.setIsActive(true);
        user.setIsAdmin(false);
        user.setPassword(Encodes.encodeHex(hashPassword));
        user.setPasswordSalt(Encodes.encodeHex(passwordSalt));
        user.setCreateDate(LocalDateTime.now());

        if (admin == null) {
            user.setCreateBy("sys");
        } else {
            user.setCreateBy(admin.getPhone().toString());
        }
        return userMapper.insert(user);

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

    public User findOne(long id) {
        return userMapper.selectByPrimaryKey(id);
    }


    /**
     * 更新用户
     * @param user
     * @return
     */
    public int update(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public int changePassword(User userUpdate) {

        byte[] passwordSalt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(userUpdate.getPassword().getBytes(), passwordSalt, HASH_INTERATIONS);

        userUpdate.setPassword(Encodes.encodeHex(hashPassword));
        userUpdate.setPasswordSalt(Encodes.encodeHex(passwordSalt));

        int rtn = userMapper.updateByPrimaryKeySelective(userUpdate);

        return rtn;

    }

    /**
     * 获取用户同部门的其他用户
     * @param user
     * @return
     */
    public List<User> getUsersOfSameDept(User user) {
        return userMapper.getUsersOfSameDept(user);
    }
}
