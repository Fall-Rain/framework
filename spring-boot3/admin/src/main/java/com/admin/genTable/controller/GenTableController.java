package com.admin.genTable.controller;

import com.admin.genTable.entity.GenTable;
import com.admin.genTable.entity.GenTableColumn;
import com.admin.genTable.service.IGenTableColumnService;
import com.admin.genTable.service.IGenTableService;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.entity.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("genTable")
@RequiredArgsConstructor
public class GenTableController {
    private final IGenTableService genTableService;
    private final IGenTableColumnService genTableColumnService;

    @GetMapping("list")
    public R list(GenTable genTable,
                  @RequestParam(name = "pageNo") Integer pageNo,
                  @RequestParam(name = "pageSize") Integer pageSize) {
        Page<GenTable> page = new Page<>(pageNo, pageSize);
        return R.ok(genTableService.page(page));
    }

    @PostMapping("createTable")
    public R createTable(@RequestBody JSONObject jsonObject) {
        String sql = jsonObject.getString("sql");
        return genTableService.createTable(sql);
    }

    @DeleteMapping("deleteTable")
    public R deleteTable(@RequestParam("id") String id) {
        genTableService.removeById(id);
        genTableColumnService.remove(Wrappers.<GenTableColumn>lambdaQuery().eq(GenTableColumn::getTableId, id));
        return R.ok("删除完成");
    }

    @PostMapping("addTable")
    public R addTable(@RequestBody GenTable genTable) {
        genTableService.save(genTable);
        return R.ok("新增完成");
    }

    @PutMapping("editTable")
    public R editTable(@RequestBody GenTable genTable) {
        genTableService.updateById(genTable);
        return R.ok("更新完成");
    }
}
