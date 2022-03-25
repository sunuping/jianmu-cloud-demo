package com.jianmu.config.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class EnhanceServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements EnhanceService<T> {
    private final String KEY_PREFIX;
    private final ValueOperations<String, Object> redisValue;
    private final RedisTemplate<String, Object> redis;
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    public EnhanceServiceImpl(RedisTemplate<String, Object> redis) {
        this.redis = redis;
        this.redisValue = redis.opsForValue();
        KEY_PREFIX = EnhanceConstant.REDIS_PREFIX + getClassName();
    }

    private String getClassName() {
        Type type = getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        final Class<T> entityClass = (Class<T>) types[1];
        return entityClass.getSimpleName();
    }

    private String getId(T t) throws IllegalAccessException {
        return String.valueOf(FieldUtils.readField(t, "id", true));
    }

    @Override
    public Boolean add(T t) {
        Boolean flag = save(t);
        try {
            redis.delete(KEY_PREFIX + getId(t));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Boolean modify(T t) {
        try {
            final String id = getId(t);
            final boolean flag = update(new UpdateWrapper<T>().setEntity(t).apply("id=" + id).last("limit 1"));
            redis.delete(KEY_PREFIX + id);
            return flag;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean del(Serializable id) {
        final Boolean flag = remove(new LambdaUpdateWrapper<T>().apply("id=" + id).last("limit 1"));
        redis.delete(KEY_PREFIX + id);
        return flag;
    }

    @Override
    public T get(Serializable id) {
        Object o = redisValue.get(KEY_PREFIX + id);
        T t;
        if (o == null) {
            t = getOne(new LambdaQueryWrapper<T>().apply("id=" + id + "").last("limit 1"));
            if (t == null) {
                redis.delete(KEY_PREFIX + id);
            } else {
                redisValue.set(KEY_PREFIX + id, t, 10000 + RANDOM.nextLong() * 10000L, TimeUnit.SECONDS);
            }
        } else {
            t = (T) o;
        }
        return t;
    }

}
