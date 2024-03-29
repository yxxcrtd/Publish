package com.chinaedustar.rtda.oper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to perform arithmetic operations.
 */
@SuppressWarnings("rawtypes")
public abstract class ArithmeticEngine {
    /**
     * Arithmetic engine that converts all numbers to {@link BigDecimal} and
     * then operates on them. This is FreeMarker's default arithmetic engine.
     */
    public static final BigDecimalEngine BIGDECIMAL_ENGINE = new BigDecimalEngine();
    
    /**
     * Arithmetic engine that uses (more-or-less) the widening conversions of
     * Java language to determine the type of result of operation, instead of
     * converting everything to BigDecimal up front.
     */
    public static final ConservativeEngine CONSERVATIVE_ENGINE = new ConservativeEngine();
    
    /**
     * 我们用这个做为引擎。
     */
    public static final ArithmeticEngine DEFAULT_ENGINE = CONSERVATIVE_ENGINE;

    public abstract int compareNumbers(Number first, Number second);
    public abstract Number add(Number first, Number second);
    public abstract Number subtract(Number first, Number second);
    public abstract Number multiply(Number first, Number second);
    public abstract Number divide(Number first, Number second);
    public abstract Number modulus(Number first, Number second);
    public abstract Number toNumber(String s);

    protected int minScale = 12;
    protected int maxScale = 12;
    protected int roundingPolicy = BigDecimal.ROUND_HALF_UP;

    /**
     * Sets the minimal scale to use when dividing BigDecimal numbers. Default
     * value is 12.
     */
    public void setMinScale(int minScale) {
        if(minScale < 0) {
            throw new IllegalArgumentException("minScale < 0");
        }
        this.minScale = minScale;
    }
    
    /**
     * Sets the maximal scale to use when multiplying BigDecimal numbers. 
     * Default value is 100.
     */
    public void setMaxScale(int maxScale) {
        if(maxScale < minScale) {
            throw new IllegalArgumentException("maxScale < minScale");
        }
        this.maxScale = maxScale;
    }

    public void setRoundingPolicy(int roundingPolicy) {
        if (roundingPolicy != BigDecimal.ROUND_CEILING
            && roundingPolicy != BigDecimal.ROUND_DOWN
            && roundingPolicy != BigDecimal.ROUND_FLOOR
            && roundingPolicy != BigDecimal.ROUND_HALF_DOWN
            && roundingPolicy != BigDecimal.ROUND_HALF_EVEN
            && roundingPolicy != BigDecimal.ROUND_HALF_UP
            && roundingPolicy != BigDecimal.ROUND_UNNECESSARY
            && roundingPolicy != BigDecimal.ROUND_UP) 
        {
            throw new IllegalArgumentException("invalid rounding policy");        
        }
        
        this.roundingPolicy = roundingPolicy;
    }

    /**
     * This is the default arithmetic engine in FreeMarker. It converts every
     * number it receives into {@link BigDecimal}, then operates on these
     * converted {@link BigDecimal}s.
     */
    public static class BigDecimalEngine
    extends
        ArithmeticEngine    
    {
        public int compareNumbers(Number first, Number second) {
            BigDecimal left = toBigDecimal(first);
            BigDecimal right = toBigDecimal(second);
            return left.compareTo(right);
        }
    
        public Number add(Number first, Number second) {
            BigDecimal left = toBigDecimal(first);
            BigDecimal right = toBigDecimal(second);
            return left.add(right);
        }
    
        public Number subtract(Number first, Number second) {
            BigDecimal left = toBigDecimal(first);
            BigDecimal right = toBigDecimal(second);
            return left.subtract(right);
        }
    
        public Number multiply(Number first, Number second) {
            BigDecimal left = toBigDecimal(first);
            BigDecimal right = toBigDecimal(second);
            BigDecimal result = left.multiply(right);
            if (result.scale() > maxScale) {
                result = result.setScale(maxScale, roundingPolicy);
            }
            return result;
        }
    
        public Number divide(Number first, Number second) {
            BigDecimal left = toBigDecimal(first);
            BigDecimal right = toBigDecimal(second);
            return divide(left, right);
        }
    
        public Number modulus(Number first, Number second) {
            long left = first.longValue();
            long right = second.longValue();
            return new Long(left % right);
        }
    
        public Number toNumber(String s) {
            return new BigDecimal(s);
        }
        
        private BigDecimal divide(BigDecimal left, BigDecimal right) {
            int scale1 = left.scale();
            int scale2 = right.scale();
            int scale = Math.max(scale1, scale2);
            scale = Math.max(minScale, scale);
            return left.divide(right, scale, roundingPolicy);
        }
    }

