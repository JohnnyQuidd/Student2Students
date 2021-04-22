package com.zuul.message_broker;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MessageListener {
    public void listenForMessages(byte[] bytes){
        String event = new String(bytes, StandardCharsets.UTF_8);

        //TODO: Perform an action when data is received through queue
    }
}
