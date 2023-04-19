package eu.britenet.mdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

class SomeEventParamsProcessor implements EventParamsProcessor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(Map<String, Object> params) {
        log.info("Process params: {} by SomeProcessor", params);
    }
}
