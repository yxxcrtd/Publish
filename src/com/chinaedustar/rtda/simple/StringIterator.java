package com.chinaedustar.rtda.simple;

import java.util.NoSuchElementException;

/**
 * 字符串枚举器的实现。
 * 
 * @author liujunxing
 */
public final class StringIterator implements java.util.Iterator<String> {
	/** 字符串值。*/
	private final String value;
	
	/** 此字符串的长度。 */
	private final int length;
	
	/** 枚举的位置。 */
	private int iter_pos;
	
	/**
	 * 使用指定的字符串构造一个字符串枚举器。
	 * @param value
	 */
	public StringIterator(String value) {
		this.value = value;
		this.length = value.length();
		this.iter_pos = 0;
	}
	
    /**
     * Returns <tt>true</tt> if the iteration has more elements. (In other
     * words, returns <tt>true</tt> if <tt>next</tt> would return an element
     * rather than throwing an exception.)
     *
     * @return <tt>true</tt> if the iterator has more elements.
     */
    public boolean hasNext() {
    	return this.iter_pos < this.length;
    }

    /**
     * Returns the next element in the iteration.  Calling this method
     * repeatedly until the {@link #hasNext()} method returns false will
     * return each element in the underlying collection exactly once.
     *
     * @return the next element in the iteration.
     * @exception NoSuchElementException iteration has no more elements.
     */
    public String next() {
    	if (iter_pos >= this.length)
    		throw new NoSuchElementException();
    	return new String( new char[] {this.value.charAt(iter_pos++)});
    }

    /**
     * 
     * Removes from the underlying collection the last element returned by the
     * iterator (optional operation).  This method can be called only once per
     * call to <tt>next</tt>.  The behavior of an iterator is unspecified if
     * the underlying collection is modified while the iteration is in
     * progress in any way other than by calling this method.
     *
     * @exception UnsupportedOperationException if the <tt>remove</tt>
     *		  operation is not supported by this Iterator.
     
     * @exception IllegalStateException if the <tt>next</tt> method has not
     *		  yet been called, or the <tt>remove</tt> method has already
     *		  been called after the last call to the <tt>next</tt>
     *		  method.
     */
    public void remove() {
    	throw new UnsupportedOperationException("string iterator is read-only.");
    }
}
