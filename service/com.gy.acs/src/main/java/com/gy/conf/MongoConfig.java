package com.gy.conf;

import com.gy.mongo.GyMongoBean;
import com.gy.mongo.MongoDBDaoImpl;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: mongoDB配置
 * @作者: DuKai
 * @创建时间: 2018/12/17 19:21
 * @版本号: V1.0
 */

@Configuration
public class MongoConfig {
    private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    @Bean(name = "gyMongoBean")
    public GyMongoBean gyMongoBean(){
        return new GyMongoBean();
    }

    //覆盖默认的MongoDbFacotry
    @Bean(name = "mongoDbFactory")
    public MongoDbFactory mongoDbFactory(@Qualifier("gyMongoBean") GyMongoBean gyMongoBean) {
        //客户端配置（连接数，副本集群验证）
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(gyMongoBean.getMaxConnectionsPerHost());
        builder.minConnectionsPerHost(gyMongoBean.getMinConnectionsPerHost());
        if (gyMongoBean.getReplicaSet() != null) {
            builder.requiredReplicaSetName(gyMongoBean.getReplicaSet());
        }
        builder.threadsAllowedToBlockForConnectionMultiplier(gyMongoBean.getThreadsAllowedToBlockForConnectionMultiplier());
        builder.serverSelectionTimeout(gyMongoBean.getServerSelectionTimeout());
        builder.maxWaitTime(gyMongoBean.getMaxWaitTime());
        builder.maxConnectionIdleTime(gyMongoBean.getMaxConnectionIdleTime());
        builder.maxConnectionLifeTime(gyMongoBean.getMaxConnectionLifeTime());
        builder.connectTimeout(gyMongoBean.getConnectTimeout());
        builder.socketTimeout(gyMongoBean.getSocketTimeout());
        //builder.socketKeepAlive(gyMongoBean.getSocketKeepAlive());
        builder.sslEnabled(gyMongoBean.getSslEnabled());
        builder.sslInvalidHostNameAllowed(gyMongoBean.getSslInvalidHostNameAllowed());
        builder.alwaysUseMBeans(gyMongoBean.getAlwaysUseMBeans());
        builder.heartbeatFrequency(gyMongoBean.getHeartbeatFrequency());
        builder.minHeartbeatFrequency(gyMongoBean.getMinHeartbeatFrequency());
        builder.heartbeatConnectTimeout(gyMongoBean.getHeartbeatConnectTimeout());
        builder.heartbeatSocketTimeout(gyMongoBean.getHeartbeatSocketTimeout());
        builder.localThreshold(gyMongoBean.getLocalThreshold());
        MongoClientOptions mongoClientOptions = builder.build();

        // MongoDB地址列表
        List<ServerAddress> serverAddresses = new ArrayList<>();
        for (String address : gyMongoBean.getAddress()) {
            String[] hostAndPort = address.split(":");
            String host = hostAndPort[0];
            Integer port = Integer.parseInt(hostAndPort[1]);
            ServerAddress serverAddress = new ServerAddress(host, port);
            serverAddresses.add(serverAddress);
        }
        logger.info("serverAddresses：" + serverAddresses.toString());

        //连接认证
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(gyMongoBean.getUsername(),
                gyMongoBean.getDatabase(), gyMongoBean.getPassword().toCharArray());
        logger.info("mongoCredential：" + mongoCredential.toString());
        //创建客户端和Factory
        MongoClient mongoClient = new MongoClient(serverAddresses, mongoCredential, mongoClientOptions);

        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, gyMongoBean.getDatabase());
        return mongoDbFactory;
    }


    @Bean(name="mongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("mongoDbFactory") MongoDbFactory mongoDbFactory){
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }

    @Bean(name = "mongoDBDao")
    public MongoDBDaoImpl mongoDBDao(){
        return new MongoDBDaoImpl();
    }
}
