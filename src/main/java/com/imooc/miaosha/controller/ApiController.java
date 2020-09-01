package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.ApiDemoEntity;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.ApiDemoService;
import com.imooc.miaosha.service.ApiService;
import com.imooc.miaosha.util.HttpClientUtil;
import com.imooc.miaosha.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 开放接口,提供数据
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final static Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private ApiDemoService apiDemoService;

    @Autowired
    private ApiService apiService;

    @Value("${ccmsServer.url}")
    private String url;

    @Value("${ccmsServer.port}")
    private String port;

    @Value("${ccmsServer.num}")
    private String num;

    /**
     * 通过本地获取数据
     *
     * @return
     */
    @RequestMapping("/getApi")
    public Result<List<ApiDemoEntity>> findAll() {
        logger.info("run method findAll message on local");
        List<ApiDemoEntity> list = apiDemoService.findAll();
        return Result.success(list);
    }

    //本地获取10条数据
    @RequestMapping("/getLimit")
    public Result<List<ApiDemoEntity>> findByLocalLimit() {
        logger.info("run method findByLocalLimit message on local");
        List<ApiDemoEntity> limitList = apiDemoService.findByLimit();
        return Result.success(limitList);
    }

    @RequestMapping("/getByRemote")
    public String findAllByRemote() {
        logger.info("run method findAllByRemote message on remote");
        String url = "http://47.100.173.184:9602/api/getApi";
        String data = null;
        try {
            data = HttpUtil.get(url, null, 60000, 60000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @RequestMapping("/getByRemoteLimit")
    public String findByRemoteLimit() {
        logger.info("run method findByRemoteLimit message on remote");
        String url = "http://47.100.173.184:9602/api/getLimit";
        String data = null;
        try {
            data = HttpUtil.get(url, null, 60000, 60000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @RequestMapping("/test")
    public String testHttpClientUtil() {
        logger.info("run method testHttpClientUtil message on remote");
        String url = "http://47.100.173.184:9602/api/getLimit";
        return HttpClientUtil.get(url);
    }

    @RequestMapping("/findById")
    public Result<List<ApiDemoEntity>> findById(@RequestParam("status") int status) {
        logger.info(String.format("run method findById params=%s", status));
        List<ApiDemoEntity> list = apiDemoService.findByStatus(status);
        return Result.success(list);
    }

    @RequestMapping("/findByParam")
    public Result<ApiDemoEntity> findByParam(@RequestBody com.imooc.miaosha.request.RequestParam requestParam) {
        logger.info(String.format("run method findByParam params=%s", requestParam.toString()));
        return Result.success(apiDemoService.findOne(requestParam));
    }

    @RequestMapping("/selectByPrimaryKey")
    public Result<ApiDemoEntity> selectByPrimaryKey(Long id) {
        logger.info(String.format("run method selectByPrimaryKey param=%s", id));
        ApiDemoEntity apiDemoEntity = apiDemoService.selectByPrimaryKey(id);
        return Result.success(apiDemoEntity);
    }

    @RequestMapping("/findOne")
    public Result<ApiDemoEntity> findOne(@RequestParam("demo") Long id) {
        logger.info(String.format("run method findOne param=%s",id));
        ApiDemoEntity apiDemoEntity = apiDemoService.selectByPrimaryKey(id);
        return Result.success(apiDemoEntity);
    }

    @RequestMapping("/selectApi")
    public Result<ApiDemoEntity> selectApi(ApiDemoEntity apiDemoEntity) {
        logger.info(String.format("run method selectApi param=%s", apiDemoEntity.toString()));
//        apiDemoService.
        return null;
    }

    /**
     * 测试配置参数
     * @return
     */
    @RequestMapping("/testApplication")
    public Result<List<String>> testApplication() {
        logger.info("run method testApplication");
        List<String> demoList = new ArrayList<String>();
        demoList.add(url);
        demoList.add(port);
        demoList.add(num);
        return Result.success(demoList);
    }

    /**
     * 测试配置文件
     * @return
     */
    @RequestMapping("/testMapper")
    public Result<ApiDemoEntity> testMapper() {
        logger.info("run method testMapper");
        ApiDemoEntity apiDemoEntity = apiService.selectByPrimaryKey(70L);
        return Result.success(apiDemoEntity);
    }

}
