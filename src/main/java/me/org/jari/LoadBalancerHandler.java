package me.org.jari;

import java.util.List;

public class LoadBalancerHandler implements LoadBalancer {

    private int index = 0;

    @Override
    public Node next(List<Node> nodes) {
        if (nodes.isEmpty())
            throw new RuntimeException("No nodes active");

        index++;
        if (index >= nodes.size())
            index = 0;

        return nodes.get(index);
    }

}
