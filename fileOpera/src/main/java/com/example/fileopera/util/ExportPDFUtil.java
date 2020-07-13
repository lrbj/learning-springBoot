package com.example.fileopera.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportPDFUtil {


    /**
     * 导出PDF
     *
     * @param reportSrc 模板地址
     * @param fbList    模板填充数据
     * @param
     * @param response
     */
    public static void exportPDF(String reportSrc, List<?> fbList, HttpServletResponse response) {
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(fbList);
        File reportFile = null;
        Map<String, Object> oneMap = new HashMap<String, Object>();
        oneMap.put("datasource", JRdataSource);
        oneMap.put("SUBREPORT_DIR", "classpath:/ca.jasper");
        reportFile = new File("classpath:/ca.jasper");
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(reportFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(fis, oneMap, JRdataSource);
            byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setHeader("Content-Disposition", "attachment;filename=zzz.pdf");
            os = response.getOutputStream();
            os.write(bytes);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void prepareReport(JasperReport jasperReport, String type) {
        /*
         * 如果导出的是excel，则需要去掉周围的margin
         */
        if ("excel".equals(type))
            try {
                Field margin = JRBaseReport.class.getDeclaredField("leftMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                margin = JRBaseReport.class.getDeclaredField("topMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                margin = JRBaseReport.class.getDeclaredField("bottomMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                Field pageHeight = JRBaseReport.class.getDeclaredField("pageHeight");
                pageHeight.setAccessible(true);
                pageHeight.setInt(jasperReport, 2147483647);
            } catch (Exception exception) {
            }
    }

    /**
     * 导出pdf，注意此处中文问题，
     * <p>
     * 这里应该详细说：主要在ireport里变下就行了。看图
     * <p>
     * 1）在ireport的classpath中加入iTextAsian.jar 2）在ireport画jrxml时，看ireport最左边有个属性栏。
     * <p>
     * 下边的设置就在点字段的属性后出现。 pdf font name ：STSong-Light ，pdf encoding ：UniGB-UCS2-H
     */
    public static void exportPdf(Collection datas, String defaultFilename, HttpServletResponse response) throws IOException, JRException {

        URL url = ClassPathResource.class.getClassLoader().getResource("jasper/" + defaultFilename + ".jasper");
        File file = new File(url.getFile());
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //加载jasper
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
        prepareReport(jasperReport, "pdf");
        JRDataSource ds = new JRBeanCollectionDataSource(datas, false);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SUBREPORT_DIR", "1212");
        parameters.put("IMAGEPATH_DIR", "1212");
        // 将数据和xml组合，生成需要的打印文件
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        response.setContentType("application/pdf");
        String defaultname = null;
        if (defaultFilename.trim() != null && defaultFilename != null) {
            defaultname = defaultFilename + ".pdf";
        } else {
            defaultname = "export.pdf";
        }
        String fileName = new String(defaultname.getBytes("GBK"), "ISO8859_1");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }


}
