package com.jnshu.sildenafil.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jnshu.sildenafil.common.exception.ServiceException;
import com.jnshu.sildenafil.system.domain.CollectionAsset;
import com.jnshu.sildenafil.system.mapper.CollectionAssetDao;
import com.jnshu.sildenafil.system.service.CollectionAssetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lihoo
 * @since 2018-11-11
 */
@Service
@Slf4j
public class CollectionAssetServiceImpl extends ServiceImpl<CollectionAssetDao, CollectionAsset> implements CollectionAssetService {
    private static final long NOW = System.currentTimeMillis();

    private final CollectionAssetDao collectionAssetDao;

    @Autowired
    public CollectionAssetServiceImpl(CollectionAssetDao collectionAssetDao) {
        this.collectionAssetDao = collectionAssetDao;
    }

    /**前台对资料进行收藏
     * @param type 资料类型
     * @param typeId 资料id
     * @param studentId 学生id
     * @return 返回资料id
     */
    @Override
    public Long insertCollection(Integer type, Long typeId, Long studentId) throws ServiceException{
        log.info("args for insertCollection is : type={}&typeId={}&studentId={}&",type,typeId,studentId);
        if(null==type || null==typeId || null==studentId){
            log.error("result for insertCollection error;args null");
            throw new ServiceException("insertCollection error;args null");
        }
        QueryWrapper<CollectionAsset> collectionAssetQueryWrapper = new QueryWrapper<>();
        collectionAssetQueryWrapper
                .eq("type_id", typeId)
                .eq("student_id", studentId)
                .eq("type", type);
        collectionAssetDao.delete(collectionAssetQueryWrapper);
        CollectionAsset newColl = new CollectionAsset();
        newColl.setCreateAt(NOW);
        newColl.setUpdateAt(NOW);
        newColl.setType(type);
        newColl.setTypeId(typeId);
        newColl.setStudentId(studentId);
        int result = collectionAssetDao.insert(newColl) ;
        if(result==0){
            log.warn("result for insertCollection error; insert fail");
            return null;
        }
        log.info("result for insertCollection success; result detail: typeId={}", typeId);
        return typeId;
    }

    /**前台取消资料的收藏状态
     * @param type      资料类型
     * @param typeId    资料id
     * @param studentId 学生id
     * @return 返回资料id
     */
    @Override
    public Long removeCollection(Integer type, Long typeId, Long studentId) throws ServiceException{
        log.info("args for removeCollection is : type={}&typeId={}&studentId={}&", type, typeId, studentId);
        if(null==type || null==typeId || null==studentId){
            log.error("result for removeCollection error;args null");
            throw new ServiceException("removeCollection error;args null");
        }
        QueryWrapper<CollectionAsset> collectionAssetQueryWrapper = new QueryWrapper<>();
        collectionAssetQueryWrapper
                .eq("type_id", typeId)
                .eq("student_id", studentId)
                .eq("type", type);
        int result = collectionAssetDao.delete(collectionAssetQueryWrapper);
        if(result==0){
            log.warn("result for removeCollection error; delete fail or asset not collection;");
            return null;
        }
        log.info("result for removeCollection success; result detail: typeId={}", typeId);
        return typeId;
    }

    /**前台查询资料的收藏状态
     * @param type 资料类型
     * @param typeId 资料id
     * @param studentId 学生id
     * @return 0为未收藏，1为收藏
     */
    @Override
    public int selectCollection(Integer type, Long typeId, Long studentId) throws ServiceException{
        log.info("args for selectCollection is : type={}&typeId={}&studentId={}&",type,typeId,studentId);
        if(null==type || null==typeId || null==studentId){
            log.error("result for selectCollection error;args null");
            throw new ServiceException("selectCollection error;args null");
        }
        QueryWrapper<CollectionAsset> collectionAssetQueryWrapper = new QueryWrapper<>();
        collectionAssetQueryWrapper
                .eq("type_id", typeId)
                .eq("student_id", studentId)
                .eq("type", type);
        CollectionAsset collAsset = collectionAssetDao.selectOne(collectionAssetQueryWrapper);
        if (collAsset != null) {
            log.info("result for selectCollection success:you collected");
            return 1;
        } else {
            log.info("result for selectCollection success:not collect");
            return 0;
        }
    }
}
