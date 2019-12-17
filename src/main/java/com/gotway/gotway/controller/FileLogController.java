package com.gotway.gotway.controller;

import com.gotway.gotway.common.util.Base64ImageUtil;
import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.pojo.FileLog;
import com.gotway.gotway.service.FileLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RestController
@RequestMapping("${api_version}/file")
public class FileLogController {
    @Autowired
    private FileLogService fileLogService;
    @Value("${file_upload_dir}")
    private String file_upload_dir ;
    @Value("${img_url}")
    private String img_url ;
    private DateFormat yy = new SimpleDateFormat("yyyy");
    private DateFormat md = new SimpleDateFormat("MMdd");

    @PostMapping(value="/upload")
    public Object add(HttpServletRequest request, MultipartFile file) throws Exception{
        String filename = file.getOriginalFilename();
        String ss = filename.toLowerCase();
        Date date = new Date();
        if(ss.endsWith(".jpg") || ss.endsWith(".bmp") || ss.endsWith(".gif") || ss.endsWith("png") || ss.endsWith(".jpeg")
                ||ss.endsWith(".icon") ||ss.endsWith(".mp4") || ss.endsWith(".avi") || ss.endsWith(".wmv")
                || ss.endsWith(".flv") || ss.endsWith(".rmvb") || ss.endsWith(".rm")
                ){
            filename = UUID.randomUUID().toString().replace("-","")+filename.substring(filename.lastIndexOf("."));
        }else{
            filename = URLEncoder.encode(filename,"utf-8");
            String filename1 = filename.substring(0,filename.lastIndexOf(".")) +"_"+ date.getTime();
            filename = filename1+filename.substring(filename.lastIndexOf("."));
        }
        String temp = "/upload/"+yy.format(date)+"/"+md.format(date);
        String filePath = file_upload_dir+temp;
        File dir = new File(filePath);
        if(!dir.exists())dir.mkdirs();
        File outFile = new File(dir, filename);
        byte[] bytes = FileCopyUtils.copyToByteArray(file.getInputStream());
        FileCopyUtils.copy(bytes,outFile);

        String url = temp+"/"+filename;
        Map<String, Object> mapByRequest = RequestUtil.getMapByRequest(request);
        mapByRequest.put("picUrl",url);
        Map<String, Object> map = fileLogService.addOrUpdate(mapByRequest).getMap();
        map.put("img_url",img_url);
        return map;
    }
    @PostMapping(value="/uploadByBase64")
    public Object add(HttpServletRequest request, String imgStr) throws Exception{
        Pattern p = Pattern.compile("^data:image/(.*);");
        Matcher m = p.matcher(imgStr);
        String ext = "";
        if(m.find()){
            ext = m.group(1);
        }

        imgStr = imgStr.replaceFirst("^data:image/(.*);base64,","");

        String filename = "";
        Date date = new Date();
        filename = date.getTime() + (10000+new Random().nextInt(9999)+ ".").substring(1) +ext;

        String temp = "/upload/"+yy.format(date)+"/"+md.format(date);
        String filePath = file_upload_dir+temp;
        File dir = new File(filePath);
        if(!dir.exists())dir.mkdirs();
        File outFile = new File(dir, filename);
        Base64ImageUtil.generateImage(imgStr,outFile.toString());


        String url = temp+"/"+filename;
        Map<String, Object> mapByRequest = RequestUtil.getMapByRequest(request);
        mapByRequest.put("picUrl",url);
        Map<String, Object> map = fileLogService.addOrUpdate(mapByRequest).getMap();
        map.put("img_url",img_url);
        return map;
    }

}
