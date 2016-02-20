package org.revo.controller.Student

import org.revo.domain.ErrorMessage
import org.revo.domain.Student
import org.revo.service.LoggerService
import org.revo.service.SecurityService
import org.revo.service.StudentService
import org.revo.service.impl.ErrorNumbers
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by ashraf on 12/4/2015.
 */
@RestController
@RequestMapping(value = "/api/student/student")
class S_StudentCtrl {
    private static final Logger logger = LoggerFactory.getLogger(S_StudentCtrl.class);
    @Autowired
    StudentService studentService
    @Autowired
    SecurityService securityService
    @Autowired
    LoggerService loggerService

    @RequestMapping(method = RequestMethod.GET)
    def profile() {
        try {
            Student student = studentService.findOne(securityService.GetRevoUser().id)
            student.pt.each {
                it.ps*.subject*.required = null
            }
            new ResponseEntity<>(student, HttpStatus.OK);
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.S_StudentCtrl1, ignored.message))
        }
    }
}
