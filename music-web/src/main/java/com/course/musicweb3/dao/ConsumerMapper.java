package com.course.musicweb3.dao;

import com.course.musicweb3.domain.Consumer;

import java.util.List;

public interface ConsumerMapper {
    public int insert(Consumer consumer);
    public int update(Consumer consumer);
    public int delete(Integer id);
    public Consumer selectByPrimaryKey(Integer id);
    public List<Consumer> allConsumer();
    public int verifyPassword(String username,String password);
    public Consumer getByUsername(String username);

}
