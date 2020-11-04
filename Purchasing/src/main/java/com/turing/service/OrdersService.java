package com.turing.service;

import com.turing.entity.EasyUIDataGrid;
import com.turing.entity.Orders;

import java.util.List;


public interface OrdersService {

    /**
     * 添加一条需求
     * @param orders
     * @return
     */
    public int addOrders(Orders orders);

    /**
     * 分页模糊查询
     * @param materialCode 编码
     * @param materialName 名称
     * @param status 状态
     * @param cusPage 当前页码
     * @param pageSize 每页显示条数
     * @return
     */
    public EasyUIDataGrid findOrdersByEasy(String materialCode, String materialName, String status, Integer cusPage, Integer pageSize);


    /**
     * 查看详情
     * @param id 通过id查询
     * @return
     */
    public Orders findOrderById(Integer id);


    /**
     * 修改信息
     * @param orders 对象
     * @return
     */
    public int updateOrderById(Orders orders);


    /**
     * 删除信息
     * @param id
     * @return
     */
    public int deleteOrderById(Integer id);

}
