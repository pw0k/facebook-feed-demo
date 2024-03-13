package pw.feed.postwriter.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pw.feed.postwriter.service.PostService;

@Slf4j
@Component
@RequiredArgsConstructor
//@ConditionalOnProperty(prefix = "schedulers", value = ["outbox.enable"], havingValue = "true")
public class PostOutboxScheduler {

    private final PostService postService;


    //todo sheduller depend property + limit in property too + batch ?? + conditions ??
    //postOutboxProducer.sendAll(postOutboxList);
    //@Scheduled(initialDelayString = "\${schedulers.outbox.initialDelayMillis}", fixedRateString = "\${schedulers.outbox.fixedRate}")
    //init 10sec, after each 5 sec
    @Scheduled(initialDelay = 15000, fixedDelay = 5000)
    public void sendPendingPostOutboxMessages() {
        log.info("Starting scheduled task for processing and sending pending PostOutboxes");
        postService.sendToKafka();
    }

}
