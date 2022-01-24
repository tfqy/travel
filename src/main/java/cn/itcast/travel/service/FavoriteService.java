package cn.itcast.travel.service;

import java.util.List;

/**
 * @author tfqy
 */

public interface FavoriteService {
    /**
     * 用户是否收藏
     *
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(String rid, int uid);

    /**
     * 添加收藏
     *
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);

    /**
     * 展示用户收藏路线
     *
     * @param uid
     * @return
     */
//    List show(int uid);
}
