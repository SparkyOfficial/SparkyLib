package com.sparky.minecraft.math;

/**
 * Матриця 4x4 для лінійної алгебри та 3D перетворень.
 *
 * @author Андрій Будильников
 */
public class Matrix4x4 {
    private double[][] elements;
    
    public Matrix4x4() {
        elements = new double[4][4];
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                elements[i][j] = (i == j) ? 1.0 : 0.0;
            }
        }
    }
    
    public Matrix4x4(double[][] elements) {
        if (elements.length != 4 || elements[0].length != 4) {
            throw new IllegalArgumentException("Matrix must be 4x4");
        }
        this.elements = new double[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(elements[i], 0, this.elements[i], 0, 4);
        }
    }
    
    public Matrix4x4(Matrix4x4 other) {
        this.elements = new double[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(other.elements[i], 0, this.elements[i], 0, 4);
        }
    }
    
    
    public double get(int row, int col) {
        if (row < 0 || row >= 4 || col < 0 || col >= 4) {
            throw new IndexOutOfBoundsException("Matrix indices must be between 0 and 3");
        }
        return elements[row][col];
    }
    
    public void set(int row, int col, double value) {
        if (row < 0 || row >= 4 || col < 0 || col >= 4) {
            throw new IndexOutOfBoundsException("Matrix indices must be between 0 and 3");
        }
        elements[row][col] = value;
    }
    
    /**
     * Повертає транспоновану матрицю.
     */
    public Matrix4x4 transpose() {
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.elements[i][j] = elements[j][i];
            }
        }
        return result;
    }
    
    /**
     * Додає іншу матрицю.
     */
    public Matrix4x4 add(Matrix4x4 other) {
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.elements[i][j] = elements[i][j] + other.elements[i][j];
            }
        }
        return result;
    }
    
    /**
     * Віднімає іншу матрицю.
     */
    public Matrix4x4 subtract(Matrix4x4 other) {
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.elements[i][j] = elements[i][j] - other.elements[i][j];
            }
        }
        return result;
    }
    
    /**
     * Множить на іншу матрицю.
     */
    public Matrix4x4 multiply(Matrix4x4 other) {
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum += elements[i][k] * other.elements[k][j];
                }
                result.elements[i][j] = sum;
            }
        }
        return result;
    }
    
    /**
     * Множить на скаляр.
     */
    public Matrix4x4 multiply(double scalar) {
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.elements[i][j] = elements[i][j] * scalar;
            }
        }
        return result;
    }
    
    /**
     * Ділить на скаляр.
     */
    public Matrix4x4 divide(double scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Division by zero");
        }
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.elements[i][j] = elements[i][j] / scalar;
            }
        }
        return result;
    }
    
    /**
     * Обчислює детермінант матриці.
     */
    public double determinant() {
        // Використовуємо розклад за першим рядком
        double det = 0;
        for (int j = 0; j < 4; j++) {
            det += elements[0][j] * cofactor(0, j);
        }
        return det;
    }
    
    /**
     * Обчислює мінор елемента.
     */
    private double minor(int row, int col) {
        // Створюємо 3x3 матрицю без вказаного рядка та стовпця
        double[][] minorMatrix = new double[3][3];
        int minorRow = 0;
        for (int i = 0; i < 4; i++) {
            if (i == row) continue;
            int minorCol = 0;
            for (int j = 0; j < 4; j++) {
                if (j == col) continue;
                minorMatrix[minorRow][minorCol] = elements[i][j];
                minorCol++;
            }
            minorRow++;
        }
        
        // Обчислюємо детермінант 3x3 матриці
        return minorMatrix[0][0] * (minorMatrix[1][1] * minorMatrix[2][2] - minorMatrix[1][2] * minorMatrix[2][1])
             - minorMatrix[0][1] * (minorMatrix[1][0] * minorMatrix[2][2] - minorMatrix[1][2] * minorMatrix[2][0])
             + minorMatrix[0][2] * (minorMatrix[1][0] * minorMatrix[2][1] - minorMatrix[1][1] * minorMatrix[2][0]);
    }
    
    /**
     * Обчислює алгебраїчне доповнення елемента.
     */
    private double cofactor(int row, int col) {
        double minor = minor(row, col);
        return ((row + col) % 2 == 0) ? minor : -minor;
    }
    
    /**
     * Повертає обернену матрицю.
     */
    public Matrix4x4 inverse() {
        double det = determinant();
        if (Math.abs(det) < 1e-10) {
            throw new ArithmeticException("Matrix is not invertible (determinant is zero)");
        }
        
        Matrix4x4 cofactorMatrix = new Matrix4x4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cofactorMatrix.elements[i][j] = cofactor(i, j);
            }
        }
        
        Matrix4x4 adjugate = cofactorMatrix.transpose();
        return adjugate.multiply(1.0 / det);
    }
    
    /**
     * Створює матрицю перенесення.
     */
    public static Matrix4x4 translation(double x, double y, double z) {
        Matrix4x4 matrix = new Matrix4x4();
        matrix.elements[0][3] = x;
        matrix.elements[1][3] = y;
        matrix.elements[2][3] = z;
        return matrix;
    }
    
    /**
     * Створює матрицю масштабування.
     */
    public static Matrix4x4 scaling(double x, double y, double z) {
        Matrix4x4 matrix = new Matrix4x4();
        matrix.elements[0][0] = x;
        matrix.elements[1][1] = y;
        matrix.elements[2][2] = z;
        return matrix;
    }
    
    /**
     * Створює матрицю обертання навколо осі X.
     */
    public static Matrix4x4 rotationX(double angleRadians) {
        Matrix4x4 matrix = new Matrix4x4();
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);
        matrix.elements[1][1] = cos;
        matrix.elements[1][2] = -sin;
        matrix.elements[2][1] = sin;
        matrix.elements[2][2] = cos;
        return matrix;
    }
    
    /**
     * Створює матрицю обертання навколо осі Y.
     */
    public static Matrix4x4 rotationY(double angleRadians) {
        Matrix4x4 matrix = new Matrix4x4();
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);
        matrix.elements[0][0] = cos;
        matrix.elements[0][2] = sin;
        matrix.elements[2][0] = -sin;
        matrix.elements[2][2] = cos;
        return matrix;
    }
    
    /**
     * Створює матрицю обертання навколо осі Z.
     */
    public static Matrix4x4 rotationZ(double angleRadians) {
        Matrix4x4 matrix = new Matrix4x4();
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);
        matrix.elements[0][0] = cos;
        matrix.elements[0][1] = -sin;
        matrix.elements[1][0] = sin;
        matrix.elements[1][1] = cos;
        return matrix;
    }
    
    /**
     * Створює матрицю перспективної проекції.
     */
    public static Matrix4x4 perspective(double fovRadians, double aspectRatio, double near, double far) {
        Matrix4x4 matrix = new Matrix4x4();
        double f = 1.0 / Math.tan(fovRadians / 2);
        
        matrix.elements[0][0] = f / aspectRatio;
        matrix.elements[1][1] = f;
        matrix.elements[2][2] = (far + near) / (near - far);
        matrix.elements[2][3] = (2 * far * near) / (near - far);
        matrix.elements[3][2] = -1;
        matrix.elements[3][3] = 0;
        
        return matrix;
    }
    
    /**
     * Створює матрицю ортографічної проекції.
     */
    public static Matrix4x4 orthographic(double left, double right, double bottom, double top, double near, double far) {
        Matrix4x4 matrix = new Matrix4x4();
        
        matrix.elements[0][0] = 2 / (right - left);
        matrix.elements[1][1] = 2 / (top - bottom);
        matrix.elements[2][2] = -2 / (far - near);
        matrix.elements[0][3] = -(right + left) / (right - left);
        matrix.elements[1][3] = -(top + bottom) / (top - bottom);
        matrix.elements[2][3] = -(far + near) / (far - near);
        
        return matrix;
    }
    
    /**
     * Множить матрицю на вектор.
     */
    public com.sparky.minecraft.math.Vector3D transform(com.sparky.minecraft.math.Vector3D vector) {
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();
        
        double newX = elements[0][0] * x + elements[0][1] * y + elements[0][2] * z + elements[0][3];
        double newY = elements[1][0] * x + elements[1][1] * y + elements[1][2] * z + elements[1][3];
        double newZ = elements[2][0] * x + elements[2][1] * y + elements[2][2] * z + elements[2][3];
        double w = elements[3][0] * x + elements[3][1] * y + elements[3][2] * z + elements[3][3];
        
        // Якщо w не дорівнює 1, нормалізуємо
        if (Math.abs(w - 1.0) > 1e-10) {
            newX /= w;
            newY /= w;
            newZ /= w;
        }
        
        return new com.sparky.minecraft.math.Vector3D(newX, newY, newZ);
    }
    
    /**
     * Повертає одиничну матрицю.
     */
    public static Matrix4x4 identity() {
        return new Matrix4x4();
    }
    
    /**
     * Повертає нульову матрицю.
     */
    public static Matrix4x4 zero() {
        return new Matrix4x4(new double[4][4]);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Matrix4x4 matrix4x4 = (Matrix4x4) obj;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Math.abs(elements[i][j] - matrix4x4.elements[i][j]) > 1e-10) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                long bits = Double.doubleToLongBits(elements[i][j]);
                result = 31 * result + (int) (bits ^ (bits >>> 32));
            }
        }
        return result;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrix4x4:\n");
        for (int i = 0; i < 4; i++) {
            sb.append("[ ");
            for (int j = 0; j < 4; j++) {
                sb.append(String.format("%8.3f", elements[i][j]));
                if (j < 3) sb.append(", ");
            }
            sb.append(" ]\n");
        }
        return sb.toString();
    }
}