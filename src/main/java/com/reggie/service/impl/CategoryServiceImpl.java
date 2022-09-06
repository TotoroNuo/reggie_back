package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.CustomException;
import com.reggie.entity.Category;
import com.reggie.entity.Dish;
import com.reggie.entity.Setmeal;
import com.reggie.mapper.CategoryMapper;
import com.reggie.service.CategoryService;
import com.reggie.service.DishService;
import com.reggie.service.SetmealDishService;
import com.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TotoroNuo
 * @date 2022/8/30 9:26
 * @function :
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 重写的删除方法
     *
     * @param ids
     */
    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据id查询
        dishQueryWrapper.eq(Dish::getCategoryId, ids);
        int count1 = dishService.count(dishQueryWrapper);

        //如果有菜品关联，则无法删除
        if (count1 > 0) {
            throw new CustomException("关联菜品,无法删除");
        }
        //套餐判断
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, ids);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);

        if (count2 > 0) {
            throw new CustomException("关联套餐,无法删除");
        }
        super.removeById(ids);
    }
}
