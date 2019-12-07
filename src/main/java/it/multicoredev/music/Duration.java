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
public enum Duration {
    SEMIBREVE(1f),              // 4/4
    SEMIBREVE_DOTTED(SEMIBREVE.getDuration() + (SEMIBREVE.getDuration() / 2)),
    MINIMA(0.5f),               // 2/4
    MINIMA_DOTTED(MINIMA.getDuration() + (MINIMA.getDuration() / 2)),
    SEMIMINIMA(0.25f),          // 1/4
    SEMIMINIMA_DOTTED(SEMIMINIMA.getDuration() + (SEMIMINIMA.getDuration() / 2)),
    CROMA(0.125f),              // 1/8
    CROMA_DOTTED(CROMA.getDuration() + (CROMA.getDuration() / 2)),
    SEMICROMA(0.0625f),         // 1/16
    SEMICROMA_DOTTED(SEMICROMA.getDuration() + (SEMICROMA.getDuration() / 2)),
    BISCROMA(0.03125f),         // 1/32
    BISCROMA_DOTTED(BISCROMA.getDuration() + (BISCROMA.getDuration() / 2)),
    SEMIBISCROMA(0.015625f),    // 1/64
    SEMIBISCROMA_DOTTED(SEMIBISCROMA.getDuration() + (SEMIBISCROMA.getDuration() / 2));

    private float duration;

    Duration(float duration) {
        this.duration = duration;
    }

    public float getDuration() {
        return duration;
    }
}
