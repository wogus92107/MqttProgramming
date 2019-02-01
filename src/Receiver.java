import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Receiver {
    public static void main(String[] args) throws Exception{
        //MQTT Broker 연결하기
        MqttClient mqttClient = new MqttClient(
                "tcp://192.168.3.250:1883",
                MqttClient.generateClientId(),//알아서 생성할것.
                null//연결 안될 일 없으니까 local에 저장하지 말아라
        );//연결문자열, 클라이언트ID

        mqttClient.connect();

        //메시지 받기//Call back: 어떤 이벤트가 발생하면 자동적으로 실행하겠다
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {//브로커와 연결이 끊어졌을 경우//이것도 필요없음.

            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {//메시지가 도착했을 떄
                byte[] payload = mqttMessage.getPayload();
                String data = new String(payload);//****
                System.out.println("받은 데이터: " + data);//receive는 disconnect가 되면 안된다. 어느정도 대기 시간을 줘야함.
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {//메시지를 전송완료했을때.Receiver기때문에 필요없다.

            }
        });
        //구독 시작을 하겠다.
        mqttClient.subscribe("/topic1");

        ///10초간 프로세스가 종료되지 않도록 지연.
        Thread.sleep(100000);

        //연결 끊기

        mqttClient.disconnect(); // 종료 안되므로 close까지 해준다.
        mqttClient.close();
    }


}
