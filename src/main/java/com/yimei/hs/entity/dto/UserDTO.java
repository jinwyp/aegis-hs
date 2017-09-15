package com.yimei.hs.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by xiangyang on 2017/7/2.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;

    @NotEmpty
    private String personalName;

    @NotEmpty
    private String securePhone;

    @NotEmpty
    private String plainPassword;
}
