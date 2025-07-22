package com.admin.genTable.service.impl;

import com.admin.genTable.entity.GenTableColumn;
import com.admin.genTable.mapper.GenTableColumnMapper;
import com.admin.genTable.service.IGenTableColumnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GenTableColumnServiceImpl extends ServiceImpl<GenTableColumnMapper, GenTableColumn> implements IGenTableColumnService {
}
