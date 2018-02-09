# maze-solver
This app solves mazes according to spec. Solver uses wall following
algorithm, which means it can solve only mazes with start and end points
connected through unrolled wall. In case algorithm figures out that it
cannot find solution in throws RuntimeException.

Run time for large sample maze was ~150ms. (Actually initial solution -
random solver from release v0.1 - could also solve large maze in 11 s,
if Random was seeded with 42. Just because we know that answer is 42)

To run application:
- Checkout git repo `git clone https://github.com/romanmarkunas/maze-solver.git`
- `cd maze-solver`
- `./gradlew run -Pargs="sample-mazes/input.txt"`

To run other than sample mazes, substitute `-Pargs` value with path to
your maze.
