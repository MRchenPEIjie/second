package com.xcy.video.controller;


import com.xcy.video.pojo.User;
import com.xcy.video.service.UserService;
import com.xcy.video.utils.ImageCut;
import com.xcy.video.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Value("${IMAGE_DIR}")
    String imageDir;

    @Value("${IMAGE_URL}")
    String imageURL;

    @Autowired
    UserService userService;
    @RequestMapping("/validateEmail")
    @ResponseBody
    public String validateEmail(String email){

        boolean isExist = userService.validateEmail(email);
        //数据库操作
        return isExist?"fail":"success";
    }


    @RequestMapping("/insertUser")
    @ResponseBody
    public String insertUser(User user){
       int result = userService.insertUser(user);
        System.out.println("插入数据后是否可以获取一个值："+result);
       return result > 0 ? "success":"fail";
    }

    @RequestMapping("/loginUser")
    @ResponseBody
    public String loginUser(User user, HttpSession session){
        boolean result = userService.loginUser(user);
        if(result){
            session.setAttribute("userAccount",user.getEmail());
        }
        return result  ? "success":"fail";
    }

    @RequestMapping("/showMyProfile")
    public String showMyInfo(HttpSession session, Model model){
        //此处需要查找数据库的信息，返回给页面
        String email = (String) session.getAttribute("userAccount");
        User user = userService.selectUserByEmail(email);
        System.out.println(imageURL+user.getImgurl());
        user.setImgurl(imageURL+user.getImgurl());
        model.addAttribute("user",user);
        return "before/my_profile";
    }

    @RequestMapping("/changeProfile")
    public String editMyInfo(HttpSession session, Model model){

        String email = (String) session.getAttribute("userAccount");
        User user = userService.selectUserByEmail(email);
        model.addAttribute("user",user);
        return "before/change_profile";
    }


    @RequestMapping("/changeAvatar")
    public String changeImage(HttpSession session, Model model){

        String email = (String) session.getAttribute("userAccount");
        User user = userService.selectUserByEmail(email);
        System.out.println(imageURL+user.getImgurl());
        user.setImgurl(imageURL+user.getImgurl());
        model.addAttribute("user",user);

        return "before/change_avatar";
    }

    @RequestMapping("/passwordSafe")
    public String passwordSafe(HttpSession session, Model model){


        return "before/password_safe";
    }

    @RequestMapping("/updateUser")
    public String updateUser(User user){
        userService.updateUserById(user);
       return "redirect:/user/showMyProfile";
    }

    /**
     * 1、导入两个包  fileupload,io
     * 2、页面上要有三元素   multipart/form-data   post   type="file"
     * 3、一定要配置文件解析器  springmvc中
     * 4、type=file中的name值，要和controller中，MultipartFile image_file 照应
     */
    @RequestMapping("/upLoadImage")
    public String upLoadImage(HttpServletRequest request, MultipartFile image_file,HttpSession session) throws IOException {

        String x1 = request.getParameter("x1");
        String x2 = request.getParameter("x2");
        String y1 = request.getParameter("y1");
        String y2 = request.getParameter("y2");



        System.out.println(x1+"+++++++++");
        String oldFilename = image_file.getOriginalFilename();
        System.out.println(oldFilename);

        //只是为得到一个新的名字
        String suffixName = oldFilename.substring(oldFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString().replace("-","")+suffixName;

        //为了将图片进行归类，我们可以以时间的形式进行文件夹的创建
        Date date =new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dirName = dateFormat.format(date);
        String targetName = imageDir+dirName;
        File file = new File(targetName);
        if(!file.exists()){
            file.mkdirs();
        }
        System.out.println(targetName);
        System.out.println(newFileName);
        image_file.transferTo(new File(targetName,newFileName));

        //图片切割
        float x1Val = 0,x2Val = 0,y1Val =0,y2Val = 0,width=0,height=0;

        if(null != x1 && !x1.equals("")){
            x1Val = Float.parseFloat(x1);
            y1Val = Float.parseFloat(y1);
            x2Val = Float.parseFloat(x2);
            y2Val = Float.parseFloat(y2);
            width = x2Val-x1Val;
            height = y2Val -y1Val;
            ImageCut imageCut =new ImageCut();
            //System.out.println(targetName+newFileName);
            String imagePath = targetName+File.separator+newFileName;
            System.out.println(imagePath);
            imageCut.cutImage(imagePath,(int)x1Val,(int)y1Val,(int)width,(int)height);

        }


        String email = (String) session.getAttribute("userAccount");
        //保存到数据库
        userService.updateUserImageByEmail(dirName+"/"+newFileName,email);

        return "redirect:/user/showMyProfile";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword(){
        return "before/forget_password";
    }


    @RequestMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email){
        //发送邮件
        String validateCode = MailUtils.getValidateCode(6);
        MailUtils.sendMail(email,"您好:<br/>您本次的验证码是"+validateCode+",请于两小时内输入，否则失效。","Y先生学习网忘记密码验证码邮件");
        //保存验证到数据库，用于校验
        User user =new User();
        user.setEmail(email);
        user.setCode(validateCode);
        userService.updateUserValidateCodeByEmail(user);
        return "success";
    }

    @RequestMapping("/validateEmailCode")
    public String validateEmailCode(User user,Model model){
        boolean result = userService.validateEmailCode(user);
        if(result){
            model.addAttribute("email",user.getEmail());
            return "before/reset_password";
        }else{
            return "before/forget_password";
        }
    }


}
