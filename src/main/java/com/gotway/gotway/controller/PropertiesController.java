package com.gotway.gotway.controller;

import com.gotway.gotway.aop.PointAndTopProduceAspect;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.common.util.SysParam;
import com.gotway.gotway.pojo.Properties;
import com.gotway.gotway.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RestController
@RequestMapping("${api_version}/properties")
public class PropertiesController extends Observable {
    @Autowired
    private PropertiesService propertiesService;
    @Value("${img_url}")
    private String img_url ;
    @Autowired
    private PointAndTopProduceAspect  pta;

    //初始化系统参数
    @PostConstruct
    private void initSysParam(){
        super.addObserver(pta);
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("num",1);
            map.put("size",10000000);
            List<Properties> list = (List<Properties>)propertiesService.getList(map).getData();
            if(list!=null && list.size()>0)for (Properties p:list) {
               SysParam.set(p.getKey(),p.getValue());
            }
            this.setChanged();//状态有改变
            this.notifyObservers();//通知订阅者更新
            propertiesService.set_GLOBAL_group_concat_max_len();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PostMapping(value="/getList")
    public Object list(HttpServletRequest request) throws Exception{
        Map<String, Object> map = propertiesService.getList(RequestUtil.getMapByRequest(request)).getMap();
        map.put("img_url",img_url);
        return map;
    }
    @PostMapping(value="/addOrUpdate")
    public Object add(HttpServletRequest request) throws Exception{
        return propertiesService.addOrUpdate(RequestUtil.getMapByRequest(request)).getMap();
    }
    @GetMapping(value="/reload")
    public Object reload(HttpServletRequest request) throws Exception{
        this.initSysParam();
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"reload success").getMap();
    }
    @PostMapping(value="/updateForXL")
    public Object updateForXL(HttpServletRequest request) throws Exception{
        return propertiesService.updateForXL(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/messageTypes")
    public Object messageTypes(HttpServletRequest request) throws Exception{
        return propertiesService.messageTypes(RequestUtil.getMapByRequest(request)).getMap();
    }
    @PostMapping(value="/feedbackTypes")
    public Object feedbackTypes(HttpServletRequest request) throws Exception{
        return propertiesService.feedbackTypes(RequestUtil.getMapByRequest(request)).getMap();
    }
}
