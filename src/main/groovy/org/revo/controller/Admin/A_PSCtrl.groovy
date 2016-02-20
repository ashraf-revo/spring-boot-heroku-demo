package org.revo.controller.Admin

import org.revo.domain.ErrorMessage
import org.revo.domain.PS
import org.revo.service.LoggerService
import org.revo.service.PSService
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
@RequestMapping(value = "/api/admin/ps")
class A_PSCtrl {
    private static final Logger logger = LoggerFactory.getLogger(A_PSCtrl.class);
    @Autowired
    PSService psService
    @Autowired
    LoggerService loggerService

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    def findOne(@PathVariable("id") Long id) {
        try {
            new ResponseEntity<>(psService.findOne(id), HttpStatus.OK)
        }
        catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_PSCtrl1, ignored.message))
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    def save(@RequestBody PS ps) {
        try {
            ps = psService.save(ps)
            ps.subject.required = null
            new ResponseEntity<>(ps, HttpStatus.OK)
        }
        catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_PSCtrl2, ignored.message))
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    def delete(@PathVariable("id") Long id) {
        try {
            psService.delete(id)
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception ignored) {
            loggerService.Error(logger, new ErrorMessage(ErrorNumbers.A_PSCtrl3, ignored.message))
        }
    }
}
