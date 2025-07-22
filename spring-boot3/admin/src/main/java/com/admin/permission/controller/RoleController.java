package com.admin.permission.controller;

import com.admin.permission.entity.RoleEntity;
import com.admin.permission.service.IRoleService;
import com.admin.userInfo.entity.UserInfoEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.entity.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;

@RestController
@RequestMapping("role")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;

    @GetMapping("list")
    public R list(RoleEntity role,
                  @RequestParam(name = "pageNo") Integer pageNo,
                  @RequestParam(name = "pageSize") Integer pageSize) {
        Page<RoleEntity> page = new Page<>(pageNo, pageSize);
        return R.ok(roleService.page(page));
    }

    @PutMapping("update")
    public R update(@RequestBody RoleEntity roleEntity) {
        return R.ok(roleService.updateById(roleEntity));
    }

    @PostMapping("save")
    public R save(@RequestBody RoleEntity roleEntity) {
        roleService.save(roleEntity);
        return R.ok("添加成功");
    }

    @DeleteMapping("delete")
    public R delete(@RequestParam(name = "id") String id) {
        roleService.removeById(id);
        return R.ok("删除成功");
    }

    @GetMapping("getById")
    public R getById(String id) {
        return R.ok(roleService.getById(id));
    }

    @GetMapping("getRoleList")
    public R getRoleList() {
        return R.ok(roleService.list());
    }
}
