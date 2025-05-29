package com.admin.userInfo.controller;

import com.admin.userInfo.entity.UserInfoEntity;
import com.admin.userInfo.service.IUserInfoService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.entity.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("userInfo")
@RequiredArgsConstructor
public class UserInfoController {
    private final IUserInfoService userInfoService;

    @GetMapping("list")
    public R list(UserInfoEntity userInfo,
                  @RequestParam(name = "pageNo") Integer pageNo,
                  @RequestParam(name = "pageSize") Integer pageSize) {
        Page<UserInfoEntity> page = new Page<>(pageNo, pageSize);
        return R.ok(userInfoService.page(page));
    }

    @PutMapping("update")
    public R update(@RequestBody UserInfoEntity userInfo) {
        return R.ok(userInfoService.updateById(userInfo));
    }

    @PostMapping("save")
    public R save(@RequestBody UserInfoEntity userInfo) {
        userInfoService.save(userInfo);
        return R.ok("添加成功");
    }

    @DeleteMapping("delete")
    public R delete(@RequestParam(name = "id") String id) {
        userInfoService.removeById(id);
        return R.ok("删除成功");
    }

    @GetMapping("getById")
    public R getById(Long id) {
        return R.ok(userInfoService.getById(id));
    }

}
