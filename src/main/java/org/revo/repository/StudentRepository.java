package org.revo.repository;

import org.revo.domain.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Created by revo on 04/12/15.
 */
public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    Optional<Student> findByEmailAndAdmin_Id(String email, Long id);

    Set<Student> findByAdmin_Id(Long admin);

    Set<Student> findByPt_Term_IdAndAdmin_Id(Long term, Long admin);

    Student findByIdAndAdmin_Id(Long student, Long admin);

    void deleteByIdAndAdmin_Id(Long student, Long admin);

    int countByAdmin_Id(Long id);
}
