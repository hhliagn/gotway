package com.gotway.gotway.controller;

import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.service.CommentsLogService;
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
@RequestMapping("${api_version}/commentsLog")
public class CommentsLogController {
    @Autowired
    private CommentsLogService commentsLogService;
    @Value("${img_url}")
    private String img_url ;
    @PostMapping(value="/getList")
    public Object list(HttpServletRequest request) throws Exception{
        return commentsLogService.getList(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/addOrUpdate")
    public Object add(HttpServletRequest request) throws Exception{
        return commentsLogService.addOrUpdate(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/del")
    public Object del(HttpServletRequest request) throws Exception{
        return commentsLogService.del(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/get")
    public Object getItem(HttpServletRequest request) throws Exception{
        Map<String, Object> map = commentsLogService.getItem(RequestUtil.getMapByRequest(request)).getMap();
        map.put("img_url",img_url);
        return map;
    }
    @PostMapping(value="/updateState")
    public Object restPassword(HttpServletRequest request) throws Exception {
        return commentsLogService.updateState(RequestUtil.getMapByRequest(request)).getMap();
    }

}
