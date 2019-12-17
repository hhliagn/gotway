package com.gotway.gotway.controller;

import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("${api_version}/theme")
public class ThemeController {
    @Autowired
    private ThemeService themeService;
    @Value("${img_url}")
    private String img_url ;
    @PostMapping(value="/getList")
    public Object list(HttpServletRequest request) throws Exception{
        Map<String, Object> map = themeService.getList(RequestUtil.getMapByRequest(request)).getMap();
        map.put("img_url",img_url);
        return map;
    }
    @PostMapping(value="/addOrUpdate")
    public Object add(HttpServletRequest request) throws Exception{
        return themeService.addOrUpdate(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/del")
    public Object del(HttpServletRequest request) throws Exception{
        return themeService.del(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/updateState")
    public Object restPassword(HttpServletRequest request) throws Exception {
        return themeService.updateState(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/upDown")
    public Object upDown(HttpServletRequest request) throws Exception {
        return themeService.upDown(RequestUtil.getMapByRequest(request)).getMap();
    }
}
