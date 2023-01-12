package me.org.jari;

import java.util.List;

public interface LoadBalancer {

    Node next(List<Node> nodes);
}
