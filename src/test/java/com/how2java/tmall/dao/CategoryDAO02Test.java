package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * the class is create by @Author:oweson
 *
 * @Date：2018/11/14 0014 10:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CategoryDAO02Test {
    @Autowired
    CategoryDAO02 categoryDAO02;

    @Test
    public void testInsert() {
        Category category = new Category();
        category.setName("南极");
        Category save = categoryDAO02.save(category);
        Assert.assertNotNull(save);

    }

    @Test
    public void select() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<Category> categoryDAO02All = categoryDAO02.findAll(pageRequest);
        Assert.assertEquals(2L, categoryDAO02All.getSize());
    }

    @Test
    public void superSelect() {
        /**降序大的往上飘*/
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Category> categoryDAO02All = categoryDAO02.findAll(sort);
        for (Category category : categoryDAO02All) {
            System.out.println(category);

        }
    }

}