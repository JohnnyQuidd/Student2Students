package com.student2students.message_broker;

import com.google.gson.Gson;
import com.student2students.dto.StudentRegisterDTO;
import com.student2students.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    private final StudentService studentService;

    @Autowired
    public MessageListener(StudentService studentService) {
        this.studentService = studentService;
    }

    public void listenForMessages(String studentGson){
        Gson gson = new Gson();
        StudentRegisterDTO studentRegisterDTO = gson.fromJson(studentGson, StudentRegisterDTO.class);

        studentService.registerStudent(studentRegisterDTO);
    }
}
