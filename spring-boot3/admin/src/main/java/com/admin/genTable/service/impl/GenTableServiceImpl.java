package com.admin.genTable.service.impl;

import com.admin.genTable.entity.GenTable;
import com.admin.genTable.entity.GenTableColumn;
import com.admin.genTable.mapper.GenTableMapper;
import com.admin.genTable.service.IGenTableColumnService;
import com.admin.genTable.service.IGenTableService;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.common.entity.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements IGenTableService {
    private final IGenTableColumnService genTableColumnService;

    @Override
    public R createTable(String sql) {
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(sql, DbType.mysql);
        GenTable genTable = null;
        List<GenTableColumn> genTableColumnList = new ArrayList<>();
        if (sqlStatement instanceof MySqlCreateTableStatement mySqlCreateTableStatement) {
            genTable = new GenTable()
                    .setId(IdWorker.getIdStr())
                    .setTableName(mySqlCreateTableStatement.getTableSource().getTableName().replaceAll("`","")) // 表名
                    .setTableComment(mySqlCreateTableStatement.getComment() != null ? mySqlCreateTableStatement.getComment().toString().replaceAll("'", "") : "")
                    .setClassName(StringUtils.capitalize(toCamelCase(mySqlCreateTableStatement.getTableSource().getTableName().replaceAll("`",""))))
                    .setFunctionAuthor("yourName")
                    .setTplCategory("crud")
                    .setPackageName("com.example.module")
                    .setModuleName("module")
                    .setBusinessName("business")
                    .setGenType("0");
            for (SQLTableElement sqlTableElement : mySqlCreateTableStatement.getTableElementList()) {
//                SQLColumnDefinition column = (SQLColumnDefinition) element;
                if (sqlTableElement instanceof SQLColumnDefinition column) {

                    String comment = column.getComment() != null ? column.getComment().toString().replaceAll("'", "") : "";
                    GenTableColumn tableColumn = new GenTableColumn()
                            .setColumnName(column.getNameAsString().replaceAll("`",""))
                            // 类型（如 varchar(255)）
                            .setColumnType(column.getDataType().toString())
                            // 注释
                            .setColumnComment(comment)
                            // Java 类型推断（你可以自己实现规则）
                            .setJavaType(mapToJavaType(column.getDataType().getName()))
                            // Java 字段名（驼峰）
                            .setJavaField(toCamelCase(column.getNameAsString()).replaceAll("`",""))
                            // 是否主键
                            .setIsPk(false) // 后面单独处理主键
                            .setIsIncrement(false)
                            .setIsInsert(true)
                            .setIsEdit(true)
                            .setIsList(true)
                            .setIsQuery(false)
                            .setQueryType("EQ")
                            .setHtmlType("input")
                            .setSort(1)
                            .setTableId(genTable.getId());
                    genTableColumnList.add(tableColumn);
                }
            }
        }
        if (Objects.isNull(genTable)) {
            return R.error("生成失败");
        }
        this.save(genTable);
        if (!genTableColumnList.isEmpty()) {
            genTableColumnService.saveBatch(genTableColumnList);
        }
        return R.ok("生成完成");
    }

    private String toCamelCase(String str) {
        String[] parts = str.split("_");
        StringBuilder camel = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            camel.append(Character.toUpperCase(parts[i].charAt(0))).append(parts[i].substring(1));
        }
        return camel.toString();
    }

    private String mapToJavaType(String sqlType) {
        sqlType = sqlType.toLowerCase();
        if (sqlType.contains("varchar") || sqlType.contains("text") || sqlType.contains("char")) return "String";
        if (sqlType.contains("int")) return "Integer";
        if (sqlType.contains("bigint")) return "Long";
        if (sqlType.contains("decimal") || sqlType.contains("numeric")) return "BigDecimal";
        if (sqlType.contains("datetime") || sqlType.contains("timestamp")) return "LocalDateTime";
        if (sqlType.contains("date")) return "LocalDate";
        return "String";
    }
}
