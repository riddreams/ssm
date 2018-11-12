package service;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;
/**
 * @author lwyan on 2018-06-03 10:01
 */
public interface ExcelExportService {
	void makeWorkBook(Map<String,Object> map,Workbook workbook);
}