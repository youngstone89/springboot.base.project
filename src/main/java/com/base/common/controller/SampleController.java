package com.base.common.controller;

import com.base.common.environment.ApplicationEnvironmentProperties;
import com.base.common.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {


    @Autowired
    SampleService sampleService;

    private final ApplicationEnvironmentProperties props;

    public SampleController(ApplicationEnvironmentProperties props) {
        this.props = props;
    }

    @GetMapping(value = "/sample")
    public String getSample(){

        return sampleService.getSample();

    }

    @GetMapping(value = "/")
    public String get(){

        return "Healthy";

    }

    @GetMapping(value = "/label")
    public String getEnv(){

        return  props.getEnv().getLabel();

    }

}
