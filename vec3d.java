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

class vec3d {

    public double[] vec;

    public int alloc() {
	vec = new double[3];
	return 0;
    }
    
    public int show(String desc) {
	System.out.println(desc + " " + vec[0] + " " + vec[1] + " " + vec[2]);
	return 0;
    }
    
    public vec3d() {

	alloc();
	
    }
    
}
