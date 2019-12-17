package com.gotway.gotway.controller;

import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RestController
@RequestMapping("${api_version}/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping(value="/getList")
    public Object list(HttpServletRequest request) throws Exception{
        return menuService.getList(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/addOrUpdate")
    public Object add(HttpServletRequest request) throws Exception{
        return menuService.addOrUpdate(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/del")
    public Object del(HttpServletRequest request) throws Exception{
        return menuService.del(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/getRoleMenu")
    public Object getRoleMenu(HttpServletRequest request) throws Exception{
        return menuService.getRoleMenu(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/getUserMenu")
    public Object getUserMenu(HttpServletRequest request) throws Exception{
        Map<String, Object> mapByRequest = RequestUtil.getMapByRequest(request);
        mapByRequest.put("token",request.getAttribute("token"));
        return menuService.getUserMenu(mapByRequest).getMap();
    }
}
