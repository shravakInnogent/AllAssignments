package com.innogent.demo.repository;

import com.innogent.demo.entity.Student;
import com.innogent.demo.entity.StudentEnrollWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT DISTINCT s FROM Student s JOIN FETCH s.courses c WHERE c.courseName = :name")
    Set<Student> StudentByCourse(@Param("name") String courseName);

    @Query("SELECT DISTINCT s FROM Student s JOIN FETCH s.courses c WHERE c.instructor = :instructor AND s.city = :city")
    Set<Student> StudentByCourseInstructor(@Param("instructor") String instructor, @Param("city") String city);


    @Query("SELECT DISTINCT s FROM Student s JOIN FETCH s.courses c WHERE c.courseId = null")
    List<Student> getStudentsWithNoCourse();
}
