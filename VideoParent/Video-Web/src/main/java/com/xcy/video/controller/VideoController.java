package com.xcy.video.controller;


import com.xcy.video.pojo.Course;
import com.xcy.video.pojo.Speaker;
import com.xcy.video.pojo.Video;
import com.xcy.video.pojo.VideoQueryVo;
import com.xcy.video.service.CourseService;
import com.xcy.video.service.SpeakerService;
import com.xcy.video.service.VideoService;
import com.xcy.video.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;


/*
    Ctrl + N 可以通过类名查找源代码，包括jar包中的代码
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    VideoService videoService ;
    @Autowired
    SpeakerService speakerService;
    @Autowired
    CourseService courseService ;



    @RequestMapping("/list")
    public String list(Model model, VideoQueryVo videoQueryVo){

        //当前页数  每页多少条   计算begin 怎么算
        videoQueryVo.setBegin((videoQueryVo.getPage()-1)*videoQueryVo.getRows());
        List<Video> videos = videoService.selectVideoList(videoQueryVo);

       // model.addAttribute("list",videos);

        List<Speaker> speakerList = speakerService.selectAllSpeaker();
        model.addAttribute("speakerList",speakerList);

        List<Course> courseList = courseService.selectAllCourse();
        model.addAttribute("courseList",courseList);

        Page<Video> page =new Page<Video>();
        page.setPage(videoQueryVo.getPage());
        page.setSize(videoQueryVo.getRows());
        page.setTotal(videoService.getCount(videoQueryVo));
        page.setRows(videos);
        model.addAttribute("page",page);
        return "/behind/videoList";
    }
    //  ids
    @RequestMapping("/delBatchVideos")
    public String delBatchVideos(Integer[] ids/*,HttpServletRequest request*/){
        //request.getParamersValues("ids");
        System.out.println(Arrays.toString(ids));

        videoService.batchDeleteVideos(ids);
        return "redirect:/video/list";
    }

    @RequestMapping("/addVideo")
    public String showAddVideo(Model model){

        List<Speaker> speakerList = speakerService.selectAllSpeaker();
        model.addAttribute("speakerList",speakerList);

        List<Course> courseList = courseService.selectAllCourse();
        model.addAttribute("courseList",courseList);

        return "behind/addVideo";
    }

    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Video video){
        System.out.println(video);
        System.out.println("保存之前："+video.getId());
        videoService.saveOrUpdate(video);
        System.out.println("保存之后："+video.getId());
        return "redirect:/video/list";
    }

    @RequestMapping("/edit")
    public String showEdit(int id,Model model){
        Video video = videoService.getVideoById(id);
        model.addAttribute("video",video);
        List<Speaker> speakerList = speakerService.selectAllSpeaker();
        model.addAttribute("speakerList",speakerList);
        List<Course> courseList = courseService.selectAllCourse();
        model.addAttribute("courseList",courseList);
        return "behind/addVideo";
    }

    @RequestMapping("/showVideo")
    public String showVideo(int videoId,String subjectName,Model model){

        //根据视频ID查找该视频的详情
        // 关联视频的主讲人信息
        Video video = videoService.getVideoById(videoId);
        System.out.println(video);
        // 通过视频 获取该视频属于哪个课程，根据课程id查找课程信息
        Course course = courseService.selectCourseById(video.getCourseId());
        //获取和该视频是同一个课程的所有视频
        model.addAttribute(video);
        model.addAttribute(course);
        model.addAttribute("subjectName",subjectName);
        return "before/section";
    }
}
