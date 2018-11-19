package com.jnshu.sildenafil.system.service;

import com.jnshu.sildenafil.common.exception.ServiceException;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.jnshu.sildenafil.system.domain.CollectionAsset;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lihoo
 * @since 2018-11-11
 */
public interface CollectionAssetService extends IService<CollectionAsset> {

    /**前台对资料进行收藏
     * @param type 资料类型
     * @param typeId 资料id
     * @param studentId 学生id
     * @return 返回收藏的记录id
     * @throws ServiceException 自定义异常,参数异常
     */
    Long insertCollection(Integer type,Long typeId,Long studentId)throws ServiceException;

    /**前台取消资料的收藏状态
     * @param type      资料类型
     * @param typeId    资料id
     * @param studentId 学生id
     * @return 返回资料id
     * @throws ServiceException 自定义异常,参数异常
     */
    Long removeCollection(Integer type, Long typeId, Long studentId)throws ServiceException;

    /**前台查询资料的收藏状态
     * @param type 资料类型
     * @param typeId 资料id
     * @param studentId 学生id
     * @throws ServiceException 自定义异常,参数异常
     * @return 0为未收藏，1为收藏
     */
    int selectCollection(Integer type,Long typeId,Long studentId)throws ServiceException;

    /**
     * 查询学生收藏列表
     * @param [ type, studentId]
     * @return  com.baomidou.mybatisplus.core.metadata.IPage<com.jnshu.sildenafil.system.domain.CollectionAsset>
     */
    List<Long> collectiongListByStudent(Integer type, Long studentId);

}
