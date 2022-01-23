package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author tfqy
 */
public interface CategoryService {

    /**
     * 查询所有
     *
     * @return
     */
    List<Category> findAll();
}
