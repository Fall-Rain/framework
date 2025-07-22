package com.admin.genTable.controller;

import com.admin.genTable.entity.GenTable;
import com.admin.genTable.entity.GenTableColumn;
import com.admin.genTable.service.IGenTableColumnService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.entity.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("genTableColumn")
@RequiredArgsConstructor
public class GenTableColumnController {
    private final IGenTableColumnService genTableColumnService;

    @GetMapping("list")
    public R list(GenTableColumn genTableColumn,
                  @RequestParam(name = "pageNo") Integer pageNo,
                  @RequestParam(name = "pageSize") Integer pageSize) {
        Page<GenTableColumn> page = new Page<>(pageNo, pageSize);
        return R.ok(genTableColumnService.page(page, Wrappers.<GenTableColumn>lambdaQuery().eq(GenTableColumn::getTableId, genTableColumn.getTableId())));
    }

}
