/**
 * Project Name:excelutil
 * File Name:ExcelUtil.java
 * Package Name:com.lkx.util
 * Date:2019年7月12日 23:18:00
 * Copyright (c) 2017~2020, 934268568@qq.com All Rights Reserved.
 *
 */
package com.imooc.miaosha.util;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ExcelUtil Function: excel快速读取、写入公共类
 * Date: 2019年7月12日 23:17:45
 * @author likaixuan
 * @version V1.0 898
 * @since JDK 1.8
 */
public class ExcelUtil extends ExcelUtilBase implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 本地读取
	 */
	public static <T> List<T> readXls(String filePath, Map map, Class clazz) throws Exception {
		ExcelParam excelParam = new ExcelParam();
		excelParam.setFilePath(filePath);
		excelParam.setMap(map);
		excelParam.setClazz(clazz);
		return getResult(excelParam);
	}

	/**
	 * 本地通过注解匹配属性和表头
	 */
	public static <T> List<T> readXls(String filePath, Class clazz) throws Exception {

		ExcelParam excelParam = new ExcelParam();
		excelParam.setFilePath(filePath);
		excelParam.setClazz(clazz);
		return getResult(excelParam);
	}


	/**
	 * 本地通过注解匹配属性和表头
	 */
	public static <T> List<T> readXls(String filePath, Class clazz, int... rowNumIndex) throws Exception {

		ExcelParam excelParam = new ExcelParam();
		excelParam.setFilePath(filePath);
		excelParam.setClazz(clazz);
		if(rowNumIndex.length>0) {
			excelParam.setRowNumIndex(rowNumIndex[0]);
		}
		return getResult(excelParam);
	}

	/**
	 * 使用流读取Excel
	 *
	 * @param 字节流
	 * @param map
	 * @param clazz
	 * @param rowNumIndex
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> readXls(byte[] buf, Map map, Class<T> clazz, int... rowNumIndex) throws Exception {

		ExcelParam excelParam = new ExcelParam();
		excelParam.setMap(map);
		excelParam.setClazz(clazz);
		excelParam.setBuf(buf);
		if(rowNumIndex.length>0) {
			excelParam.setRowNumIndex(rowNumIndex[0]);
		}
		return getResult(excelParam);
	}

	public static <T> List<T> readXls(byte[] buf, Class<T> clazz, int... rowNumIndex) throws Exception {
		ExcelParam excelParam = new ExcelParam();
		excelParam.setClazz(clazz);
		excelParam.setStream(true);
		excelParam.setBuf(buf);
		if(rowNumIndex.length>0) {
			excelParam.setRowNumIndex(rowNumIndex[0]);
		}
		return getResult(excelParam);
	}

	/**
	 * 导出Excel到指定磁盘位置
	 */
	public static void exportExcel(String outFilePath, String keyValue, List<?> list, Class clazz)
			throws Exception {
		ExcelParam excelParam = new ExcelParam(clazz,keyValue,outFilePath,list);
		commonExportExcel(excelParam);
	}

	/**
	 * 导出Excel到指定磁盘位置
	 */
	public static void exportExcel(String outFilePath, List<?> list, Class clazz)
			throws Exception {
		ExcelParam excelParam = new ExcelParam(clazz,outFilePath,list);
		commonExportExcel(excelParam);
	}

	/**
	 * 导出Excel到指定磁盘位置
	 */
	public static void exportExcel(String outFilePath, List<?> list, Class clazz,String headerName,String waterMark)
			throws Exception {
		ExcelParam excelParam = new ExcelParam(clazz,outFilePath,list,headerName);
		excelParam.setWaterMark(waterMark);
		commonExportExcel(excelParam);
	}


	/**
	 * 导出Excel到浏览器中
	 * @param response
	 * @param keyValue
	 * @param list
	 * @param clazz
	 * @throws Exception
	 */
	public static void exportExcelOutputStream(HttpServletResponse response, String keyValue, List<?> list,
                                               Class clazz) throws Exception {
		ExcelParam excelParam = new ExcelParam(clazz,keyValue,response,list);
		commonExportExcel(excelParam);
	}

	public static void exportExcelOutputStream(HttpServletResponse response, String keyValue, List<?> list,
                                               Class clazz, String fileName) throws Exception {
		ExcelParam excelParam = new ExcelParam(clazz,keyValue,response,fileName,list);
		commonExportExcel(excelParam);
	}
	public static void exportExcelOutputStream(HttpServletResponse response, String keyValue, List<?> list,
                                               Class clazz, String fileName, Boolean fileNameAsHeaderName) throws Exception {
		ExcelParam excelParam = new ExcelParam(clazz,keyValue,response,fileName,fileNameAsHeaderName,list);
		commonExportExcel(excelParam);
	}

	public static void exportExcelOutputStream(HttpServletResponse response, List<?> list,
                                               Class clazz) throws Exception {
		ExcelParam excelParam = new ExcelParam(clazz,response,list);
		commonExportExcel(excelParam);
	}


	public static void exportExcelOutputStream(HttpServletResponse response, List<?> list,
                                               Class clazz, String fileName) throws Exception {
		ExcelParam excelParam = new ExcelParam(clazz,response,fileName,list);
		commonExportExcel(excelParam);
	}

	public static void exportExcelOutputStream(HttpServletResponse response, List<?> list,
                                               Class clazz, String fileName, String waterMark) throws Exception {
		ExcelParam excelParam = new ExcelParam(clazz,response,fileName,list);
		excelParam.setWaterMark(waterMark);
		commonExportExcel(excelParam);
	}

	public static void exportExcelOutputStream(ExcelParamAbstract excelParamAbstract) throws Exception {
		commonExportExcel2(excelParamAbstract);
	}

	public static void exportExcelOutputStream(HttpServletResponse response, List<?> list,
                                               Class clazz, String fileName, Boolean fileNameAsHeaderName) throws Exception {
		ExcelParam excelParam = new ExcelParam(clazz,response,fileName,fileNameAsHeaderName,list);
		commonExportExcel(excelParam);
	}


	public static void templateWrite(HttpServletResponse response, String templatePath, String outFilePath, Object obj) {
		ExcelParam excelParam = new ExcelParam(response,templatePath,outFilePath,obj);
		templateWrite(excelParam);
	}

	public static void templateWrite(HttpServletResponse response, String templatePath, Object obj, String fileName) {
		ExcelParam excelParam = new ExcelParam(response,templatePath,obj,fileName);
		templateWrite(excelParam);
	}


}


