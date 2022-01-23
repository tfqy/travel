package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * @author tfqy
 */

public interface UserDao {

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 用户保存
     *
     * @param user
     */
    void save(User user);

    /**
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码进行查找用户
     *
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);
}
