package org.example.web.api.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.business.user.UserService;
import org.example.common.Constant;
import org.example.data.model.user.User;
import org.example.web.annotation.UserSwagger;
import org.example.web.response.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("人员")
@UserSwagger
@RestController
@RequestMapping(Constant.API + "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("添加人员")
    @PostMapping
    public RestResponse<User> save(@RequestBody User request) {
        return new RestResponse<>(userService.save(request));
    }

}
