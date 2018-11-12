package service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import service.ExcelExportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author lwyan on 2018-06-03 10:17
 */
public class ExcelView extends AbstractXlsView{
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ExcelExportService getExcelExportService() {
		return excelExportService;
	}

	public void setExcelExportService(ExcelExportService excelExportService) {
		this.excelExportService = excelExportService;
	}

	// 文件名
	private String fileName = null;
	// 导出视图自定义接口
	private ExcelExportService excelExportService = null;
	public ExcelView(ExcelExportService excelExportService){
		this.excelExportService = excelExportService;
	}
	public ExcelView(String viewName,ExcelExportService excelExportService){
		this.setBeanName(viewName);
	}
	@Override
	protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(excelExportService == null){
			throw new RuntimeException("导出服务接口不能为空");
		}
		if(!StringUtils.isEmpty(fileName)){
			String reqCharset = request.getCharacterEncoding();
			reqCharset = reqCharset == null ? "UTF-8" : reqCharset;
			fileName = new String(fileName.getBytes(reqCharset),"ISO-8859-1");
			response.setHeader("Content-disposition","attachment;filename="+fileName);
			excelExportService.makeWorkBook(map,workbook);
		}
	}
}