    /**
     * An arithmetic engine that conservatively widens the operation arguments
     * to extent that they can hold the result of the operation. Widening 
     * conversions occur in following situations:
     * <ul>
     * <li>byte and short are always widened to int (alike to Java language).</li>
     * <li>To preserve magnitude: when operands are of different types, the 
     * result type is the type of the wider operand.</li>
     * <li>to avoid overflows: if add, subtract, or multiply would overflow on
     * integer types, the result is widened from int to long, or from long to 
     * BigInteger.</li>
     * <li>to preserve fractional part: if a division of integer types would 
     * have a fractional part, int and long are converted to double, and 
     * BigInteger is converted to BigDecimal. An operation on a float and a 
     * long results in a double. An operation on a float or double and a
     * BigInteger results in a BigDecimal.</li>
     * </ul>
     */
    public static class ConservativeEngine extends ArithmeticEngine {
        private static final int INTEGER = 0;
        private static final int LONG = 1;
        private static final int FLOAT = 2;
        private static final int DOUBLE = 3;
        private static final int BIGINTEGER = 4;
        private static final int BIGDECIMAL = 5;
        
		private static final Map classCodes = createClassCodesMap();
        
        public int compareNumbers(Number first, Number second) {
            switch(getCommonClassCode(first, second)) {
                case INTEGER: {
                    int n1 = first.intValue();
                    int n2 = second.intValue();
                    return  n1 < n2 ? -1 : (n1 == n2 ? 0 : 1);
                }
                case LONG: {
                    long n1 = first.longValue();
                    long n2 = second.longValue();
                    return  n1 < n2 ? -1 : (n1 == n2 ? 0 : 1);
                }
                case FLOAT: {
                    float n1 = first.floatValue();
                    float n2 = second.floatValue();
                    return  n1 < n2 ? -1 : (n1 == n2 ? 0 : 1);
                }
                case DOUBLE: {
                    double n1 = first.doubleValue();
                    double n2 = second.doubleValue();
                    return  n1 < n2 ? -1 : (n1 == n2 ? 0 : 1);
                }
                case BIGINTEGER: {
                    BigInteger n1 = toBigInteger(first);
                    BigInteger n2 = toBigInteger(second);
                    return n1.compareTo(n2);
                }
                case BIGDECIMAL: {
                    BigDecimal n1 = toBigDecimal(first);
                    BigDecimal n2 = toBigDecimal(second);
                    return n1.compareTo(n2);
                }
            }
            // Make the compiler happy. getCommonClassCode() is guaranteed to 
            // return only above codes, or throw an exception.
            throw new Error();
        }
    
        public Number add(Number first, Number second) {
            switch(getCommonClassCode(first, second)) {
                case INTEGER: {
                    int n1 = first.intValue();
                    int n2 = second.intValue();
                    int n = n1 + n2;
                    return
                        ((n ^ n1) < 0 && (n ^ n2) < 0) // overflow check
                        ? (Number)new Long(((long)n1) + n2)
                        : (Number)new Integer(n);
                }
                case LONG: {
                    long n1 = first.longValue();
                    long n2 = second.longValue();
                    long n = n1 + n2;
                    return
                        ((n ^ n1) < 0 && (n ^ n2) < 0) // overflow check
                        ? (Number)toBigInteger(first).add(toBigInteger(second))
                        : (Number)new Long(n);
                }
                case FLOAT: {
                    return new Float(first.floatValue() + second.floatValue());
                }
                case DOUBLE: {
                    return new Double(first.doubleValue() + second.doubleValue());
                }
                case BIGINTEGER: {
                    BigInteger n1 = toBigInteger(first);
                    BigInteger n2 = toBigInteger(second);
                    return n1.add(n2);
                }
                case BIGDECIMAL: {
                    BigDecimal n1 = toBigDecimal(first);
                    BigDecimal n2 = toBigDecimal(second);
                    return n1.add(n2);
                }
            }
            // Make the compiler happy. getCommonClassCode() is guaranteed to 
            // return only above codes, or throw an exception.
            throw new Error();
        }
    
