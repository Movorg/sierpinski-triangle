# Sierpinski Triangle

![sierpinski_triangle](https://github.com/Movorg/sierpinski-triangle/assets/60103760/2ef5590d-49b9-48f1-a732-0536a3808f3d)

A program for constructing a Sierpinski triangle by the chaos method.

# Features

*   You can change the following parameters (all these parameters can be changed directly during the rendering process):
      * point color;
      * poin size;
      * drawing speed of elements.
*   You can stop and resume the rendering process from the same place.

#  What is Sierpinski Triangle?

The Sierpinski triangle is a fractal attractive fixed set with the overall shape of an equilateral triangle, subdivided recursively into smaller equilateral triangles. Originally constructed as a curve, this is one of the basic examples of self-similar sets — that is, it is a mathematically generated pattern that is reproducible at any magnification or reduction. It is named after the Polish mathematician Wacław Sierpiński, but appeared as a decorative pattern many centuries before the work of Sierpiński. 

# Chaos game

Animated creation of a Sierpinski triangle using the chaos game.

+ Take three points in a plane to form a triangle.
+ Randomly select any point inside the triangle and consider that your current position.
+ Randomly select any one of the three vertex points.
+ Move half the distance from your current position to the selected vertex.
+ Plot the current position.
+ Repeat from step 3.

This method is also called the chaos game, and is an example of an iterated function system. You can start from any point outside or inside the triangle, and it would eventually form the Sierpiński Gasket with a few leftover points (if the starting point lies on the outline of the triangle, there are no leftover points).
