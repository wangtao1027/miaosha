package com.imooc.miaosha.service;

import com.alibaba.druid.util.StringUtils;
import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.redis.CheckCodeKey;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    private final static Logger logger = LoggerFactory.getLogger(MiaoshaUserService.class);

    public static final String COOKI_NAME_TOKEN = "token";

    @Resource
    private MiaoshaUserDao miaoshaUserDao;

    @Autowired
    private RedisService redisService;

    public MiaoshaUser getById(Long id) {
        //从缓存中读取
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user != null) {
            return user;
        }
        user = miaoshaUserDao.getById(id);
        if (user != null) {
            redisService.set(MiaoshaUserKey.getById,"" + id,user);
        }
        return user;
    }

    /**
     * 更新密码操作
     * @param token
     * @param id
     * @param formPass
     * @return
     */
    // http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323         //先更新数据库,在删除缓存
    public boolean updatePassword(String token,long id,String formPass) {
        MiaoshaUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //先更新数据库,在删除缓存,不然并发读修改,会造成只能取到旧的缓存
        //更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDbPass(formPass,user.getSalt()));
        miaoshaUserDao.update(toBeUpdate);

        //清除缓存
        redisService.delete(MiaoshaUserKey.getById,"" + id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token, token,user);
        return true;
    }

    //登录方法
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        //校验参数是否为空,参数为空系统异常
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        String checkCode = loginVo.getCheckCode();

        //校验手机号
        MiaoshaUser user = getById(Long.valueOf(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //校验密码
        String dbPassword = user.getPassword();
        String dbSalt = user.getSalt();
        String str = MD5Util.formPassToDbPass(password, dbSalt);
        if (!dbPassword.equals(str)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        //校验短信验证码
        //缓存中取出验证码,对比
        String dbCheckCode = redisService.get(CheckCodeKey.getCheckCode, mobile, String.class);
        if (dbCheckCode == null || !dbCheckCode.equals(checkCode)) {
            throw new GlobalException(CodeMsg.CHECK_CODE_ERROR);
        }

        //登录成功生成token,写入cookie中,传递给客户端,客户端再随后的访问中都在cookie中上传这个token,服务端取到token来得到用户对应的session信息
        String token = UUIDUtil.uuid();
//        redisService.set(MiaoshaUserKey.token,token,user);  //将用户信息存入到缓存中
//        Cookie cookie = new Cookie(COOKI_NAME_TOKEN,token);
//        cookie.setMaxAge(MiaoshaUserKey.tokxlen.expireSeconds()); //如果redis中的数据过期,那么cookie中的数据也过期
//        cookie.setPath("/");
//        response.addCookie(cookie); //写入到response中即可
        addCookie(response,token,user);
        return true;
    }

    //public方法要进行参数校验
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        //key为空,返回为空
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        if (user != null) { //为空不需要做任何处理
            //重新向缓存中设置值,以达到延长有效期的作用
            addCookie(response,token,user);
        }
        return user;
    }

//    public MiaoshaUser getByToken(String token) {
//        //key为空,返回为空
//        if (StringUtils.isEmpty(token)) {
//            return null;
//        }
//        return redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
//    }

    //方法抽取
    public void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user);   //将用户信息存入到缓存中
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds()); //如果redis中数据过期,那么cookie中的数据也过期
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
