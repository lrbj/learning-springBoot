package com.example.ssldemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
@Component
public class HttpsClientRequestFactory extends SimpleClientHttpRequestFactory {

   @Autowired
   SSLClientProperties sslClientProperties;

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
            String keyPassword = sslClientProperties.getKeystorePassword();

            //证书管理器，指定证书及证书类型
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            //KeyStore用于存放证书，创建对象时 指定交换数字证书的加密标准
            KeyStore keyStore = KeyStore.getInstance(sslClientProperties.getKeystoreType());
           FileInputStream inputStream =  new FileInputStream(sslClientProperties.getKeystore());
            try {
                //添加证书
                keyStore.load(inputStream, keyPassword.toCharArray());
            } finally {
                inputStream.close();
            }
            keyManagerFactory.init(keyStore, keyPassword.toCharArray());

         //   SSLContext sslContext = SSLContext.getInstance("TLS");
//            if (auth) {
                // 设置信任证书（绕过TrustStore验证）
                sslContext.init(keyManagerFactory.getKeyManagers(), trustAllCerts, null);
 //               HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//            } else {
//                //加载证书材料，构建sslContext
//                sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, keyPassword.toCharArray()).build();
//            }
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


}