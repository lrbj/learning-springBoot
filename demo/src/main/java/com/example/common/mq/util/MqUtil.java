package com.example.common.mq.util;




import com.google.protobuf.ByteString;
import com.example.common.mq.jpush.JPushMessage;
import com.example.common.mq.mail.Message;
import com.example.common.mq.vo.AttachFile;
import com.example.common.mq.vo.JPushVo;
import com.example.common.mq.vo.MailVo;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;

public class MqUtil {

    private static Logger logger = LoggerFactory.getLogger(MqUtil.class);

    public static byte[] createMailBody(MailVo mailEntity){
       // logger.info("createMailBody: mailEntity{}",mailEntity);
         Message.SendTemplateMailInfo.Builder msgBuilder = Message.SendTemplateMailInfo.newBuilder();
         if(mailEntity.getSubject() != null){
             msgBuilder.setSubject(mailEntity.getSubject());}
          if(mailEntity.getContent() != null){
              msgBuilder.setContent(mailEntity.getContent());
          }
          if(mailEntity.getCcAddress() != null){
              msgBuilder.addAllCcAddress(mailEntity.getCcAddress());
          }
          if(mailEntity.getToAddress() != null){
              msgBuilder.addAllToAddress(mailEntity.getToAddress());
          }
          if(mailEntity.getFiles() != null){
              List<AttachFile> files = mailEntity.getFiles();
              for( AttachFile file: files){
                  Message.AttachFile.Builder attachFile  =  Message.AttachFile.newBuilder();
                  attachFile.setFilename(file.getFilename());
                  attachFile.setPayload(ByteString.copyFrom(file.getPayload()));
                  msgBuilder.addFiles(attachFile.build());
              }
          }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         try {
             msgBuilder.build().writeTo(outputStream);

         } catch (Exception e) {
             System.out.println(e);
         }
         return  outputStream.toByteArray();

     }


    public static Object createJpushBody(JPushVo jPushVo){
       // logger.info("createJpushBody: jPushVo{}",jPushVo);
        JPushMessage.Message.Builder msgBuilder = JPushMessage.Message.newBuilder();
        if(jPushVo.getAppkey() != null){
            msgBuilder.setAppkey(jPushVo.getAppkey());
        }
        if(jPushVo.getSecret() != null){
            msgBuilder.setSecret(jPushVo.getSecret());
        }

        if(jPushVo.getTitle() != null){
            msgBuilder.setTitle(jPushVo.getTitle());
        }
        if(jPushVo.getAlias() != null){
            String[] eids = jPushVo.getAlias();
             for(int i = 0; i < eids.length;i++){
             msgBuilder.addAlias(eids[i]);
            }
        }
        if(jPushVo.getExtra() != null){
            msgBuilder.setExtra(jPushVo.getExtra());
        }

        if(jPushVo.getAlert() != null){
            msgBuilder.setAlert(jPushVo.getAlert());
        }

        JSONObject natsMessage = new JSONObject();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            msgBuilder.build().writeTo(outputStream);
            // dump base64 encoded
            natsMessage = createMessage(outputStream.toByteArray());
        } catch (Exception e) {
            System.out.println(e);
        }

        logger.info("createJpushBody: natsMessage {}", natsMessage);
        return  natsMessage;

    }


    private static JSONObject createMessage(byte[] data){
        String body = Base64.getEncoder().encodeToString(data);
         JSONObject natsMessage = new JSONObject();
         try {
             natsMessage.put("Header", new JSONObject().put("Content-Type", "application/octet-stream"));
             natsMessage.put("Body", body);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         return  natsMessage;

     }


}
