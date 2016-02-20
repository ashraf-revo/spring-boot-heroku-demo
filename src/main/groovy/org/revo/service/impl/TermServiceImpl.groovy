package org.revo.service.impl

import org.revo.domain.Admin
import org.revo.domain.PT
import org.revo.domain.Term
import org.revo.repository.TermRepository
import org.revo.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by revo on 13/01/16.
 */
@Service
class TermServiceImpl implements TermService {
    @Autowired
    TermRepository termRepository
    @Autowired
    AdminService adminService
    @Autowired
    SecurityService securityService
    @Autowired
    PTService ptService
    @Autowired
    StudentService studentService

    @Override
    @Transactional(readOnly = true)
    Set<Term> findAll() {
        termRepository.findByAdmin_Id(securityService.GetRevoUser().id)
    }

    @Override
    @Transactional(readOnly = true)
    Set<Term> findAllToStudent() {
        Set<PT> pts = ptService.findAll()
        Set<Term> terms = termRepository.findByEnabledTrueAndAdmin_Id(securityService.GetRevoUser().admin)
        terms.removeAll {
            pts.any { x ->
                x.term.id == it.id
            }
        }
        terms
    }

    @Override
    @Transactional(readOnly = true)
    Term findOne(Long term) {
        termRepository.findByIdAndAdmin_Id(term, securityService.GetRevoUser().id)
    }

    @Override
    @Transactional
    Term save(Term term) throws Exception {
        if (!term.id) {
            term = Util.CloneObject(new Term(), term, ["id"]) as Term
            term.admin = adminService.findOne(securityService.GetRevoUser().id)
            Term save = termRepository.save(term)
            studentService.findAll().each {
                ptService.save(new PT(term: save, student: it))
            }
            save
        } else {
            Term one = termRepository.findOne(term.id)
            if (one) {
                Term termClone = Util.CloneObject(one, term, ["id"]) as Term
                one = termClone
                Admin admin = adminService.findOne(securityService.GetRevoUser().id)
                if (one.admin.id == admin.id) {
                    term.admin = admin
                    term.pt = termClone.pt
                    termRepository.save(term)
                } else throw new Exception("it not your term")
            } else throw new Exception("no term with id")
        }
    }

    @Transactional
    @Override
    void delete(Long term) {
        termRepository.deleteByIdAndAdmin_Id(term, securityService.GetRevoUser().id)
    }
}
