package com.miaoshaproject.miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miaoshaproject.dao.ItemDOMapper;
import com.miaoshaproject.dao.OrderDOMapper;
import com.miaoshaproject.dao.UserDoMapper;
import com.miaoshaproject.dataobject.UserDo;

//交给Spring托管，作为主启动类
@SpringBootApplication(scanBasePackages= {"com.miaoshaproject"})

//设置dao存放的地方
@MapperScan("com.miaoshaproject.dao")

//控制层
@RestController
public class App 
{
	@Autowired
	private UserDoMapper userDOMapper;


	
	
	//用户访问时，返回字符串
	@RequestMapping("/")
	public String home() {
		UserDo userdo= userDOMapper.selectByPrimaryKey(1);
		if(userdo==null) {
			return "0";
		}else {
			return "1";
		}
	}
	
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
