/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.turns;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import pkg.foxoft.bombnshovel.net.Cmd;

/**
 * Logs players actions in a file. As a bonus feature, able to retrieve logs and
 * make Cmd arrays from them.
 */
public class Logger
{

	public double seed;

	String filename;
	PrintWriter out;
	BufferedReader in;
	File file;

	public Logger(String filename_arg, int mode)
	{
		filename = filename_arg;
		file = new File(filename);
		try
		{
			if (!file.exists())
			{
				file.createNewFile();
			}

			if (mode == 0)
			{
				out = new PrintWriter(file.getAbsoluteFile());
			}
			else
			{
				in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			}
		}
		catch (IOException e)
		{
			System.out.println("FILE ERROR!");
		}
	}

	public void write(double x)
	{
		String xstr = String.format("%.0f", x);
		try
		{
			out.println(xstr);
		}
		catch (Exception e)
		{
			System.out.println("FILE ERROR!");
		}
	}

	public void write(Cmd cmd)
	{
		try
		{
			out.println(cmd.get());          //Writing command itself.
			out.println(cmd.size());         //Writing amount of arguments.
			for (int i = 0; i < cmd.size(); i += 1) //Writing arguments.
			{
				out.println(cmd.get(i));
			}
		}
		catch (Exception e)
		{
			System.out.println("FILE ERROR!");
		}
	}

	public ArrayList<Cmd> read()
	{
		try
		{
			seed = Double.parseDouble(in.readLine().replaceAll(",", "."));
			System.out.println(seed);

			ArrayList<Cmd> commands = new ArrayList<>();

			while (true)
			{
				String cmdStr = in.readLine();
				if (cmdStr == null)
				{
					return commands;
				}

				double argsAm = Double.parseDouble(in.readLine());
				double[] args = new double[(int) argsAm];

				for (int i = 0; i < argsAm; i += 1)
				{
					args[i] = Double.parseDouble(in.readLine());
				}
				commands.add(new Cmd(cmdStr, args));
			}

		}
		catch (IOException e)
		{
			System.out.println("CORRUPTED LOG FILE!");
			return null;
		}
	}

	public void close()
	{
		if (out != null)
		{
			out.close();
		}
	}
}
