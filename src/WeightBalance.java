import java.util.Scanner;


public class WeightBalance {

	public static void main(String[] args) {
		String[] inputStringArray ;
		
		Scanner in = new Scanner(System.in);		
		do
		{
			System.out.println("Введите последовательность натуральных чисел, разделенных любым символом (в одну строку):");
	        String inputString = in.nextLine();     
	        inputStringArray = inputString.split("\\D+");

			if (inputStringArray.length<=1)
			{System.out.println("Нужно ввести последовательность из как минимум 2 чисел.");}

		}while (inputStringArray.length<=1);	
		in.close();
		
		int[] numbers = new int[inputStringArray.length];
		int n = 0;
		for (int i = 0; i< inputStringArray.length; i++)
		{
			try
			{
				numbers[n] = Integer.parseInt(inputStringArray[i]);
				n++;
			} 
			catch (NumberFormatException e) {numbers[n] =0;} 
		}
		
		SubsetSum SumSearch = new SubsetSum(numbers);
		long Allsum = SumSearch.SetSum;
		if (Allsum % 2 == 0)
		{
			try 
			{
				Subset HalfSubset = SumSearch.FindSubset(Allsum / 2);	
				if (HalfSubset.Sum == Allsum/2)
				{System.out.println(HalfSubset.PrintSubset() + " - " 
						+ HalfSubset.PrintReversedSubset());}
				else {System.out.println("no");}	
			} 
			catch (java.lang.OutOfMemoryError e)
			{System.out.println("Недостаточно памяти.");}
			catch (Exception e) 
			{System.out.println(e.getMessage());}
		}
		else {System.out.println("no");}	
		
		try
		{
			Subset HundredSubset = SumSearch.FindSubset(100) ;
			if (HundredSubset.Sum == 100){System.out.println("yes");}
			else {System.out.println("no");}	
		}
		catch (IllegalArgumentException e)
		{System.out.println("no");}
		catch (Exception e)
		{System.out.println(e.getMessage());}
	}

}
