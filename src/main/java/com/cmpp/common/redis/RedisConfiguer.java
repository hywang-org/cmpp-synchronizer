package com.cmpp.common.redis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguer {

	@Value("${redis.hostName}")
	private String hostName;

	@Value("${redis.port}")
	private int port;

	@Value("${redis.password}")
	private String password;

	@Value("${redis.maxIdle}")
	private int maxIdle;

	@Value("${redis.maxTotal}")
	private int maxTotal;

	@Value("${redis.timeOutSeconds}")
	private int timeOutSeconds;

	@Value("${redis.maxWaitMillis}")
	private long maxWaitMillis;

	@Value("${redis.database0}")
	private int database0;

	@Value("${redis.database1}")
	private int database1;

	@Value("${redis.database2}")
	private int database2;

	@Value("${redis.database3}")
	private int database3;

	@Value("${redis.database4}")
	private int database4;

	@Bean(name = "redisAppInfo")
	public StringRedisTemplate redisTemplateConnectDB3() {
		StringRedisTemplate temple = new StringRedisTemplate();
		temple.setConnectionFactory(
				connectionFactory(hostName, port, password, maxIdle, maxTotal, database0, maxWaitMillis, false));
		return temple;
	}

	@Bean(name = "redisDaoValidateClient")
	public RedisTemplate<String, Object> redisTemplateConnectDB2() {
		RedisTemplate<String, Object> temple = new RedisTemplate<>();
		temple.setConnectionFactory(
				connectionFactory(hostName, port, password, maxIdle, maxTotal, database1, maxWaitMillis, false));
		RedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
		RedisSerializer stringRedisSerializer = new StringRedisSerializer();
		temple.setDefaultSerializer(fastJsonRedisSerializer);
		temple.setKeySerializer(stringRedisSerializer);
		temple.setHashKeySerializer(stringRedisSerializer);
		return temple;
	}

	@Bean(name = "redisDaoSpeedLimit")
	public StringRedisTemplate redisTemplateConnectDB() {
		StringRedisTemplate temple = new StringRedisTemplate();
		temple.setConnectionFactory(
				connectionFactory(hostName, port, password, maxIdle, maxTotal, database2, maxWaitMillis, false));
		return temple;
	}

	@Bean(name = "redisProducer")
	public StringRedisTemplate redisTemplateConnectDB4() {
		StringRedisTemplate temple = new StringRedisTemplate();
		temple.setConnectionFactory(
				connectionFactory(hostName, port, password, maxIdle, maxTotal, database3, maxWaitMillis, false));
		return temple;
	}

	@Bean(name = "saveDeletedOrders")
	public StringRedisTemplate saveDeletedOrders() {
		StringRedisTemplate temple = new StringRedisTemplate();
		temple.setConnectionFactory(
				connectionFactory(hostName, port, password, maxIdle, maxTotal, database4, maxWaitMillis, false));
		return temple;
	}

	public RedisConnectionFactory connectionFactory(String hostName, int port, String password, int maxIdle,
			int maxTotal, int index, long maxWaitMillis, boolean testOnBorrow) {
		JedisConnectionFactory jedis = new JedisConnectionFactory();
		jedis.setHostName(hostName);
		jedis.setPort(port);
		if (!StringUtils.isEmpty(password)) {
			jedis.setPassword(password);
		}
		if (index != 0) {
			jedis.setDatabase(index);
		}
		jedis.setPoolConfig(poolConfig(maxIdle, maxTotal, maxWaitMillis, testOnBorrow));
		jedis.afterPropertiesSet();
		// 初始化连接pool
		return jedis;
	}

	public JedisPoolConfig poolConfig(int maxIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMaxTotal(maxTotal);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		poolConfig.setTestOnBorrow(testOnBorrow);
		return poolConfig;
	}

}
