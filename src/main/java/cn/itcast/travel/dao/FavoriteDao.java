package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

import java.util.List;

/**
 * @author tfqy
 */

public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     *
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据线路id查询收藏次数
     *
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏
     *
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);

    /**
     * 展示用户收藏
     *
     * @param uid
     * @return
     */
    List show(int uid);
}
