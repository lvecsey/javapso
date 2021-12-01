
all : point3d.class particle.class vec3d.class particlepack.class update_param.class reserrs.class pso.class javapso.class

%.class : %.java
	@javac $^

clean :
	rm -f *.class
