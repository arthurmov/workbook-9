package com.pluralsight;

import com.pluralsight.dao.RegistrationDAO;
import com.pluralsight.dao.SimpleRegistrationDAO;
import com.pluralsight.model.Student;
import com.pluralsight.service.RegistrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context;
		context = SpringApplication.run(DemoApplication.class, args);

		for(String bean : context.getBeanDefinitionNames()) {
			System.out.println(bean);
		}

//		RegistrationDAO registrationDAO = new SimpleRegistrationDAO();
//		RegistrationService registrationService = new RegistrationService(registrationDAO);

		RegistrationService registrationService = context.getBean(RegistrationService.class);

		Student s = registrationService.getStudentById(10);

		System.out.println(s.getFirstName());

		CommandLineRunner clr = context.getBean(CLI.class);
		clr.run();

	}

}
