package com.gotway.gotway.controller;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.TokenService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.security.auth.Subject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RestController
@RequestMapping("${api_version}/field")
public class FieldController {
    @Autowired
    private FieldService fieldService;
    @Autowired
    private TokenService tokenService;
    @Value("${img_url}")
    private String img_url ;
    @PostMapping(value="/is_repeated")
    public Object is_repeated(HttpServletRequest request) throws Exception {
        Map<String, Object> mapByRequest = RequestUtil.getMapByRequest(request);
        return fieldService.is_repeated(mapByRequest).getMap();
    }
    @RequestMapping(value="/imgUrl")
    public Object imgUrl(HttpServletRequest request) throws Exception {
        return new ReturnModel(ReturnModel.CODE_SUCCESS,   "success",img_url,null).getMap();
    }
    /** ***************************************************************
     *
     * @method: code
     * @Description: 生成验证码
     * @author zxh   @date 2017年11月22日 上午12:40:51
     * @param
     * @return void    返回类型
     * @throws
     */

    @GetMapping(value="/verifyCode")
    public void code(HttpServletRequest request,HttpServletResponse response)  {
        String key = UUID.randomUUID().toString();
        key = key.replace("-", "");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String code = drawImg(output);

        tokenService.set("vc:"+key,code,60);//60秒有效

        try {
            response.setHeader("key",key);
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                output.close();
            } catch (IOException e) {
            }
        }
    }

    private String drawImg(ByteArrayOutputStream output){
        String code = "";
        for(int i=0; i<4; i++){
            code += randomChar();
        }
        int width = 70;
        int height = 25;
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman",Font.PLAIN,20);
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66,2,82);
        g.setColor(color);
        g.setBackground(new Color(226,226,240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int)x, (int)baseY);
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    private char randomChar(){
        Random r = new Random();
        String s = "ABCDEFGHJKLMNPRSTUVWXYZ123456789";
        return s.charAt(r.nextInt(s.length()));
    }

    /** ************************生成验证码代码over************************************** */

}
