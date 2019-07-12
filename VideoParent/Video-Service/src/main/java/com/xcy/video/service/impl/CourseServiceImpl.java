package com.xcy.video.service.impl;


import com.xcy.video.mapper.CourseMapper;
import com.xcy.video.pojo.Course;
import com.xcy.video.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    public List<Course> selectAllCourse() {

        List<Course> courseList = courseMapper.selectCourseList();

        return courseList;
    }

    public List<Course> selectCouseBySubjectId(int subjectId) {
        return courseMapper.selectCouseBySubjectId(subjectId);
    }

    public Course selectCourseById(int courseId) {
        return courseMapper.selectCourseById(courseId);
    }
}
