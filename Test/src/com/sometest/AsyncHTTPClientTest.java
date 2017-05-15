package com.sometest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.xmlbeans.impl.common.IOUtil;

public class AsyncHTTPClientTest {

    public static void main(String[] args) throws IOReactorException, InterruptedException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        availableProcessors = 3;
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(availableProcessors)
                .setConnectTimeout(30000).setSoTimeout(30000).build();
        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        //
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connManager.setDefaultMaxPerRoute(availableProcessors);
        //
        CloseableHttpAsyncClient instance = HttpAsyncClients.custom().setConnectionManager(connManager).build();
        instance.start();

        List<HttpResponse> httpResponse = new ArrayList<>();

        FutureCallback<HttpResponse> callback = new FutureCallback<HttpResponse>() {

            @Override
            public void completed(HttpResponse response) {
                System.out.println("Added");
                httpResponse.add(response);
                /*try {
                    response.getEntity().getContent().close();
                } catch (UnsupportedOperationException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
                /*System.out.println("Started");
                
                printCurrentThread();
                System.out.println("Completed");
                
                try {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                    ReadableByteChannel source = Channels.newChannel(response.getEntity().getContent());
                    WritableByteChannel destination = Channels.newChannel(outputStream);
                    int transferred;
                    int totalRead = 0;
                    while ((transferred = source.read(buffer)) != -1) {
                        buffer.flip();
                        destination.write(buffer);
                        totalRead += transferred;
                        buffer.clear();
                    }
                
                  //  System.out.println(destination.toString());
                    System.out.println("total written " + totalRead);
                } catch (UnsupportedOperationException | IOException e) {
                    e.printStackTrace();
                } finally {}*/

            }

            @Override
            public void failed(Exception ex) {
                System.out.println("Failed");
                System.out.println(ex);
            }

            @Override
            public void cancelled() {
                System.out.println("Cancelled");
                System.out.println("canceled");
            }
        };

        HttpUriRequest request = new HttpGet("http://localhost:4567/hello");
        for (int i = 0; i < 1; i++) {
            instance.execute(request, callback);
        }
        
        Thread.sleep(5000);
        System.out.println("Calling");
        
        instance.execute(new HttpGet("http://localhost:4567/hello"), new FutureCallback<HttpResponse>() {

            @Override
            public void completed(HttpResponse result) {
                try {
                    System.out.println(result.getEntity().getContent());
                } catch (UnsupportedOperationException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Exception ex) {
                ex.printStackTrace();
            }

            @Override
            public void cancelled() {
                // TODO Auto-generated method stub

            }
        });
        Thread.sleep(1000);
        
        System.out.println("Clearing");
        
        httpResponse.stream().forEach(resp -> {
            try {
                resp.getEntity().getContent().close();
            } catch (UnsupportedOperationException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

    }

    private static void printCurrentThread() {
        System.out.println(Thread.currentThread().getName());
    }

}
