package subscriber;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class SubClient extends Thread {
    private Jedis jedis;
    private String channel;
    private JedisPubSub listener;

    public SubClient(JedisPubSub listener) {
        jedis = new Jedis();
        this.listener = listener;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    private void subscribe(){
        System.out.println("SUBCRIBE Channel: " + channel);
        jedis.subscribe(listener, channel);
    }

    public void unsubscribe() {
        System.out.println("UNSUBCRIBE Channel: " + channel);
        listener.unsubscribe(channel);
    }

    @Override
    public void run() {
        try {
            System.out.println();
            System.out.println("---------SUBSCRIBE Begin-------");
            subscribe();
            System.out.println("----------SUBSCRIBE End-------");
            System.out.println();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
