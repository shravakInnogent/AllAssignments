package com.innogent.demo.controller;

import java.util.List;
import java.util.Set;

import com.innogent.demo.dto.StudentRequestDto;
import com.innogent.demo.entity.Student;
import com.innogent.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
      void create(@RequestBody Student student){
          studentService.create(student);
    }


    @PostMapping("/createStudentWithCourse")
        void createStudentWithCourse(@RequestBody StudentRequestDto studentRequestDto){
            studentService.createStudentWithCourse(studentRequestDto);
        }

    @PostMapping("/createStudentWithNewCourse")
    void createStudentWithCourse(@RequestBody Student student){
        studentService.createStudentWithNewCourse(student);
    }


    @GetMapping("/getStudents")
    List<Student>  getStudents(){
        return studentService.getStudents();
    }

    @GetMapping("/getStudentsByCourse/{courseName}")
    Set<Student> stuList(@PathVariable String courseName){
      return  studentService.stuList(courseName);
    }

    @GetMapping("/getStudentsByCity&Instructor/{instructor}/{city}")
    Set<Student> stuList(@PathVariable String instructor, String city){
        return  studentService.stuListByCityInstructor(instructor, city);
    }

    @GetMapping("/getStudentById/{id}")
    Student  getStudentById(@PathVariable Integer id){
        return studentService.getStudentById(id);
    }

    @GetMapping("/getStudentWithNoCourse")
    public List<Student> studentWithNoCourse(){
        return   studentService.getStudentsWithNoCourse();
    }

    @PutMapping("/update/{id}")
    String update(@RequestBody Student student,
    @PathVariable Integer id){
       return studentService.update(student, id);
    }

    @DeleteMapping("/delete/{id}")
    String delete(@PathVariable Integer id){
        return studentService.delete(id);
    }
}
