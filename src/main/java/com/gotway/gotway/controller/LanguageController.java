package com.gotway.gotway.controller;


import com.gotway.gotway.common.util.ExcelUtil;
import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RestController
@RequestMapping("${api_version}/language")
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    @PostMapping(value="/getList")
    public Object list(HttpServletRequest request) throws Exception{
        return languageService.getList(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/del")
    public Object del(HttpServletRequest request) throws Exception{
        return languageService.del(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/addOrUpdate")
    public Object add(HttpServletRequest request , MultipartFile file) throws Exception{
        //file的处理
        Map<String, String> data=null;
        if(file!=null){
            data = ExcelUtil.importExcel(file.getInputStream(), file.getOriginalFilename());
        }
        Map<String, Object> mapByRequest = RequestUtil.getMapByRequest(request);
        mapByRequest.put("data",data);
        if(StringUtils.isEmpty(mapByRequest.get("id"))){
            if(data!=null)mapByRequest.put("tag","2");//状态[1:未启用2:启用]
            else mapByRequest.put("tag","1");
        }else mapByRequest.put("tag","2");

        return languageService.addOrUpdate(mapByRequest).getMap();
    }
    @PostMapping(value="/get")
    public Object getItem(HttpServletRequest request) throws Exception{
        return languageService.getItem(RequestUtil.getMapByRequest(request)).getMap();
    }
}
