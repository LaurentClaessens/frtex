The "latex actor system" is intended to take as input a LaTeX filename and produce as output the "recursive" content of that file with all the \input{file} explicitly replaced by the content of "file.tex".

The behaviour of an actor is
- read its LaTeX file
- create a list of the inputed files.
- for each of them, ask an other actor to make the job.
- when all the answers are received, recompose the LaTeX file
- send the result to the actor whose asked.

The actor system need more functionalities than the basic one.
- A method "getFreeActor" that return an actor reference to an actor that is not working (in the sense that he has already send its result). If no free actors are available, create a new one.
