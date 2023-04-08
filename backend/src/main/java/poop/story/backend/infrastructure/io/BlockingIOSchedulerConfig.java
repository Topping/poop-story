package poop.story.backend.infrastructure.io;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class BlockingIOSchedulerConfig {
    public static final String IO_SCHEDULER_BEAN = "io-scheduler";

    @Bean(IO_SCHEDULER_BEAN)
    public Scheduler ioScheduler() {
        return Schedulers.boundedElastic();
    }
}
