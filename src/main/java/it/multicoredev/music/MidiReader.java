package it.multicoredev.music;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

import static it.multicoredev.music.SoundGenerator.*;
import static javax.sound.midi.ShortMessage.NOTE_ON;

/**
 * Copyright © 2019 by Lorenzo Magni
 * This file is part of Music.
 * Music is under "The 3-Clause BSD License", you can find a copy <a href="https://opensource.org/licenses/BSD-3-Clause">here</a>.
 * <p>
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
public class MidiReader {
    private final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    private File midi;
    private float speed = 1;
    private int[] trackTick;
    private int[] trackVelocity;

    public MidiReader(File midi) {
        this.midi = midi;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void read() throws IOException, InvalidMidiDataException, InterruptedException {
        Sequence sequence = MidiSystem.getSequence(midi);

        long totalTick = sequence.getTickLength();
        double totalTime = sequence.getMicrosecondLength() * speed;
        Track[] tracks = sequence.getTracks();

        for (int i = 0; i < tracks.length; i++) {
            Track track = tracks[i];
            System.out.println("Track " + i + ": size = " + track.size());
            new Thread(() -> readTrack(track, totalTick, totalTime)).start();
        }
    }

    private void readTrack(Track track, long totalTick, double totalTime) {
        long tickTime = 0;
        int prevVelocity = 0;

        for (int j = 0; j < track.size(); j++) {
            MidiEvent event = track.get(j);
            long time = event.getTick();
            MidiMessage message = event.getMessage();

            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;

                if (sm.getCommand() == ShortMessage.NOTE_ON) {
                    int key = sm.getData1();
                    int octave = (key / 12) - 1;
                    int note = key % 12;
                    String noteName = NOTE_NAMES[note];
                    int velocity = sm.getData2();

                    if (velocity != 0) {
                        float duration = ((time - tickTime) / ((float) totalTick / (float) totalTime)) / 1000;
                        if (duration > 1) {
                            try {
                                System.out.println("Pause, duration: " + duration + " ms");
                                Thread.sleep((int) duration);
                            } catch (InterruptedException ignored) {
                            }
                        }

                        tickTime = time;
                        prevVelocity = velocity;
                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                    } else {
                        float duration = ((time - tickTime) / ((float) totalTick / (float) totalTime)) / 1000;
                        int finalVelocity = prevVelocity;

                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity + " duration: " + duration + " ms");

                        new Thread(() -> playSound(Frequency.fromKey(key).getFrequency(), duration / 1000, finalVelocity, FADE_NONE, WAVE_SQUARE)).start();
                        try {
                            Thread.sleep((int) duration + 13);
                        } catch (InterruptedException ignored) {
                        }
                        tickTime = time;
                    }
                }
            }
        }
    }

    private int getLongestTrackSize(Track[] tracks) {
        int max = 0;
        for (Track track : tracks) {
            if (max < track.size()) max = track.size();
        }

        return max;
    }
}
