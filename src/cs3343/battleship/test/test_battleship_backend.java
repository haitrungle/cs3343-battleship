package cs3343.battleship.test;


import java.io.*;
import java.net.ServerSocket;


import cs3343.battleship.game.*;
import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;

import cs3343.battleship.exceptions.*;
import cs3343.battleship.backend.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.time.ZoneOffset;


import static org.junit.Assert.*;

public class test_battleship_backend {

    @Test
    public void test_message_1(){
        Message m = Message.InitMsg();
        assertEquals(Message.Type.INIT, m.getType());
    }
    @Test
    public void test_message_2(){
        Message m = Message.ShotMsg(new Position(1,1));
        assertEquals(Message.Type.SHOT, m.getType());
    }
    @Test
    public void test_message_3(){
        Message m = Message.ResultMsg(false);
        assertEquals(Message.Type.RESULT, m.getType());
    }
    @Test
    public void test_message_4(){
        Message m = Message.LostMsg();
        assertEquals(Message.Type.LOST, m.getType());
    }

    @Test
    public void test_message_5(){
        Message m = Message.LostMsg();
        int cur_min = Instant.now().atZone(ZoneOffset.UTC).getMinute();
        assertEquals(cur_min, m.getTimestamp().atZone(ZoneOffset.UTC).getMinute());
    }

    @Test
    public void test_message_6(){

        Message m = Message.ShotMsg(new Position(1,1));
        boolean b = m.getShot().equals(new Position(1,1));
        assertTrue(b);
    }

    @Test
    public void test_message_7(){
        Message m = Message.ResultMsg(false);
        assertFalse(m.getHit());
    }






}

//class test_backend_socket_backend{
//    private SocketBackend server;
//    private SocketBackend client;
//    private Thread server_start;
//    private final int port = 1234;
//    @Before
//    public void setup() throws Exception{
//
//
//        server_start = new Thread(()->{
//            try {
//                server = new Server(port);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//
//        });
//        server_start.start();
//
//        client = new Client(null, port);
//
//
//    }
//    @After
//    public void teardown() throws Exception{
//        server_start.join();
//        server.close();
//        client.close();
//
//        Thread.sleep(100);
//    }
//
//    @Test
//    public void test_backend_socket_backend_1(){
//        Message m = Message.InitMsg();
//        client.sendMessage(m);
//    }
//
//    @Test
//    public void test_backend_socket_backend_2() throws Exception {
//
//        Thread client_wait_message = new Thread(()->{
//            try {
//                client.waitForMessage();
//            } catch (Exception e) {
//                assertEquals("Error reading incoming message: null" ,e.getMessage());
//            }
//        });
//        client_wait_message.start();
//        Message m = Message.InitMsg();
//        client.sendMessage(m);
//    }
//
//    @Test
//    public void test_backend_socket_backend_3() {
//
//        Exception e = assertThrows(Exception.class,()->new Server(1234));
//        assertEquals("Error initializing Server: Address already in use: JVM_Bind", e.getMessage());
//    }
//
//    @Test
//    public void test_backend_socket_backend_4() throws Exception {
//        Message m = Message.ResultMsg(true);
//        client.sendMessage(m);
//        Message message = server.waitForMessage();
//        assertEquals(m.getType(),message.getType());
//
//
//    }
//}