        public Number subtract(Number first, Number second) {
            switch(getCommonClassCode(first, second)) {
                case INTEGER: {
                    int n1 = first.intValue();
                    int n2 = second.intValue();
                    int n = n1 - n2;
                    return
                        ((n ^ n1) < 0 && (n ^ ~n2) < 0) // overflow check
                        ? (Number)new Long(((long)n1) - n2)
                        : (Number)new Integer(n);
                }
                case LONG: {
                    long n1 = first.longValue();
                    long n2 = second.longValue();
                    long n = n1 - n2;
                    return
                        ((n ^ n1) < 0 && (n ^ ~n2) < 0) // overflow check
                        ? (Number)toBigInteger(first).subtract(toBigInteger(second))
                        : (Number)new Long(n);
                }
                case FLOAT: {
                    return new Float(first.floatValue() - second.floatValue());
                }
                case DOUBLE: {
                    return new Double(first.doubleValue() - second.doubleValue());
                }
                case BIGINTEGER: {
                    BigInteger n1 = toBigInteger(first);
                    BigInteger n2 = toBigInteger(second);
                    return n1.subtract(n2);
                }
                case BIGDECIMAL: {
                    BigDecimal n1 = toBigDecimal(first);
                    BigDecimal n2 = toBigDecimal(second);
                    return n1.subtract(n2);
                }
            }
            // Make the compiler happy. getCommonClassCode() is guaranteed to 
            // return only above codes, or throw an exception.
            throw new Error();
        }
    
        public Number multiply(Number first, Number second) {
            switch(getCommonClassCode(first, second)) {
                case INTEGER: {
                    int n1 = first.intValue();
                    int n2 = second.intValue();
                    int n = n1 * n2;
                    return
                        n1== 0 || n/n1 == n2 // overflow check
                        ? (Number)new Integer(n)
                        : (Number)new Long(((long)n1) * n2);
                }
                case LONG: {
                    long n1 = first.longValue();
                    long n2 = second.longValue();
                    long n = n1 * n2;
                    return
                        n1==0L || n / n1 == n2 // overflow check
                        ? (Number)new Long(n)
                        : (Number)toBigInteger(first).multiply(toBigInteger(second));
                }
                case FLOAT: {
                    return new Float(first.floatValue() * second.floatValue());
                }
                case DOUBLE: {
                    return new Double(first.doubleValue() * second.doubleValue());
                }
                case BIGINTEGER: {
                    BigInteger n1 = toBigInteger(first);
                    BigInteger n2 = toBigInteger(second);
                    return n1.multiply(n2);
                }
                case BIGDECIMAL: {
                    BigDecimal n1 = toBigDecimal(first);
                    BigDecimal n2 = toBigDecimal(second);
                    BigDecimal r = n1.multiply(n2);
                    return r.scale() > maxScale ? r.setScale(maxScale, roundingPolicy) : r;
                }
            }
            // Make the compiler happy. getCommonClassCode() is guaranteed to 
            // return only above codes, or throw an exception.
            throw new Error();
        }
    
