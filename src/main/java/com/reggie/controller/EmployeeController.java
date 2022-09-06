package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.R;
import com.reggie.entity.Employee;
import com.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

//@RestController = @ResponseBody+@Controller   666
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录模块
     * 1、将页面提交的密码password进行md5加密处理
     * 2、根据页面提交的用户名username查询数据库
     * 3、如果没有查询到则返回登录失败结果
     * 4、密码比对，如果不一致则返回登录失败结果
     * 5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
     * 6、登录成功，将员工id存入Session并返回登录成功结果
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //1.加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        //StandardCharsets.UTF_8

        //2.查询
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3.如果没有
        if (emp == null) {
            return R.error("登陆失败，用户名或密码错误");
        }

        //4.密码不对
        if (!emp.getPassword().equals(password)) {
            return R.error("登陆失败，用户名或密码错误");
        }

        //5.查看员工状态
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        //6.员工ID存入session，返回对象
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 退出模块
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清除Session
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 添加模块
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        //设置默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //添加返回的数据
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 分页查询模块
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 通用的修改方法,任何此页的修改都由他提交
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        //执行
        employeeService.updateById(employee);
        return R.success("员工修改信息成功");
    }

    /**
     * 根据id查询要修改的员工ID，并将结果输出到编辑界面
     * 1.点击编辑按钮时，页面跳转到add.html，并在url中携带参数[员工id]
     * 2.在add.html页面获取url中的参数[员工id]
     * 3.发送ajax请求，请求服务端，同时提交员工id参数
     * 4.服务端接收请求，根据员工id查询员工信息，将员工信息以json形式响应给页面
     * 5.页面接收服务端响应的json数据，通过VUE的数据绑定进行员工信息回显
     * 6.点击保存按钮，发送ajax请求，将页面中的员工信息以json方式提交给服务端
     * 7.服务端接收员工信息，并进行处理，完成后给页面响应
     * 8.页面接收到服务端响应信息后进行相应处理
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }else{
            return R.error("没有找到");
        }

    }
}
