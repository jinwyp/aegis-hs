package com.yimei.hs.same.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    @NotNull(groups = {CreateGroup.class}, message = "业务线不能为空")
    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    @NotNull(groups = {CreateGroup.class}, message = "实体id不能为空")
    private Long entityId;

    @NotNull(groups = {CreateGroup.class}, message = "实体类型不能为空")
    private EntityType entityType;

    @NotNull(groups = {CreateGroup.class}, message = "备注不能为空")
    private String memo;

    @Null(groups = {UpdateGroup.class}, message = "创建时间有数据库生成")
    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;
}
