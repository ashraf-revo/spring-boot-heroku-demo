package org.revo.service

import org.revo.domain.PS

/**
 * Created by revo on 13/01/16.
 */
interface PSService {
    Set<PS> findInTerm(Long term)

    PS findOne(Long ps)

    PS save(PS ps) throws Exception

    void delete(Long ps)

}