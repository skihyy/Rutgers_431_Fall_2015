/**
 * 
 */
package com.pikachu.cs431.server;

import java.util.List;

import com.pikachu.cs431.service.Service;
import com.pikachu.cs431.tool.DBHandler;
import com.pikachu.cs431.vo.IPAddress;

/**
 * This is the main function of a single service.
 * 
 * @author Yuyang He
 * @date 1:07:00 AM, Oct 3, 2015
 * @version 1.0
 * @since
 */
public class MainFuction
{
	/**
	 * Main function.
	 * 
	 * @param args
	 *            1st parameter is the port number.
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args)
	{
		// in order to make sure every actor is activated
		// has to sleep 10 sec before any process is initialized
		System.out.println("Sleeping 10 sec. Current time is " + System.currentTimeMillis());
		try
		{
			Thread.currentThread().sleep(10000);
		}
		catch (InterruptedException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Sleeping Complete. Current time is " + System.currentTimeMillis());
		
		if (1 != args.length)
		{
			System.out.println("No port number is entered. Program will quit.");
			return;
		}

		Integer port = null;
		try
		{
			port = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Invalid port number. Program will quit.");
			return;
		}

		// reading from local file to get all ips and ports
		List<IPAddress> ipList = DBHandler.readSortedIPs();

		if (0 == ipList.size())
		{
			System.out.println("Error for reading DB file. Quitting.");
			return;
		}

		// service for selecting leaders
		Service service = new Service(ipList, port);

		// 1 actor doesn't need to start
		if (1 == service.getTotalNodes())
		{
			System.out.println("The leader ID: " + service.getCurrentWinner());
			return;
		}

		// server starts
		service.startService(port);
	}
}
