package com.innogent.demo.controller;

import com.innogent.demo.dto.CourseWithStudentCount;
import com.innogent.demo.dto.InstructorRequestDto;
import com.innogent.demo.entity.Course;
import com.innogent.demo.entity.Student;
import com.innogent.demo.entity.StudentEnrollWrapper;
import com.innogent.demo.service.CourseService;
import com.innogent.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/createCourse")
    public void createCourse(@RequestBody Course course){
        courseService.createCourse(course);
    }

    @GetMapping("/getCourseByStudentName/{studentName}")
    public StudentEnrollWrapper courseList(@PathVariable String studentName){
        return  courseService.courseList(studentName);
    }

    @GetMapping("/getCount")
    public List<CourseWithStudentCount> getCount() {
        return courseService.getCount();
    }

    @PutMapping("/update/instructor")
    public void  update(@RequestBody InstructorRequestDto updateInstructorObj){
         courseService.updateInstructor(updateInstructorObj);
    }


}
