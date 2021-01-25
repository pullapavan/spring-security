package com.a23.server.students;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author pavanpulla
 *
 */
@RestController
@RequestMapping("/api/students")
public class StudentsController {
	
	private static final List<Student> STUDENTS = (List<Student>) Arrays.asList(
		      new Student(1, "James Bond"),
		      new Student(2, "Maria Jones"),
		      new Student(3, "Anna Smith")
		    );

		    @GetMapping(path = "{studentId}")
		    public Student getStudent(@PathVariable("studentId") Integer studentId) {
		        return STUDENTS.stream()
		                .filter(student -> studentId.equals(student.getStudentId()))
		                .findFirst()
		                .orElseThrow(() -> new IllegalStateException(
		                        "Student " + studentId + " does not exists"
		                ));
		    }

}
