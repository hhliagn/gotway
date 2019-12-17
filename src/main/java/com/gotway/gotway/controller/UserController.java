package com.gotway.gotway.controller;

import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.service.TokenService;
import com.gotway.gotway.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RestController
@RequestMapping("${api_version}/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Value("${img_url}")
    private String img_url ;
    @PostMapping(value="/register")
    public Object register(HttpServletRequest request)throws Exception{
        return userService.register(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/edit")
    public Object edit(HttpServletRequest request)throws Exception{
        return userService.edit(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/edit2")
    public Object edit2(HttpServletRequest request)throws Exception{
        return userService.edit2(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/back/getList")
    public Object list(HttpServletRequest request) throws Exception{
        Map<String, Object> map = userService.getList(RequestUtil.getMapByRequest(request)).getMap();
        map.put("img_url",img_url);
        return map;
    }
    @PostMapping(value="/back/addOrUpdate")
    public Object add(HttpServletRequest request) throws Exception{
        return userService.addOrUpdate(RequestUtil.getMapByRequest(request)).getMap();
    }

    @PostMapping(value="/login")
    public Object login(HttpServletRequest request) throws Exception {
        return userService.login(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/back/login")
    public Object backLogin(HttpServletRequest request) throws Exception {
        return userService.backLogin(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/logout")
    public Object logout(HttpServletRequest request) throws Exception {
        return userService.logout(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/checkToken")
    public Object is_login(HttpServletRequest request) throws Exception {
        Map<String, Object> mapByRequest = RequestUtil.getMapByRequest(request);
        //mapByRequest.put("token",request.getAttribute("token"));
        return tokenService.checkToken(mapByRequest).getMap();
    }
    @PostMapping(value="/del")
    public Object del(HttpServletRequest request) throws Exception {
        return userService.del(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/getAdminUser")
    public Object getAdminUser(HttpServletRequest request) throws Exception {
        return userService.getAdminUser(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/getVerifyCode")
    public Object getVerifyCode(HttpServletRequest request) throws Exception {
        return userService.getVerifyCode(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/resetPassword")
    public Object resetPassword(HttpServletRequest request) throws Exception {
        return userService.resetPassword(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/updateState")
    public Object restPassword(HttpServletRequest request) throws Exception {
        return userService.updateState(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/nearby")
    public Object nearby(HttpServletRequest request) throws Exception {
        return userService.nearby(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/updateLocation")
    public Object updateLocation(HttpServletRequest request) throws Exception {
        return userService.updateLocation(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/getSelfInfo")
    public Object getSelfInfo(HttpServletRequest request) throws Exception {
        return userService.getSelfInfo(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/updatePassword")
    public Object updatePassword(HttpServletRequest request) throws Exception {
        return userService.updatePassword(RequestUtil.getMapByRequest(request)).getMap();
    }
}
