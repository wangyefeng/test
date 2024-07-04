package org.wangyefeng.random;

/**
 * 
 * 权重计算器
 * 
 * @param <T>
 * @see IWeight
 */
public interface WeightCalculator<T> {
	
	int weight(T t);
}
