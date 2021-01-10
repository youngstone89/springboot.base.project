package com.base.common.service;

import com.base.common.dao.SampleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    @Autowired
    SampleDao sampleDao;
    public String getSample(){
        return sampleDao.getSample();
    }
}
