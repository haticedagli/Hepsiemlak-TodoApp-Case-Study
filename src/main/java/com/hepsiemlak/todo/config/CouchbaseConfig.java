package com.hepsiemlak.todo.config;

import com.couchbase.client.core.cnc.tracing.NoopRequestTracer;
import com.couchbase.client.core.env.IoConfig;
import com.couchbase.client.core.env.TimeoutConfig;
import com.couchbase.client.java.env.ClusterEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import java.time.Duration;

@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Value( "${couchbase.connectionString}" )
    private String connectionString;
    @Value( "${couchbase.username}" )
    private String username;
    @Value( "${couchbase.password}" )
    private String password;
    @Value( "${couchbase.bucket}" )
    private String bucket;

    @Override
    public String getConnectionString() {
        return connectionString;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucket;
    }

    @Override
    public ClusterEnvironment couchbaseClusterEnvironment() {
        return ClusterEnvironment.builder()
                .ioConfig(IoConfig.idleHttpConnectionTimeout(Duration.ofSeconds(4)))
                .requestTracer(NoopRequestTracer.INSTANCE)
                .timeoutConfig(TimeoutConfig.builder()
                        .queryTimeout(Duration.ofSeconds(12))
                        .connectTimeout(Duration.ofSeconds(12))
                        .kvTimeout(Duration.ofSeconds(12))
                        .disconnectTimeout(Duration.ofSeconds(12))
                )
                .build();
    }
}
