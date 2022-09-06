package com.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.entity.Setmeal;
import com.reggie.mapper.SetmealMapper;
import com.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

/**
 * @author TotoroNuo
 * @date 2022/8/30 15:52
 * @function :
 */

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
