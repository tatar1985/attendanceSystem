package com.tataren.main.model;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class MyAudioPlayer {

          private URL url = null;// �����ļ���URl

        //  private AudioStream as = null;// ������

public MyAudioPlayer() {

          try {

               //url = MyAudioPlayer.class.getResource("D:\\login.wav");// ��ȡ�����ļ���url���ĳ����Լ��������ļ���
        	  File a=new File("D:\\login.wav");
        	  url =a.toURI().toURL();
        	  System.out.println(url);
              InputStream is = url.openStream();// ��������ļ���������
            //  as = new AudioStream(is);

           } catch (Exception e) {

                     e.printStackTrace();

            }

}

// ��������

        public void play() {

               // AudioPlayer.player.start(as);// ��AudioPlayer��̬��Աplayer.start��������

       }

        public static void main(String[] args) {

               new MyAudioPlayer().play();

         }

}