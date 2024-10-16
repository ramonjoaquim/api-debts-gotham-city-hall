package com.gotham.batman.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    public void send(String nomefila, Map<String, Object> mensagem) {
        Queue fila = new Queue(nomefila, true);
        rabbitAdmin.declareQueue(fila);

        rabbitTemplate.convertAndSend(nomefila, mensagem);
    }
}