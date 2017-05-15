package com.sometest;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonClientTest {

    public static void main(String[] args) throws IOException{


        Config config = Config.fromYAML(RedissonClientTest.class.getClassLoader().getResourceAsStream("redisson.yaml"));
        // config.setExecutor(EXECUTOR_SERVICE);

        // 2. Create Redisson instance
        RedissonClient redissonClient = Redisson.create(config);
        // RBinaryStream bucket = ;
        try {
           /* AdGroup adGroup = new AdGroupServiceClient("qa1006.planet.corp.inmobi.com", 8030)
                    .getAdGroup("b8fd53b9e23344dca6228b936ce6bb90", new RequestContext("1", "12", "123"), null)
                    .getAdGroup();
            redissonClient.getBinaryStream("b8fd53b9e23344dca6228b936ce6bb90")
                    .set(new TSerializer(new TJSONProtocol.Factory()).serialize(adGroup));

            byte[] content = redissonClient.getBinaryStream("b8fd53b9e23344dca6228b936ce6bb90").get();
            AdGroup oldAdGroup = new AdGroup();
            new TDeserializer(new TJSONProtocol.Factory()).deserialize(oldAdGroup, content);
            
            System.out.println(oldAdGroup);*/
        } finally {
            redissonClient.shutdown();
        }
    }

}
