package net.booone.chromaleague.rest.http.client;

import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.core5.util.TimeValue;

import java.util.Collections;

public class AlwaysRetryHttpRequestRetryStrategy extends DefaultHttpRequestRetryStrategy {
    public AlwaysRetryHttpRequestRetryStrategy() {
        super(5, TimeValue.ofSeconds(1l), Collections.emptyList(), Collections.emptyList());
    }
}
