package com.soho.inko.database.repository;

import com.soho.inko.database.entity.EnvelopeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
public interface EnvelopeRepository extends JpaRepository<EnvelopeEntity, String> {
    /**
     * 根据OrderId，获取所有Envelope独享
     *
     * @param orderId orderId
     * @return 此order下的所有envelope
     */
    public List<EnvelopeEntity> findAllByOrderId(String orderId);

    /**
     * 根据OrderId，删除所有的Envelope
     *
     * @param orderId ordeId
     */
    @Modifying
    @Transactional()
    @Query("delete FROM EnvelopeEntity WHERE orderId=?1")
    public void deleteByOrderId(String orderId);
}
