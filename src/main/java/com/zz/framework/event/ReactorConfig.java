package com.zz.framework.event;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.spring.context.config.EnableReactor;

import static reactor.core.Environment.RING_BUFFER;


@Configuration
@EnableReactor
public class ReactorConfig{

    @Bean(name = "rootReactor")
    public Reactor rootReactor(Environment env) {
        return Reactors.reactor().env(env).dispatcher(RING_BUFFER).get();

    }
}
