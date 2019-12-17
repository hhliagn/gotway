package com.gotway.gotway.controller;

import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.service.LanguagePackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RestController
@RequestMapping("${api_version}/languagePack")
public class LanguagePackController {
    @Autowired
    private LanguagePackService languagePackService;

    @PostMapping(value="/getList")
    public Object list(HttpServletRequest request) throws Exception{
        return languagePackService.getList(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/addOrUpdate")
    public Object add(HttpServletRequest request) throws Exception{
        return languagePackService.addOrUpdate(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/del")
    public Object del(HttpServletRequest request) throws Exception{
        return languagePackService.del(RequestUtil.getMapByRequest(request)).getMap();
    }

}
