import java.util.BitSet;


public class SubsetSum {
	private int[] Set;
	private int MaxW = 0; //������������ �������� ������ ���� < Integer.MAX_VALUE/2
	private int MinW = Integer.MAX_VALUE;
	private Subset[] Solutions;
	private int[] Values;
	private long Sum;
	protected long SetSum = 0;
	
	
	public SubsetSum(int[] Array)throws IllegalArgumentException
	{
		if (Array.length <=1)
		{throw new IllegalArgumentException("������ ������������������ ������ ���� ������ 1.");}
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
		{throw new IllegalArgumentException("��������� ����� " + Sum 
				+ " ����� �� ����� ���� ������� �� ������������������.");}

		//������ ��� ������ - BreakSolution
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
		int[] LastValues;
		
		Solutions = new Subset[2*MaxW]; //�� Sum-MaxW+1  �� Sum+MaxW
		Values = new int[2*MaxW]; //�� Sum-MaxW+1  �� Sum+MaxW
		
		for (int i = 0; i<MaxW-1; i++){Values[i] = 0;}	//������ MaxW-1 - ������������� ������� ����� 	
		for (int i = MaxW; i<2*MaxW; i++){Values[i] = 1;}
		Values[breakSolutionIndex] = bitnumber+1;//���������� ��������� ��� t �  � ���� + 1, �.�. ������� � ����, � � ��� 0 - ��������� �������
		Solutions[breakSolutionIndex] = BreakSolution;
		NewInsSolution = BreakSolution;
		
		int lastsumindex = 0;
		int newsumindex=0;
		
		for (int i = bitnumber; i < Set.length; i++)
		{	
			LastValues = Values.clone();
			LastSolutions = Solutions.clone();
			for( int n = 0; n<MaxW-1; n++) //MaxW-1 �� �������, ��� �� ��� ���� �������
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
						Values[newsumindex]= 
								Math.max(Values[newsumindex],LastValues[lastsumindex]);
						Solutions[newsumindex]=(Subset) NewInsSolution.Clone();
						
						if (newsumindex>=MaxW)
						{
							int start = Values[newsumindex]-1; // ��� -1, ������ ��� ������� � ��� � ����, � �������� � 1
							int end = LastValues[newsumindex]-1;
							
							Subset Ret = RecourciveRemove(NewInsSolution,start-1, end);// ��� -1 ������ ��� ������� �������� ��� ���������
							if (Ret != null){return Ret;}
						}
					}
				}
			}
		}
		//������� ������� ���, ����� ��������� �����
		for (int n = MaxW-2; n>=0; n--)
		{if (Solutions[n] != null) {return Solutions[n];}}
		throw new Exception ("���� ���������.");
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
						Math.max(Values[newsumindex],n+1); //k+1 ������ ��� ������� � 0 
				Solutions[newsumindex]=(Subset) NewRemSolution.Clone();	
				
				if (n-1>0 && n-1>=kmin && newsumindex>MaxW-1) //��������� ������� ���������, ���� kmin != 0
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
