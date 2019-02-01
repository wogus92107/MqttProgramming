import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sender {
    public static void main(String[] args) throws Exception{
        //MQTT Broker 연결하기
        MqttClient mqttClient = new MqttClient(
                "tcp://192.168.3.250:1883",
                MqttClient.generateClientId(),//알아서 생성할것.
                null//연결 안될 일 없으니까 local에 저장하지 말아라
        );//연결문자열, 클라이언트ID

        mqttClient.connect();

        //메시지 보내기
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                for(int i =0; i<1012324142 ; i++){
                    byte[] payload = ("메롱" + i).getBytes();
                    try {
                        mqttClient.publish("/topic1", payload,0,false);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executorService.submit(task);

        Thread.sleep(60000);


        //연결 끊기
        mqttClient.disconnect();
        mqttClient.close();

        //1대 다 다 대 다 통신이 가능하다.


    }

}
