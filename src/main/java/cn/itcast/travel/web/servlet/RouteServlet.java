package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author tfqy
 */

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private final RouteService service = new RouteServiceImpl();
    private final FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        //处理参数
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }

        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }

        PageBean<Route> pb = service.pageQuery(cid, currentPage, pageSize, rname);

        writeValue(pb, response);
    }

    /**
     * 根据id查询一个旅游线路的详细信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = service.findOne(rid);
        writeValue(route, response);
    }

    /**
     * 判断登录用户是否收藏过该路线
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取路线id
        String rid = request.getParameter("rid");
        //获取登录用户
        User user = (User) request.getSession().getAttribute("user");
        //用户id
        int uid;
        if (user == null) {
            //用户未登录
            uid = 0;
        } else {
            //用户已经登录
            uid = user.getUid();
        }

        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag, response);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取路线id
        String rid = request.getParameter("rid");
        //获取登录用户
        User user = (User) request.getSession().getAttribute("user");
        //用户id
        int uid;
        if (user == null) {
            //用户未登录
            return;
        } else {
            //用户已经登录
            uid = user.getUid();
        }

        favoriteService.add(rid, uid);
    }

    public void showFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取登录用户
        User user = (User) request.getSession().getAttribute("user");
        //用户id
        int uid;
        if (user == null) {
            //用户未登录
            return;
        } else {
            //用户已经登录
            uid = user.getUid();
        }
        List list = favoriteService.show(uid);
        Route route = null;
        for (Object rid : list) {
            route = service.findOne(String.valueOf(rid));
            writeValue(route, response);
        }
    }
}
