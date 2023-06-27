//package com.example.webapis.student;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Configuration
//public class StudentConfig {
//    @Bean
//    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
//        return args -> {
//            Student amaaz = new Student(
//                    "amaaz",
//                    "amaaz@gmail.com",
//                    LocalDate.of(2000, 9, 9)
//            );
//
//            Student john = new Student(
//                    "john",
//                    "john@gmail.com",
//                    LocalDate.of(2002, 5, 3)
//            );
//
//            studentRepository.saveAll(List.of(amaaz, john));
//        };
//    }
//}
