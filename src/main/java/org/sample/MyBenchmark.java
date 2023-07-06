/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.TimeUnit;

// We're interested in the average running time of our parseInt benchmark
@BenchmarkMode(Mode.AverageTime)
// We can set the time unit of the average time output
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// In order to trim down the amount of time it takes to run this benchmark, we set Fork to 1,
// meaning that all the benchmark runs that JMH does are executed in a single JVM,
// and they wouldn't be executed in 5 different JVMs, 1 after the other,
// as it would with a default configuration of 5 forks.
@Fork(1)
// The scope of the state is the complete benchmark,
// it will now treat that this single parseInt benchmark method
// as 3 separate parameterized benchmarks running them with the values added in @Param
@State(Scope.Benchmark)
public class MyBenchmark {

    // State management from JMH
    // JMH assigns each of these values to the toParse field for three different runs.
    // we need to also indicate that this makes the benchmark class a state class using State annotation.
    @Param({"1", "12345", "2147483647"})
    String toParse;

    @Benchmark
    public Integer parseInt() throws Exception {
        // In this benchmark we're going to look at the parseInt methods defined on Integer.
        // Let's assume we're interested in the performance behavior of the parseInt method on Integer.
        // How can we benchmark this using JMH?
        // The simplest approach will be to just call the method with some value.
//        Integer.parseInt("1234");
        // Sometimes the compiler might conclude that nothing important happens here
        // and eliminates the given statement in hte bytecode that's generated for the parseInt()
        // To ensure that doesn't happen, we're going to return the value calculated by the parseInt().
        return Integer.parseInt(toParse);

    }

}
