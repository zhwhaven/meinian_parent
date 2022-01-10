package com.haven.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.haven.constant.MessageConstant;
import com.haven.constant.RedisMessageConstant;
import com.haven.dao.MemberDao;
import com.haven.dao.OrderDao;
import com.haven.dao.OrderSettingDao;
import com.haven.entry.Result;
import com.haven.pojo.Member;
import com.haven.pojo.Order;
import com.haven.pojo.OrderSetting;
import com.haven.service.OrderService;
import com.haven.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderSettingDao orderSettingDao;
    @Autowired
    MemberDao memberDao;
     @Autowired
     OrderDao orderDao;
    @Autowired
     JedisPool jedisPool;
    @Override
    public Result submit(Map map) {

        try {
            String telephone = (String) map.get("telephone");
            System.out.println(RedisMessageConstant.SENDTYPE_ORDER);
            System.out.println(telephone);
            String validateCode = (String) map.get("validateCode");
            String refirmCode = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER+telephone);
            System.out.println("jedis 得到的"+refirmCode);
            if(refirmCode==null){
                return new Result(false,"验证码错误");
            }else if(!refirmCode.equals(validateCode)){
                return new Result(false,"验证码错误");
            }else{}
            String idCard = (String)map.get("idCard");
            String name = (String)map.get("name");
            Date orderDate = DateUtils.parseString2Date((String)map.get("orderDate"));
            Integer setmealId = Integer.parseInt((String)map.get("setmealId"));
            String sex = (String) map.get("sex");

//            判断当前日期能否预约，查询orderSetting表
            OrderSetting orderSetting = orderSettingDao.findAllByDate(orderDate);
            if(orderSetting==null){
                return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
            }else {//            判断已预约人数是否已满
                if(orderSetting.getNumber()==orderSetting.getReservations()){
                    return new Result(false,"该日期预约的预约人数已满");
                }else{
                    //            查询member表，看是否是会员
                    Member member=memberDao.findByTelephone(telephone);
                    if(member==null){
                        //            不是则自动注册
                        member=new Member();
                        member.setIdCard(idCard);
                        member.setName(name);
                        member.setSex(sex);
                        member.setPhoneNumber(telephone);
                        member.setRegTime(new Date());
                        memberDao.add(member);
                    }else{
                         Order order1=new Order();
                         order1.setSetmealId(setmealId);
                         order1.setOrderDate(orderDate);
                         order1.setMemberId(member.getId());
                        Order order = orderDao.findByMessage(order1);
                        if(order!=null){
                            return new Result(false,"已有预约");
                        }
                    }

                    try {
                        //            向order表中插入数据


                        Order order=new Order();
                        order.setMemberId(member.getId());
                        order.setOrderDate(orderDate);
                        order.setOrderType("微信预约");
                        order.setOrderStatus("已预约");
                        order.setSetmealId(setmealId);
                        orderDao.add(order);
                        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);

                    } catch (Exception e) {
                        e.printStackTrace();
                        return new Result(true,MessageConstant.ORDERSETTING_FAIL);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"参数异常");
        }

    }
}
