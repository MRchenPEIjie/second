package com.xcy.video.service;


import com.xcy.video.pojo.Speaker;

import java.util.List;

public interface SpeakerService {

    List<Speaker> selectAllSpeaker();
    List<Speaker> selectAllSpeaker(int page, int pageSize);
}
