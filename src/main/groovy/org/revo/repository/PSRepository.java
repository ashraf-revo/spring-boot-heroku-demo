package org.revo.repository;

import org.revo.domain.PS;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created by revo on 04/12/15.
 */
public interface PSRepository extends CrudRepository<PS, Long> {
    Set<PS> findByPt_Student_IdAndPt_Term_Id(Long Student, Long term);

    PS findByIdAndPt_Term_Admin_Id(Long ps, Long admin);

    void deleteByIdAndPt_Term_Admin_Id(Long ps, Long admin);
}
