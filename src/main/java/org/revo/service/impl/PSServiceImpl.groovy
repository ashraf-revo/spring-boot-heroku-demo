package org.revo.service.impl

import org.revo.domain.Admin
import org.revo.domain.PS
import org.revo.domain.State
import org.revo.repository.PSRepository
import org.revo.service.AdminService
import org.revo.service.PSService
import org.revo.service.SecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by revo on 13/01/16.
 */
@Service
class PSServiceImpl implements PSService {
    @Autowired
    PSRepository psRepository
    @Autowired
    AdminService adminService
    @Autowired
    SecurityService securityService

    @Transactional(readOnly = true)
    @Override
    Set<PS> findInTerm(Long term) {
        psRepository.findByPt_Student_IdAndPt_Term_Id(securityService.GetRevoUser().id, term)
    }

    @Transactional(readOnly = true)
    @Override
    PS findOne(Long ps) {
        psRepository.findByIdAndPt_Term_Admin_Id(ps, securityService.GetRevoUser().id)
    }

    @Transactional
    @Override
    PS save(PS ps) throws Exception {
        if (!ps.id) {
            psRepository.save(ps)
        } else {
            PS one = psRepository.findOne(ps.id)
            if (one) {
                one = Util.CloneObject(one, ps, ["id"]) as PS
                Admin admin = adminService.findOne(securityService.GetRevoUser().id)
                if (one.pt.term.admin.id == admin.id) {
                    if (ps.state == State.non) {
                        one.state = ps.state
                        one.grade = 0
                    } else {
                        one.state = ps.state
                        if (ps.grade > one.subject.maxGrade)
                            one.grade = one.subject.maxGrade
                        else
                            one.grade = ps.grade
                    }
                    psRepository.save(one)
                } else throw new Exception("it not your ps")
            } else throw new Exception("no ps with this id")

        }
    }

    @Transactional
    @Override
    void delete(Long ps) {
        psRepository.deleteByIdAndPt_Term_Admin_Id(ps, securityService.GetRevoUser().id)
    }
}
