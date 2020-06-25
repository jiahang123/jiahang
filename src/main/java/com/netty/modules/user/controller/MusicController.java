package com.netty.modules.user.controller;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Api(value = "music")
@RequestMapping("/music")
class MusicController extends Thread{

    //tts语音播报
    private static List<String> strList=new ArrayList<>();
    private static Thread tts = new Thread();
    //播放音乐
    private static AudioStream as=null;
    private static Thread t = new Thread();

    /*文字转语音播报开始*/
    @ApiOperation(value="播放音频", notes="test: 仅1和2有正确返回")
    @GetMapping("/ttsStart")
    public void  ttsStart(String text) {
        ActiveXComponent activeXComponent = new ActiveXComponent("Sapi.SpVoice");
        //运行时输出语音内容
        Dispatch dispatch = activeXComponent.getObject();
        //文件名称
        long t1=System.currentTimeMillis();
        String strName= String.valueOf(t1);
        strList.add(strName);
        try{
            //生成语音文件
            activeXComponent = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch fileStreamDispatch = activeXComponent.getObject();
            //音频
            activeXComponent = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch audioDispatch = activeXComponent.getObject();
            //设置文件流格式
            Dispatch.putRef(fileStreamDispatch, "Format", audioDispatch);
            //设置音频流格式
            Dispatch.put(audioDispatch, "Type", new Variant(22));
            //调用输出文件流打开方法，创建一个.wav .mp3 .mp4   .wma文件
            Dispatch.call(fileStreamDispatch, "Open", new Variant("D:\\"+strName+".wav"),new Variant(3),new Variant(true));
            //设置声音对象的音频流输出流为输出文件对象
            Dispatch.putRef(dispatch, "AudioOutputStream", fileStreamDispatch);
            //设置音量0-100
            Dispatch.put(dispatch, "Volume", new Variant(100));
            //设置朗读速度
            Dispatch.put(dispatch, "Rate", new Variant(-2));
            //开始朗读
            Dispatch.call(dispatch, "Speak",new Variant(text));
            //关闭输出文件流
            Dispatch.call(fileStreamDispatch, "Close");
            Dispatch.putRef(dispatch, "AudioOutputStream", null);
            audioDispatch.safeRelease();
            fileStreamDispatch.safeRelease();
            dispatch.safeRelease();
            activeXComponent.safeRelease();

            //判断线程状态
            if(!tts.isAlive()){
                tts = new Thread(){
                    public void run(){
                        //是否正在播放音乐
                        if(t.isAlive()){
                            musicStop();
                        }
                        //播放音频
                        ttsPlayer();
                    }
                };
                tts.start();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //播放音频
    public void ttsPlayer(){
        try {
            for(int i=0;i<strList.size();i++){
                //输出音频时长
                File file=new File("D:\\"+strList.get(i).toString()+".wav");
                AudioFile mp3= AudioFileIO.read(file);
                FileInputStream fileau=new FileInputStream("D:\\"+strList.get(i)+".wav");
                as=new AudioStream(fileau);
                //开始播放
                AudioPlayer.player.start(as);
                Thread.sleep(mp3.getAudioHeader().getTrackLength()*1000+500);
            }
            if(strList.size()>0){
                ttsPlayer();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*文字转语音播报停止*/
    @GetMapping("/ttsStop")
    public void  ttsStop(Integer index){
        try {
            //全部终止播放
            if(index==-1){
                //停止线程
                tts.interrupt();
                //停止播报
                AudioPlayer.player.stop(as);
                //清空列表所有元素
                strList.clear();
            }else if(index>-1){
                //将指定脚标删除
                strList.remove(strList.get(index));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*开始播放音乐*/
    @GetMapping("/playStart")
    public void musicStart(String path){
        //判断是否正在语音播报
        if(!tts.isAlive()){
            //停止线程
            t.interrupt();
            //停止播放
            AudioPlayer.player.stop(as);
            t = new Thread(){
                public void run(){
                    try{
                        //输出音频时长
                        File file=new File(path);
                        AudioFile mp3= AudioFileIO.read(file);
                        //System.out.println(mp3.getAudioHeader().getTrackLength());
                        //循环播放
                        while (true){
                            FileInputStream fileau=new FileInputStream(path);
                            as=new AudioStream(fileau);
                            //开始播放
                            AudioPlayer.player.start(as);
                            Thread.sleep(mp3.getAudioHeader().getTrackLength()*1000+500);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            //启动线程
            t.start();
        }
    }
    /*停止播放音乐*/
    @GetMapping("/playStop")
    public void musicStop(){
        try {
            //判断是否正在语音播报
            if(!tts.isAlive()) {
                //停止线程
                t.interrupt();
                //停止播放
                AudioPlayer.player.stop(as);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
