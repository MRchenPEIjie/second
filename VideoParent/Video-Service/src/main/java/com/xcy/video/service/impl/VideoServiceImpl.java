package com.xcy.video.service.impl;


import com.xcy.video.mapper.VideoMapper;
import com.xcy.video.pojo.Video;
import com.xcy.video.pojo.VideoQueryVo;
import com.xcy.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;


    public List<Video> selectVideoList(VideoQueryVo videoQueryVo) {
        //videoQueryVo.setTitle("%"+videoQueryVo.getTitle()+"%");
        List<Video> videos = videoMapper.selectVideoList(videoQueryVo);
        return videos;
    }


    public void batchDeleteVideos(Integer[] ids) {

        VideoQueryVo videoQueryVo =new VideoQueryVo();
        List<Integer> integers = Arrays.asList(ids);
        videoQueryVo.setIdList(integers);

        videoMapper.batchDeleteVideos(videoQueryVo);

    }


    public int getCount(VideoQueryVo videoQueryVo) {

        int  result = videoMapper.getCount(videoQueryVo);
        return result ;
    }


    public int saveOrUpdate(Video video) {

        int id = 0;
        if(video.getId() != 0){
            videoMapper.updateVideoById(video);
            id =  video.getId();
        }else{
           videoMapper.saveVideo(video);



           id =  video.getId();
        }

        return id;

    }


    public Video getVideoById(int id) {

        return videoMapper.getVideoById(id);
    }

    public List<Video> selectVideoListByCourseId(int id) {
        return videoMapper.selectVideoListByCourseId(id);
    }
}
