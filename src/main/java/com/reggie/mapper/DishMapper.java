package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author TotoroNuo
 * @date 2022/8/30 15:18
 * @function :
 */

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
