package com.xcy.video.controller;


import com.github.pagehelper.PageInfo;
import com.xcy.video.pojo.Speaker;
import com.xcy.video.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/speaker")
public class SpeakerController {

    @Autowired
    SpeakerService speakerService;
    @RequestMapping("/list")
    public String list(Model model, @RequestParam(name = "page",required = false,defaultValue="1") int page, @RequestParam(name="pageSize",required = false,defaultValue="5")int pageSize){
        List<Speaker> speakerList = speakerService.selectAllSpeaker(page,pageSize);
        //model.addAttribute("speakerList",speakerList);

        PageInfo<Speaker> pageInfo =new PageInfo<Speaker>(speakerList);

        model.addAttribute("pageInfo",pageInfo);
        return "behind/speakerList";
    }

}
