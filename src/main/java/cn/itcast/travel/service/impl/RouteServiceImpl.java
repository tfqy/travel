package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tfqy
 */

public class RouteServiceImpl implements RouteService {
    private final RouteDao routeDao = new RouteDaoImpl();
    private final RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private final SellerDao sellerDao = new SellerDaoImpl();
    private final FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        //设置每页显示条数
        int totalCount = routeDao.findTotalCount(cid, rname);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        pb.setList(list);
        //设置总页数
        int totalPage = (totalCount % pageSize) == 0 ? (totalCount / pageSize) : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public PageBean<Route> favoritePageQuery(int uid, int currentPage, int pageSize) {
        // 创建PageBean<Route>对象
        PageBean<Route> routePageBean = new PageBean<>();
        // 查询总记录数totalCount
        int totalCount = favoriteDao.findCountByUid(uid);
        // 没有查到收藏记录
        if (totalCount == 0) {
            return routePageBean;
        }
        // 计算起始记录数start，计算总页数totalPage
        int start = (currentPage - 1) * pageSize;
        int totalPage = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);
        // 分页查询rid列表（封装在Route类中）
        List<Route> pageFavoriteList = favoriteDao.findByUidAndPage(uid, start, pageSize);
        // 创新一个空的List<Route>集合
        List<Route> routeList = new ArrayList<>();
        // 遍历pageFavoriteList组装favoriteRoute
        for (Route favoriteRoute : pageFavoriteList) {
            // 根据其rid属性利用routeDao查route对象
            Route route = routeDao.findOne(favoriteRoute.getRid());
            // 向routeList中追加route属性
            routeList.add(route);
        }

        // 并组装PageBean<Route>对象
        routePageBean.setCurrentPage(currentPage);     // 设置当前页码
        routePageBean.setPageSize(pageSize);           // 设置每页显示条数
        routePageBean.setTotalPage(totalPage);         // 设置总页数
        routePageBean.setTotalCount(totalCount);       // 查询并设置总记录数
        routePageBean.setList(routeList);
        return routePageBean;
    }

    @Override
    public Route findOne(String rid) {
        Route route = routeDao.findOne(Integer.parseInt(rid));

        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(routeImgList);

        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);

        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
