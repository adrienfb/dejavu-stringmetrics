# DéjàVu Stringmetrics

This project is a collection of various string similiarity algorithms
implemented in Java and conveniently packaged into a single jar file with no
dependencies.

## Algorithms

* Hamming Distance
* Jaro Distance
* Jaro-Winkler Distance
* Dice Coefficient Metric
* Levenshtein Distance
* Damerau-Levenshtein Distance
* Needleman Wunsch Distance
* Smith Waterman Distance
* Optimal String Alignment Distance
* Gotoh Distance
* Ratcliff/Oberhshelp String Matching
* Soundex Coding

The following metrics are vector based metrics. The strings are represented as
frequency count vectors (bag of words model).

* L1 Distance
* L2 Distance
* Cosine Distance
* Jaccard Distance
* Dice Coefficient
* Matching Coefficient
* Overlap Coefficient
* QGram Metric
* Tversky Index

## Usage

The project comes in form of a single jar (plus a jar containing the source code
and another jar for the javadoc, if you are interested).

To get a working jar you can:

1. Download one of the prepackaged versions
2. Clone this repository and use `mvn package` to generate a jar from the latest
 source
3. Add the project as a maven dependency `to come`

TODO: briefly describe architecture (string and vector metrics, interfaces, default getter, builders)
TODO: code example for one metric

## License

The project is released under the Apache 2.0 license. In plain language that
means that you can modify and distribute it and that commercial use is allowed.
We only ask you to mention us somewhere in your project.