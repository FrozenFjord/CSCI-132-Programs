// Lachlan Mullen

import java.lang.Comparable;

public class Customer implements Comparable<Customer> {

    private String name;
    private String order;
    private long waitTime;

    private boolean isVIP;

    public Customer(String name, String order, boolean isVIP) {
        this.name = name;
        this.order = order;
        this.waitTime = System.nanoTime();
        this.isVIP = isVIP;
    }

    @Override
    public int compareTo(Customer other) {
        if (this.isVIP != other.isVIP) {
            return this.isVIP ? -1 : 1;
        }
        return Long.compare(this.waitTime, other.waitTime);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean isVIP) {
        this.isVIP = isVIP;
    }

    @Override
    public String toString() {
        return name + " - " + order + " - " + waitTime + " - " + isVIP;
    }
}
