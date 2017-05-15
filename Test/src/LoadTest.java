import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class LoadTest {

    public static void main(String[] args) throws HttpException, IOException, InterruptedException {

        final AtomicInteger inte = new AtomicInteger(0);
        int threads = 50;
        CountDownLatch latch = new CountDownLatch(threads);
        String UAT_URL = "http://uat-svc-1003.ci.uh1.inmobi.com:8080/apigateway/advData-1.0/external/reports.json";
        String PROD_URL = "http://api-fe-1001.ci.uh1.inmobi.com:8080/apigateway/advData-1.0/external/reports.json";

        for (int i = 0; i < threads; i++) {
            System.out.println("Thread " + i);
            new Thread(() -> {
                try {
                    String url = PROD_URL;

                    CloseableHttpClient client = HttpClientBuilder.create().build();
                    HttpPost post = new HttpPost(url);

                    // add header
                    post.setHeader("secretKey", "cbb1ee39c0574a94b3b2e06cb87c769b");
                    post.setHeader("sessionId", "f4ca71f6376b4e97898150a16e6a6dbe");
                    post.setHeader("accountId", "4028cb8b2b17f7cb012b186611b20014");
                    post.setHeader("Content-Type", "application/json");

                    List<NameValuePair> urlParameters = new ArrayList<>();
                    // urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));

                    StringEntity input = new StringEntity(
                            "{\"reportRequest\":{\"metrics\":[\"impressions\",\"clicks\",\"conversions\",\"adSpend\"],\"groupBy\":[\"campaign\",\"date\",\"platform\"],\"timeFrame\":\"2017-03-02:2017-03-02\"}}");
                    input.setContentType("application/json");
                    post.setEntity(input);

                    HttpResponse response = client.execute(post);
                    System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

                    String output = IOUtils.toString(response.getEntity().getContent());
                    if (response.getStatusLine().getStatusCode() != 200) {
                        inte.incrementAndGet();
                        System.out.println(output);
                    } else {

                    }

                    /*  System.out.println("response-status "+response.getStatusLine().getStatusCode());*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }).start();
        }

        latch.await();
        System.out.println("Total Errors : " + inte.get());
    }

}
