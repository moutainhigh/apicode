package com.ycandyz.master.base.extension;/*
 * package com.sangang.video.admin.common.api.base.extension;
 * 
 * import java.io.File; import java.io.FileOutputStream; import
 * java.lang.reflect.ParameterizedType; import java.util.ArrayList; import
 * java.util.Collections; import java.util.List; import java.util.Map;
 * 
 * import org.apache.poi.ss.usermodel.Workbook; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.ui.ModelMap; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod;
 * 
 * import com.baomidou.mybatisplus.annotation.TableName; import
 * com.baomidou.mybatisplus.extension.activerecord.Model; import
 * com.baomidou.mybatisplus.extension.plugins.pagination.Page; import
 * com.sangang.video.admin.common.api.baseMapper; import
 * com.sangang.video.admin.common.api.base.BaseController; import
 * com.sangang.video.admin.common.api.base.BaseService;
 * 
 * import cn.afterturn.easypoi.entity.vo.BigExcelConstants; import
 * cn.afterturn.easypoi.excel.ExcelExportUtil; import
 * cn.afterturn.easypoi.excel.entity.ExportParams; import
 * cn.afterturn.easypoi.excel.entity.enmus.ExcelType; import
 * cn.afterturn.easypoi.handler.inter.IExcelExportServer; import
 * cn.hutool.core.date.DateUtil; import io.swagger.annotations.ApiModel; import
 * io.swagger.annotations.ApiOperation;
 * 
 *//**
	 * <p>
	 * 
	 * @Description poi基础类
	 *              </p>
	 *
	 * @author hesangang
	 * @since 2019-07-14
	 * @version 2.0
	 *//*
		 * public abstract class BaseExcelController<S extends BaseService,E extends
		 * Model, Q > extends BaseController<S,E,Q> implements IExcelExportServer {
		 * 
		 * @Autowired public BaseExcelController<S,E, Q> excelExportServer; // private
		 * static final String ROOT_DIR = "/mnt/sangang/"; private static final String
		 * EXCEL_DIR = "/download/excel/";
		 * 
		 * public List getListForExcelExport(Q query, int i) { Page page = new Page(i,
		 * baseMapper.MAX_SIZE, false); List<E> list = service.page(page,
		 * query).getRecords(); List result = new ArrayList<>(); result.addAll(list);
		 * return result; }
		 * 
		 * @ApiOperation(value = "分页导出数据")
		 * 
		 * @RequestMapping(value = "/excel/page", method = RequestMethod.GET) public
		 * String excelPage(Q query, ModelMap map) throws Exception { Class class_ =
		 * (Class) ((ParameterizedType)
		 * this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]; ApiModel
		 * annotation_1 = (ApiModel) class_.getAnnotation(ApiModel.class); TableName
		 * annotation_2 = (TableName) class_.getAnnotation(TableName.class);
		 * ExportParams params = new ExportParams(annotation_1.value(),
		 * annotation_2.value(), ExcelType.XSSF); map.put(BigExcelConstants.CLASS,
		 * class_); map.put(BigExcelConstants.PARAMS, params);
		 * map.put(BigExcelConstants.FILE_NAME, annotation_2.value());
		 * map.put(BigExcelConstants.DATA_PARAMS, query);
		 * map.put(BigExcelConstants.DATA_INTER, excelExportServer); return
		 * renderExcel(map); }
		 * 
		 * protected static String renderExcel(Map<String, Object> model) throws
		 * Exception { String codedFileName = model.get(BigExcelConstants.FILE_NAME) +
		 * "_" + DateUtil.currentSeconds() + ".xls"; Workbook workbook =
		 * ExcelExportUtil.exportBigExcel((ExportParams)
		 * model.get(BigExcelConstants.PARAMS), (Class)
		 * model.get(BigExcelConstants.CLASS), Collections.EMPTY_LIST);
		 * IExcelExportServer server = (IExcelExportServer)
		 * model.get(BigExcelConstants.DATA_INTER); int page = 1; int next = page + 1;
		 * Object query = model.get(BigExcelConstants.DATA_PARAMS); for (List list =
		 * server.selectListForExcelExport(query, page); list != null && list.size() >
		 * 0; list = server.selectListForExcelExport(query, next++)) { workbook =
		 * ExcelExportUtil.exportBigExcel((ExportParams)
		 * model.get(BigExcelConstants.PARAMS), (Class)
		 * model.get(BigExcelConstants.CLASS), list); }
		 * ExcelExportUtil.closeExportBigExcel(); String webPath = EXCEL_DIR +
		 * DateUtil.today() + "/"; String filePath = ROOT_DIR + webPath; if (!new
		 * File(filePath).exists()) { new File(filePath).mkdirs(); } FileOutputStream
		 * out = new FileOutputStream(filePath + codedFileName); workbook.write(out);
		 * out.flush(); return webPath + codedFileName; }
		 * 
		 * }
		 */