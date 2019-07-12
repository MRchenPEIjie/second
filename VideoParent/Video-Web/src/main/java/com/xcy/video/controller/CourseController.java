package com.xcy.video.controller;

import com.xcy.video.pojo.Course;
import com.xcy.video.pojo.Subject;
import com.xcy.video.pojo.Video;
import com.xcy.video.service.CourseService;
import com.xcy.video.service.SubjectService;
import com.xcy.video.service.VideoService;
import com.xcy.video.utils.JedisClient;
import com.xcy.video.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    VideoService videoService;

   /* @Autowired
    JedisPool jedisPool;*/
    @Autowired
    JedisClient jedisClient;

    @RequestMapping("/list")

    public String showList(int subjectId, Model model){
        //subjectId学科id
        /*
           思路：
             1、根据学科ID，查找学科信息
             2、根据学科ID，查找该学科下所有的课程
             3、再根据每一个课程的id，查找所有视频，封装
             4、将封装好的学科信息，发送给页面
         */

        // Web层添加一个逻辑，给我一个subjectId,我从数据库中查找数据，并放入到缓存中
        // 第二次的时候，直接从缓存中取值
        //Jedis jedis = jedisPool.getResource();
        String subjectJson= jedisClient.get("SUBJECT:"+subjectId);
        Subject subject = null;
        if(null == subjectJson || subjectJson.equals("")){
            subject= subjectService.selectSubjectById(subjectId);



            //将数据转换为json，放入到redis数据库中
            String jsonData = JsonUtils.objectToJson(subject);
            jedisClient.set("SUBJECT:"+subjectId,jsonData);

            System.out.println("此数据是从数据库中获取"+jsonData);
        }else{
            //将json数据转换为对象
            subject = JsonUtils.jsonToPojo(subjectJson,Subject.class);
            System.out.println("此数据是从缓存中来，subject:"+subject);
        }

        // 接下来开始查找course
        String courseJson= jedisClient.get("SUBJECT_ID:"+subjectId);
        List<Course> courseList = null;
        if(null == courseJson || courseJson.equals("")){
            courseList = courseService.selectCouseBySubjectId(subjectId);
            //将数据转换为json，放入到redis数据库中
            String jsonData = JsonUtils.objectToJson(courseList);
            jedisClient.set("SUBJECT_ID:"+subjectId,jsonData);

            System.out.println("此数据是从数据库中获取"+jsonData);
        }else{
            //将json数据转换为对象
            courseList = JsonUtils.jsonToList(courseJson,Course.class);
            System.out.println("此数据是从缓存中来，courseList:"+courseList);
        }
        subject.setCourseList(courseList);
        // course list<Video>
        //再从redis中查找course数据
        for (Course course:courseList) {
            String jsonList = jedisClient.get("COURSE_ID:"+course.getId());
            List<Video> videoList=null;
            if(null == jsonList || jsonList.equals("")){
                videoList = videoService.selectVideoListByCourseId(course.getId());
                jedisClient.set("COURSE_ID:"+course.getId(),JsonUtils.objectToJson(videoList));
            }else{
                videoList  = JsonUtils.jsonToList(jsonList,Video.class);
            }

            course.setVideoList(videoList);
        }

        model.addAttribute("subject",subject);
        return "before/course";
    }
}
