package com.example.common.file;

import com.example.common.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


@Service
public class FileService {
    @Autowired
    FileApiProperties fileApiProperties;

    @Autowired
    RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger (FileService.class);

    public FileDto uploadFile(String customfolder, String target,String fileName,String fileId, InputStream inputStream){
        Map<String, Object> map = new HashMap<>();
        if(!StringUtils.isEmpty(customfolder)){
            map.put("customfolder", customfolder);
        }
        if(!StringUtils.isEmpty(target)){
            map.put("target",target);
        }
        if(!StringUtils.isEmpty(fileId)){
            map.put("id",fileId);
        }

        FileInputStreamResource fileInputStreamResource = new FileInputStreamResource(inputStream,fileName);
        MultiValueMap<String, Object> dataMap = new LinkedMultiValueMap<>();
        dataMap.add("file",fileInputStreamResource);

        HttpEntity< MultiValueMap<String, Object>> httpEntity = new HttpEntity(dataMap,new HttpHeaders());
        String url = HttpUtils.getUrl(fileApiProperties.getUpload(),map);
        FileDto responseObject = restTemplate.exchange(url,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<FileDto>() {
                }).getBody();
        LOGGER.error("uploadFile:{}",responseObject.toString());
        return responseObject;
    }

    public Object deleteFile(String fileId){
        Map<String, Object> map = new HashMap<String, Object>();
        String url = fileApiProperties.getDelete()+"/{id}";
        map.put("id", fileId);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url,HttpMethod.DELETE,null, String.class,map);
        return responseEntity;
    }


}
