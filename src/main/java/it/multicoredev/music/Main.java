package it.multicoredev.music;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import static it.multicoredev.music.Note.*;

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
public class Main {
    public static void main(String[] args) throws LineUnavailableException {
        //Note[] song = new Note[]{F4, F4, C5, F4, A4s, F4, A4, F4};
        Note[] song = new Note[]{A4};

        for (Note note : song) {
            createTone(note.getFrequency(), 100);
        }
    }

    private static void createTone(float frequency, int volume) throws LineUnavailableException {
        float rate = 48000;
        byte[] buffer;
        AudioFormat audioFormat;

        buffer = new byte[1];
        audioFormat = new AudioFormat(rate, 8, 1, true, false);

        SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        for (int i = 0; i < rate; i++) {
            double angle = (i / rate) * frequency * 2.0 * Math.PI;
            buffer[0] = (byte) (Math.sin(angle) * volume);
            sourceDataLine.write(buffer, 0, 1);
        }

        sourceDataLine.drain();
        sourceDataLine.stop();
        sourceDataLine.close();
    }
}
