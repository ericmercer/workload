/**
Written by Michael Sharp
This program takes in the text files from the directory with the interpreter.java file and converts it to graph format.
It also makes a bat file (for windows) to change the graphs to be more interactive


*/



package model.scaffold.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;

import model.scaffold.Interpreter;

public class GraphParser 
{
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	
	public static void main(String[] args) throws IOException, URISyntaxException 
	{
		//gets the directory of the Interpreter.java
		File f = getJTRFolder();
		
		//gets the info and sets up the printwriter for the bat file
		PrintWriter for_bat = new PrintWriter("src\\model\\scaffold\\graph\\graph.bat","ASCII");
		
		File temp_bat = new File(new File(Interpreter.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent());
		for_bat.println("cd "+ temp_bat.toString()+"\\src\\model\\scaffold\\graph\\");
		
		//for all the files in there, it parses and outputs the graph file
		for(File file : f.listFiles())
		{
			
			int i = file.getName().lastIndexOf('.');
			if(i < 0|| !file.getName().substring(i+1).equals("txt") || file.length() == 0)
				continue;
			String name = (String) file.getName().subSequence(0, file.getName().indexOf('.'));
			PrintWriter writer = new PrintWriter("src\\model\\scaffold\\graph\\"+name+".gv","ASCII");
			
			writer.println("digraph " + name + '{');
			writer.println("rankdir = LR;");
			writer.println("edge [weight = 1.0 penwidth=1.5];");
			writer.println("node [nodesep = 20.0];");
			
			
			
			parseFile(file, writer, for_bat, name);
		}
		for_bat.close();
	}
	
	
	/**
	 * parses the file into the .gv format
	 * @param file
	 * @param writer
	 * @param for_bat
	 * @param name
	 * @throws IOException
	 */
	private static void parseFile(File file, PrintWriter writer, PrintWriter for_bat, String name) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		String node;
		String to_what;
		String tool_tip;
		while((line = br.readLine())!=null)
		{
			if(line.length() > 0 && line.substring(0, 1).equals("("))
			{
				//gets the first node
				node = line.substring(1, line.indexOf(','));
				line = line.substring(1);
				
				//gets the channel and memory info for the tooltip
				line = line.substring(line.indexOf('[')+1);
				tool_tip = "tooltip = \"\\E Channel info: ";
				tool_tip += line.substring(0,line.indexOf(']'));
				tool_tip +="	Memory: ";
				
				line = line.substring(line.indexOf('[')+1);
				tool_tip += line.substring(0,line.indexOf(']'));
				
				//gets what the node is pointing to
				line = line.substring(line.indexOf('(')+1);
				to_what = line.substring(0,line.indexOf(','));
				
				//gets the output of the transition
				tool_tip+="		Output: ";
				tool_tip += line.substring(line.indexOf('[')+1,line.indexOf(']'));
				tool_tip +="\"";
				
				writer.println(node + " -> " + to_what + "["+ tool_tip + "];");
				
			}
		}
		br.close();
		writer.print('}');
		for_bat.println("dot -Tsvg -o " + name+".svg " + name+".gv");
		for_bat.println("del "+name+".gv");
		writer.close();
	}
	
	/**
	 * gets the location of the Interpreter.java file
	 * @return file
	 */
	private static File getJTRFolder() {
		File f = null;
		try {
			f = new File(new File(Interpreter.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(File file : f.listFiles()){
			if(file.getName().equals("src"))
				f = file;
		}
		for(File file : f.listFiles()){
			if(file.getName().equals("model"))
				f = file;
		}
		for(File file : f.listFiles()){
			if(file.getName().equals("scaffold"))
				f = file;
		}
		return f;
	}
}