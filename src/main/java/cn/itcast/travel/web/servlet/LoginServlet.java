package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author tfqy
 */

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        UserService service = new UserServiceImpl();
        User loginUser = service.login(user);

        ResultInfo info = new ResultInfo();

        //登录验证
        if (loginUser == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }

        //验证是否激活
        if (loginUser != null && !"Y".equals(loginUser.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活");
        }

        //登录成功
        if (loginUser != null && "Y".equals(loginUser.getStatus())) {
            //登录成功标记
            request.getSession().setAttribute("user", loginUser);
            //登录成功
            info.setFlag(true);
        }

        //设置编码
        request.setCharacterEncoding("utf8");

        //获取用户填写验证码
        String check = request.getParameter("check");

        //验证码校验
        HttpSession session = request.getSession();
        String checkcodeServer = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (loginUser != null && "Y".equals(loginUser.getStatus()) && !checkcodeServer.equalsIgnoreCase(check)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误，请重新输入");
        }

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=uft8");
        mapper.writeValue(response.getOutputStream(), info);

    }
}
