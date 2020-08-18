package com.example.ssldemo.server;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 3:14 PM 8/17/2020
 */
@Service
public class SSLServer {
    @Value("${server.ssl.key-store}")
    private   String keystorePath;

    @Value("${server.ssl.key-store-password}")
    private  String keystorePassword;

    @Value("${server.ssl.key-store-type}")
    private  String keystoreType;

    @Value("${cert.holder}")
    private String certHolder;
    @Value("${ssl.token.publickey.path}")
    private String publickeyPath;

    private String privateKeyPath = "D:/token/token-key.pem";

    public static final String JWT_SECRET = "onyx";
    public static final int JWT_TTL = 60 * 60 * 1000;  //millisecond
    public static  final String CURRENT_SER = "demo";
    private static  final String SUFFIX_PFX = ".pfx";
    private static final String SUFFIX_PEM = ".pem";

    /**提取pfx文件中的公钥和私钥*/
    private KeyStore getKeyStore(){
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance(keystoreType);
            FileInputStream fis = null;
            fis = new FileInputStream(keystorePath);

            // If the keystore password is empty(""), then we have to set
            // to null, otherwise it won't work!!!
            char[] nPassword = null;
            if (StringUtils.isEmpty(keystorePassword))
            {
                nPassword = null;
            }
            else
            {   //把密码字符串转为字符数组
                nPassword = keystorePassword.toCharArray();
            }
            //将.pfx证书信息加载密钥库

            ks.load(fis, nPassword);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return ks;

    }

