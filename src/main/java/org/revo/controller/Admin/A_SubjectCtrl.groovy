package org.revo.controller.Admin

import org.revo.domain.ErrorMessage
import org.revo.domain.Subject
import org.revo.service.LoggerService
import org.revo.service.SubjectService
import org.revo.service.impl.ErrorNumbers
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by revo on 06/01/16.
 */
@RestController
@RequestMapping(value = "/api/admin/subject")
class A_SubjectCtrl {
    private static final Logger logger = LoggerFactory.getLogger(A_SubjectCtrl.class);
    @Autowired
    SubjectService subjectService
    @Autowired
    LoggerService loggerService

    @RequestMapping(method = RequestMethod.GET)
    def findAll() {
        try {
            new ResponseEntity<>(subjectService.findAll(), HttpStatus.OK)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_SubjectCtrl1, ignored.message))
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    def findOne(@PathVariable("id") Long id) {
        try {
            Subject one = subjectService.findOne(id)
            one.required*.required = null
            new ResponseEntity<>(one, HttpStatus.OK)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_SubjectCtrl2, ignored.message))
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    def save(@RequestBody Subject subject) {
        try {
            Subject save = subjectService.save(subject)
            save.required*.required = null
            new ResponseEntity<>(save, HttpStatus.OK)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_SubjectCtrl3, ignored.message))
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    def required(@RequestBody Subject subject) {
        try {
            new ResponseEntity<>(subjectService.required(subject), HttpStatus.OK)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_SubjectCtrl4, ignored.message))
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    def delete(@PathVariable("id") Long id) {
        try {
            subjectService.delete(id)
            new ResponseEntity<>(HttpStatus.NO_CONTENT)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_SubjectCtrl5, ignored.message))
        }
    }
}
