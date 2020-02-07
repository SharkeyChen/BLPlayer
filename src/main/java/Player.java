import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Player extends JFrame implements ActionListener{
    private JPanel contentPanel, videoPanel;
    private JMenuBar menuBar;
    private JMenu mnFile,mnSetting,mnHelp;
    private JMenuItem mnOpenVideo,mnExit;
    private JPanel panel;
    private JProgressBar progressBar;
    private JPanel progressPanel;
    private JPanel controlPanel;
    private JButton btnStop,btnPlay,btnPause,btnFullscreen;
    private JSlider slider;
    private Timer timer;
    private String ResourceDir = "src/main/resources/";
    EmbeddedMediaPlayerComponent playerComponent;
    private Point pos;
    private BarrageScene barrageScene;
    public static void main(String[] args){

    }

    public Player(Point pos){
        setTitle("Sharkey的播放器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pos = pos;
        setLocation(pos);
        setSize(900,600);
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5,5,5,5));
        contentPanel.setLayout(new BorderLayout(0,0));
        setContentPane(contentPanel);

        /*菜单*/
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        mnFile = new JMenu("文件");
        mnSetting = new JMenu("设置");
        mnHelp = new JMenu("帮助");
        menuBar.add(mnFile);
        menuBar.add(mnSetting);
        menuBar.add(mnHelp);
        mnOpenVideo = new JMenuItem("打开文件");
        mnOpenVideo.addActionListener(this);
        mnFile.add(mnOpenVideo);
        mnExit = new JMenuItem("退出");
        mnExit.addActionListener(this);
        mnFile.add(mnExit);

        //视频窗口中界面
        videoPanel = new JPanel();
        contentPanel.add(videoPanel, BorderLayout.CENTER);
        videoPanel.setLayout(new BorderLayout(0,0));

        playerComponent = new EmbeddedMediaPlayerComponent();
        videoPanel.add(playerComponent);

        //视频窗口控制部分
        panel = new JPanel();
        videoPanel.add(panel, BorderLayout.SOUTH);
        progressPanel = new JPanel();
        panel.add(progressPanel, BorderLayout.NORTH);

        //弹幕界面
        barrageScene = new BarrageScene(pos);

        //Timer
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long total = getMediaPlayer().getLength();
                long curr = getMediaPlayer().getTime();
                float percent = (float)curr / total;
                getProgressBar().setValue((int)(percent * 100));
                System.out.println((getMediaPlayer().getPosition() * getMediaPlayer().getLength() / 1000) + "SS");
                barrageScene.CheckBarrageList(getMediaPlayer().getPosition() * getMediaPlayer().getLength() / 1000);
            }
        });

        //进度条
        progressBar = new JProgressBar();
        progressPanel.add(progressBar);

        progressBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                int x = e.getX();
                getMediaPlayer().setTime((long)((float)x / progressBar.getWidth() * getMediaPlayer().getLength()));
            }
        });
        progressBar.setStringPainted(true);
        controlPanel = new JPanel();
        panel.add(controlPanel, BorderLayout.SOUTH);

        //停止按钮
        btnStop = new JButton("停止");
        btnStop.addActionListener(this);
        controlPanel.add(btnStop);

        //播放按钮
        btnPlay = new JButton();
        btnPlay.addActionListener(this);
        DIYButton(btnPlay, ResourceDir + "播放.png");
        btnPlay.setBorderPainted(false);
        btnPlay.setBorder(null);
        controlPanel.add(btnPlay);

        //暂停按钮
        btnPause = new JButton("暂停");
        btnPause.addActionListener(this);
        controlPanel.add(btnPause);

        //音量控制
        slider = new JSlider();
        slider.setValue(80);
        slider.setMaximum(100);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                getMediaPlayer().setVolume(slider.getValue());
            }
        });
        controlPanel.add(slider);

        //全屏按钮
        btnFullscreen = new JButton("全屏");
        getMediaPlayer().setFullScreenStrategy(new DefaultAdaptiveRuntimeFullScreenStrategy(this));
        btnFullscreen.addActionListener(this);
        controlPanel.add(btnFullscreen);

        this.setVisible(true);

        //视频表面鼠标点击
        playerComponent.getVideoSurface().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("surface clicked!");
