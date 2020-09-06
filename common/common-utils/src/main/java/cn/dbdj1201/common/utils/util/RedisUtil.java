package cn.dbdj1201.common.utils.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yz1201
 * @date 2020-08-24 22:19
 **/
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定失效时间
     *
     * @param key
     * @param time 单位秒
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("redis 指定失效时间出问题了");
            return false;
        }
    }

    /**
     * 获取指定key过期时间
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        Long expire = this.redisTemplate.getExpire(key);
        if (expire == null) {
            log.error("传错key了是吧w(ﾟДﾟ)w");
            throw new NullPointerException();
        }
        return expire;
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            Boolean hasKey = this.redisTemplate.hasKey(key);
            Assert.notNull(hasKey, "你这key是人传过来的？");
            return hasKey;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("你这key是人传过来的O(∩_∩)O");
            return false;
        }
    }

    /**
     * 删除某个key
     *
     * @param keys
     */
    public void del(String... keys) {
        if (keys.length < 1) {
            log.warn("做点人事吧");
            return;
        }
        Arrays.stream(keys).forEach(key -> this.redisTemplate.delete(key));
    }

    /**
     * 获取某个key对应的value
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : this.redisTemplate.opsForValue().get(key);
    }

    /**
     * string 缓存存储
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("插入数据失败");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加记录，设置失效时间
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public boolean set(String key, Object value, long timeout) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
            if (timeout > 0) {
                this.expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            log.error("插入数据失败");
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 按步长递增
     *
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0哦");
        }

        Long increment = this.redisTemplate.opsForValue().increment(key, delta);
        Assert.notNull(increment, "你传你🐎呢？key呢");
        return increment;
    }

    /**
     * 递减
     *
     * @param key
     * @param delta
     * @return
     */
    public long desc(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("敬业精神");
        }

        Long decrement = this.redisTemplate.opsForValue().decrement(key, delta);
        Assert.notNull(decrement, "你传你🐎呢？key呢");
        return decrement;
    }

    //======================================MAP=========================================================

    /**
     * 获取某个key对应的所有的键值对
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hmget(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    /**
     * 存储一个键值对
     *
     * @param key
     * @param map
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("put hash hash-key key value,{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 存储一个键值对并且设置过期时间
     *
     * @param key
     * @param map
     * @param time
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("put hash hash-key key value,{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中存放数据，如果不存在将创建
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean hset(String key, String item, Object value) {

        try {
            this.redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("存储hash-key 的value失败-{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中存放数据，如果不存在将创建 设置过期时间
     *
     * @param key
     * @param item
     * @param value
     * @param time
     * @return
     */
    public boolean hset(String key, String item, Object value, long time) {

        try {
            this.redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("存储hash-key 的value失败-{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key
     * @param items
     */
    public void hdel(String key, Object... items) {
        this.redisTemplate.opsForHash().delete(key, items);
    }

    /**
     * hash表中是否含有该hash key
     *
     * @param key
     * @param item
     * @return
     */
    public boolean hHasKey(String key, String item) {
        return this.redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 递增
     *
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hincr(String key, String item, double by) {
        return this.redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * 递减
     *
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hdesc(String key, String item, double by) {
        return this.redisTemplate.opsForHash().increment(key, item, -by);
    }

    //================================ SET ==============================================

    /**
     * 获取某key对应的set集合
     *
     * @param key
     * @return
     */
    public Set<Object> sGet(String key) {

        try {
            return this.redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("获取某key对应的set失败-{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 看看key对应的value set中是否含有某个value
     *
     * @param key
     * @param value
     * @return
     */
    public boolean sHasKey(String key, Object value) {
        try {
            Boolean member = this.redisTemplate.opsForSet().isMember(key, value);
            Assert.notNull(member, "找点空闲，找点时间");
            return member;
        } catch (Exception e) {
            log.error("该key应该是不存在的对吧？");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key
     * @param values
     * @return
     */
    public long sSet(String key, Object... values) {
        try {
            Long add = this.redisTemplate.opsForSet().add(key, values);
            Assert.notNull(add, "添加过程出问题了？redis跑没跑到底");
            return add;
        } catch (Exception e) {
            log.error("想不起来写什么，不如不写-{}", e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 将数据放入set缓存 过期时间设置
     *
     * @param key
     * @param values
     * @param time
     * @return
     */
    public long sSet(String key, long time, Object... values) {
        try {
            Long add = this.redisTemplate.opsForSet().add(key, values);
            Assert.notNull(add, "添加过程出问题了？redis跑没跑到底");
            if (time > 0) {
                this.expire(key, time);
            }
            return add;
        } catch (Exception e) {
            log.error("想不起来写什么，不如不写-{}", e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取set长度
     *
     * @param key
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            Long size = this.redisTemplate.opsForSet().size(key);
            Assert.notNull(size, "有点问题，我傻了");
            return size;

        } catch (Exception e) {
            log.error("redis还活着呢？");
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key
     * @param values
     * @return
     */
    public long setRemove(String key, Object... values) {

        try {
            Long remove = this.redisTemplate.opsForSet().remove(key, values);
            Assert.notNull(remove, "移除失败");
            return remove;
        } catch (Exception e) {
            log.error("移除过程中有大问题-{}", e.getMessage());
            e.printStackTrace();
            return -1;
        }

    }


    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public long lGetListSize(String key) {
        try {
            Long size = redisTemplate.opsForList().size(key);
            Assert.notNull(size, "获取长度失败");
            return size;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存 尾插
     *
     * @param key   键
     * @param value 值
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 将list放入缓存 且设置过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 将list放入缓存
     *
     * @param key    键
     * @param values 值
     * @return
     */
    public boolean lSet(String key, List<Object> values) {
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */

    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个为value的值
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */

    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            Assert.notNull(remove, "移除失败");
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    //====================================== ZSET =============================================

    /**
     * 获取有序set长度
     *
     * @param key
     * @return
     */
    public long zSetSize(String key) {

        try {
            Long size = this.redisTemplate.opsForZSet().size(key);
            Assert.notNull(size, "获取长度败了");
            return size;
        } catch (Exception e) {
            log.error("获取不到，你有点问题");
            e.printStackTrace();
            return -1;
        }
    }

//    public void zGet(){
//        this.redisTemplate.opsForZSet().a
//    }
}
