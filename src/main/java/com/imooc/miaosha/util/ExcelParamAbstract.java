package com.imooc.miaosha.util;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

/**
 * ClassName: ExcelParamAbstract
 * Function:
 * Date:      2020/5/31 22:04
 * @author     likaixuan
 * version    V1.0
 */
public abstract class ExcelParamAbstract implements Serializable {

    /**
     * 	文件地址,本地读取时用
     */
    protected String filePath;

    /**
     * 输出流
     */
    protected HttpServletResponse response;

    /**
     * 文件名
     */
    protected String fileName;

    /**
     * 表头
     */
    protected Boolean fileNameAsHeadName;
    /**
     * 文件输出路径
     */
    protected String outFilePath;

    /**
     * list params
     */
    protected List<ExcelParam> list;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getFileNameAsHeadName() {
        return fileNameAsHeadName;
    }

    public void setFileNameAsHeadName(Boolean fileNameAsHeadName) {
        this.fileNameAsHeadName = fileNameAsHeadName;
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }

    public List<ExcelParam> getList() {
        return list;
    }

    public void setList(List<ExcelParam> list) {
        this.list = list;
    }
}
