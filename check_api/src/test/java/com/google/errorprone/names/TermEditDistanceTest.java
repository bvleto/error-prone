/*
 * Copyright 2017 The Error Prone Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.errorprone.names;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests for TermEditDistance */
@RunWith(JUnit4.class)
public class TermEditDistanceTest {

  @Test
  public void getNormalizedEditDistance_returnsMatch_withPermutedTerms() {
    TermEditDistance termEditDistance = new TermEditDistance();
    String sourceIdentifier = "fooBarBaz";
    String targetIdentifier = "bazFooBar";

    double distance =
        termEditDistance.getNormalizedEditDistance(sourceIdentifier, targetIdentifier);

    assertThat(distance).isEqualTo(0.0);
  }

  @Test
  public void getNormalizedEditDistance_isSymmetric_withExtraTerm() {
    TermEditDistance termEditDistance = new TermEditDistance();
    String identifier = "fooBarBaz";
    String otherIdentifier = "barBaz";

    double distanceFwd = termEditDistance.getNormalizedEditDistance(identifier, otherIdentifier);
    double distanceBwd = termEditDistance.getNormalizedEditDistance(otherIdentifier, identifier);

    assertThat(distanceFwd).isEqualTo(distanceBwd);
  }

  @Test
  public void getNormalizedEditDistance_returnsNoMatch_withDifferentTerms() {
    TermEditDistance termEditDistance =
        new TermEditDistance((s, t) -> s.equals(t) ? 0.0 : 1.0, (s, t) -> 1.0);

    String sourceIdentifier = "fooBar";
    String targetIdentifier = "bazQux";

    double distance =
        termEditDistance.getNormalizedEditDistance(sourceIdentifier, targetIdentifier);

    assertThat(distance).isEqualTo(1.0);
  }
}
