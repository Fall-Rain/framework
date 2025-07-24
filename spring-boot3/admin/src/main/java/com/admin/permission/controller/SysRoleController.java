package com.admin.permission.controller;

import com.admin.permission.entity.SysRole;
import com.admin.permission.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.entity.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sysRole")
@RequiredArgsConstructor
public class SysRoleController {
    private final ISysRoleService sysRoleService;

    @GetMapping("list")
    public R list(SysRole role,
                  @RequestParam(name = "pageNo") Integer pageNo,
                  @RequestParam(name = "pageSize") Integer pageSize) {
        Page<SysRole> page = new Page<>(pageNo, pageSize);
        return R.ok(sysRoleService.page(page));
    }

    @PutMapping("update")
    public R update(@RequestBody SysRole sysRole) {
        return R.ok(sysRoleService.updateById(sysRole));
    }

    @PostMapping("save")
    public R save(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return R.ok("添加成功");
    }

    @DeleteMapping("delete")
    public R delete(@RequestParam(name = "id") String id) {
        sysRoleService.removeById(id);
        return R.ok("删除成功");
    }

    @GetMapping("getById")
    public R getById(String id) {
        return R.ok(sysRoleService.getById(id));
    }

    @GetMapping("getRoleList")
    public R getRoleList() {
        return R.ok(sysRoleService.list());
    }
}
