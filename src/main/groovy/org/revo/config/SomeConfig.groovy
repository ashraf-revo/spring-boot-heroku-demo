package org.revo.config

import org.revo.repository.AdminRepository
import org.revo.service.util.SomeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

/**
 * Created by ashraf on 2/17/2016.
 */
@Configuration
class SomeConfig {
    @Autowired
    SomeService service
    @Autowired
    AdminRepository adminRepository

    @Profile("init")
    @Bean
    CommandLineRunner initData() {
        { args ->
            if (adminRepository.count() == 0) service.init()
        }
    }
}
