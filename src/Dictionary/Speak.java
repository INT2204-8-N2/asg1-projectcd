package Dictionary;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


public class Speak extends Thread{

    public static void Speak(String text) {

        try {
            try {
                System.setProperty("freetts.voices",
                        "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
                VoiceManager voiceManager = VoiceManager.getInstance();
                Voice syntheticVoice = voiceManager.getVoice("kevin16");
                syntheticVoice.allocate();
                syntheticVoice.speak(text);
                syntheticVoice.deallocate();
            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
