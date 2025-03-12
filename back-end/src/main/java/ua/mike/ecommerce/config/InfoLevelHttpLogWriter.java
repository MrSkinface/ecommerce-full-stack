package ua.mike.ecommerce.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

@Slf4j
@Component
public class InfoLevelHttpLogWriter implements HttpLogWriter {

    @Override
    public void write(Precorrelation precorrelation, String request) {
        log.info(request);
    }

    @Override
    public void write(Correlation correlation, String response) {
        log.info(response);
    }

    @Override
    public boolean isActive() {
        return log.isInfoEnabled();
    }
}
