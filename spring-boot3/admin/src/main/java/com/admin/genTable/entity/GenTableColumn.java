package com.admin.genTable.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName("gen_table_column")
@Accessors(chain = true)
public class GenTableColumn {
    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /** 所属表ID */
    private String tableId;

    /** 列名 */
    private String columnName;

    /** 列描述 */
    private String columnComment;

    /** 列类型（如 varchar(255)、int 等） */
    private String columnType;

    /** Java类型（如 java.lang.String、Integer） */
    private String javaType;

    /** Java字段名（如 userName） */
    private String javaField;

    /** 是否主键，1是，0否 */
    private Boolean isPk;

    /** 是否自增，1是，0否 */
    private Boolean isIncrement;

    /** 是否必填，1是，0否 */
    private Boolean isRequired;

    /** 是否插入字段，1是，0否 */
    private Boolean isInsert;

    /** 是否编辑字段，1是，0否 */
    private Boolean isEdit;

    /** 是否列表字段，1是，0否 */
    private Boolean isList;

    /** 是否查询字段，1是，0否 */
    private Boolean isQuery;

    /** 查询方式（如 EQ、LIKE、BETWEEN） */
    private String queryType;

    /** 表单控件类型（如 input、select、datetime） */
    private String htmlType;

    /** 字段排序 */
    private Integer sort;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
