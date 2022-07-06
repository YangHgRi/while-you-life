package yanghgri.whileyouplay.conf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableCaching
public class RedisCacheConf extends CachingConfigurerSupport {
    private final StringRedisSerializer stringSerializer;
    private final GenericJackson2JsonRedisSerializer jsonSerializer;

    public RedisCacheConf() {
        //JSON序列化反序列化器
        ObjectMapper objectMapper = new ObjectMapper();
        //配置映射对象的成员变量和方法对OM的可见性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //配置OM序列化时在JSON中附带对象类型信息和容器对象中元素类型信息，之后反序列化时不需要手动强转对象类型或容器对象内元素类型
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        //默认序列化器
        this.stringSerializer = new StringRedisSerializer();
        //Value与HashValue序列化器
        this.jsonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        //选择Lettuce库作为底层连接redis
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        //配置默认序列化器，除了Value和HashValue以外都用StringRedisSerializer
        redisTemplate.setDefaultSerializer(stringSerializer);
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashValueSerializer(jsonSerializer);
        return redisTemplate;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        //TODO 使用target或method元数据生成key时，不同对象或不同方法不能操作同一个key，导致能增不能删，暂时没有解决思路
        return ((target, method, params) -> Arrays.asList(params));
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //设置缓存1H有效期
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1L)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer));
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(redisCacheConfiguration).build();
    }
}