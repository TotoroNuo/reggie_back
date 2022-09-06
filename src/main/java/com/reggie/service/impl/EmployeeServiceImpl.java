package com.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.entity.Employee;
import com.reggie.mapper.EmployeeMapper;
import com.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author TotoroNuo
 * @date 2022/8/26 17:04
 * @function :
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
