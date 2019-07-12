package com.xcy.video.mapper;


import com.xcy.video.pojo.Video;
import com.xcy.video.pojo.VideoQueryVo;

import java.util.List;

public interface VideoMapper {
    List<Video> selectVideoList(VideoQueryVo videoQueryVo);

    void batchDeleteVideos(VideoQueryVo videoQueryVo);

    int getCount(VideoQueryVo videoQueryVo);

    void saveVideo(Video video);

    Video getVideoById(int id);

    void updateVideoById(Video video);

    List<Video> selectVideoListByCourseId(int id);
}
