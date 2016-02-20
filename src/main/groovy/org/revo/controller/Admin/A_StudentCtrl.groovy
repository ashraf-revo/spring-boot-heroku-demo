package org.revo.controller.Admin

import org.revo.domain.ErrorMessage
import org.revo.domain.Student
import org.revo.service.LoggerService
import org.revo.service.StudentService
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
@RequestMapping(value = "/api/admin/student")
class A_StudentCtrl {
    private static final Logger logger = LoggerFactory.getLogger(A_StudentCtrl.class);
    @Autowired
    StudentService studentService
    @Autowired
    LoggerService loggerService

    @RequestMapping(method = RequestMethod.GET)
    def findAll() {
        try {
            Set<Student> findAll = studentService.findAll()
            findAll*.pt = null
            new ResponseEntity<>(findAll, HttpStatus.OK)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_StudentCtrl1, ignored.message))
        }
    }

    @RequestMapping(params = "term", method = RequestMethod.GET)
    def findAllInTerm(@RequestParam("term") Long term) {
        try {
            Set<Student> findAll = studentService.findAll(term)
            findAll*.pt = null
            new ResponseEntity<>(findAll, HttpStatus.OK)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_StudentCtrl2, ignored.message))
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    def findOne(@PathVariable("id") Long id) {
        try {
            Student student = studentService.findOne(id)
            student.pt.each {
                it.ps*.subject*.required = null
            }
            new ResponseEntity<>(student, HttpStatus.OK);
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_StudentCtrl3, ignored.message))
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    def save(@RequestBody Student student) {
        try {
            Student one = studentService.save(student)
            one.pt = null
            new ResponseEntity<>(one, HttpStatus.OK)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_StudentCtrl4, ignored.message))
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    def delete(@PathVariable("id") Long id) {
        try {
            studentService.delete(id)
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_StudentCtrl5, ignored.message))
        }
    }

    @RequestMapping(value = "/{id}", params = "term", method = RequestMethod.DELETE)
    def deleteInTerm(@PathVariable("id") Long student, @RequestParam("term") Long term) {
        try {
            studentService.delete(student, term)
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_StudentCtrl6, ignored.message))
        }
    }
}
