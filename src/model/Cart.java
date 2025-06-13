package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart<T extends MenuItem> {
    private List<T> items = new ArrayList<>();

    public void addItem(T item) {
        for (T i : items) {
            if (i.getId() == item.getId()) {
                i.incrementQuantity();
                return;
            }
        }
        item.setQuantity(1); // default 1 saat pertama kali ditambahkan
        items.add(item);
    }

    public void removeItem(T item) {
        Iterator<T> iterator = items.iterator();
        while (iterator.hasNext()) {
            T i = iterator.next();
            if (i.getId() == item.getId()) {
                if (i.getQuantity() > 1) {
                    i.setQuantity(i.getQuantity() - 1);
                } else {
                    iterator.remove();
                }
                break;
            }
        }
    }

    public List<T> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (T item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public int getTotalQuantity() {
        int total = 0;
        for (T item : items) {
            total += item.getQuantity();
        }
        return total;
    }
}