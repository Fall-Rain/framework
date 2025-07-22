package com.admin.genTable.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName("gen_table")
@Accessors(chain = true)
public class GenTable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 实体类名
     */
    private String className;

    /**
     * 模板类别
     */
    private String tplCategory;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 功能名称
     */
    private String functionName;

    /**
     * 作者
     */
    private String functionAuthor;

    /**
     * 生成类型 0本地 1远程
     */
    private String genType;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
