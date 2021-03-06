package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author tfqy
 */

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     *
     * @param cid
     * @return
     */
    int findTotalCount(int cid, String rname);

    /**
     * 根据cid，start，pageSize查询当前页的数据集合
     *
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     * 查询路线详细信息
     *
     * @param rid
     * @return
     */
    Route findOne(int rid);
}
