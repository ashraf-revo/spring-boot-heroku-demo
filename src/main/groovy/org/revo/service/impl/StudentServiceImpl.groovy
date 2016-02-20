package org.revo.service.impl

import org.revo.domain.Admin
import org.revo.domain.Student
import org.revo.repository.PTRepository
import org.revo.repository.StudentRepository
import org.revo.service.AdminService
import org.revo.service.SecurityService
import org.revo.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by revo on 13/01/16.
 */
@Service
class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository
    @Autowired
    AdminService adminService
    @Autowired
    PTRepository ptRepository
    @Autowired
    SecurityService securityService
    @Autowired
    PasswordEncoder encoder
    @Autowired
    Environment env

    @Transactional(readOnly = true)
    @Override
    Set<Student> findAll() {
        studentRepository.findByAdmin_Id(securityService.GetRevoUser().id)
    }

    @Transactional(readOnly = true)
    @Override
    Set<Student> findAll(Long term) {
        studentRepository.findByPt_Term_IdAndAdmin_Id(term, securityService.GetRevoUser().id)
    }

    @Transactional(readOnly = true)
    @Override
    Student findOne(Long student) {
        studentRepository.findByIdAndAdmin_Id(student, securityService.GetRevoUser().admin)
    }

    @Transactional(readOnly = true)
    @Override
    Student findOne(String student) {
        studentRepository.findByEmail(student).get()
    }

    @Transactional
    @Override
    Student save(Student student) throws Exception {
        if (!student.id) {
            if (!canSave()) throw new Exception("to save this one scale your plane")
            student = Util.CloneObject(new Student(), student, ["id"]) as Student
            student.admin = adminService.findOne(securityService.GetRevoUser().id)
            student.password = encoder.encode(student.password)
            studentRepository.save(student)
        } else {
            Student one = studentRepository.findOne(student.id)
            if (one) {
                def ig = ["id"];
                if (student.password == null || student.password.trim().size() == 0) {
                    student.password = one.password
                    ig << "password"
                } else
                    student.password = encoder.encode(student.password)
                Student studentClone = Util.CloneObject(one, student, ig) as Student
                one = studentClone
                Admin admin = adminService.findOne(securityService.GetRevoUser().id)
                if (one.admin.id == admin.id) {
                    student.admin = admin
                    student.pt = studentClone.pt
                    studentRepository.save(student)
                } else throw new Exception("it not your student")
            } else throw new Exception("no student with this id")
        }

    }

    @Transactional(readOnly = true)
    @Override
    void delete(Long student) {
        studentRepository.deleteByIdAndAdmin_Id(student, securityService.GetRevoUser().id)

    }

    @Transactional(readOnly = true)
    @Override
    void delete(Long student, Long term) {
        Long admin = securityService.GetRevoUser().id
        ptRepository.deleteByStudent_IdAndStudent_Admin_IdAndTerm_IdAndTerm_Admin_Id(student, admin, term, admin)
    }

    @Override
    boolean canSave() {
        int Max = (securityService.GetRevoUser().plane * env.getProperty("rate").toInteger()) + env.getProperty("freeStudents").toInteger()
        Max > count()
    }

    @Override
    int count() {
        studentRepository.countByAdmin_Id(securityService.GetRevoUser().id)
    }
}
