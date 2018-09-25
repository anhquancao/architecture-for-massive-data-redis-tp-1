package client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.HashSet;
import java.util.Set;

public class Client {
    private static final String JEDIS_SERVER = ;
    private Set<String> channels;
    private Jedis jedis;
    

    private Client() {
        jedis = new Jedis();
        this.channels = new HashSet<String>();
    }

    public void subscribe(String channel) {
        channels.add(channel);
    }

    public void unsubscribe(String channel) {
        channels.remove(channel);
    }

    private JedisPubSub setupSubscriber() {
        final JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onUnsubscribe(String channel, int subscribedChannels) {
                System.out.println("onUnsubscribe");
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onPUnsubscribe(String pattern, int subscribedChannels) {
            }

            @Override
            public void onPSubscribe(String pattern, int subscribedChannels) {
            }

            @Override
            public void onPMessage(String pattern, String channel, String message) {
            }

            @Override
            public void onMessage(String channel, String message) {
                System.out.println(message);
            }
        };
        new Thread(new Runnable() {

            public void run() {
                try {
                    System.out.println("Connecting");
                    Jedis jedis = new Jedis(JEDIS_SERVER);
                    log("subscribing");
                    jedis.subscribe(jedisPubSub, "test");
                    log("subscribe returned, closing down");
                    jedis.quit();
                } catch (Exception e) {
                    log(">>> OH NOES Sub - " + e.getMessage());
                    // e.printStackTrace();
                }
            }
        }, "subscriberThread").start();
        return jedisPubSub;
    }

    public void start() {

    }


    public static void main(String[] args) {

    }
}
