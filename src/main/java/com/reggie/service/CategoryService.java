package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.entity.Category;

/**
 * @author TotoroNuo
 * @date 2022/8/30 9:26
 * @function :
 */


public interface CategoryService extends IService<Category> {
    public void remove(Long ids);
}
