
*Overview*

Particle swarm optimization (PSO) algorithm in Java. For a comparison to a C program see libpso.

*Compiling*

```console
make
```

*Running*

```console
java -classpath . javapso 50000 1250 8
```

This will run PSO algorithm with specified number of particles, number of generations, and number of threads.

*Viewing results*

```console
meshlab javapso.asc
```

You can convert the particle positions to a mesh using meshlab.

The steps are to do a Clustering Decimiation, then Compute normals for point sets, and finally Surface Reconstruction: Screened Poisson.

*Notes*

Threaded support is not implemented yet.

![Image of output](https://phrasep.com/~lvecsey/software/javapso/screenshot_bumps2.png)

	       
