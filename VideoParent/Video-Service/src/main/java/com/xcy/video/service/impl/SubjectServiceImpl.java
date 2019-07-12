package com.xcy.video.service.impl;

import com.xcy.video.mapper.SubjectMapper;
import com.xcy.video.pojo.Subject;
import com.xcy.video.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectMapper subjectMapper;

    public Subject selectSubjectById(int subjectId) {
        return subjectMapper.selectSubjectById(subjectId);
    }
}
