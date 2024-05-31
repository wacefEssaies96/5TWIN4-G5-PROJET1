package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.services.IInstructorServices;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

class InstructorDTO{
	Long numInstructor;
	String firstName;
	String lastName;
	LocalDate dateOfHire;
	Set<Course> courses;
}
@Tag(name = "\uD83D\uDC69\u200D\uD83C\uDFEB Instructor Management")
@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorRestController {

    private final IInstructorServices instructorServices;

    @Operation(description = "Add Instructor")
    @PostMapping("/add")
    public Instructor addInstructor(@RequestBody InstructorDTO instructor){
    	Instructor i = new Instructor();
        return  instructorServices.addInstructor(i);
    }
    @Operation(description = "Add Instructor and Assign To Course")
    @PutMapping("/addAndAssignToCourse/{numCourse}")
    public Instructor addAndAssignToInstructor(@RequestBody InstructorDTO instructor, @PathVariable("numCourse")Long numCourse){
    	Instructor i = new Instructor();
    	return  instructorServices.addInstructorAndAssignToCourse(i,numCourse);
    }
    @Operation(description = "Retrieve all Instructors")
    @GetMapping("/all")
    public List<Instructor> getAllInstructors(){
        return instructorServices.retrieveAllInstructors();
    }

    @Operation(description = "Update Instructor ")
    @PutMapping("/update")
    public Instructor updateInstructor(@RequestBody InstructorDTO instructor){
    	Instructor i = new Instructor();
        return  instructorServices.updateInstructor(i);
    }

    @Operation(description = "Retrieve Instructor by Id")
    @GetMapping("/get/{id-instructor}")
    public Instructor getById(@PathVariable("id-instructor") Long numInstructor){
        return instructorServices.retrieveInstructor(numInstructor);
    }

}
