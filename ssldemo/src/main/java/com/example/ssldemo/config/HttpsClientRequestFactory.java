package com.example.ssldemo.config;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.List;

public class HttpsClientRequestFactory extends SimpleClientHttpRequestFactory {
    private  String  keyPassword;
    private  String  keyStoreType = "PKCS12";
    private  String  keyStorePath;

//    @Override
//    protected void prepareConnection(HttpURLConnection connection, String httpMethod) {
//        try {
//            if (!(connection instanceof HttpsURLConnection)) {
//                throw new RuntimeException("An instance of HttpsURLConnection is expected");
//            }
//
//            HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
//
//            TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                            return null;
//                        }
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        }
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        }
//
//                    }
//            };
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            httpsConnection.setSSLSocketFactory(new MyCustomSSLSocketFactory(sslContext.getSocketFactory()));
//
//            httpsConnection.setHostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String s, SSLSession sslSession) {
//                    return true;
//                }
//            });
//
//            super.prepareConnection(httpsConnection, httpMethod);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) {
        try {
            if (!(connection instanceof HttpsURLConnection)) {
                throw new RuntimeException("An instance of HttpsURLConnection is expected");
            }

            HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;

            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }
                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }

                    }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");

            //证书管理器，指定证书及证书类型
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            //KeyStore用于存放证书，创建对象时 指定交换数字证书的加密标准
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
           FileInputStream inputStream =  new FileInputStream(keyStorePath);
            try {
                //添加证书
                keyStore.load(inputStream, keyPassword.toCharArray());
            } finally {
                inputStream.close();
            }
            keyManagerFactory.init(keyStore, keyPassword.toCharArray());

         //   SSLContext sslContext = SSLContext.getInstance("TLS");
            if (auth) {
                // 设置信任证书（绕过TrustStore验证）
                sslContext.init(keyManagerFactory.getKeyManagers(), trustAllCerts, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            } else {
                //加载证书材料，构建sslContext
                sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, keyPassword.toCharArray()).build();
            }
            httpsConnection.setSSLSocketFactory(new MyCustomSSLSocketFactory(sslContext.getSocketFactory()));

            httpsConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });

            super.prepareConnection(httpsConnection, httpMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * We need to invoke sslSocket.setEnabledProtocols(new String[] {"SSLv3"});
     * see http://www.oracle.com/technetwork/java/javase/documentation/cve-2014-3566-2342133.html (Java 8 section)
     */
    // SSLSocketFactory用于创建 SSLSockets
    private static class MyCustomSSLSocketFactory extends SSLSocketFactory {

        private final SSLSocketFactory delegate;

        public MyCustomSSLSocketFactory(SSLSocketFactory delegate) {
            this.delegate = delegate;
        }

        // 返回默认启用的密码套件。除非一个列表启用，对SSL连接的握手会使用这些密码套件。
        // 这些默认的服务的最低质量要求保密保护和服务器身份验证
        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        // 返回的密码套件可用于SSL连接启用的名字
        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }


        @Override
        public Socket createSocket(final Socket socket, final String host, final int port,
                                   final boolean autoClose) throws IOException {
            final Socket underlyingSocket = delegate.createSocket(socket, host, port, autoClose);
            return overrideProtocol(underlyingSocket);
        }


        @Override
        public Socket createSocket(final String host, final int port) throws IOException {
            final Socket underlyingSocket = delegate.createSocket(host, port);
            return overrideProtocol(underlyingSocket);
        }

        @Override
        public Socket createSocket(final String host, final int port, final InetAddress localAddress,
                                   final int localPort) throws
                IOException {
            final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
            return overrideProtocol(underlyingSocket);
        }

        @Override
        public Socket createSocket(final InetAddress host, final int port) throws IOException {
            final Socket underlyingSocket = delegate.createSocket(host, port);
            return overrideProtocol(underlyingSocket);
        }

        @Override
        public Socket createSocket(final InetAddress host, final int port, final InetAddress localAddress,
                                   final int localPort) throws
                IOException {
            final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
            return overrideProtocol(underlyingSocket);
        }

        private Socket overrideProtocol(final Socket socket) {
            if (!(socket instanceof SSLSocket)) {
                throw new RuntimeException("An instance of SSLSocket is expected");
            }
            ((SSLSocket) socket).setEnabledProtocols(new String[]{"TLSv1"});
            return socket;
        }
    }

    public RestTemplate tmsRestTemplate() throws Exception {
        //新建RestTemplate对象
        RestTemplate restTemplate = new RestTemplate();
        //判断证书文件地址是否存在
        if (StringUtils.isNotEmpty(sslProperties.getKeyfile())) {
            //在握手期间，如果URL的主机名和服务器的标识主机名不匹配，则验证机制可以回调此接口的实现
            程序来确定是否应该允许此连接
            HostnameVerifier hv = new HostnameVerifier() {
                @Override
                public boolean verify(String urlHostName, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            //构建SSL-Socket链接工厂
            SSLConnectionSocketFactory ssLSocketFactory = buildSSLSocketFactory("PKCS12",
                    sslProperties.getKeyfile(),sslProperties.getPassword(),
                    Lists.newArrayList("TLSv1"), true);
            //Spring提供HttpComponentsClientHttpRequestFactory指定使用HttpClient作为底层实现创建 HTTP请求
            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                    HttpClients.custom().setSSLSocketFactory(ssLSocketFactory).build()
            );
            //设置传递数据超时时长
            httpRequestFactory.setReadTimeout(sslProperties.getTimeout());
            //设置建立连接超时时长
            httpRequestFactory.setConnectTimeout(sslProperties.getTimeout());
            //设置获取连接超时时长
            httpRequestFactory.setConnectionRequestTimeout(tmsProperties.getTimeout());

            restTemplate.setRequestFactory(httpRequestFactory);

            // 返回消息头也是text_html,消息格式是XML,添加新的message converter
            Jaxb2RootElementHttpMessageConverter messageConverter = new Jaxb2RootElementHttpMessageConverter();
            //设置message Converter支持的媒体类型
            List<MediaType> finalMediaTypes = new ArrayList<>();
            finalMediaTypes.addAll(messageConverter.getSupportedMediaTypes());
            finalMediaTypes.add(MediaType.TEXT_HTML);
            messageConverter.setSupportedMediaTypes(finalMediaTypes);
            restTemplate.setMessageConverters(Lists.newArrayList(messageConverter));

        }
        return restTemplate;
    }

    /**
     * 构建SSLSocketFactory
     *
     * @param keyStoreType
     * @param keyFilePath
     * @param keyPassword
     * @param sslProtocols
     * @param auth 是否需要client默认相信不安全证书
     * @return
     * @throws Exception
     */
    private SSLConnectionSocketFactory buildSSLSocketFactory(String keyStoreType, String keyFilePath,
                                                             String keyPassword, List<String> sslProtocols, boolean auth) throws Exception {
        //证书管理器，指定证书及证书类型
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        //KeyStore用于存放证书，创建对象时 指定交换数字证书的加密标准
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        FileInputStream instream = new FileInputStream(new File(merchantInfo.getPfx()));
        try {
            //添加证书
            keyStore.load(inputStream, keyPassword.toCharArray());
        } finally {
            inputStream.close();
        }
        keyManagerFactory.init(keyStore, keyPassword.toCharArray());

        SSLContext sslContext = SSLContext.getInstance("SSL");
        if (auth) {
            // 设置信任证书（绕过TrustStore验证）
            TrustManager[] trustAllCerts = new TrustManager[1];
            TrustManager trustManager = new AuthX509TrustManager();
            trustAllCerts[0] = trustManager;
            sslContext.init(keyManagerFactory.getKeyManagers(), trustAllCerts, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } else {
            //加载证书材料，构建sslContext
            sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, keyPassword.toCharArray()).build();
        }

        SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(sslContext, sslProtocols.toArray(new String[sslProtocols.size()]),
                        null,
                        new HostnameVerifier() {
                            // 这里不校验hostname
                            @Override
                            public boolean verify(String urlHostName, SSLSession session) {
                                return true;
                            }
                        });

        return sslConnectionSocketFactory;
    }

}