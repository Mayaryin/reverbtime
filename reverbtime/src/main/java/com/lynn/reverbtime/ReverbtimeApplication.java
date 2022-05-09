package com.lynn.reverbtime;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Starts the server
*/
@SpringBootApplication
public class ReverbtimeApplication {

	public static void main(String[] args) {

		System.out.println(System.env(„DATABASE_URL“));
		SpringApplication.run(ReverbtimeApplication.class, args);


	}

}
