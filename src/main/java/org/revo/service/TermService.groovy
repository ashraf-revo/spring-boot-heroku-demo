package org.revo.service

import org.revo.domain.Term

/**
 * Created by revo on 13/01/16.
 */
interface TermService {
    Set<Term> findAll()

    Set<Term> findAllToStudent()

    Term findOne(Long term)

    Term save(Term term)throws Exception

    void delete(Long term)

}