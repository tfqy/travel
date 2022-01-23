package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * @author tfqy
 */

public interface RouteImgDao {
    List<RouteImg> findByRid(int rid);
}
