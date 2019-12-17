package com.gotway.gotway.controller;

import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.service.RoleService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RestController
@RequestMapping("${api_version}/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping(value="/getList")
    public Object list(HttpServletRequest request) throws Exception{
        return roleService.getList(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/addOrUpdate")
    public Object add(HttpServletRequest request) throws Exception{
        return roleService.addOrUpdate(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/del")
    public Object del(HttpServletRequest request) throws Exception{
        return roleService.del(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/auth")
    public Object auth(HttpServletRequest request) throws Exception{
        return roleService.auth(RequestUtil.getMapByRequest(request)).getMap();
    }
}
