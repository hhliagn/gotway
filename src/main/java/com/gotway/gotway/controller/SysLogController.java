package com.gotway.gotway.controller;

import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.service.SysLogService;
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
@RequestMapping("${api_version}/sysLog")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;
    @PostMapping(value="/getList")
    public Object is_repeated(HttpServletRequest request) throws Exception {
        Map<String, Object> mapByRequest = RequestUtil.getMapByRequest(request);
        return sysLogService.getList(mapByRequest).getMap();
    }

}
