package com.admin.sysUser.controller;

import com.admin.sysUser.entity.SysUser;
import com.admin.sysUser.request.SysUserRequest;
import com.admin.sysUser.service.ISysUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.entity.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sysUser")
@RequiredArgsConstructor
public class SysUserController {
    private final ISysUserService userInfoService;

    @GetMapping("list")
    public R list(SysUser userInfo,
                  @RequestParam(name = "pageNo") Integer pageNo,
                  @RequestParam(name = "pageSize") Integer pageSize) {
        Page<SysUser> page = new Page<>(pageNo, pageSize);
        return R.ok(userInfoService.getList(page));
    }

    @PutMapping("update")
    public R update(@RequestBody SysUserRequest userInfo) {
        userInfoService.updateById(userInfo);
        return R.ok("更新成功");
    }

    @PostMapping("save")
    public R save(@RequestBody SysUserRequest userInfo) {
        userInfoService.save(userInfo);
        return R.ok("添加成功");
    }

    @DeleteMapping("delete")
    public R delete(@RequestParam(name = "id") String id) {
        userInfoService.removeById(id);
        return R.ok("删除成功");
    }

    @GetMapping("getById")
    public R getById(@RequestParam(name = "id") String id) {
        return R.ok(userInfoService.getById(id));
    }

    @PutMapping("generatePassword")
    public R generatePassword(@RequestBody SysUser userInfo) {
        userInfoService.generatePassword(userInfo);
        return R.ok("生成完成");
    }


}
