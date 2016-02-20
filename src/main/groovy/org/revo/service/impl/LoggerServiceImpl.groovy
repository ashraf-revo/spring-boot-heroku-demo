package org.revo.service.impl

import org.revo.domain.ErrorMessage
import org.revo.service.LoggerService
import org.revo.service.SecurityService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * Created by revo on 23/01/16.
 */
@Service
class LoggerServiceImpl implements LoggerService {
    @Autowired
    SecurityService securityService

    @Override
    def Error(Logger logger, ErrorMessage em) {
        em.users(securityService.GetRevoUser())
        logger.warn(em.users(securityService.GetRevoUser()).toString())
        new ResponseEntity<>(em, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
