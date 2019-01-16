package com.felix.security.jwt.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @author grez
 * @since 19-1-13
 **/
@TableName("user_authority")
public class UserAuthority {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name", strategy = FieldStrategy.NOT_NULL)
    private AuthorityName name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }
}
