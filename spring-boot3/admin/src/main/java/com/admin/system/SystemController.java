package com.admin.system;

import com.admin.userInfo.entity.UserInfoEntity;
import com.admin.userInfo.response.LoginUserResponse;
import com.common.entity.R;
import com.framework.utils.FileUtils;
import com.framework.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("system")
public class SystemController {
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public R login(@RequestBody UserInfoEntity userInfoEntity) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userInfoEntity.getUsername(), userInfoEntity.getPassword()));
        UserInfoEntity loginUser = (UserInfoEntity) authenticate.getPrincipal();
        String token = TokenUtils.generateSystemToken(loginUser.getId());
        return R.ok(token, "登录成功");
    }

    @GetMapping("getUserInfo")
    public R getUserInfo() {
        return R.ok(TokenUtils.getUser(LoginUserResponse.class));
    }

    @PostMapping("upload")
    public R upload(@RequestParam("file") MultipartFile file) throws IOException {
        return FileUtils.upload(file.getInputStream(), "", "");
    }
}
