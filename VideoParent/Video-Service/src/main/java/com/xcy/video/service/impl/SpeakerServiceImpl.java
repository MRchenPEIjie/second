package com.xcy.video.service.impl;


import com.github.pagehelper.PageHelper;
import com.xcy.video.mapper.SpeakerMapper;
import com.xcy.video.pojo.Speaker;
import com.xcy.video.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeakerServiceImpl implements SpeakerService {

    @Autowired
    SpeakerMapper speakerMapper;

    public List<Speaker> selectAllSpeaker() {

        List<Speaker> speakerList = speakerMapper.selectAllSpeaker();
        return speakerList;
    }


    public List<Speaker> selectAllSpeaker(int page,int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Speaker> speakerList = speakerMapper.selectAllSpeaker();
        return speakerList;
    }
}
