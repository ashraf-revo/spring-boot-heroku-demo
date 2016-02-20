package org.revo.service

import org.revo.domain.Student

/**
 * Created by revo on 13/01/16.
 */
interface StudentService {
    Set<Student> findAll()

    Set<Student> findAll(Long term)

    Student findOne(Long student)

    Student findOne(String student)

    Student save(Student student) throws Exception

    void delete(Long student)

    void delete(Long student, Long term)

    boolean canSave()

    int count()
}