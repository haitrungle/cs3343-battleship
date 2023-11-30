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

import static org.testng.AssertJUnit.*;

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
	private SocketBackend server;
    private SocketBackend client;
    private final int port = 1234;
    @Before
    public void setup() throws Exception{


        new Thread(()->{
            server = new Server(port);

        }).start();

        client = new Client(null, port);


    }
    @After
    public void teardown() throws Exception{
            server.close();
            client.close();
            Thread.sleep(100);
    }

    @Test
    public void test_backend_socket_backend_1(){
        Message m = Message.InitMsg();
        client.sendMessage(m);
    }

    @Test
    public void test_backend_socket_backend_2(){
        new Thread(()->{
            client.waitForMessage();

        }).start();
        Message m = Message.InitMsg();
        client.sendMessage(m);
    }
//    @Test
//    public void test_backend_socket_backend_3() throws Exception {
//
//
//
//        new Thread(()->{
//            SocketBackend c = new Client(null, 1234);
//        }).start();
//
//
//    }




}
