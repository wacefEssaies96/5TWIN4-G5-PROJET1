package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.services.ICourseServices;

import java.util.List;
import java.util.Set;


class CourseDTO{
	Long numCourse;
	int level;
	TypeCourse typeCourse;
	Support support;
	Float price;
	int timeSlot;
	Set<Registration> registrations;
}

@Tag(name = "\uD83D\uDCDA Course Management")
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseRestController {
    
    private final ICourseServices courseServices;

    @Operation(description = "Add Course")
    @PostMapping("/add")
    public Course addCourse(@RequestBody CourseDTO course){
    	Course c = new Course();
    	c.setNumCourse(course.numCourse);
    	c.setLevel(course.level);
    	c.setPrice(course.price);
    	c.setRegistrations(course.registrations);
    	c.setSupport(course.support);
    	c.setTypeCourse(course.typeCourse);
        return  courseServices.addCourse(c);
    }

    @Operation(description = "Retrieve all Courses")
    @GetMapping("/all")
    public List<Course> getAllCourses(){
        return courseServices.retrieveAllCourses();
    }

    @Operation(description = "Update Course ")
    @PutMapping("/update")
    public Course updateCourse(@RequestBody CourseDTO course){
    	Course c = new Course();
    	c.setNumCourse(course.numCourse);
    	c.setLevel(course.level);
    	c.setPrice(course.price);
    	c.setRegistrations(course.registrations);
    	c.setSupport(course.support);
    	c.setTypeCourse(course.typeCourse);
        return  courseServices.updateCourse(c);
    }

    @Operation(description = "Retrieve Course by Id")
    @GetMapping("/get/{id-course}")
    public Course getById(@PathVariable("id-course") Long numCourse){
        return courseServices.retrieveCourse(numCourse);
    }

}
