package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.ApiDemoEntity;
import com.imooc.miaosha.model.GoodsModel;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.ApiDemoService;
import com.imooc.miaosha.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/excel")
public class ExcelController {

    private final static Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private ApiDemoService apiDemoService;

    /**
     * 下载模板
     *
     * @param response
     * @return
     */
    @RequestMapping("/downloadTemplate")
    public void export(HttpServletResponse response) {
        logger.info("run method export");
        List<GoodsModel> list = new ArrayList<GoodsModel>();
        try {
            ExcelUtil.exportExcelOutputStream(response, list, GoodsModel.class, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入数据
     */
    @RequestMapping("/import")
    @ResponseBody
    public Result<List<GoodsModel>> importTemplate(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        logger.info("run method importTemplate");
        List<GoodsModel> goodsModels = null;
        try {
            goodsModels = ExcelUtil.readXls(file.getBytes(), GoodsModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //插入数据,批量插入,在mapper中进行操作

        //重新查询数据,放入模型数据中,刷新页面

        return Result.success(goodsModels);
    }

    /**
     * 导出数据
     *
     * @return
     */
    @RequestMapping("/export")
    @ResponseBody
    public void exportTemplate(HttpServletResponse response) {
        logger.info("run method exportTemplate");
        List<ApiDemoEntity> list = apiDemoService.findAll();
        Object objList = (Object) list;
        List<GoodsModel> paramList = (List<GoodsModel>) objList;
        try {
            ExcelUtil.exportExcelOutputStream(response,paramList,GoodsModel.class,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
