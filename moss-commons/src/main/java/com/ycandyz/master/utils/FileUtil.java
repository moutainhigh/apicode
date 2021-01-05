package com.ycandyz.master.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
	/**
	 * 
	 * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
	 */
	private FileUtil() {

	}

	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。
	 * 
	 * @param file
	 *            需要修改最后访问时间的文件。
	 */
	public static void touch(File file) {
		long currentTime = System.currentTimeMillis();
		if (!file.exists()) {
			System.err.println("file not found:" + file.getName());
			System.err.println("Create a new file:" + file.getName());
			try {
				if (file.createNewFile()) {
					System.out.println("Succeeded!");
				} else {
					System.err.println("Create file failed!");
				}
			} catch (IOException e) {
				System.err.println("Create file failed!");
				e.printStackTrace();
			}
		}
		boolean result = file.setLastModified(currentTime);
		if (!result) {
			System.err.println("touch failed: " + file.getName());
		}
	}

	public static void touch(String fileName) {
		File file = new File(fileName);
		touch(file);
	}

	public static void touch(File[] files) {
		for (int i = 0; i < files.length; i++) {
			touch(files[i]);
		}
	}

	public static void touch(String[] fileNames) {
		File[] files = new File[fileNames.length];
		for (int i = 0; i < fileNames.length; i++) {
			files[i] = new File(fileNames[i]);
		}
		touch(files);
	}

	/**
	 * 判断指定的文件是否存在。
	 * 
	 * @param fileName
	 *            要判断的文件的文件名
	 * @return 存在时返回true，否则返回false。
	 * @since 1.0
	 */
	public static boolean isFileExist(String fileName) {
		return new File(fileName).isFile();
	}

	/**
	 * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。 <b>注意：可能会在返回false的时候创建部分父目录。</b>
	 * 
	 * @param file
	 *            要创建的目录
	 * @return 完全创建成功时返回true，否则返回false。
	 * @since 1.0
	 */
	public static boolean makeDirectory(File file) {
		File parent = file.getParentFile();
		if (parent != null) {
			return parent.mkdirs();
		}
		return false;
	}

	public static boolean makeDirectory(String fileName) {
		File file = new File(fileName);
		return makeDirectory(file);
	}

	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 * 
	 * @param directory
	 *            要清空的目录
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
	 */
	public static boolean emptyDirectory(File directory) {
		boolean result = true;
		File[] entries = directory.listFiles();
		for (int i = 0; i < entries.length; i++) {
			if (!entries[i].delete()) {
				result = false;
			}
		}
		return result;
	}

	public static boolean emptyDirectory(String directoryName) {
		File dir = new File(directoryName);
		return emptyDirectory(dir);
	}

	/**
	 * 删除指定目录及其中的所有内容。
	 * 
	 * @param dirName
	 *            要删除的目录的目录名
	 * @return 删除成功时返回true，否则返回false。
	 * @since 1.0
	 */
	public static boolean deleteDirectory(String dirName) {
		return deleteDirectory(new File(dirName));
	}

	public static boolean deleteDirectory(File dir) {
		if ((dir == null) || !dir.isDirectory()) {
			throw new IllegalArgumentException("Argument " + dir + " is not a directory. ");
		}

		File[] entries = dir.listFiles();
		int sz = entries.length;

		for (int i = 0; i < sz; i++) {
			if (entries[i].isDirectory()) {
				if (!deleteDirectory(entries[i])) {
					return false;
				}
			} else {
				if (!entries[i].delete()) {
					return false;
				}
			}
		}

		if (!dir.delete()) {
			return false;
		}
		return true;
	}

	/**
	 * 列出目录中的所有内容，包括其子目录中的内容。
	 *
	 *            要列出的目录的目录名
	 * @return 目录内容的文件数组。
	 * @since 1.0
	 */
	public static File[] listAll(File file, FileFilter filter) {
		ArrayList<File> list = new ArrayList<File>();
		File[] files;
		if (!file.exists() || file.isFile()) {
			return null;
		}
		list(list, file, filter);
		files = new File[list.size()];
		list.toArray(files);
		return files;
	}

	/**
	 * 将目录中的内容添加到列表集合。
	 * 
	 * @param list
	 *            文件列表
	 * @param filter
	 *            过滤器
	 * @param file
	 *            目录
	 */
	private static void list(ArrayList<File> list, File file, FileFilter filter) {
		if (filter.accept(file)) {
			list.add(file);
			if (file.isFile()) {
				return;
			}
		}
		if (file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				list(list, files[i], filter);
			}
		}

	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件名中的类型部分
	 */
	public static String getTypePart(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	public static String getFileType(File file) {
		return getTypePart(file.getName());
	}

	/**
	 * 检查给定目录的存在性 保证指定的路径可用，如果指定的路径不存在，那么建立该路径，可以为多级路径
	 */
	public static final boolean pathValidate(String path) {
		String[] arraypath = path.split("/");
		String tmppath = "";
		for (int i = 0; i < arraypath.length; i++) {
			tmppath += "/" + arraypath[i];
			File d = new File(tmppath.substring(1));
			if (!d.exists()) { // 检查Sub目录是否存在
				System.out.println(tmppath.substring(1));
				if (!d.mkdir()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 读取文件的内容 读取指定文件的内容
	 * 
	 * @param path
	 *            为要读取文件的绝对路径
	 * @return 以行读取文件后的内容。
	 * @since 1.0
	 */
	public static final String getFileContent(String path) throws IOException {
		String filecontent = "";
		try {
			File f = new File(path);
			if (f.exists()) {
				FileReader fr = new FileReader(path);
				BufferedReader br = new BufferedReader(fr); // 建立BufferedReader对象，并实例化为br
				String line = br.readLine(); // 从文件读取一行字符串
				// 判断读取到的字符串是否不为空
				while (line != null) {
					filecontent += line + "\n";
					line = br.readLine(); // 从文件中继续读取一行数据
				}
				br.close(); // 关闭BufferedReader对象
				fr.close(); // 关闭文件
			}

		} catch (IOException e) {
			throw e;
		}
		return filecontent;
	}

	// 写入文件
	public static final boolean writeFile(byte[] in, File out) throws Exception {
		return writeFile(new ByteArrayInputStream(in), out);
	}

	// 拷贝文件
	public static final boolean CopyFile(File in, File out) throws Exception {
		return writeFile(new FileInputStream(in), out);
	}

	// 拷贝文件
	public static final boolean CopyFile(String infile, String outfile) throws Exception {
		try {
			File in = new File(infile);
			File out = new File(outfile);
			return CopyFile(in, out);
		} catch (IOException ie) {
			ie.printStackTrace();
			return false;
		}

	}

	// 写入文件
	public static final boolean writeFile(InputStream in, File out) throws Exception {
		try {
			FileOutputStream fos = new FileOutputStream(out);
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = in.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
			in.close();
			fos.close();
			return true;
		} catch (IOException ie) {
			ie.printStackTrace();
			return false;
		}
	}

	public static final boolean uploadFile(String path,String name,InputStream in) throws Exception {
		try {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(path + name));
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = in.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
			in.close();
			fos.close();
			return true;
		} catch (IOException ie) {
			ie.printStackTrace();
			return false;
		}
	}

	public static void deleteFile(File dir) {
		if (dir.exists()) {
			dir.delete();
		}
	}

	/**
	 * unicode解码
	 * @param str
	 * @return
	 */
	public static String unicodetoString(String str) {
		if (str!=null && !"".equals(str)) {
			Charset set = Charset.forName("UTF-16");
			Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
			Matcher m = p.matcher(str);
			int start = 0;
			int start2 = 0;
			StringBuffer sb = new StringBuffer();
			while (m.find(start)) {
				start2 = m.start();
				if (start2 > start) {
					String seg = str.substring(start, start2);
					sb.append(seg);
				}
				String code = m.group(1);
				int i = Integer.valueOf(code, 16);
				byte[] bb = new byte[4];
				bb[0] = (byte) ((i >> 8) & 0xFF);
				bb[1] = (byte) (i & 0xFF);
				ByteBuffer b = ByteBuffer.wrap(bb);
				sb.append(String.valueOf(set.decode(b)).trim());
				start = m.end();
			}
			start2 = str.length();
			if (start2 > start) {
				String seg = str.substring(start, start2);
				sb.append(seg);
			}
			return sb.toString();
		}
		return str;
	}

	public static void main(String[] args) {
		String a = unicodetoString("https://test-file.ycandyz.com/auth/202012/22/7003e5ac-444b-11eb-9c0e-e2ffaa4677cf/\\u767d\\u8272\\u84dd\\u7259\\u8033\\u673a.jpeg");
//		String a = unicodetoString("https://test-file.ycandyz.com/auth/202007/28/d5ca7bc6-d07e-11ea-8e86-e2a716bf74cc/迪奥商品详情.png");
		System.out.println(a);
	}
}
