package com.admin.genTable.service;

import com.admin.genTable.entity.GenTable;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.entity.R;

public interface IGenTableService extends IService<GenTable> {
    R createTable(String sql);
}