        public Number divide(Number first, Number second) {
            switch(getCommonClassCode(first, second)) {
                case INTEGER: {
                    int n1 = first.intValue();
                    int n2 = second.intValue();
                    if (n1 % n2 == 0) {
                        return new Integer(n1/n2);
                    }
                    return new Double(((double)n1)/n2);
                }
                case LONG: {
                    long n1 = first.longValue();
                    long n2 = second.longValue();
                    if (n1 % n2 == 0) {
                        return new Long(n1/n2);
                    }
                    return new Double(((double)n1)/n2);
                }
                case FLOAT: {
                    return new Float(first.floatValue() / second.floatValue());
                }
                case DOUBLE: {
                    return new Double(first.doubleValue() / second.doubleValue());
                }
                case BIGINTEGER: {
                    BigInteger n1 = toBigInteger(first);
                    BigInteger n2 = toBigInteger(second);
                    BigInteger[] divmod = n1.divideAndRemainder(n2);
                    if(divmod[1].equals(BigInteger.ZERO)) {
                        return divmod[0];
                    }
                    else {
                        BigDecimal bd1 = new BigDecimal(n1);
                        BigDecimal bd2 = new BigDecimal(n2);
                        return bd1.divide(bd2, minScale, roundingPolicy);
                    }
                }
                case BIGDECIMAL: {
                    BigDecimal n1 = toBigDecimal(first);
                    BigDecimal n2 = toBigDecimal(second);
                    int scale1 = n1.scale();
                    int scale2 = n2.scale();
                    int scale = Math.max(scale1, scale2);
                    scale = Math.max(minScale, scale);
                    return n1.divide(n2, scale, roundingPolicy);
                }
            }
            // Make the compiler happy. getCommonClassCode() is guaranteed to 
            // return only above codes, or throw an exception.
            throw new Error();
        }
    
        public Number modulus(Number first, Number second) {
            switch(getCommonClassCode(first, second)) {
                case INTEGER: {
                    return new Integer(first.intValue() % second.intValue());
                }
                case LONG: {
                    return new Long(first.longValue() % second.longValue());
                }
                case FLOAT: {
                    return new Float(first.floatValue() % second.floatValue());
                }
                case DOUBLE: {
                    return new Double(first.doubleValue() % second.doubleValue());
                }
                case BIGINTEGER: {
                    BigInteger n1 = toBigInteger(first);
                    BigInteger n2 = toBigInteger(second);
                    return n1.mod(n2);
                }
                case BIGDECIMAL: {
                    throw new CalculationException("Can't calculate remainder on BigDecimals");
                }
            }
            // Make the compiler happy. getCommonClassCode() is guaranteed to 
            // return only above codes, or throw an exception.
            throw new Error();
        }
    
        public Number toNumber(String s) {
            return OptimizerUtil.optimizeNumberRepresentation(new BigDecimal(s));
        }
        
        private static Map createClassCodesMap() {
            Map<Class, Integer> map = new HashMap<Class, Integer>(17);
            Integer intcode = new Integer(INTEGER);
            map.put(Byte.class, intcode);
            map.put(Short.class, intcode);
            map.put(Integer.class, intcode);
            map.put(Long.class, new Integer(LONG));
            map.put(Float.class, new Integer(FLOAT));
            map.put(Double.class, new Integer(DOUBLE));
            map.put(BigInteger.class, new Integer(BIGINTEGER));
            map.put(BigDecimal.class, new Integer(BIGDECIMAL));
            return map;
        }
        
        private static int getClassCode(Number num) {
            try {
                return ((Integer)classCodes.get(num.getClass())).intValue();
            }
            catch(NullPointerException e) {
                if(num == null) {
                    throw new CalculationException("Unknown number type null");
                }
                throw new CalculationException("Unknown number type " + num.getClass().getName());
            }
        }
        
        private static int getCommonClassCode(Number num1, Number num2) {
            int c1 = getClassCode(num1);
            int c2 = getClassCode(num2);
            int c = c1 > c2 ? c1 : c2;
            // If BigInteger is combined with a Float or Double, the result is a
            // BigDecimal instead of BigInteger in order not to lose the 
            // fractional parts. If Float is combined with Long, the result is a
            // Double instead of Float to preserve the bigger bit width.
            switch(c) {
                case FLOAT: {
                    if((c1 < c2 ? c1 : c2) == LONG) {
                        return DOUBLE;
                    }
                    break;
                }
                case BIGINTEGER: {
                    int min = c1 < c2 ? c1 : c2;
                    if(min == DOUBLE || min == FLOAT) {
                        return BIGDECIMAL;
                    }
                    break;
                }
            }
            return c;
        }
        
        private static BigInteger toBigInteger(Number num) {
            return num instanceof BigInteger ? (BigInteger) num : new BigInteger(num.toString());
        }
    }

    private static BigDecimal toBigDecimal(Number num) {
        return num instanceof BigDecimal ? (BigDecimal) num : new BigDecimal(num.toString());
    }
}
