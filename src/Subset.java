import java.util.BitSet;


public class Subset implements Cloneable {
	
	protected int[] Set;
	protected long Sum = 0;
	protected BitSet SubsetFilling;
	protected int CompareValue = 0;
	
	public Subset(int[] Set, BitSet SubsetFilling) throws IllegalArgumentException
	{
		if (SubsetFilling.length() > Set.length)
		{throw new IllegalArgumentException("Размеры массива последовательности должен быть "
				+ "не меньше размера массива производящей функции.");}	
		if (Set.length <1)
		{throw new IllegalArgumentException("Размер последовательности должен быть больше 0.");}
		
		this.Set = Set;
		this.SubsetFilling = SubsetFilling;
		this.Sum = GetSubsetSum();
	}
	private Subset (){}
	
	private long GetSubsetSum()
	{
		long sum = 0;
		for (int i=0; i<SubsetFilling.length(); i++)
		{
			if (SubsetFilling.get(i)){sum += Set[i];}
		}
		return sum;
	}
	
	public void BalancedInsert (int position)throws IllegalArgumentException
	{
		if (position< 0 || position > Set.length-1){throw new IllegalArgumentException("Позиция для включения "
				+ "элемента последовательности за пределами размера последовательности.");}
		if (!SubsetFilling.get(position))
		{
			SubsetFilling.set(position); Sum = Sum + Set[position];
		}
	}
	public void BalancedRemove (int position)throws IllegalArgumentException
	{
		if (position< 0 || position > Set.length-1){throw new IllegalArgumentException("Позиция для включения "
				+ "элемента последовательности за пределами размера последовательности.");}
		if (SubsetFilling.get(position))
		{
			SubsetFilling.clear(position); Sum = Sum - Set[position];
		}
	}
	
	public String PrintSubset ()
	{
		StringBuilder StrB = new StringBuilder();
		for (int i = 0; i< SubsetFilling.length(); i++)
		{
			if (SubsetFilling.get(i)){StrB.append(Set[i]).append(" ");}
		}
		return StrB.toString().trim();
	}
	public String PrintReversedSubset ()
	{
		StringBuilder StrB = new StringBuilder();
		for (int i = 0; i< Set.length; i++)
		{
			if (i < SubsetFilling.length())
			{
				if (!SubsetFilling.get(i)){StrB.append(Set[i]).append(" ");}
			}
			else {StrB.append(Set[i]).append(" ");}
		}
		return StrB.toString().trim();
	}
	public Object Clone()
	{
		Subset newSubset = new Subset();
		newSubset.SubsetFilling =(BitSet)this.SubsetFilling.clone();
		newSubset.Set = this.Set;
		newSubset.Sum = this.Sum;
		return newSubset;
	}
	
}