//                barrageScene.Add_Barrage("弹幕", 50);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //窗口关闭
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                exitActionPerformed();
                if(timer.isRunning()){
                    timer.stop();
                }
            }
        });

        //窗口变化监听器
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                barrageScene.setLocation(getBarPanelPos());
            }

            @Override
            public void componentResized(ComponentEvent e){
                super.componentResized(e);
                System.out.println("变形了");
                barrageScene.setBarrageWH(getWidth(),getHeight());
                System.out.println(getWidth() + " " + getHeight());
            }
        });

        //视频播放器播放状态监听器
        getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventListener() {
            @Override
            public void mediaChanged(MediaPlayer mediaPlayer, libvlc_media_t libvlc_media_t, String s) {

            }

            @Override
            public void opening(MediaPlayer mediaPlayer) {

            }

            @Override
            public void buffering(MediaPlayer mediaPlayer, float v) {

            }

            @Override
            public void playing(MediaPlayer mediaPlayer) {
                timer.start();
            }

            @Override
            public void paused(MediaPlayer mediaPlayer) {
                timer.stop();
            }

            @Override
            public void stopped(MediaPlayer mediaPlayer) {
                timer.stop();
            }

            @Override
            public void forward(MediaPlayer mediaPlayer) {

            }

            @Override
            public void backward(MediaPlayer mediaPlayer) {

            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {

            }

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long l) {

            }

            @Override
            public void positionChanged(MediaPlayer mediaPlayer, float v) {

            }

            @Override
            public void seekableChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void pausableChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void titleChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void snapshotTaken(MediaPlayer mediaPlayer, String s) {

            }

            @Override
            public void lengthChanged(MediaPlayer mediaPlayer, long l) {

            }

            @Override
            public void videoOutput(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void scrambledChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void elementaryStreamAdded(MediaPlayer mediaPlayer, int i, int i1) {

            }

            @Override
            public void elementaryStreamDeleted(MediaPlayer mediaPlayer, int i, int i1) {

            }

            @Override
            public void elementaryStreamSelected(MediaPlayer mediaPlayer, int i, int i1) {

            }

            @Override
            public void corked(MediaPlayer mediaPlayer, boolean b) {

            }

            @Override
            public void muted(MediaPlayer mediaPlayer, boolean b) {

            }

            @Override
            public void volumeChanged(MediaPlayer mediaPlayer, float v) {

            }

            @Override
            public void audioDeviceChanged(MediaPlayer mediaPlayer, String s) {

            }

            @Override
            public void chapterChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void error(MediaPlayer mediaPlayer) {

            }

            @Override
            public void mediaPlayerReady(MediaPlayer mediaPlayer) {

            }

            @Override
            public void mediaMetaChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void mediaSubItemAdded(MediaPlayer mediaPlayer, libvlc_media_t libvlc_media_t) {

            }

            @Override
            public void mediaDurationChanged(MediaPlayer mediaPlayer, long l) {

            }

            @Override
            public void mediaParsedChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void mediaParsedStatus(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void mediaFreed(MediaPlayer mediaPlayer) {

            }

            @Override
            public void mediaStateChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void mediaSubItemTreeAdded(MediaPlayer mediaPlayer, libvlc_media_t libvlc_media_t) {

            }

            @Override
            public void newMedia(MediaPlayer mediaPlayer) {

            }

            @Override
            public void subItemPlayed(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void subItemFinished(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void endOfSubItems(MediaPlayer mediaPlayer) {

            }
        });

    }

    public EmbeddedMediaPlayer getMediaPlayer(){
        return playerComponent.getMediaPlayer();
    }

    public void DIYButton(JButton btn, String str){
        btn.setIcon(new ImageIcon(str));
    }

    public void exitActionPerformed(){
        getMediaPlayer().release();
        System.exit(0);
    }

    public JProgressBar getProgressBar(){
        return progressBar;
    }

    //点击响应
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mnOpenVideo){
            JFileChooser chooser = new JFileChooser("G:\\pythontest\\Chatroom\\BLSpider");
            int v = chooser.showOpenDialog(null);
            if(v == JFileChooser.APPROVE_OPTION){
                File file = chooser.getSelectedFile();
                try{
                    getMediaPlayer().playMedia(file.getAbsolutePath());
                }catch (Exception ex){
                    System.out.println("Error");
                }
                System.out.println("视频读取成功！");
                this.ResourceDir = chooser.getCurrentDirectory().getAbsolutePath();
                barrageScene.ReadBarrageFile(this.ResourceDir);
            }
        }
        else if(e.getSource() == mnExit){
            exitActionPerformed();
        }
        else if(e.getSource() == btnStop){
            getMediaPlayer().stop();
//            getProgressBar().setValue(-1);
        }
        else if(e.getSource() == btnPlay){
            getMediaPlayer().play();
        }
        else if(e.getSource() == btnPause){
            getMediaPlayer().pause();
        }
        else if(e.getSource() == btnFullscreen){
            if(getMediaPlayer().isFullScreen() == false){
                getMediaPlayer().setFullScreen(true);
                System.out.println("Full Screen!");
            }
            else if(getMediaPlayer().isFullScreen() == true){
                getMediaPlayer().setFullScreen(false);
                System.out.println("Small Screen!");
            }
        }
    }

    public Point getBarPanelPos(){
        return this.getLocation();
    }

    public void setPosition(Point pos){
        this.pos = pos;
    }

    public void info(){
        System.out.println(videoPanel.getX() + " " + videoPanel.getY() + " " + videoPanel.getHeight() + " " + videoPanel.getWidth());
    }
}
