
class particlepack {

    public particle[] xcur;    
    public vec3d[] vcur;
    
    public particle[] pbest;
    public double[] fitness;
    
    public particle gbest;
    public double gbesterr;

    public int alloc(int num_particles) {

	int particleno;
	
	xcur = new particle[num_particles];	
	vcur = new vec3d[num_particles];	
	
	pbest = new particle[num_particles];
	fitness = new double[num_particles];

	gbest = new particle();
	
	for (particleno = 0; particleno < num_particles; particleno++) {

	    xcur[particleno] = new particle();
	    vcur[particleno] = new vec3d();	    
	    
	    pbest[particleno] = new particle();
	    
	}

	return 0;
	
    }

    int show(int num_particles) {

	int particleno;
	
	System.out.println("gbest " + gbest.pos[0] + " " + gbest.pos[1] + " " + gbest.pos[2]);
	System.out.println("gbesterr " + gbesterr);

	for (particleno = 0; particleno < num_particles; particleno++) {

	    System.out.println("Fitness " + particleno + " value " + fitness[particleno]);
	    
	}
	
	for (particleno = 0; particleno < num_particles; particleno++) {

	    System.out.println("Particle pbest " + particleno + " value " + pbest[particleno].pos[0] + " " + pbest[particleno].pos[1] + " " + pbest[particleno].pos[2]);
	    
	}
	
	return 0;
	
    }
    
    public particlepack(int num_particles) {

	alloc(num_particles);
	
    }
    
}
