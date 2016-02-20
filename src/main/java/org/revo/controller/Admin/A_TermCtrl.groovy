package org.revo.controller.Admin

import org.revo.domain.ErrorMessage
import org.revo.domain.Term
import org.revo.service.LoggerService
import org.revo.service.TermService
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
@RequestMapping(value = "/api/admin/term")
class A_TermCtrl {
    private static final Logger logger = LoggerFactory.getLogger(A_TermCtrl.class);
    @Autowired
    TermService termService
    @Autowired
    LoggerService loggerService

    @RequestMapping(method = RequestMethod.GET)
    def findAll() {
        try {
            new ResponseEntity<>(termService.findAll(), HttpStatus.OK)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_TermCtrl1, ignored.message))
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    def findOne(@PathVariable("id") Long id) {
        try {
            new ResponseEntity<>(termService.findOne(id), HttpStatus.OK)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_TermCtrl2, ignored.message))
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    def save(@RequestBody Term term) {
        try {
            Term one = termService.save(term)
            new ResponseEntity<>(one, HttpStatus.OK)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_TermCtrl3, ignored.message))
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    def delete(@PathVariable("id") Long term) {
        try {
            termService.delete(term)
            new ResponseEntity<>(HttpStatus.NO_CONTENT)
        } catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_TermCtrl4, ignored.message))
        }
    }
}
