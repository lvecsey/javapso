/*
    Use the PSO algorithm for a swarm of particles that matches a function
    Copyright (C) 2021  Lester Vecsey

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.util.Random;

class pso {

    int num_particles;
    int num_generations;
    int num_threads;

    double blo, bup;

    Random rand;
    
    point3d math3d_bumps(double x, double y) {

	point3d pnta;

	pnta = new point3d();
	
	pnta.x = x;
	pnta.y = y;
	pnta.z = Math.sin(5.0 * x) * Math.cos(5.0 * y) / 5.0;
  
	return pnta;

    }

    point3d math3d_pyramid(double x, double y) {

	point3d pnta;

	pnta = new point3d();
	
	pnta.x = x;
	pnta.y = y;
	pnta.z = 1.0 - Math.abs(x + y) - Math.abs(y - x);
  
	return pnta;

    }
    
    double calc_single(point3d cmp_pnt1, point3d cmp_pnt2) {

	double xdiff, ydiff, zdiff;
	
	double err;

	xdiff = (cmp_pnt1.x - cmp_pnt2.x);
	ydiff = (cmp_pnt1.y - cmp_pnt2.y);
	zdiff = (cmp_pnt1.z - cmp_pnt2.z);      

	err = (xdiff * xdiff + ydiff * ydiff + zdiff * zdiff);

	return err;
  
    }

    double calc_single(particle x, point3d cmp_pnt2) {

	double xdiff, ydiff, zdiff;

	double err;

	xdiff = (x.pos[0] - cmp_pnt2.x);
	ydiff = (x.pos[1] - cmp_pnt2.y);
	zdiff = (x.pos[2] - cmp_pnt2.z);      

	err = (xdiff * xdiff + ydiff * ydiff + zdiff * zdiff);

	return err;
  
    }

    int within_range(point3d pnta, double blo, double bup) {

	if (pnta.x > bup || pnta.y > bup || pnta.z > bup) {
	    return 0;
	}

	if (pnta.x < blo || pnta.y < blo || pnta.z < blo) {
	    return 0;
	}
  
	return 1;

    }

    int within_range(particle x, double blo, double bup) {

	if (x.pos[0] > bup || x.pos[1] > bup || x.pos[2] > bup) {
	    return 0;
	}

	if (x.pos[0] < blo || x.pos[1] < blo || x.pos[2] < blo) {
	    return 0;
	}
  
	return 1;

    }
    
    double fitness_func(particle x) {

	point3d expected_pntc;

	double err;

	expected_pntc = math3d_bumps(x.pos[0], x.pos[1]);

	if (1 == within_range(x, blo, bup)) {
	
	    err = calc_single(x, expected_pntc);

	}

	else {

	    err = 1e100;

	}
	    
	return err;
  
    }

    double calc_totalerror(particle[] particles, int num_particles) {

	double sum;
  
	double total_err;

	double err;
  
	int particleno;

	sum = 0.0;
	
	for (particleno = 0; particleno < num_particles; particleno++) {

	    err = fitness_func(particles[particleno]);

	    sum += err;
    
	}

	total_err = (sum / num_particles);
  
	return total_err;

    }

    particlepack fill_initialrnd(int num_particles, double blo, double bup) {

	double span;

	int particleno;

	particlepack ppret;

	double fa;

	ppret = new particlepack(num_particles);

	span = (bup - blo);

	fa = Math.abs(bup - blo);

	for (particleno = 0; particleno < num_particles; particleno++) {

	    ppret.xcur[particleno].pos[0] = blo + span * rand.nextDouble();
	    ppret.xcur[particleno].pos[1] = blo + span * rand.nextDouble();
	    ppret.xcur[particleno].pos[2] = blo + span * rand.nextDouble();	    

	    ppret.vcur[particleno].vec[0] = (-fa + fa * rand.nextDouble());
	    ppret.vcur[particleno].vec[1] = (-fa + fa * rand.nextDouble());
	    ppret.vcur[particleno].vec[2] = (-fa + fa * rand.nextDouble());

	}

	return ppret;

    }
	
    particlepack set_initial(particlepack pp, int num_particles) {

	int particleno;

	particlepack ppret;

	ppret = new particlepack(num_particles);

	for (particleno = 0; particleno < num_particles; particleno++) {

	    ppret.xcur[particleno].pos[0] = pp.xcur[particleno].pos[0];
	    ppret.xcur[particleno].pos[1] = pp.xcur[particleno].pos[1];
	    ppret.xcur[particleno].pos[2] = pp.xcur[particleno].pos[2];	    

	    ppret.vcur[particleno].vec[0] = pp.vcur[particleno].vec[0];
	    ppret.vcur[particleno].vec[1] = pp.vcur[particleno].vec[1];
	    ppret.vcur[particleno].vec[2] = pp.vcur[particleno].vec[2];	    
	    
	}

	ppret.gbest.pos[0] = ppret.xcur[0].pos[0];
	ppret.gbest.pos[1] = ppret.xcur[0].pos[1];
	ppret.gbest.pos[2] = ppret.xcur[0].pos[2];
	ppret.gbesterr = fitness_func(ppret.gbest);
	
	for (particleno = 0; particleno < num_particles; particleno++) {
	
	    ppret.pbest[particleno].pos[0] = pp.xcur[particleno].pos[0];
	    ppret.pbest[particleno].pos[1] = pp.xcur[particleno].pos[1];
	    ppret.pbest[particleno].pos[2] = pp.xcur[particleno].pos[2];
	    ppret.fitness[particleno] = fitness_func(ppret.pbest[particleno]);
	    
	}
	      
	for (particleno = 0; particleno < num_particles; particleno++) {

	    if (ppret.fitness[particleno] < ppret.gbesterr) {

		ppret.gbest.pos[0] = ppret.pbest[particleno].pos[0];
		ppret.gbest.pos[1] = ppret.pbest[particleno].pos[1];		
		ppret.gbest.pos[2] = ppret.pbest[particleno].pos[2];
		ppret.gbesterr = ppret.fitness[particleno];
      
	    }
      
	}

	return ppret;

    }
    
    particlepack update_pso(particlepack pp, double percent, int num_particles) {

	point3d pnta, pntb;
  
	int particleno;

	double inertia_weight;

	double c1, c2;

	double cur_err;

	vec3d vnext;
	particle xnext;

	particlepack ppret;

	update_param up;
	
	inertia_weight = (1.0 - percent);

	c1 = 2.0;
	c2 = 2.0;

	pnta = new point3d();
	pntb = new point3d();

	vnext = new vec3d();
	xnext = new particle();

	ppret = new particlepack(num_particles);
	
	ppret.gbest.pos[0] = pp.gbest.pos[0];
	ppret.gbest.pos[1] = pp.gbest.pos[1];
	ppret.gbest.pos[2] = pp.gbest.pos[2];
	ppret.gbesterr = pp.gbesterr;

	up = new update_param();
	
	up.accel_c = 0.895;
	
	for (particleno = 0; particleno < num_particles; particleno++) {

	    pnta.x = (pp.pbest[particleno].pos[0] - pp.xcur[particleno].pos[0]);
	    pnta.y = (pp.pbest[particleno].pos[1] - pp.xcur[particleno].pos[1]);
	    pnta.z = (pp.pbest[particleno].pos[2] - pp.xcur[particleno].pos[2]);

	    pntb.x = (ppret.gbest.pos[0] - pp.xcur[particleno].pos[0]);
	    pntb.y = (ppret.gbest.pos[1] - pp.xcur[particleno].pos[1]);
	    pntb.z = (ppret.gbest.pos[2] - pp.xcur[particleno].pos[2]);

	    up.rand1 = rand.nextDouble();
	    up.rand2 = rand.nextDouble();
	    vnext.vec[0] = inertia_weight * pp.vcur[particleno].vec[0] + c1 * up.accel_c * up.rand1 * (pnta.x) + c2 * up.accel_c * up.rand2 * (pntb.x);

	    up.rand1 = rand.nextDouble();
	    up.rand2 = rand.nextDouble();
	    vnext.vec[1] = inertia_weight * pp.vcur[particleno].vec[1] + c1 * up.accel_c * up.rand1 * (pnta.y) + c2 * up.accel_c * up.rand2 * (pntb.y);

	    up.rand1 = rand.nextDouble();
	    up.rand2 = rand.nextDouble();
	    vnext.vec[2] = inertia_weight * pp.vcur[particleno].vec[2] + c1 * up.accel_c * up.rand1 * (pnta.z) + c2 * up.accel_c * up.rand2 * (pntb.z);  

	    xnext.pos[0] = (pp.xcur[particleno].pos[0] + vnext.vec[0]);
	    xnext.pos[1] = (pp.xcur[particleno].pos[1] + vnext.vec[1]);
	    xnext.pos[2] = (pp.xcur[particleno].pos[2] + vnext.vec[2]);
	    
	    ppret.vcur[particleno].vec[0] = vnext.vec[0];
	    ppret.vcur[particleno].vec[1] = vnext.vec[1];
	    ppret.vcur[particleno].vec[2] = vnext.vec[2];	    
		
	    ppret.xcur[particleno].pos[0] = xnext.pos[0];
	    ppret.xcur[particleno].pos[1] = xnext.pos[1];
	    ppret.xcur[particleno].pos[2] = xnext.pos[2];

	    cur_err = fitness_func(xnext);

	    if (cur_err < pp.fitness[particleno]) {

		ppret.fitness[particleno] = cur_err;
		    
		ppret.pbest[particleno].pos[0] = xnext.pos[0];
		ppret.pbest[particleno].pos[1] = xnext.pos[1];
		ppret.pbest[particleno].pos[2] = xnext.pos[2];

		if (cur_err < ppret.gbesterr) {

		    ppret.gbest.pos[0] = xnext.pos[0];
		    ppret.gbest.pos[1] = xnext.pos[1];
		    ppret.gbest.pos[2] = xnext.pos[2];		    
		    ppret.gbesterr = cur_err;
	
		}
			
	    }

	    else {

		ppret.fitness[particleno] = pp.fitness[particleno];

		ppret.pbest[particleno].pos[0] = pp.pbest[particleno].pos[0];
		ppret.pbest[particleno].pos[1] = pp.pbest[particleno].pos[1];
		ppret.pbest[particleno].pos[2] = pp.pbest[particleno].pos[2];		    
		    
	    }

	}
   
	return ppret;

    }

    
}
