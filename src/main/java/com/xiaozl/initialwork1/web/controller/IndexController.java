package com.xiaozl.initialwork1.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaozl.initialwork1.entity.User;
import com.xiaozl.initialwork1.service.UserService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * @author xiaozl
 * @date 2017/11/20
 */
@Controller
@RequestMapping(value = "")
public class IndexController {

    @Resource
    private UserService userService;

    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request)
    {
        //If session have attribute "user", jump to index page, else jump to login page.
        if (request.getSession().getAttribute("user") != null){
            return "index";
        }
        else {
            return "login";
        }
    }

    //登录
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(User user, Model model, HttpServletRequest request) throws Exception{
        try {
            //If pass, set attribute to session, then redirect to index page.
            if (userService.checkLogin(user)) {
                request.getSession().setAttribute("user", user);
                return "index";
            }
            //If not pass, send error attribute.
            else{
                model.addAttribute("login_err", "登录失败!");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
    //注册
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showReguster(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mav=new ModelAndView("register");
        mav.addObject("User",new User());
        return mav;
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request,HttpServletResponse response, @ModelAttribute("user")User user) throws Exception {
        userService.newUser(user);
        return new ModelAndView("success","entity",user.getUserName());

    }
//查询
    @RequestMapping(value = "/manageUser", method = RequestMethod.GET)
    public String getUsers(Model model) {
        try {
            // 查询user表中所有记录
            List<User> userList = userService.listAllUsers();
            // 将所有记录传递给要返回的jsp页面，放在userList当中
            model.addAttribute("userList", userList);
        } catch (Exception e){

        }
        // 返回pages目录下的admin/users.jsp页面
        return "users";
    }

    /**
     * 跳转到添加的页面
     * @return
     */
    @RequestMapping(value="/insert",method = RequestMethod.GET)
    public String toInsert(HttpServletRequest request,HttpServletResponse response,User user)
    {
        if (request.getSession().getAttribute("user") != null){
            return "toAdd";
        }
        else {
            return "users";
        }

    }
    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value="/insert",method = RequestMethod.POST)
    public String insert(User user){
        userService.insert(user);
        return "redirect:/manageUser";
    }
   /**
     * 根据ID查找用户
     * @param id
     * @return
     */


    @RequestMapping(value="/findById")
    public String findById(Model model,int id){
        User user=userService.findById(id);
        model.addAttribute("user", user);

        return "toEdit";
    }

    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteUserById")
    public String delete(int id){
        userService.delete(id);
        return "redirect:/manageUser";
    }



    @RequestMapping(value = {"", "/", "/updateUser"}, method = RequestMethod.GET)
    public String toUpdate(HttpServletRequest request,Model model,User user){
        String username =request.getParameter("userName");
        String password = request.getParameter("password");
        int id = Integer.parseInt (request.getParameter("id"));

        model.addAttribute("userName",username);
        model.addAttribute("password",password);
        model.addAttribute("id",id);
        return "redirect:/findById";
    }

    @RequestMapping(value="/updateUser",method = RequestMethod.POST)
    public String update(User user){
        userService.update(user);
        return "redirect:/manageUser";
    }


@RequestMapping(value="/main",method = RequestMethod.GET)
    public String main(){
        return "fileDown";
    }
}