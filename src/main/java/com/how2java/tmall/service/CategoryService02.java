package com.how2java.tmall.service;

import com.how2java.tmall.dao.CategoryDAO02;
import com.how2java.tmall.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * the class is create by @Author:oweson
 *
 * @Date：2018/11/14 0014 11:00
 */
@Service
public class CategoryService02 {
    @Autowired
    CategoryDAO02 categoryDAO02;

    public void add(Category category) {
        categoryDAO02.save(category);
    }

    public void update(Category category) {
        categoryDAO02.save(category);
    }

    public void delete(int id) {
        categoryDAO02.delete(id);
    }

    public Category getById(int id) {
        return categoryDAO02.findOne(id);
    }

}
