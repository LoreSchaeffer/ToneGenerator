package it.multicoredev.music;

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
public enum Frequency {
    NULL(0f),
    C0(16.35f),
    C0s(17.32f),
    D0(18.35f),
    D0s(19.45f),
    E0(20.60f),
    F0(21.83f),
    F0s(23.12f),
    G0(24.50f),
    G0s(25.96f),
    A0(27.50f),
    A0s(29.14f),
    B0(30.87f),
    C1(32.70f),
    C1s(34.65f),
    D1(36.71f),
    D1s(38.89f),
    E1(41.50f),
    F1(43.65f),
    F1s(46.25f),
    G1(49f),
    G1s(51.91f),
    A1(55f),
    A1s(58.27f),
    B1(61.74f),
    C2(65.41f),
    C2s(69.30f),
    D2(73.42f),
    D2s(77.78f),
    E2(82.41f),
    F2(87.31f),
    F2s(92.50f),
    G2(98f),
    G2s(103.83f),
    A2(110f),
    A2s(116.54f),
    B2(123.47f),
    C3(130.84f),
    C3s(138.59f),
    D3(146.83f),
    D3s(155.56f),
    E3(164.81f),
    F3(174.61f),
    F3s(185f),
    G3(196f),
    G3s(207.65f),
    A3(220f),
    A3s(233.08f),
    B3(246.94f),
    C4(261.63f),
    C4s(277.18f),
    D4(293.66f),
    D4s(311.13f),
    E4(329.63f),
    F4(349.23f),
    F4s(369.99f),
    G4(392f),
    G4s(415.3f),
    A4(440f),
    A4s(466.16f),
    B4(493.88f),
    C5(523.25f),
    C5s(554.37f),
    D5(587.33f),
    D5s(622.25f),
    E5(659.25f),
    F5(698.46f),
    F5s(739.99f),
    G5(783.99f),
    G5s(830.61f),
    A5(880f),
    A5s(932.33f),
    B5(987.77f),
    C6(1046.5f),
    C6s(1108.73f),
    D6(1174.66f),
    D6s(1244.51f),
    E6(1318.51f),
    F6(1396.91f),
    F6s(1479.98f),
    G6(1567.98f),
    G6s(1661.22f),
    A6(1760f),
    A6s(1864.66f),
    B6(1975.53f),
    C7(2093f),
    C7s(2217.46f),
    D7(2349.32f),
    D7s(2489.02f),
    E7(2637.02f),
    F7(2793.83f),
    F7s(2959.96f),
    G7(3135.96f),
    G7s(3322.44f),
    A7(3520f),
    A7s(3729.31f),
    B7(3951.07f),
    C8(4186.01f),
    C8s(4434.92f),
    D8(4698.63f),
    D8s(4978.03f),
    E8(5274.04f),
    F8(5587.65f),
    F8s(5919.91f),
    G8(6271.96f),
    G8s(6644.88f),
    A8(7040f),
    A8s(7458.62f),
    B8(7902.13f);

    private float frequency;

    Frequency(float frequency) {
        this.frequency = frequency;
    }

    public float getFrequency() {
        return frequency;
    }

    public static Frequency fromKey(int key) {
        key = key - 20;

        return values()[key];
    }
}
