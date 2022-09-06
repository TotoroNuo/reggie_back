package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author TotoroNuo
 * @date 2022/8/26 17:02
 * @function :
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
