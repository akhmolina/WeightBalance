# WeightBalance
Определяет, можно ли из целочисленной последовательности весов предметов отобрать какое-то количество с суммарным весом, равным половине суммы всех весов последовательности, и с суммарным весом 100.

Используется [балансный алгоритм нахождения суммы подмножества](http://www.sciencedirect.com/science/article/pii/S0196677499910349). 

При достаточном количестве памяти:

+ Максимальное допустимое значение чисел последовательности - 1073741823. Накладывает самое сильное ограничение по памяти, поскольку алгоритм использует массивы размерностью 2W, где W - максимальное число в последовательности.
+ Максимальное количество чисел в последовательности - 2147483647. 
+ Максимальное допустимое значение суммы всей последовательности - 9223372036854775807.

##Решает задачу: 
Баланс весов

Дана конечная последовательность натуральных чисел. 
Считая их массами имеющихся в наличии предметов, определить, можно ли все эти предметы положить на весы так, чтобы весы находились в равновесии. Вывести вариант расположения.
Определить, можно ли из них отобрать какое-то количество предметов с суммарным весом 100 (вывести yes или no, в зависимости от результата).

Пример входных данных:

2 4 3 6 5

Пример выходных данных:

2 3 5 - 4 6

no
