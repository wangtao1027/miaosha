package com.imooc.miaosha.validator;
import com.imooc.miaosha.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//校验器类
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> { //两个参数，一直是注解，一个是注解修饰的字段的类型

	//定义一个成员变量来接受注解中的参数
	private boolean required = false;		//参数为空时设置默认值为空，定义一个参数接受注解里面的值
	
	public void initialize(IsMobile constraintAnnotation) {	//初始化方法
		required = constraintAnnotation.required();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {	//如果是必须的，就进行正则判断
			return ValidatorUtil.isMobile(value);
		}else {	//如果不是必须的我们就判断有没有值
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
