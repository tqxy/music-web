package com.course.musicweb3.service;

import com.course.musicweb3.domain.Consumer;

import java.util.List;

public interface ConsumerService {
    public boolean insert(Consumer consumer);
    public boolean update(Consumer consumer);
    public boolean delete(Integer id);
    public Consumer selectByPrimaryKey(Integer id);
    public List<Consumer> allConsumer();
    public boolean verifyPassword(String username,String password);
    public Consumer getByUsername(String username);
}
