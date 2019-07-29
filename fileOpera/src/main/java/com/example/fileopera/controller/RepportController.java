package com.example.fileopera.controller;



import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "Report")
@RestController
@RequestMapping("/api/report")
public class RepportController {

    @Resource
    private DataSource dataSource;

    @Autowired
    ApplicationContext appContext;



    @GetMapping("/{reportName}")
    public void getReportByParam(@PathVariable("reportName") String reportName,
                                 @RequestParam(required = false) Map<String, Object> parameters,
                                 HttpServletResponse response) throws IOException, SQLException, JRException {

        parameters = parameters == null ? new HashMap<>() : parameters;
        ClassPathResource resource = new ClassPathResource("jasper/" + reportName + ".jasper");
        InputStream jasperStream = resource.getInputStream();

        JasperReport report = (JasperReport) JRLoader.loadObject(jasperStream);
        parameters.put("var1", "人们");
        parameters.put("var2","eeee");

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource.getConnection());        // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JREmptyDataSource());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;");
        final OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

    }
//    @GetMapping
//    @JpaPage
//    public void downLoad( HttpServletResponse response) throws JRException, IOException, SQLException {
//        Page all = pdfBeanRepository.findAll(JpaUtils.getSpecification(), JpaUtils.getPageRequest());
//        ExportUtil.exportPdf(all.getContent(),"report",response);
//
//    }
}
