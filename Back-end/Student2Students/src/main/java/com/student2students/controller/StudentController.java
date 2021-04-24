package com.student2students.controller;

import com.student2students.constants.RestParameters;
import com.student2students.dto.StudentRegisterDTO;
import com.student2students.model.Student;
import com.student2students.service.StudentRequestService;
import com.student2students.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final StudentRequestService studentRequestService;

    @Autowired
    public StudentController(StudentService studentService, StudentRequestService studentRequestService) {
        this.studentService = studentService;
        this.studentRequestService = studentRequestService;
    }


    @PostMapping
    public ResponseEntity<?> createNewStudent(@RequestBody StudentRegisterDTO studentDTO) {
        return studentService.registerStudent(studentDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestBody StudentRegisterDTO studentDTO) {
        return studentService.updateStudent(studentDTO);
    }

    @GetMapping("/data/{username}")
    public ResponseEntity<?> getStudentData(HttpServletRequest request, @PathVariable("username") String username) {
        Student student = studentRequestService.getStudentFromRequest(request);
        if(!student.getUsername().equals(username))
            return ResponseEntity.status(403).body("Permission denied");

        return studentService.createDTOFromStudentModel(student);
    }

    @GetMapping("/country/{countryName}")
    public ResponseEntity<?> getStudentFromCountry(@PathVariable("countryName") String countryName,
                                                   @RequestParam(value = RestParameters.PAGE, required = false, defaultValue = "0") int page,
                                                   @RequestParam(value = RestParameters.LIMIT, required = false, defaultValue = "10") int limit) {
        return studentService.getStudentsFromCountry(countryName, page, limit);
    }

    @GetMapping("/major/{majorName}")
    public ResponseEntity<?> getStudentByMajorName(@PathVariable("majorName") String majorName,
                                                   @RequestParam(value = RestParameters.PAGE, required = false, defaultValue = "0") int page,
                                                   @RequestParam(value = RestParameters.LIMIT, required = false, defaultValue = "10") int limit) {
        return studentService.getStudentsByMajorName(majorName, page, limit);
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<?> getStudentsByLanguage(@PathVariable("language") String language,
                                                   @RequestParam(value = RestParameters.PAGE, required = false, defaultValue = "0") int page,
                                                   @RequestParam(value = RestParameters.LIMIT, required = false, defaultValue = "10") int limit) {
        return studentService.getStudentsByLanguage(language, page, limit);
    }

}
