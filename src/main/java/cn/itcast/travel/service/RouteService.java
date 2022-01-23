package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @author tfqy
 */

public interface RouteService {
    /**
     * 根据类别进行分页查询
     *
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 查找路线
     *
     * @param rid
     * @return
     */
    Route findOne(String rid);

    /**
     * 查找用户收藏路线
     *
     * @param uid
     * @param currentPage
     *
     * @param pageSize
     * @return
     */
    PageBean<Route> favoritePageQuery(int uid, int currentPage, int pageSize);
}
