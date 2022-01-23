package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @author tfqy
 */

public interface SellerDao {
    /**
     * 根据id查询商家
     *
     * @param sid
     * @return
     */
    Seller findById(int sid);
}
