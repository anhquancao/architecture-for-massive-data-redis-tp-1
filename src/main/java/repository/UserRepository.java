package repository;

import config.Config;
import helper.AuthenticateHelper;
import model.User;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class UserRepository implements UserRepositoryInterface {
    private Jedis jedis;
    private AuthenticateHelper authenticateHelper;

    public UserRepository() {
        jedis = new Jedis();
        authenticateHelper = new AuthenticateHelper();
    }

    public Long save(String username, String password) {
        Long id = jedis.incr("next_user_id");
        String key = "user:" + id;

        // map username to id (used when log in)
        jedis.hset("users", username, String.valueOf(id));

        // hash password
        String hashPassword = authenticateHelper.hash(password);

        HashMap<String, String> values = new HashMap<String, String>();
        values.put("username", username);
        values.put("password", hashPassword);

        jedis.hmset(key, values);

        return id;
    }

    public User findUserByUsernameAndPassword(String username, String password) {
        Long id = Long.parseLong(jedis.hget("users", username));
        String key = "user:" + id;
        Map<String, String> values = jedis.hgetAll(key);

        String hashPassword = values.get("password");
        if (authenticateHelper.verifyPassword(password, hashPassword)) {
            return new User(id, username, hashPassword);
        }
        return null;
    }

    public boolean userExist(String username) {
        return jedis.hget("users", username) != null;
    }
}