    private String getAlias(KeyStore ks){
        Enumeration enum1 = null;
        try {
            enum1 = ks.aliases();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        String keyAlias = null;
        if (enum1.hasMoreElements())
        {   //获取证书别名
            keyAlias = (String)enum1.nextElement();
            System.out.println("alias=[" + keyAlias + "]");
        }
        return keyAlias;
    }

    public PublicKey getPublicKeyFromPfx(){
        KeyStore ks = getKeyStore();
        if(null == ks ){
            return null;
        }
        String keyAlias = getAlias(ks);
        Certificate cert = null;
        try {
            cert = ks.getCertificate(keyAlias);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return cert.getPublicKey();
    }

    public  PrivateKey getPrivateKeyFromPfx(){
        KeyStore ks = getKeyStore();
        if(null == ks ){
            return null;
        }
        String keyAlias = getAlias(ks);
        Certificate cert = null;
        PrivateKey prikey = null;
        try {
            cert = ks.getCertificate(keyAlias);
            prikey = (PrivateKey) ks.getKey(keyAlias, keystorePassword.toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return  prikey;
    }

    public   void getKeyFormPfx()  {
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance(keystoreType);
            FileInputStream fis = null;
            fis = new FileInputStream(keystorePath);

            // If the keystore password is empty(""), then we have to set
            // to null, otherwise it won't work!!!
            char[] nPassword = null;
            if (StringUtils.isEmpty(keystorePassword))
            {
                nPassword = null;
            }
            else
            {   //把密码字符串转为字符数组
                nPassword = keystorePassword.toCharArray();
            }
            //将.pfx证书信息加载密钥库

            ks.load(fis, nPassword);
            fis.close();

            //证书类型
            System.out.println("keystore type=" + ks.getType());


            Enumeration enum1 = ks.aliases();
            String keyAlias = null;
            if (enum1.hasMoreElements())
            {   //获取证书别名
                keyAlias = (String)enum1.nextElement();
                System.out.println("alias=[" + keyAlias + "]");
            }


            System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
            PrivateKey    prikey = null;

            prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
            Certificate cert = ks.getCertificate(keyAlias);
            PublicKey  pubkey = cert.getPublicKey();


            System.out.println("cert class = " + cert.getClass().getName());
            System.out.println("cert(str) = " + cert.toString());
//            System.out.println("cert(json) = " + JSON.parse(cert.toString()));
            System.out.println("public key = " + pubkey.getEncoded());
            System.out.println("private key = " + prikey.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
    }

    public  PrivateKey getPrivateKeyFromPem() {
        BufferedReader br = null;
        PrivateKey privateKey = null;
        try {
            br = new BufferedReader(new FileReader(privateKeyPath));

            String s = br.readLine();
            StringBuilder str = new StringBuilder();
            s = br.readLine();
            while (s.charAt(0) != '-') {
                str.append(s);
                s = br.readLine();
            }
            String pkcs8Str = formatPkcs1ToPkcs8(str.toString());
            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] decoded = base64decoder.decodeBuffer(pkcs8Str);
            // 生成私匙
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            privateKey = kf.generatePrivate(keySpec);
        }catch (IOException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public  PublicKey getPublicKeyFromPem() {
        StringBuilder str = new StringBuilder();
        PublicKey pubKey = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(publickeyPath));
            String s = br.readLine();
            s = br.readLine();
            while (s.charAt(0) != '-') {
                str.append(s);
                s = br.readLine();
            }
            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] decoded = base64decoder.decodeBuffer(str.toString());
            X509Certificate certificate = (X509Certificate) CertificateFactory.getInstance("X.509")
                    .generateCertificate(new ByteArrayInputStream(decoded));
            pubKey = certificate.getPublicKey();
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (CertificateException e) {
            e.printStackTrace();
        }

        return pubKey;
    }

    /**
     *     Pkcs1toPkcs8
     */
    public  String formatPkcs1ToPkcs8(String rawKey) throws Exception {
        String result = null;
        //extract valid key content
        String validKey = rawKey;//RsaPemUtil.extractFromPem(rawKey); // pem文件多行合并为一行
        if (!StringUtils.isEmpty(validKey))
        {
            //将BASE64编码的私钥字符串进行解码
            byte[] encodeByte = Base64.decodeBase64(validKey);

            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.pkcs8ShroudedKeyBag);    //PKCSObjectIdentifiers.pkcs8ShroudedKeyBag
//            ASN1Object asn1Object = ASN1Object.fromByteArray(encodeByte);
            ASN1Object asn1Object = ASN1ObjectIdentifier.fromByteArray(encodeByte);
            PrivateKeyInfo privKeyInfo = new PrivateKeyInfo(algorithmIdentifier, asn1Object);
            byte[] pkcs8Bytes = privKeyInfo.getEncoded();
            return Base64.encodeBase64String(pkcs8Bytes); // 直接一行字符串输出

        }
        return result;
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public  Key generalKey(SignatureAlgorithm signatureAlgorithm,Boolean isEncrypt ) {
        if(signatureAlgorithm.equals(SignatureAlgorithm.RS256)){
            if(isEncrypt){
//                PrivateKey prikey = getPrikey();
                PrivateKey prikey = getPrivateKey();
                System.out.println("priv+"+Base64.encodeBase64(prikey.getEncoded()));
                return prikey;
            }else{
          //      PublicKey pubkey = getPubkey();
                PublicKey pubkey = getPublicKey();
                System.out.println("publc+"+Base64.encodeBase64(pubkey.getEncoded()));
              return pubkey;
            }
        }
        String stringKey = JWT_SECRET;
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    private PublicKey getPublicKey() {
        int dd = publickeyPath.lastIndexOf(".");
       String suffix = publickeyPath.substring(publickeyPath.lastIndexOf("."));
       if(suffix.equals(SUFFIX_PEM)){
           return getPublicKeyFromPem();
       }
       if(suffix.equals(SUFFIX_PFX)){
           return getPublicKeyFromPfx();
       }
       return null;
    }

    private PrivateKey getPrivateKey() {
        int dd = privateKeyPath.lastIndexOf(".");
        String suffix = privateKeyPath.substring(privateKeyPath.lastIndexOf("."));
        if(suffix.equals(SUFFIX_PEM)){
            return getPrivateKeyFromPem();
        }
        if(suffix.equals(SUFFIX_PFX)){
            return getPrivateKeyFromPfx();
        }
        return null;

    }

    

    /**
     * 创建jwt
     */
    public  String createJWT(String id, String issuer, SignatureAlgorithm signatureAlgorithm, long ttlMillis, Map<String, Object> claims) throws Exception {

        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();

        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        Key key = generalKey(signatureAlgorithm,true);


        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(id)                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(new Date(nowMillis))           // iat: jwt的签发时间
                .setIssuer(issuer)          // issuer：jwt签发人
                //        .setSubject(subject)        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key); // 设置签名使用的签名算法和签名使用的秘钥
        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            builder.setExpiration(new Date(expMillis));
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     */
    public  Claims parseJWT(String jwt,SignatureAlgorithm signatureAlgorithm) {
        Key key = generalKey(signatureAlgorithm, false);  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)                 //设置签名的秘钥
                .parseClaimsJws(jwt).getBody();     //设置需要解析的jwt
        return claims;
    }

    // boolean
    public  boolean verify(String token, SignatureAlgorithm signatureAlgorithm){
        Claims  claims = parseJWT(token,signatureAlgorithm);
        long exp = claims.getExpiration().getTime();
        if(System.currentTimeMillis() >= exp){
            return  false;
        }
        List<String> scopes = (List<String>) claims.get("scopes");
        System.out.println("scopes"+scopes.toString());
        if(scopes.contains(CURRENT_SER)){
            return  true;
        }
       String sub = claims.getSubject();

        return true;
    }
}
