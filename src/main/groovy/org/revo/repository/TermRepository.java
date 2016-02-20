package org.revo.repository;

import org.revo.domain.Term;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created by revo on 04/12/15.
 */
public interface TermRepository extends CrudRepository<Term, Long> {

    Set<Term> findByEnabledTrueAndAdmin_Id(Long admin);

    void deleteByIdAndAdmin_Id(Long term, Long admin);

    Term findByIdAndAdmin_Id(Long term, Long admin);

    Set<Term> findByAdmin_Id(Long id);
}
