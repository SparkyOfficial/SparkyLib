package com.sparky.minecraft.math;

/**
 * Комплексне число для математичних обчислень.
 *
 * @author Андрій Будильников
 */
public class ComplexNumber {
    private double real;
    private double imaginary;
    
    public ComplexNumber() {
        this(0, 0);
    }
    
    public ComplexNumber(double real) {
        this(real, 0);
    }
    
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }
    
    public ComplexNumber(ComplexNumber other) {
        this.real = other.real;
        this.imaginary = other.imaginary;
    }
    
    // Геттери та сеттери
    public double getReal() {
        return real;
    }
    
    public void setReal(double real) {
        this.real = real;
    }
    
    public double getImaginary() {
        return imaginary;
    }
    
    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }
    
    /**
     * Повертає модуль (абсолютне значення) комплексного числа.
     */
    public double modulus() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }
    
    /**
     * Повертає квадрат модуля комплексного числа.
     */
    public double modulusSquared() {
        return real * real + imaginary * imaginary;
    }
    
    /**
     * Повертає аргумент комплексного числа в радіанах.
     */
    public double argument() {
        return Math.atan2(imaginary, real);
    }
    
    /**
     * Повертає аргумент комплексного числа в градусах.
     */
    public double argumentDegrees() {
        return Math.toDegrees(argument());
    }
    
    /**
     * Повертає спряжене комплексне число.
     */
    public ComplexNumber conjugate() {
        return new ComplexNumber(real, -imaginary);
    }
    
    /**
     * Додає інше комплексне число.
     */
    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.real + other.real, this.imaginary + other.imaginary);
    }
    
    /**
     * Додає дійсне число.
     */
    public ComplexNumber add(double real) {
        return new ComplexNumber(this.real + real, this.imaginary);
    }
    
    /**
     * Віднімає інше комплексне число.
     */
    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.real - other.real, this.imaginary - other.imaginary);
    }
    
    /**
     * Віднімає дійсне число.
     */
    public ComplexNumber subtract(double real) {
        return new ComplexNumber(this.real - real, this.imaginary);
    }
    
    /**
     * Множить на інше комплексне число.
     */
    public ComplexNumber multiply(ComplexNumber other) {
        double newReal = this.real * other.real - this.imaginary * other.imaginary;
        double newImaginary = this.real * other.imaginary + this.imaginary * other.real;
        return new ComplexNumber(newReal, newImaginary);
    }
    
    /**
     * Множить на дійсне число.
     */
    public ComplexNumber multiply(double scalar) {
        return new ComplexNumber(this.real * scalar, this.imaginary * scalar);
    }
    
    /**
     * Ділить на інше комплексне число.
     */
    public ComplexNumber divide(ComplexNumber other) {
        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        if (denominator == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        double newReal = (this.real * other.real + this.imaginary * other.imaginary) / denominator;
        double newImaginary = (this.imaginary * other.real - this.real * other.imaginary) / denominator;
        return new ComplexNumber(newReal, newImaginary);
    }
    
    /**
     * Ділить на дійсне число.
     */
    public ComplexNumber divide(double scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new ComplexNumber(this.real / scalar, this.imaginary / scalar);
    }
    
    /**
     * Підносить до степеня.
     */
    public ComplexNumber power(double exponent) {
        if (real == 0 && imaginary == 0) {
            if (exponent > 0) {
                return new ComplexNumber(0, 0);
            } else {
                throw new ArithmeticException("Zero to negative or zero power");
            }
        }
        
        double modulus = Math.pow(modulus(), exponent);
        double argument = argument() * exponent;
        
        double newReal = modulus * Math.cos(argument);
        double newImaginary = modulus * Math.sin(argument);
        
        return new ComplexNumber(newReal, newImaginary);
    }
    
    /**
     * Обчислює квадратний корінь.
     */
    public ComplexNumber sqrt() {
        return power(0.5);
    }
    
    /**
     * Обчислює експоненту.
     */
    public ComplexNumber exp() {
        double expReal = Math.exp(real);
        double newReal = expReal * Math.cos(imaginary);
        double newImaginary = expReal * Math.sin(imaginary);
        return new ComplexNumber(newReal, newImaginary);
    }
    
    /**
     * Обчислює натуральний логарифм.
     */
    public ComplexNumber log() {
        double modulus = modulus();
        if (modulus == 0) {
            throw new ArithmeticException("Logarithm of zero");
        }
        
        double newReal = Math.log(modulus);
        double newImaginary = argument();
        
        return new ComplexNumber(newReal, newImaginary);
    }
    
    /**
     * Обчислює синус.
     */
    public ComplexNumber sin() {
        double expPlus = Math.exp(imaginary);
        double expMinus = Math.exp(-imaginary);
        double newReal = Math.sin(real) * (expPlus + expMinus) / 2;
        double newImaginary = Math.cos(real) * (expPlus - expMinus) / 2;
        return new ComplexNumber(newReal, newImaginary);
    }
    
    /**
     * Обчислює косинус.
     */
    public ComplexNumber cos() {
        double expPlus = Math.exp(imaginary);
        double expMinus = Math.exp(-imaginary);
        double newReal = Math.cos(real) * (expPlus + expMinus) / 2;
        double newImaginary = -Math.sin(real) * (expPlus - expMinus) / 2;
        return new ComplexNumber(newReal, newImaginary);
    }
    
    /**
     * Обчислює тангенс.
     */
    public ComplexNumber tan() {
        ComplexNumber sinValue = sin();
        ComplexNumber cosValue = cos();
        return sinValue.divide(cosValue);
    }
    
    /**
     * Інтерполює між цим комплексним числом та іншим.
     */
    public ComplexNumber lerp(ComplexNumber other, double t) {
        t = Math.max(0, Math.min(1, t)); // Обмежуємо t між 0 та 1
        double newReal = this.real + (other.real - this.real) * t;
        double newImaginary = this.imaginary + (other.imaginary - this.imaginary) * t;
        return new ComplexNumber(newReal, newImaginary);
    }
    
    /**
     * Повертає комплексне число у форматі "a + bi".
     */
    @Override
    public String toString() {
        if (imaginary == 0) {
            return String.valueOf(real);
        } else if (real == 0) {
            return imaginary + "i";
        } else if (imaginary < 0) {
            return real + " - " + (-imaginary) + "i";
        } else {
            return real + " + " + imaginary + "i";
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ComplexNumber that = (ComplexNumber) obj;
        return Double.compare(that.real, real) == 0 &&
               Double.compare(that.imaginary, imaginary) == 0;
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(real, imaginary);
    }
}