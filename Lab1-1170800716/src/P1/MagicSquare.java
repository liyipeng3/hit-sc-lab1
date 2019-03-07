import java.io.*;
import java.util.regex.*;
import java.util.List;
import java.util.ArrayList;

public class MagicSquare {

	public static void main(String[] args) {
		System.out.println(isLegalMagicSquare("1.txt"));
		System.out.println(isLegalMagicSquare("2.txt"));
		System.out.println(isLegalMagicSquare("3.txt"));
		System.out.println(isLegalMagicSquare("4.txt"));
		System.out.println(isLegalMagicSquare("5.txt"));
		System.out.println(generateMagicSquare(5));
		System.out.println(isLegalMagicSquare("6.txt"));
	}

	public static boolean isInteger(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	public static boolean isLegalMagicSquare(String fileName) {
		int linenum = 0;
		int sum = 0;
		int temp = 0;
		String[] linesp;
		List<String[]> list = new ArrayList<>();
		String path = "src\\P1\\txt\\" + fileName;
		int[][] matrix;

		try (FileReader reader = new FileReader(path); BufferedReader br = new BufferedReader(reader)) {
			String line;
			while ((line = br.readLine()) != null) {
				linesp = line.split("\t");
				for (int i = 0; i < linesp.length; i++) {
					if (linesp[i].indexOf(' ') != -1) {
						throw new Exception("Error！Tne number is not spilt by '\\t'.");
					}
				}
				list.add(linesp);
				linenum++;
			}
			for (int i = 0; i < list.size(); i++) {
				linesp = list.get(i);
				if (linesp.length != linenum) {
					throw new Exception("Error！The matrix is not a standard n*n matrix.");
				}
			}
			matrix = new int[linenum][linenum];
			for (int i = 0; i < linenum; i++) {
				linesp = list.get(i);
				for (int j = 0; j < linenum; j++) {
					if (!isInteger(linesp[j]) || Integer.valueOf(linesp[j]) < 0)
						throw new Exception("Error！The matrix has negative or decimal.");

					matrix[i][j] = Integer.valueOf(linesp[j]); // Storage the matrix to two-dimensional array.
				}
			}
			for (int i = 0; i < linenum; i++) {
				sum += matrix[0][i];
				temp += matrix[i][i];
			}
			if (sum != temp)
				return false;
			temp = 0;
			for (int i = 0; i < linenum; i++) {
				temp += matrix[i][linenum - i - 1];
			}
			if (sum != temp)
				return false;
			for (int i = 0; i < linenum; i++) {
				temp = 0;
				for (int j = 0; j < linenum; j++) {
					temp += matrix[i][j];
				}
				if (sum != temp)
					return false;
			}
			for (int i = 0; i < linenum; i++) {
				temp = 0;
				for (int j = 0; j < linenum; j++) {
					temp += matrix[j][i];
				}
				if (sum != temp)
					return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public static boolean generateMagicSquare(int n) {
		try {
			if (n < 0) {
				throw new Exception("错误！n为负数");
			}
			if (n % 2 == 0 || n == 0) {
				throw new Exception("错误！n为偶数");
			}
			int magic[][] = new int[n][n];
			int row = 0, col = n / 2, i, j, square = n * n;
			for (i = 1; i <= square; i++) {
				magic[row][col] = i; // 把1放在第一行正中间
				if (i % n == 0)
					row++; // 如果这个数所要放的格已经有数填入，那么就把它放在前一个数的下一行同一列的格内
				else {
					if (row == 0) // 如果这个数所要放的格已经超出了顶行那么就把它放在底行，仍然要放在右一列
						row = n - 1;
					else
						row--;
					if (col == (n - 1)) // 如果这个数所要放的格已经超出了最右列那么就把它放在最左列，仍然要放在上一行
						col = 0;
					else
						col++;
				}
			}
			FileWriter fw = null;
			File f = new File("src\\P1\\txt\\" + "6.txt"); // 创建新文件
			if (!f.exists()) {
				f.createNewFile();
			}
			fw = new FileWriter(f);
			BufferedWriter out = new BufferedWriter(fw);

			System.out.println("end");
			for (i = 0; i < n; i++) { // 打印并且储存矩阵
				for (j = 0; j < n; j++) {
					String s = magic[i][j] + "\t";
					System.out.print(s);
					out.write(s, 0, s.length() - 1);
					out.write('\t');
				}
				System.out.println();
				out.write('\n');
			}
			out.close(); // 关闭文件
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
