package com.base.common.environment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app")
@Component
public class ApplicationEnvironmentProperties {

    private final Env env = new Env();

    public Env getEnv() {
        return env;
    }

    @Getter
    @Setter
    public static class Env {

        private String label;


    }
}
