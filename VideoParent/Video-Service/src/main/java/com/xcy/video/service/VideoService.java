package com.xcy.video.service;


import com.xcy.video.pojo.Video;
import com.xcy.video.pojo.VideoQueryVo;

import java.util.List;

public interface VideoService {
    List<Video> selectVideoList(VideoQueryVo videoQueryVo);

    void batchDeleteVideos(Integer[] ids);

    int getCount(VideoQueryVo videoQueryVo);

    //此处的int类型，我想弄成video在数据库中的id值，而不是影响的行数
    int saveOrUpdate(Video video);

    Video getVideoById(int id);

    List<Video> selectVideoListByCourseId(int id);
}
