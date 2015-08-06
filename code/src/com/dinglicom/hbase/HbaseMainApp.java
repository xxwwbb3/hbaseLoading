package com.dinglicom.hbase;

import org.apache.log4j.Logger;

import com.dinglicom.Scheduling.Scheduling;

public class HbaseMainApp {
	private final static Logger LOG = Logger.getLogger(HbaseMainApp.class);

	public static void main(String[] args) {
		LOG.info("......   put CSV to HBASE   begin     ......");
		try {
			if (args.length != 1) {
				LOG.error("there is a parameter:[HbaseMainApp config_path]");
			} else {
				LOG.info("config path = [" + args[0] + "].");
			}

			Scheduling schedule = new Scheduling(args[0]);
			schedule.schedule();
		} catch (Exception e) {
			LOG.error("Have a error exception,  programming is terminated !", e);
			e.printStackTrace();
		}

		LOG.info("......   put CSV to HBASE   end!      ......");
	}
}
