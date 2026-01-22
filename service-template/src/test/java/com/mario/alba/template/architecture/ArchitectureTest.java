package com.mario.alba.template.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

public class ArchitectureTest {
  private static final String BASE = "com.mario.alba.template";

  private final JavaClasses classes = new ClassFileImporter().importPackages(BASE);

  @Test
  void domain_must_not_depend_on_api_adapters_or_config() {
    ArchRule rule =
        noClasses()
            .that()
            .resideInAPackage(BASE + ".domain..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(BASE + ".api..", BASE + ".adapters..", BASE + ".config..");

    rule.check(classes);
  }

  @Test
  void api_should_only_be_accessed_by_api_or_config() {
    ArchRule rule =
        classes()
            .that()
            .resideInAPackage(BASE + ".api..")
            .should()
            .onlyBeAccessed()
            .byAnyPackage(BASE + ".api..", BASE + ".config..");

    rule.check(classes);
  }

  @Test
  void application_must_not_depend_on_api() {
    ArchRule rule =
        noClasses()
            .that()
            .resideInAPackage(BASE + ".application..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(BASE + ".api..");

    rule.check(classes);
  }

  @Test
  void adapters_should_not_be_accessed_by_api() {
    ArchRule rule =
        classes()
            .that()
            .resideInAPackage(BASE + ".adapters..")
            .should()
            .onlyBeAccessed()
            .byAnyPackage(BASE + ".adapters..", BASE + ".config..", BASE + ".application..");

    rule.check(classes);
  }
}
