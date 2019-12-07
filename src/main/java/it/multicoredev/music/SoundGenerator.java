package it.multicoredev.music;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

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
public class SoundGenerator {
    private static int SAMPLE_RATE = 44100;

    public static final byte FADE_NONE = 0;
    public static final byte FADE_LINEAR = 1;
    public static final byte FADE_QUADRATIC = 2;

    public static final byte WAVE_SIN = 0;
    public static final byte WAVE_SQUARE = 1;
    public static final byte WAVE_TRIANGLE = 2;
    public static final byte WAVE_SAWTOOTH = 3;

    private static double BYTE_OSCI = 127.0;

    private static double noFade(double i, int max) {
        return 1;
    }

    private static double linearFade(double i, int max) {
        return (max - (float) i) / max;
    }

    private static double quadraticFade(double i, int max) {
        return (-1 / Math.pow(max, 2)) * Math.pow(i, 2) + 1;
    }

    private static double sinWave(double i, double period) {
        return Math.sin(2.0 * Math.PI * i / period);
    }

    private static double squareWave(double i, double period) {
        return (i % period) / period < .5 ? 1 : -1;
    }

    private static double triangleWave(double i, double period) {
        return Math.asin(Math.sin((2 * Math.PI) * (i / period)));
    }

    private static double sawtoothWave(double i, double period) {
        return -1 + 2 * ((i % period) / period);
    }

    public static void playSounds(double[] freqs, double duration, double volume, byte fade, byte wave) {
        if (freqs.length == 0) {
            System.err.println("No frequences to play !");
            return;
        }
        volume = volume / freqs.length;

        double[][] soundData = new double[freqs.length][];

        for (int i = 0; i < soundData.length; i++) {
            soundData[i] = generateSoundData(freqs[i], duration, volume, fade, wave);
        }
        byte[] freqData = new byte[soundData[0].length];

        for (int i = 0; i < soundData[0].length; i++) {
            for (int j = 0; j < soundData.length; j++) {
                freqData[i] += (byte) (soundData[j][i]);
            }
        }

        try {
            final AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
            SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat);
            line.open(audioFormat, SAMPLE_RATE);
            line.start();
            line.write(freqData, 0, freqData.length);
            line.drain();
            line.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playSound(double freq, double duration, double volume, byte fade, byte wave) {
        double[] soundData = generateSoundData(freq, duration, volume, fade, wave);
        byte[] freqData = new byte[soundData.length];

        for (int i = 0; i < soundData.length; i++) {
            freqData[i] = (byte) soundData[i];
        }

        try {
            final AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
            SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat);
            line.open(audioFormat, SAMPLE_RATE);
            line.start();
            line.write(freqData, 0, freqData.length);
            line.drain();
            line.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static double generateValue(double i, double duration, double freq, double volume, byte fade, byte wave) {
        double period = (double) SAMPLE_RATE / freq;
        double fade_data = 0;
        double wave_data = 0;
        int length = (int) (duration * SAMPLE_RATE);

        switch (fade) {
            case FADE_NONE:
                fade_data = noFade(i, length);
                break;
            case FADE_LINEAR:
                fade_data = linearFade(i, length);
                break;
            case FADE_QUADRATIC:
                fade_data = quadraticFade(i, length);
                break;
        }
        switch (wave) {
            case WAVE_SIN:
                wave_data = sinWave(i, period);
                break;
            case WAVE_SQUARE:
                wave_data = squareWave(i, period);
                break;
            case WAVE_TRIANGLE:
                wave_data = triangleWave(i, period);
                break;
            case WAVE_SAWTOOTH:
                wave_data = sawtoothWave(i, period);
                break;
        }
        return (wave_data * BYTE_OSCI * fade_data * volume);
    }

    public static double[] generateSoundData(double freq, double duration, double volume, byte fade, byte wave) {
        double[] freqData = new double[(int) (duration * SAMPLE_RATE)];

        for (int i = 0; i < freqData.length; i++) {
            freqData[i] = generateValue(i, duration, freq, volume, fade, wave);
        }
        return freqData;
    }
}
