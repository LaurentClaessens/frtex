The "latex actor system" is intended to take as input a LaTeX filename and produce as output the "recursive" content of that file with all the \input{file} explicitly replaced by the content of "file.tex".

The behaviour of an actor is
- read its LaTeX file
- create a list of the inputed files.
- for each of them, ask an other actor to make the job.
- when all the answers are received, recompose the LaTeX file
- send the result to the actor whose asked.

The actor system need more functionalities than the basic one.
- A method "getFreeActor" that return an actor reference to an actor that is not working (in the sense that he has already send its result). If no free actors are available, create a new one.

SIMPLIFICATION 1 : For the sake of simplicity, it assumes that all inputs are on lines that _begin_ with \input.

SIMPLIFICATION 2 : We suppose that each .tex file in included only once. Thus if foo.tex needs bar.tex, it is impossible that bar.tex was already processed when the actor in charge of foo.tex initiate its work.

    In this case, the answer can be sent directly to the asking actor. 
    If a tex file could be included more than once, we should maybe use a principal actor.

