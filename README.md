#  FRTEX

## Installation and dependencies

* download the actor system from [github](https://github.com/LaurentClaessens/actors)
* compile the actor system :
<pre> <code> $  mvn package   </code>  </pre>
* make it available :
<pre><code>$ mvn  install:install-file -Dfile=path-to-actors/target/actors.jar</code></pre>
* download `frtex` from [github](https://github.com/LaurentClaessens/frtex)
* Use `frtex` by example performing the tests :
<pre><code>$ mvn  test</code></pre>


## The Latex actor system

The "latex actor system" is intended to take as input a LaTeX filename and produce as output the "recursive" content of that file with all the \input{file} explicitly replaced by the content of "file.tex". 

The use of our actor system makes the recomposition work extremely multi-thread and then(?) efficient.

This is still under development.

### How it works

The behaviour of an actor is :

- read its LaTeX file
- create a list of the inputed files.
- for each of them, ask an other actor to make the job.
- when all the answers are received, recompose the LaTeX file
- send the result to the actor whose asked.

### What you need to know

The only class you have to work with is `LatexCode`. That class provides an abstraction that hides the actor system to the user.

### Working actor vs. Active actor

A `LatexActor` can be active or not, as any actor. The `LatexActor` has an other status that is "working" or not.

An actor which is requested to create the code of the file "foo.tex" reads this file and send a request message to a new actor each time that it encounters a "\input" in "foo.tex". Such an actor has to be able to read new messages since it relies on the answer messages in order to complete its work.

So such a working actor is set "inactive" in order to unlock its mail box. But it is still working and cannot be requested to work on an other tex file until "foo.tex" is completed and sent to the calling actor.

The `LatexActorSystem` has a method 

```java
public LatexActorRef getNonWorkingActor()
```
which return an actor reference to an actor who can be requested to deal with a new tex file. The actor is 'working' from the moment it is returned.

### Latex message

The latex actor system recognize two types of messages.

* `LatexMessage` (abstract)
* `LatexRequestMessage` (extends `LatexMessage`)
* `LatexAnswerMessage` (extends `LatexMessage`)

### Hypothesis on the LaTeX source code (simplification)

* The filenames are more or less standard. Like only one dot, no curly braces and so on.

* The percent symbol should always mean a comment, with the exception of "\%". This can be a limitation if you have URL in which you substituted special characters with their %xx representation.

* We suppose that each .tex file in included only once. Thus if foo.tex needs bar.tex, it is impossible that bar.tex was already processed when the actor in charge of foo.tex initiate its work.

* The LaTeX code is supposed to be encoded in utf8

* When inputing a tex file, use
```latex
\input{foo}
```
and not
```latex
\input{foo.tex}
```
 * Filename must contain (at most) one dot. You cannot do
```latex
\input{fdl-1.3}
```
for implying an input of `fdl-1.3.tex`. And even less
```latex
\input{fdl-1.3.tex}
```

### TEST

use `mvn test` to see the result.
