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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.text.DecimalFormat;

class javapso {

    static pso ps;
    
    static int output_asc(String outasc_fn, particlepack pp, int num_particles, double blo, double bup) {

	int particleno;

	BufferedWriter fp;

	FileWriter fstream;

	fstream = null;
	
	try {
	    fstream = new FileWriter(outasc_fn, false);
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}
	
	fp = new BufferedWriter(fstream);

	try {
	
	    for (particleno = 0; particleno < num_particles; particleno++) {

		fp.write(pp.pbest[particleno].pos[0] + ", " + pp.pbest[particleno].pos[1] + ", " + pp.pbest[particleno].pos[2] + "\n");	       
		    
	    }

	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}

	try {
	    fp.close();
	} catch (IOException ioe) {
	    ioe.printStackTrace();	    
	}
	    
	return 0;
	
    }
    
    static int outcsv_errs(String outcsv_fn, reserrs re, int num_generations) {

	int generationno;

	BufferedWriter fp;

	FileWriter fstream;

	fstream = null;
	
	try {
	    fstream = new FileWriter(outcsv_fn, false);
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}
	
	fp = new BufferedWriter(fstream);

	
	try {

	    fp.write("Generation #, Total Error\n");
	    
	    for (generationno = 0; generationno < num_generations; generationno++) {

		fp.write(generationno + ", " + re.total_errs[generationno] + "\n");
	    
	    }

	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}

	try {
	    fp.close();
	} catch (IOException ioe) {
	    ioe.printStackTrace();	    
	}
	    
	return 0;
	
    }
    
    public static void main(String[] args) {

	long version_major;
	long version_minor;
	long version_patch;

	particlepack pp;

	DecimalFormat df;

	reserrs re;
	
	int def_numpart = 20000;
	int def_numgen = 3500;
	int def_numthr = 8;
	
	version_major = 1;
	version_minor = 0;
	version_patch = 0;

	String version_str = new String(version_major + "." + version_minor + "." + version_patch);
	
        System.out.println("Java PSO " + version_str); 

	ps = new pso();

	if (args[0].length() > 0) {
	    ps.num_particles = Integer.parseInt(args[0]);
	}
	else {
	    ps.num_particles = def_numpart;
	}

	if (args[1].length() > 0) {
	    ps.num_generations = Integer.parseInt(args[1]);
	}
	else {
	    ps.num_generations = def_numpart;
	}

	if (args[2].length() > 0) {
	    ps.num_threads = Integer.parseInt(args[2]);
	}
	else {
	    ps.num_threads = def_numthr;
	}
	
	ps.blo = -3.5;
	ps.bup = 3.5;

	ps.rand = new Random();
	
	int particleno;

	double span;

	pp = ps.fill_initialrnd(ps.num_particles, ps.blo, ps.bup);

	pp = ps.set_initial(pp, ps.num_particles);

	System.out.println("Particles: " + ps.num_particles);
	System.out.println("Generations: " + ps.num_generations);
	System.out.println("Threads: " + ps.num_threads);	

	re = new reserrs(ps.num_generations);
	
	{

	    int generationno;

	    double percent;

	    double totalerr;

	    double totalerr_prev;

	    double totalerr_avg;

	    double eta;

	    int verbose;

	    int debug;

	    totalerr_prev = 1e100;

	    totalerr = 1e100;
	    
	    totalerr_avg = 1e100;

	    verbose = 1;

	    debug = 0;
	    
	    df = new DecimalFormat("#.##");
	    
	    for (generationno = 0; generationno < ps.num_generations; generationno++) {

		percent = generationno; percent /= ps.num_generations;
		
		pp = ps.update_pso(pp, percent, ps.num_particles);

		if (debug > 0) {
		    for (particleno = 0; particleno < 5; particleno++) {
			System.out.println("Particle " + particleno + " " + pp.pbest[particleno].pos[0] + " " + pp.pbest[particleno].pos[1] + " " + pp.pbest[particleno].pos[2]);
		    }
		}

		totalerr_prev = totalerr;

		totalerr = ps.calc_totalerror(pp.pbest, ps.num_particles);

		totalerr_avg += totalerr;
		totalerr_avg *= 0.5;

		re.total_errs[generationno] = totalerr;
		
		if (verbose > 0) {
		    System.out.print("\r[" + generationno + "](" + df.format(100.0 * percent) + "%) totalerr " + totalerr + "     ");
		}

	    }

	}

	System.out.print("\n");

	outcsv_errs("pso_generations.csv", re, ps.num_generations);
	
	output_asc("javapso.asc", pp, ps.num_particles, ps.blo, ps.bup);
	
    }
}
