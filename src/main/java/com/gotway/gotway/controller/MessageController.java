package com.gotway.gotway.controller;

import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.service.MessageService;
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
@RequestMapping("${api_version}/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping(value="/getList")
    public Object list(HttpServletRequest request) throws Exception{
        return messageService._getList(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/addOrUpdate")
    public Object add(HttpServletRequest request) throws Exception{
        return messageService.addOrUpdate(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/del")
    public Object del(HttpServletRequest request) throws Exception{
        return messageService.del(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/updateState")
    public Object updateState(HttpServletRequest request) throws Exception{
        return messageService.updateState(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/unreadNum")
    public Object unreadNum(HttpServletRequest request) throws Exception{
        return messageService.unreadNum(RequestUtil.getMapByRequest(request)).getMap();
    }

}
