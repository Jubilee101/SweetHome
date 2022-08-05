package com.hzhang.sweethome.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;

@Component
public class DeferredRequestList {
    public static Multimap<UnreadNumKey, DeferredResult<String>> watchRequests =
            Multimaps.synchronizedMultimap(HashMultimap.create());

    public void publish(String email, String type) {
        UnreadNumKey id = new UnreadNumKey().setType(type).setEmail(email);
        System.out.println("publish!");
        if (watchRequests.containsKey(id)){
            System.out.println("has key!");
            Collection<DeferredResult<String>> deferredResults = watchRequests.get(id);
            for (DeferredResult<String> deferredResult : deferredResults) {
                deferredResult.setResult("update!");
                System.out.println("update! " + type);
            }
        }
    }

    public DeferredResult<String> watch(String email, String type) {
        final Long TIME_OUT = (long)1000 * 5;
        UnreadNumKey id = new UnreadNumKey().setType(type).setEmail(email);
        DeferredResult<String> deferredResult = new DeferredResult<>(TIME_OUT);
        deferredResult.onCompletion(() -> {
            watchRequests.remove(id, deferredResult);
        });
        watchRequests.put(id, deferredResult);
        return deferredResult;
    }
}
