package com.mario.alba.template.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

  @GetMapping("/info")
  public String info() {
    return "info";
  }
}
