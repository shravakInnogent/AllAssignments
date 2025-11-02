package com.innogent.demo.service;

import com.innogent.demo.dto.CourseWithStudentCount;
import com.innogent.demo.dto.InstructorRequestDto;
import com.innogent.demo.entity.Course;
import com.innogent.demo.entity.Student;
import com.innogent.demo.entity.StudentEnrollWrapper;
import com.innogent.demo.repository.CourseRepo;
import com.innogent.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private StudentRepository studentRepository;



    public void createCourse(Course course) {
        courseRepo.save(course);
    }


    public StudentEnrollWrapper courseList(String studentName){
        Set<Course> courses =  courseRepo.courseList(studentName);

        StudentEnrollWrapper wrapper = new StudentEnrollWrapper();
        for(Course course: courses){
            Set<Student> students = studentRepository.StudentByCourse(course.getCourseName());
            wrapper.setCourseName(course.getCourseName());
            wrapper.setInstructor(course.getInstructor());
            wrapper.setDescription(course.getDescription());
            wrapper.setCourseId(course.getCourseId());
            for(Student student: students){
                wrapper.setFirstName(student.getFirstName());
                wrapper.setLastName(student.getLastName());
                wrapper.setEmail(student.getEmail());
            }

        }
        return wrapper;
    }

    public void updateInstructor(InstructorRequestDto instructorRequestDtoObj){
        int id = instructorRequestDtoObj.getCourseId();
        Course course = courseRepo.findById(id).get();
        course.setInstructor(instructorRequestDtoObj.getInstructor());
        courseRepo.save(course);

    }

    public List<CourseWithStudentCount> getCount() {
        return courseRepo.getCourseStudentCounts();
    }
}

