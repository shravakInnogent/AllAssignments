package com.innogent.demo.service;

import com.innogent.demo.dto.StudentRequestDto;
import com.innogent.demo.entity.Course;
import com.innogent.demo.entity.Student;
import com.innogent.demo.entity.StudentEnrollWrapper;
import com.innogent.demo.repository.CourseRepo;
import com.innogent.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private CourseRepo courseRepository;

    public void create( Student student){
        studentRepository.save(student);
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Student getStudentById(Integer id){
        Optional<Student> s =  studentRepository.findById(id);
            return s.get();
    }

    public String update(Student student, Integer id){
        Student s  = getStudentById(id);
        s.setId(id);
        s.setFirstName(student.getFirstName());
        s.setLastName(student.getLastName());
        s.setEmail(student.getEmail());
        studentRepository.save(s);
        return "SUCCESS";
    }

    public String delete(Integer id){
        Student student1 =  getStudentById(id);
        studentRepository.delete(student1);
        return "Successfully Deleted.";
    }

    public Set<Student> stuList (String courseName){
        return studentRepository.StudentByCourse(courseName);

    }

    public List<Student> getStudentsWithNoCourse(){
        return studentRepository.getStudentsWithNoCourse();
    }



    public Set<Student> stuListByCityInstructor (String instructor, String city){
        return studentRepository.StudentByCourseInstructor(instructor, city);

    }

    public void createStudentWithCourse(StudentRequestDto studentRequestDto){
        Student s = new Student();
        s.setFirstName(studentRequestDto.getFirstName());
        s.setLastName(studentRequestDto.getLastName());
        s.setEmail(studentRequestDto.getEmail());

        if (studentRequestDto.getCourseIds() != null && !studentRequestDto.getCourseIds().isEmpty()) {
            Set<Course> courses = new HashSet<>(
                    courseRepository.findAllById(studentRequestDto.getCourseIds())
            );
            s.setCourses(courses);
        }
        studentRepository.save(s);
    }


    public void createStudentWithNewCourse(Student s){
        studentRepository.save(s);
    }
}
