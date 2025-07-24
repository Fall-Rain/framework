package com.admin.system;

import com.admin.sysUser.entity.SysUser;
import com.admin.sysUser.response.LoginUserResponse;
import com.common.entity.R;
import com.framework.utils.FileUtils;
import com.framework.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("system")
public class SystemController {
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public R login(@RequestBody SysUser sysUser) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword()));
            SysUser loginUser = (SysUser) authenticate.getPrincipal();
            String token = TokenUtils.generateSystemToken(loginUser.getId());
            return R.ok(token, "登录成功");
        }catch (AuthenticationException e) {
            return R.error(e.getMessage());
        }

    }

    @GetMapping("getUserInfo")
    public R getUserInfo() {
        return R.ok(TokenUtils.getUser(LoginUserResponse.class));
    }

    @PostMapping("upload")
    public R upload(@RequestParam("file") MultipartFile file) throws IOException {
        return FileUtils.upload(file.getInputStream(), file.getOriginalFilename(), "upload");
    }
}
