package pw.feed.postwriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//todo test for all relationship
//todo write facade for following and interface for services
//todo optimistic locking ?? and transactions checking after load
//todo MapStruct ??
@SpringBootApplication
@EnableScheduling
public class PostWriterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostWriterApplication.class, args);
	}

}
