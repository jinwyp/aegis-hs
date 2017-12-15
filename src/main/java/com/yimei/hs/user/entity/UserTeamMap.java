package com.yimei.hs.user.entity;

import com.yimei.hs.boot.api.CreateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTeamMap {

    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "用户Id不能为空")
    private Long userId;

    @NotNull(groups = {CreateGroup.class}, message = "团队Id不能为空")
    private Long teamId;

    private int  deleted;

    private LocalDateTime tsc;

}
