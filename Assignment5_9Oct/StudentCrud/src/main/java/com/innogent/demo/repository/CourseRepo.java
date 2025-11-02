package com.innogent.demo.repository;

import com.innogent.demo.dto.CourseWithStudentCount;
import com.innogent.demo.dto.InstructorRequestDto;
import com.innogent.demo.entity.Course;
import com.innogent.demo.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CourseRepo extends  JpaRepository<Course, Integer >  {

    @Query("SELECT DISTINCT c FROM Course c JOIN FETCH c.students s WHERE s.firstName = :name")
    public Set<Course> courseList(@Param("name") String studentName);

    @Query(
            value = """
                SELECT 
                    c.course_id AS courseId,
                    c.course_name AS courseName,
                    COUNT(sc.student_id) AS studentCount
                FROM course c
                LEFT JOIN student_courses sc 
                    ON c.course_id = sc.courses_id
                GROUP BY c.course_id, c.course_name
                """,
            nativeQuery = true
    )
    List<CourseWithStudentCount> getCourseStudentCounts();

}
