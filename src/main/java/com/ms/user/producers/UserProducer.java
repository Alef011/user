package com.ms.user.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.user.dtos.EmailDTO;
import com.ms.user.models.UserModel;

@Component
public class UserProducer {

  final RabbitTemplate rabbitTemplate;

  public UserProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Value(value = "${broker.queue.email.name}")
  private String routingKey;
  
  public void publishMessageEmail(UserModel userModel) {
    var emailDTO = new EmailDTO();
    emailDTO.setUserID(userModel.getUserId());
    emailDTO.setEmailTo(userModel.getEmail());
    emailDTO.setSubject("Cadastro realizado com sucesso!");

    //  pensando na South
    emailDTO.setText(userModel.getName() + ", seja bem vindo(a)! \nAgradecemos o seu cadastro. Aproveite agora todos os recurso da nossa plataforma South System!" );

    rabbitTemplate.convertAndSend("", routingKey, emailDTO);
  }
  
}
