package com.gotway.gotway;


import com.gotway.gotway.common.pojo.JsonObjectMapper;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.LogHelper;
import com.gotway.gotway.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@Configuration //申明这是一个配置
@PropertySource(value= {"classpath:/static/config.properties"})
public class MySrpingMVCConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private TokenService tokenService;
    // 自定义拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
                String method = request.getMethod();
                String url = request.getRequestURI();
                //URL:login/login是可以公开访问的，其它的URL都进行拦截控制
                if(url.contains("/logout")
                        || url.contains("/user/checkToken")
                        || url.contains("/field/imgUrl")
                        || url.contains("/field/is_repeated")
                        || url.contains("/user/register")
                        || url.contains("/user/updateLocation")// 更新经纬度
                        || url.contains("/login")
                        || url.contains("/file/upload")
                        || url.contains("/getVerifyCode")
                        || url.contains("/resetPassword")
                        || url.contains("/field/verifyCode")
                        || url.contains("/uploadByBase64")
                        || url.contains("/static/")
                        || url.contains("/commentsLog/getList") //评论列表
                        || url.contains("/stick/getList") //帖子列表
                        || url.contains("/language/get") //语言数据
                        || url.contains("/language/getList") //语言列表
                        || url.contains("/theme/getList") //主题列表
                        || url.contains("/properties/getList") //系统参数列表
                        || url.contains("/drivingRecord/addOrUpdate") //行驶记录
                        || url.contains(".xls")
                        || url.contains(".xlsx")
                        || url.contains("/app")
                        || url.contains(".html")
                        || "OPTIONS".equals(method)){
                    String token = request.getHeader("token");
                    if(!StringUtils.isEmpty(token))request.setAttribute("token",token);
                    LogHelper.info("url:"+url+";token:"+token);
                    return true;
                }
                //获取Session
                String token = request.getHeader("token");

                HashMap<String, Object> map = new HashMap<>();
                map.put("token",StringUtils.isEmpty(token)?"-":token);
                ReturnModel returnModel = tokenService.checkToken(map);
                if(ReturnModel.CODE_SUCCESS.equals(returnModel.getCode())) {
                    tokenService.refreshToken(map);
                    request.setAttribute("token",token);
                    return true;
                }
                //System.out.println("登陆过期");
                //不符合条件的，响应502
                response.setCharacterEncoding("UTF-8");
                if(StringUtils.isEmpty(request.getHeader("Origin"))) {
                    response.setHeader("Access-Control-Allow-Origin", "*");
                }else {
                    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
                }

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");

                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                response.setHeader("Access-Control-Max-Age", "3600");
                //  response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
                response.setHeader("Access-Control-Allow-Credentials","true");
                response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
                response.setHeader("XDomainRequestAllowed","1");
                PrintWriter out = null;
                try {
                    String result="{"
                            + "\"code\":\"502\","
                            + "\"info\":\"expired session，Please login again\""
                            + "}";
                    out = response.getWriter();
                    out.write(result);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }

                return false;
//                return true;
            }
            
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                    ModelAndView modelAndView) throws Exception {
                
            }
            
            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                    Exception ex) throws Exception {
            }
        };
        registry.addInterceptor(handlerInterceptor).addPathPatterns("/**");
    }

    // 自定义消息转化器的第二种方法
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter  = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new JsonObjectMapper());
        converters.add(converter);
    }

}
