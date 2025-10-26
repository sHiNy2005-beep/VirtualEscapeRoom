package com.speech;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Speek {
    private static final String VOICE_NAME = "kevin16";

    /**
     * @param text text to speak (ignored when null/empty)
     */
    public static void speak(String text) {
        if (text == null) return;
        text = text.trim();
        if (text.isEmpty()) return;

        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");                
    /**
     * The method is defensive: it checks that the voice exists and ensures
     * deallocation runs even if speak() throws an exception.
    */
        VoiceManager voiceManager = VoiceManager.getInstance();
        if (voiceManager == null) {
            System.err.println("VoiceManager not available");
            return;
        }

        Voice voice = voiceManager.getVoice(VOICE_NAME);
        if (voice == null) {
            System.err.println("Voice not found: " + VOICE_NAME);
            return;
        }
        try {
            voice.allocate();
            try {
                voice.speak(text);
            } catch (Throwable t) {
                // Log speak errors but continue to deallocate in finally
                System.err.println("Error while speaking: " + t.getMessage());
            }
        } catch (Throwable t) {
            System.err.println("Error allocating voice: " + t.getMessage());
        } finally {
            try {
                voice.deallocate();
            } catch (Throwable t) {
                // ignore deallocation errors
            }
        }
    }
}