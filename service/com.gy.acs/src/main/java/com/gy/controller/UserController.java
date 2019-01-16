package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.constants.RedisKeyConstants;
import com.gy.domain.ResultPage;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.sys.User;
import com.gy.domain.vo.req.UserQuery;
import com.gy.domain.vo.res.DynamicSecretKey;
import com.gy.enums.ErrorCodeEnum;
import com.gy.redis.RedisService;
import com.gy.service.sys.UserService;
import com.gy.util.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@Api(tags={"用户信息接口"}, description = "User-API")
@RequestMapping("/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户信息", notes="获取用户信息")
    @GetMapping("/getUser/{userId}")
    public ReturnInfo<User> getUserById(@ApiParam(value = "用户ID",required = true)@PathVariable String userId) {
        User user = userService.getUserById(userId);
        System.out.println(user);
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(user);
    }

    @ApiOperation(value = "获取分页用户列表", notes = "获取分页用户列表")
    @PostMapping("/listAllUserPage")
    public ReturnInfo<ResultPage<User>> ListAllUserPage(@ApiParam("用户查询参数") @RequestBody UserQuery userQuery) {
        PageInfo<User> pageInfo = userService.selectListUserPage(userQuery);
        ResultPage<User> resultPage = ResultPage.getInstance().setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return new ReturnInfo<User>().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }

    /**
     * 这里已经在 passwordFilter 进行了登录认证
     * 登录签发 JWT
     * @param appId 应用ID
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "POST用户登录签发JWT")
    @PostMapping("/login")
    public ReturnInfo<User> login(@ApiParam(value="应用ID",required=true) @RequestParam String appId) {
        //根据AppId获取用户信息
        User user = userService.getUserById(appId);
        // 根据appId获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
        String roles = userService.loadAccountRole(user.getUserId());
        // 时间以秒计算, token有效时间是 token签发到期时间的2倍
        String jwtToken = JsonWebTokenUtil.issueJWT(UUID.randomUUID().toString(),  user.getUserId(),
                "asc-server", (long) (RedisKeyConstants.JWT_TTL >> 1),
                roles, null, SignatureAlgorithm.HS512);
        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
        RedisService.getRedisService().putObject("JWT-SESSION-" + user.getUserId(), jwtToken, RedisKeyConstants.JWT_TTL);
        user.setPassword(null);
        user.setSalt(null);
        user.setToken(jwtToken);
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(user);
    }

    @ApiOperation(value = "用户获取动态秘钥", notes = "用户获取动态秘钥")
    @GetMapping("/getDynamicSecretKey")
    public ReturnInfo<DynamicSecretKey> getDynamicSecretKey(HttpServletRequest request) {
        //动态生成秘钥，redis存储秘钥供之后秘钥验证使用，设置有效期5秒用完即丢弃
        String userKey = StringUtil.randomNumStr(6).toUpperCase();
        String tokenKey = StringUtil.randomNumStr(16);
        try {
            logger.info("TOKEN_KEY_"+ IpUtil.getIpFromRequest(WebUtils.toHttp(request)).toUpperCase()+userKey);
            RedisService.getRedisService().putObject(
                    "TOKEN_KEY_"+ IpUtil.getIpFromRequest(WebUtils.toHttp(request)).toUpperCase()+userKey,
                    tokenKey,
                    RedisKeyConstants.TOKEN_TTL);
            //动态秘钥返回给前端
            DynamicSecretKey dynamicSecretKey = new DynamicSecretKey();
            dynamicSecretKey.setUserKey(userKey);
            dynamicSecretKey.setTokenKey(tokenKey);
            return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(dynamicSecretKey);
        }catch (Exception e) {
            return ReturnInfo.getInstance().setResult(ErrorCodeEnum.ISSUE_TOKEN_FAIL);
        }
    }

    @ApiOperation(value = "添加用户", notes = "添加用户")
    @PostMapping("/addUser")
    public ReturnInfo addUser(@ApiParam(name = "User",value = "用户对象",required = true) @RequestBody User user) {
        int flag = userService.addUser(user);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "修改用户", notes = "修改用户")
    @PutMapping("/updateUser")
    public ReturnInfo updateUser(@ApiParam(name = "User", value = "用户对象", required = true) @RequestBody User user) {
        int flag = userService.updateUser(user);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @DeleteMapping("/deleteUser/{userId}")
    public ReturnInfo deleteUser(@ApiParam(name = "userId", value = "用户ID", required = true) @PathVariable String userId) {
        int flag = userService.deleteUser(userId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    //重置用户密码
    @ApiOperation(value = "重置用户密码", notes = "重置用户秘密")
    @PutMapping("/reSetPassword/{userId}")
    public int reSetPassword(@ApiParam(name = "userId",value = "用户ID",required = true) @PathVariable String userId) {
        /*int flag = userService.reSetPassword(usertId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);*/
        return userService.reSetPassword(userId);
    }
}
