package org.revo.repository;

import org.revo.domain.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created by revo on 04/12/15.
 */
public interface SubjectRepository extends CrudRepository<Subject, Long> {

    Set<Subject> findByAdmin_Id(Long subject);

    Subject findByIdAndAdmin_Id(Long subject, Long admin);

    void deleteByIdAndAdmin_Id(Long subject, Long admin);

}
