package com.hzhang.sweethome.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;
import java.util.List;

@Controller
public class DeferredRequestList {
    public static Multimap<String, DeferredResult<String>> watchRequests =
            Multimaps.synchronizedMultimap(HashMultimap.create());

    public void publish(String email, String type) {
        UnreadNumKey id = new UnreadNumKey().setType(type).setEmail(email);
        System.out.println("publish!");
        if (watchRequests.containsKey(id.toString())){
            System.out.println("has key!");
            Collection<DeferredResult<String>> deferredResults = watchRequests.get(id.toString());
            for (DeferredResult<String> deferredResult : deferredResults) {
                deferredResult.setResult("update!");
                System.out.println("update! " + type);
            }
        }
    }
    public void publish(String id) {
        System.out.println("publish!");
        if (watchRequests.containsKey(id)){
            System.out.println("has key!");
            Collection<DeferredResult<String>> deferredResults = watchRequests.get(id);
            for (DeferredResult<String> deferredResult : deferredResults) {
                deferredResult.setResult("update!");
                System.out.println("update!");
            }
        }
    }

    public void publishMsg(String email, List<User> users) {
        for (User user : users) {
            if (email.equals(user.getEmail())) {
                continue;
            }
            UnreadNumKey id = new UnreadNumKey().setType("MESSAGE").setEmail(user.getEmail());
            if (watchRequests.containsKey(id.toString())){
                System.out.println(id);
                Collection<DeferredResult<String>> deferredResults = watchRequests.get(id.toString());
                for (DeferredResult<String> deferredResult : deferredResults) {
                    deferredResult.setResult("update!");
                }
            }
        }
    }

    public DeferredResult<String> watch(String email, String type) {
        final long TIME_OUT = 8000;
        String id = (type.equals(InvoiceType.PUBLIC.name())) ? type :
                new UnreadNumKey().setType(type).setEmail(email).toString();

        DeferredResult<String> deferredResult = new DeferredResult<>(TIME_OUT);
        deferredResult.onCompletion(() -> {
            watchRequests.remove(id, deferredResult);
        });
        watchRequests.put(id, deferredResult);
        System.out.println( "size "+ watchRequests.size());
        return deferredResult;
    }
}
