package menu;



import java.io.IOException;
import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.media.control.VideoControl;

public class VideoMidlet implements CommandListener {

    private Display display;
    private Form form;
    private Command exit,back,capture,camera;
    private Player player;
    private VideoControl videoControl;
    private Video video;
    MIDlet m = null;
    public VideoMidlet(MIDlet M) {
        m=M;
        exit = new Command("Exit", Command.EXIT, 0);
        camera = new Command("Camera", Command.SCREEN, 0);
        back = new Command("Back", Command.BACK, 0);
        capture = new Command("Capture", Command.SCREEN, 0);

        form = new Form("Capture Video");
        form.addCommand(camera);
        form.setCommandListener(this);
    }



    public void commandAction(Command c, Displayable s) {
        if (c == exit) {
           // m.destroyApp(true);
            m.notifyDestroyed();
        } else if (c == camera) {
            showCamera();
        } else if (c == back)
            display.setCurrent(form);
        else if (c == capture) {
            video = new Video(this);
            video.start();
        }
    }

    public void showCamera() {
        try {
            player = Manager.createPlayer("capture://video");
            player.realize();

            videoControl = (VideoControl)player.getControl("VideoControl");
            Canvas canvas = new VideoCanvas(this, videoControl);
            canvas.addCommand(back);
            canvas.addCommand(capture);
            canvas.setCommandListener(this);
            display.setCurrent(canvas);
            player.start();
        } catch (IOException ioe) {} catch (MediaException me) {}
    }

    class Video extends Thread {
        VideoMidlet midlet;
        public Video(VideoMidlet midlet) {
            this.midlet = midlet;
        }

        public void run() {
            captureVideo();

        }

        public void captureVideo() {
            try {
                byte[] raw = videoControl.getSnapshot(null);
                Image image = Image.createImage(raw, 0, raw.length);
                form.append(image);
                display.setCurrent(form);

                player.close();
                player = null;
                videoControl = null;
            } catch (MediaException me) { }
        }
    };
}
