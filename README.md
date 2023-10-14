# sierpinski-triangle
A program for constructing a Sierpinski triangle by the chaos method

+ Take three points in a plane to form a triangle.
+ Randomly select any point inside the triangle and consider that your current position.
+ Randomly select any one of the three vertex points.
+ Move half the distance from your current position to the selected vertex.
+ Plot the current position.
+ Repeat from step 3.

This method is also called the chaos game, and is an example of an iterated function system. You can start from any point outside or inside the triangle, and it would eventually form the Sierpiński Gasket with a few leftover points (if the starting point lies on the outline of the triangle, there are no leftover points).
![sierpinski_triangle](https://github.com/Movorg/sierpinski-triangle/assets/60103760/48940d0d-ccc0-4780-9cc2-ed9ea027cf8c)
