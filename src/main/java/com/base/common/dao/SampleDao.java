package com.base.common.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class SampleDao {

    @Autowired
    @Resource(name = "H2SqlSessionTemplate" )
    SqlSessionTemplate sqlSessionTemplate;

    public String getSample(){
       return sqlSessionTemplate.selectOne("Sample.selectSample");
    }
}
