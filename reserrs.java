
class reserrs {

    public double[] total_errs;

    public int alloc(int num_generations) {

	total_errs = new double[num_generations];

	return 0;
	
    }
    
    public reserrs(int num_generations) {

	alloc(num_generations);
	
    }
    
}
