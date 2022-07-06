package yanghgri.whileyouplay.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/game")
public class GameController {
    private final RedisTemplate<String, Object> redisTemplate;
    ValueOperations<String, Object> redisValueOperations;

    @Autowired
    public GameController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisValueOperations = redisTemplate.opsForValue();
    }

    @GetMapping("/list")
    @Cacheable("game:list")
    public String gameList() {
        log.info("access redis! list");
        return "HOYA";
    }

    @GetMapping("/clear")
    @CacheEvict("game:list")
    public void delete() {
        log.info("access redis! clear");
    }
}
