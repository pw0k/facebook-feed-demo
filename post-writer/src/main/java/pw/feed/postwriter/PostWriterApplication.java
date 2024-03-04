package pw.feed.postwriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//todo test for all relationship
//todo write facade for following and interface for services
//todo optimistic locking ?? and transactions checking after load
@SpringBootApplication
public class PostWriterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostWriterApplication.class, args);
	}

}
