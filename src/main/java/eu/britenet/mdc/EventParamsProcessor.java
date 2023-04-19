package eu.britenet.mdc;

import java.util.Map;

public interface EventParamsProcessor {

    void process(Map<String, Object> params);

    static SomeEventParamsProcessor getSome() {
        return new SomeEventParamsProcessor();
    }

    static AnotherEventParamsProcessor getAnother() {
        return new AnotherEventParamsProcessor();
    }

}
