# Qual-Eval: A High-Precision Math Engine (Expression Evaluation Engine)
A thread-safe Expression Parser using the Shunting-Yard algorithm with $O(n)$ time complexity.

## Why Calculator
Calculator applications are quite underestimated in the software engineering field. It's a
common project idea for beginners, so it's everywhere. I myself made (or attempted) it
twice before. The first was in my early Java days in swing. All I can say about it is I
got the UI almost right, ok? Don't ask me about function. But I comforted myself by thinking
"if I really wanted to make a calculator app, I'd make it. Come on, Is it not com.qualibits.jeval?
Simple thing! mtchew!". A few years later, I decided to make it for real as my first project
after learning JavaScript. One of the reasons calculators feel so easy to make is because of
the math library functions and methods all programming languages provide. I took advantage
of that and it definitely worked. However, I was not aware of design tricks that could keep
the development simple. To the best of my knowledge, (which you should take with a grain of
salt–I'm still learning), there are basically two ways to design the evaluation of expressions
in calculator apps. You could store all the expressions, evaluating only when the "equal"
button is pressed, or you could evaluate as the user provides you with the next token or term,
before things get complex. Most developers would go for the latter. It's easy and uncomplicated.
But it can also be limiting (for the user), they might have to rewrite some expression where the
rules of PEDMAS/BODMAS (operator precedence) is to be followed. That time, I went for the former without an inkling of what
I was getting myself into. So I ended up writing code that works sometimes and could even give
the wrong answer.

I went into this project thinking with my years of experience coding and theoretical computer
science knowledge, I should not be making those mistakes again. I think it's working because I'm
starting to appreciate the complexity that could go into developing a capable, well-done and
correct calculator. I'm enjoying the process, playing around with recursion, studying the use
binary trees queues for creating expression and evaluation trees, It's been fun.

But, why did I consider making it again considering I've attempted it before? I boasted about it
being child's play


## Thought Pattern involved in the problem-solving done in this project.
Writing this program involved a lot of theoretical categorization and pattern recognition.
Categorization can be modelled after real world concepts, or it can be done such that it's
easier to code, ("path of least resistance", so to speak).

As an example, for the sole purpose of easy evaluation, I had to write a solution that adds
a multiplication where it might have been left out. To illustrate, consider the following
simple expression,`3⫪`. Although it is mathematically correct and well-formed, it is
very incompatible with my model which has been based around an assumption that "operands
are separated by at least one operator". `3` is an operand, the same for ``⫪`` (or at
least, so represented). To evaluate the result while keeping my model, I would have to add
a multiplication/product operator implicitly to make `3 x ⫪`.

You might be thinking it's a bad idea to bend input expressions like that just to fit my model,
but in this case, it's not actually "bending". This is because, mathematically speaking, `3⫪`
can be seen as a short form of `3 x ⫪`. Therefore, it can simply be seen as an "expansion".
As long as it can be logically determined by the program when and where this "expansion" must be
done, it's a valid strategy.