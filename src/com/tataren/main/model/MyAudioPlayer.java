package com.tataren.main.model;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class MyAudioPlayer {

          private URL url = null;// 音乐文件的URl

        //  private AudioStream as = null;// 播放器

public MyAudioPlayer() {

          try {

               //url = MyAudioPlayer.class.getResource("D:\\login.wav");// 获取音乐文件的url（改成你自己的音乐文件）
        	  File a=new File("D:\\login.wav");
        	  url =a.toURI().toURL();
        	  System.out.println(url);
              InputStream is = url.openStream();// 获得音乐文件的输入流
            //  as = new AudioStream(is);

           } catch (Exception e) {

                     e.printStackTrace();

            }

}

// 播放音乐

        public void play() {

               // AudioPlayer.player.start(as);// 用AudioPlayer静态成员player.start播放音乐

       }

        public static void main(String[] args) {

               new MyAudioPlayer().play();

         }

}