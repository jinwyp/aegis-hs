package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import lombok.*;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YingTransfer implements Serializable {

    @NonNull
    private Long id;

    @NonNull
    private Long orderId;

    @NonNull
    private Long fromUserId;

    @NonNull
    private Long toUserId;

    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "创建时间由数据库决定")
    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

}
