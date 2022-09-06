package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author TotoroNuo
 * @date 2022/8/30 9:25
 * @function :
 */

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
