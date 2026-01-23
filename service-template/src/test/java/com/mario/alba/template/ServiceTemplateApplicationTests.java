package com.mario.alba.template;

import static org.assertj.core.api.Assertions.assertThat;

import com.mario.alba.template.api.HealthController;
import com.mario.alba.template.api.PingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTemplateApplicationTests {

  @Autowired PingController pingController;

  @Autowired HealthController healthController;

  @Test
  public void contextLoad() {
    assertThat(pingController).isNotNull();
    assertThat(healthController).isNotNull();
  }
}
