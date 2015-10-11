import java.util.BitSet;


public class SubsetSum {
	private int[] Set;
	private int MaxW = 0; //max element from Set must be < Integer.MAX_VALUE/2
	private int MinW = Integer.MAX_VALUE;
	private Subset[] Solutions;
	private int[] Values;
	private long Sum;
	protected long SetSum = 0;
	
	
	public SubsetSum(int[] Array)throws IllegalArgumentException
	{
		if (Array.length <=1)
		{throw new IllegalArgumentException("Размер последовательности должен быть больше 1.");}
		Set = Array;
 
		long sum = 0;
		for (int i = 0; i<Set.length; i++)
		{
			sum += Set[i];
			if (Set[i]>MaxW){MaxW=Set[i];}
			if (Set[i]<MinW){MinW=Set[i];}
		}
		SetSum = sum;
	}
	
	public Subset FindSubset(long sum)throws Exception
	{
		Sum = sum;
		if (Sum < MinW || Sum> SetSum)
		{throw new IllegalArgumentException("Требуемая сумма " + Sum 
				+ " чисел не может быть выбрана из последовательности.");}

		//search starting on BreakSolution
		BitSet BreakSolutionBitSet = new BitSet();
		BreakSolutionBitSet.set(0);
		Subset BreakSolution = new Subset(Set, BreakSolutionBitSet);
		
		int bitnumber = 0; 
		for (int i = 1; i<Set.length; i++)
		{
			if (BreakSolution.Sum>Sum){break;}
				BreakSolution.BalancedInsert(i); bitnumber++;
		}		
		BreakSolution.BalancedRemove(bitnumber); 
		
		if (BreakSolution.Sum == Sum){return BreakSolution;}
		
		int breakSolutionIndex = (int) (MaxW-1-Sum+BreakSolution.Sum);
		Subset NewInsSolution = null; 
		Subset[] LastSolutions;
		
		Solutions = new Subset[2*MaxW]; //from Sum-MaxW+1  to Sum+MaxW
		Values = new int[2*MaxW]; //from Sum-MaxW+1  to Sum+MaxW
		
		for (int i = 0; i<MaxW-1; i++){Values[i] = 0;}	// MaxW-1 index - sum we are searching for 	
		for (int i = MaxW; i<2*MaxW; i++){Values[i] = 1;}
		Values[breakSolutionIndex] = bitnumber+1;//next bit is t and + 1, cause we are counting from 0, but in algorithm 0 means no solution exist
		Solutions[breakSolutionIndex] = BreakSolution;
		NewInsSolution = BreakSolution;
		
		int lastsumindex = 0;
		int newsumindex=0;
		
		for (int i = bitnumber; i < Set.length; i++)
		{	
			LastSolutions = Solutions.clone();
			for( int n = 0; n<MaxW-1; n++)
			{
				if (LastSolutions[n]!= null)	
				{
					NewInsSolution = (Subset)LastSolutions[n].Clone();
					lastsumindex = n;
												
					NewInsSolution.BalancedInsert(i);
					newsumindex = (int) (NewInsSolution.Sum-Sum+MaxW-1);
					if (newsumindex == MaxW-1){return NewInsSolution;}
					
					if (newsumindex>=0 && newsumindex<2*MaxW)
					{
						int lastsum = Values[newsumindex];
						Values[newsumindex]= 
								Math.max(Values[newsumindex],Values[lastsumindex]);
						Solutions[newsumindex]=(Subset) NewInsSolution.Clone();
						
						if (newsumindex>=MaxW)
						{
							int start = Values[newsumindex]-1; 
							int end = lastsum-1;
							
							Subset Ret = RecourciveRemove(NewInsSolution,start-1, end);
							if (Ret != null){return Ret;}
						}
					}
				}
			}
		}
		//sum not found, we can take nearest solution
		for (int n = MaxW-2; n>=0; n--)
		{if (Solutions[n] != null) {return Solutions[n];}}
		throw new Exception ("Сбой рассчетов.");
	}
	
	private Subset RecourciveRemove(Subset Solution, int kmax, int kmin )
	{
		for (int n = kmax; n>=kmin; n--)
		{
			Subset NewRemSolution = (Subset) Solution.Clone();
			
			NewRemSolution.BalancedRemove(n);
			int newsumindex = (int) (NewRemSolution.Sum-Sum+MaxW-1);
			if (newsumindex == MaxW-1){return NewRemSolution;}
			
			if (newsumindex>=0 && newsumindex<2*MaxW)
			{
				Values[newsumindex]= 
						Math.max(Values[newsumindex],n+1); 
				Solutions[newsumindex]=(Subset) NewRemSolution.Clone();	
				
				if (n-1>0 && n-1>=kmin && newsumindex>MaxW-1) // sometimes kmin != 0
				{
					Subset Ret = RecourciveRemove(NewRemSolution, n-1, kmin);
					if (Ret != null){return Ret;}
				}
			}
			else break;
		}
		return null;
	}
}
