package com.how2java.tmall.web.oweson;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService02;
import com.how2java.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * the class is create by @Author:oweson
 *
 * @Date：2018/11/14 0014 11:07
 */

/***/
@RestController
@RequestMapping("/oweson")
public class CategoryController02 {
    @Autowired
    CategoryService02 categoryService02;
    /** 1 列表*/
    /**
     * 2 添加;
     * 1. 首选通过CategoryService 保存到数据库
     * 2. 然后接受上传图片，并保存到 img/category目录下
     * 3. 文件名使用新增分类的id
     * 4. 如果目录不存在，需要创建
     * 5. image.transferTo 进行文件复制
     * 6. 调用ImageUtil的change2jpg 进行文件类型强制转换为 jpg格式
     * 7. 保存图片
     */
    @PostMapping("/categories")
    /** 1 加*/
    public Object add(Category category, MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        categoryService02.add(category);
        saveOrUpdateImageFile(category, multipartFile, request);
        return category;


    }

    /**
     * 2 删;
     * 1. 首先根据id 删除数据库里的数据
     * 2. 删除对应的文件
     * 3. 返回 null, 会被RESTController 转换为空字符串。
     */
    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        categoryService02.delete(id);
        File fileImage = new File(request.getServletContext().getRealPath("/image/category"));
        File file = new File(fileImage, id + "jpg");
        if (file != null) {
            file.delete();
        }
        return null;

    }

    /**
     * 3 查;
     * 提供 get 方法，把id对应的Category取出来，并转换为json对象发给浏览器
     */
    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id) {
        return categoryService02.getById(id);
    }

    /**
     * 4 改,图片不重新上传的话就会null,还是原来的;
     * 1. 获取参数名称
     * 这里获取参数用的是 request.getParameter("name").
     * 为什么不用 add里的注入一个 Category对象呢？ 因为。。。PUT 方式注入不了。。。
     * 只能用这种方式取参数了，试了很多次才知道是这么个情况~
     * 2. 通过 CategoryService 的update方法更新到数据库
     * 3. 如果上传了图片，调用增加的时候共用的 saveOrUpdateImageFile 方法。
     * 4. 返回这个分类对象。
     */
    @PutMapping("/categories/{id}")
    public Object update(Category category, MultipartFile image, HttpServletRequest request) throws IOException {
        String name = request.getParameter("name");
        category.setName(name);
        categoryService02.update(category);
        if (image != null) {
            saveOrUpdateImageFile(category, image, request);
        }
        return category;
    }

    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request)
            throws IOException {
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, bean.getId() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

}
