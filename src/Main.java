import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author: StarsOne
 * @Description:
 * @Date: Create in  2019/1/28 0028 22:20
 */
class Main {

	private static Map<String, String> map;

	public static void main(String[] args) {
		System.out.println("注意：虽然本人已经做过了测试，以防万一，请使用前做好备份功能！！！");
		System.out.println("此工具仅限于Android Studio3.0.1版本，其他的版本谨慎使用（可能AS3.0以上可用）");
		map = new HashMap<>();
		System.out.println("请输入AS的根目录:");

		Scanner scanner = new Scanner(System.in);
		String pathOfAS = scanner.nextLine();
		optionChoose();
		String choose = scanner.nextLine();
		String[] chooses = choose.split(" ");
		for (int i = 0; i < chooses.length; i++) {
			getValue(chooses[i]);
		}
		for (int i = 0; i < chooses.length; i++) {
			try {
				optionChoose(chooses[i],pathOfAS);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void getValue(String s) {
		int i = Integer.valueOf(s);
		Scanner scanner = new Scanner(System.in);
		String temp;
		switch (i) {
			case 1:
				System.out.println("输入appcompat版本号");
				break;
			case 2:
				System.out.println("输入compileSdkVersion版本号");
				break;
			case 3:
				System.out.println("输入buildToolsVersion版本号");
				break;
			case 4:
				System.out.println("输入targetSdkVersion版本号");
				break;
		}
		temp = scanner.nextLine();
		map.put(s, temp);
	}
	public static void optionChoose(){

		System.out.println("选择需要修改的版本（多选，使用空格隔开）");
		System.out.println("1.appcompat版本号");
		System.out.println("2.compileSdkVersion版本号");
		System.out.println("3.buildToolsVersion版本号");
		System.out.println("4.targetSdkVersion版本号");

	}

	public static void optionChoose(String choose,String pathOfAS)  throws IOException {
		int i = Integer.valueOf(choose);
		switch (i) {
			case 1:
				AlterAppcompat(pathOfAS);
				break;
			case 2:
				AlterCSDK(pathOfAS);
				break;
			case 3:
				AlterBuildTool(pathOfAS);
				break;
			case 4:
				AlterTSDK(pathOfAS);
				break;

		}
	}

	private static void AlterBuildTool(String pathOfAS) throws IOException {
		File file = new File(pathOfAS, "\\plugins\\android\\lib\\templates\\gradle-projects\\NewAndroidModule\\root\\shared_macros.ftl");
		File temp = new File(file.getParent(),"\\"+file.getName()+"b");
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "GBK");
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(temp), "GBK");
		BufferedWriter writer = new BufferedWriter(outputStreamWriter);
		//利用之前的File对象，创建了一个`FileInputStream`对象，之后作为InputStreamReader的构造方法参数传入
		//第二个参数是编码，一般使用GBK或者是UTF-8
		BufferedReader reader = new BufferedReader(inputStreamReader);
		//把InputStreamReader作为参数构造一个BufferedReader
		String s;

		while((s=reader.readLine())!=null){


			if (s.contains("buildToolsVersion")) {
				StringBuffer temp1 = new StringBuffer(s);
				int start = temp1.indexOf(">");
				int end = temp1.lastIndexOf("<");
				temp1.replace(start+1,end,"buildToolsVersion "+map.get("3"));
				writer.write(temp1.toString());
				writer.newLine();

			} else {
				writer.write(s);
				writer.newLine();
			}
		}
		writer.close();
		reader.close();
		String oldPath = file.getPath();
		file.delete();
		temp.renameTo(new File(oldPath));
	}

	private static void AlterTSDK(String pathOfAS) throws IOException{
		File file = new File(pathOfAS, "\\plugins\\android\\lib\\templates\\gradle-projects\\NewAndroidModule\\root\\shared_macros.ftl");
		File temp = new File(file.getParent(),"\\"+file.getName()+"b");
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "GBK");
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(temp), "GBK");
		BufferedWriter writer = new BufferedWriter(outputStreamWriter);
		//利用之前的File对象，创建了一个`FileInputStream`对象，之后作为InputStreamReader的构造方法参数传入
		//第二个参数是编码，一般使用GBK或者是UTF-8
		BufferedReader reader = new BufferedReader(inputStreamReader);
		//把InputStreamReader作为参数构造一个BufferedReader
		String s;

		while((s=reader.readLine())!=null){
			if (s.contains("targetSdkVersion")) {
				writer.write("targetSdkVersion "+map.get("4"));
				writer.newLine();

			} else {
				writer.write(s);
				writer.newLine();
			}
		}
		writer.close();
		reader.close();
		String oldPath = file.getPath();
		file.delete();
		temp.renameTo(new File(oldPath));
	}

	private static void AlterCSDK(String pathOfAS) throws IOException{
		File file = new File(pathOfAS, "\\plugins\\android\\lib\\templates\\gradle-projects\\NewAndroidModule\\root\\shared_macros.ftl");
		File temp = new File(file.getParent(),"\\"+file.getName()+"b");
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "GBK");
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(temp), "GBK");
		BufferedWriter writer = new BufferedWriter(outputStreamWriter);
		//利用之前的File对象，创建了一个`FileInputStream`对象，之后作为InputStreamReader的构造方法参数传入
		//第二个参数是编码，一般使用GBK或者是UTF-8
		BufferedReader reader = new BufferedReader(inputStreamReader);
		//把InputStreamReader作为参数构造一个BufferedReader
		String s;

		while((s=reader.readLine())!=null){

			if (s.contains("compileSdkVersion")) {

				writer.write("    compileSdkVersion "+map.get("2"));
				writer.newLine();
				writer.write("    configurations.all {\n" +
						"        resolutionStrategy.force 'com.android.support:support-annotations:'"+map.get("1")+"\n" +
						"    }");
				writer.newLine();
			} else {
				writer.write(s);
				writer.newLine();
			}
		}
		writer.close();
		reader.close();
		String oldPath = file.getPath();
		file.delete();
		temp.renameTo(new File(oldPath));
	}

	private static void AlterAppcompat(String pathOfAS) throws IOException {

		File file = new File(pathOfAS, "\\plugins\\android\\lib\\templates\\gradle-projects\\NewAndroidModule\\recipe.xml.ftl");
		File temp = new File(file.getParent(),"\\"+file.getName()+"b");
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "GBK");
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(temp), "GBK");
		BufferedWriter writer = new BufferedWriter(outputStreamWriter);
		//利用之前的File对象，创建了一个`FileInputStream`对象，之后作为InputStreamReader的构造方法参数传入
		//第二个参数是编码，一般使用GBK或者是UTF-8
		BufferedReader reader = new BufferedReader(inputStreamReader);
		//把InputStreamReader作为参数构造一个BufferedReader
		String s;

		while((s=reader.readLine())!=null){

			if (s.equals("<#if backwardsCompatibility!true>")) {
				writer.write(s);
				writer.newLine();
				s = reader.readLine();
				writer.write("<dependency mavenUrl=\"com.android.support:appcompat-v7:"+map.get("1")+"\" />");
				writer.newLine();

			} else {
				writer.write(s);
				writer.newLine();
			}
		}
		writer.close();
		reader.close();
		String oldPath = file.getPath();
		file.delete();
		temp.renameTo(new File(oldPath));
	}
}
