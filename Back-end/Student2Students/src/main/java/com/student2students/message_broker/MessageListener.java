package com.student2students.message_broker;

import com.google.gson.Gson;
import com.student2students.dto.StudentRegisterDTO;
import com.student2students.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    private final StudentService studentService;
    private final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    public MessageListener(StudentService studentService) {
        this.studentService = studentService;
    }

    public void listenForMessages(String studentGson){
        Gson gson = new Gson();
        StudentRegisterDTO studentRegisterDTO = gson.fromJson(studentGson, StudentRegisterDTO.class);

        logger.info("Saving student");
        studentService.registerStudent(studentRegisterDTO);
    }
}
