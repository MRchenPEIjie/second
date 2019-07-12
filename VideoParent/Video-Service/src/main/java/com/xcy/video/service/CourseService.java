package com.xcy.video.service;


import com.xcy.video.pojo.Course;

import java.util.List;

public interface CourseService {
    List<Course> selectAllCourse();

    List<Course> selectCouseBySubjectId(int subjectId);

    Course selectCourseById(int courseId);
}
