package com.example.common.mq.notify;

import com.example.common.integrationService.rabbitmq.RabbitmqProps;
import com.example.common.mq.util.MqUtil;
import com.example.common.mq.vo.AttachFile;
import com.example.common.mq.vo.MailVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class NotiftyTrigger {
    private static Logger logger = LoggerFactory.getLogger(NotiftyTrigger.class);

    public static final String POLLING_NOTIFY = "消息提醒";
    public static final String  LINK = "http://test.com";



    @Autowired
    MessageProducer messageProducer;

    @Autowired
    RabbitmqProps rabbitmqProps;


    /**
     *
     * @param message
     * @param toAddress
     * @param ccAddress
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public void sendMail(String message, String[] toAddress, String[] ccAddress, List<AttachFile> files) throws IOException, InterruptedException {
        MailVo mailVo =  new MailVo();
        mailVo.setSubject(POLLING_NOTIFY);
        mailVo.setContent(message);

        if(toAddress != null){
            mailVo.setToAddress(Arrays.asList(toAddress));
        }
        if(ccAddress != null){
            mailVo.setCcAddress(Arrays.asList(ccAddress));
        }
        mailVo.setFiles(files);
        messageProducer.sendMessage( rabbitmqProps.getMailExchange(), rabbitmqProps.getMailRouteKey(),MqUtil.createMailBody(mailVo));
    }



    public String createMessage(String link,String body){
        StringBuilder builder = new StringBuilder("<html lang='zh-CN'><head ><meta charset='utf-8'>")
                .append("</head><body>")
                .append(body);
        if(!StringUtils.isEmpty(link)){
                builder.append("<a href='")
                .append(link)
                .append("'>【点检系统】</a>");
        }
        builder.append("</body></html>");
        return builder.toString();
    }


    public boolean sendMailDemo() throws IOException, InterruptedException {
        String[] email = {"Shuzhen.Ye@example.com","shunbo.li@example.com"};
        String meassage = new StringBuilder("您申请的单子，状态变更为:")
                .append("test")
                .append("; 详情查看链接:").toString();
        List<AttachFile> attachFiles = new ArrayList<>();
        AttachFile  file = new AttachFile();
        File dd = new File("./test.txt");
        if(!dd.exists()){
            dd.createNewFile();
        }
        byte[] data = null;
        try {
            try (InputStream in = new FileInputStream(dd)) {
                data = new byte[in.available()];
                in.read(data);
                in.close();
            }
            dd.delete();
        }catch (Exception e) {
            e.printStackTrace();
        }

        file.setPayload(data);
        file.setFilename("test.txt");
        attachFiles.add(file);
        sendMail(createMessage(LINK,meassage), email,null, attachFiles);
        return true;
    }

}
