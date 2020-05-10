package edu.whoi.redispub;

import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.util.Random;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Jedis jedis = new Jedis(args[0]);
     
        Random r = new Random();

        while (true) {
            JSONObject obj = new JSONObject();
            obj.put("time", ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME));

            String channel = "/updates/" + r.nextInt(5);
            System.out.println("Publishing to channel " + channel);

            jedis.publish(channel, obj.toString());

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
