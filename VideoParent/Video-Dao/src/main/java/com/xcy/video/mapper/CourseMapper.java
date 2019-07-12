package com.xcy.video.mapper;


import com.xcy.video.pojo.Course;

import java.util.List;

public interface CourseMapper {
    List<Course> selectCourseList();

    List<Course> selectCouseBySubjectId(int subjectId);

    Course selectCourseById(int courseId);
}
