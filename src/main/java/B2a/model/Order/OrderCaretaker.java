package B2a.domain.Order;

import java.util.ArrayList;

public class OrderCaretaker {
    private ArrayList<OrderMemento> savedStates = new ArrayList<OrderMemento>();
    public void addMemento(OrderMemento m){savedStates.add(m);}
    public OrderMemento getMemento(int index){return savedStates.get(index);}
}
