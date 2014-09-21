/*
 * Copyright 2013 Adrien Favre-Bully, Florian Froese, Adrian Schmidmeister
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package ch.ethz.student.dejavu.strings;

import org.junit.Assert;
import org.junit.Test;

public class SoundexMetricTest {

  @Test
  public void testNullArgument() {
    try {
      SoundexMetric.getInstance().computeCode(null);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      // success
    }
  }

  @Test
  public void testEmptyArgument() {
    try {
      SoundexMetric.getInstance().computeCode(null);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      // success
    }
  }

  @Test
  public void testCode() {

    SoundexMetric m = SoundexMetric.getInstance();

    // archives.gov examples
    Assert.assertEquals("W252", m.computeCode("Washington"));
    Assert.assertEquals("L000", m.computeCode("Lee"));
    Assert.assertEquals("G362", m.computeCode("Gutierrez"));
    Assert.assertEquals("P236", m.computeCode("Pfister"));
    Assert.assertEquals("J250", m.computeCode("Jackson"));
    Assert.assertEquals("T522", m.computeCode("Tymczak"));
    Assert.assertEquals("V532", m.computeCode("VanDeusen"));
    Assert.assertEquals("A261", m.computeCode("Ashcraft"));

    // other examples
    Assert.assertEquals("F161", m.computeCode("Favre-Bully"));
    Assert.assertEquals("F620", m.computeCode("Froese"));
    Assert.assertEquals("S535", m.computeCode("Schmidtmeister"));
    Assert.assertEquals("G400", m.computeCode("Geel"));
  }

}
